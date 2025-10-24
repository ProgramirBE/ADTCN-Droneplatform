package be.odisee.citymesh.service.impl;

import be.odisee.citymesh.dao.DroneRepository;
import be.odisee.citymesh.domain.Drone;
import be.odisee.citymesh.domain.Melding;
import be.odisee.citymesh.service.BatteryConsumptionStrategy;
import be.odisee.citymesh.service.DroneService;
import be.odisee.citymesh.service.Observer;
import be.odisee.citymesh.service.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementatie van de {@link DroneService} die het beheer van drones verzorgt,
 * inclusief CRUD-operaties, batterijbeheer, simulaties en meldingen.
 * <p>
 * Deze klasse maakt gebruik van het observer-patroon via de {@link Subject}-interface
 * om meldingen te verzenden bij bepaalde dronegebeurtenissen (zoals opladen of defect).
 * </p>
 * <p>
 * Bevat ook logica voor batterijverbruik via {@link BatteryConsumptionStrategy} en
 * het genereren van matrixgegevens voor simulaties.
 * </p>
 */
@Service
@Transactional
public class DroneServiceImpl implements DroneService, Subject {

    @Autowired
    private DroneRepository droneRepository;

    private List<Observer> observers = new ArrayList<>();

    @Autowired
    private MeldingServiceImpl meldingService;

    @Autowired
    @Qualifier("ecoModeStrategy")
    private BatteryConsumptionStrategy defaultStrategy;

    // Observer-patroon
    @Override
    public void addObserver(Observer o) { observers.add(o); }

    @Override
    public void removeObserver(Observer o) { observers.remove(o); }

    @Override
    public void notifyObservers(String bericht) {
        for (Observer o : observers) {
            o.update(bericht);
        }
    }

    /**
     * Verwijdert alle drones in één batchoperatie.
     */
    public void deleteAll() {
        droneRepository.deleteAllInBatch();
    }

    @Override
    public Drone saveDrone(Drone drone) {
        return droneRepository.save(drone);
    }

    @Override
    public List<Drone> getAllDrones() {
        return droneRepository.findAll();
    }

    @Override
    public Drone getDroneById(int id) {
        return droneRepository.findById(id);
    }

    @Override
    public void updateDrone(Drone drone) {
        droneRepository.save(drone);
    }

    @Override
    public void deleteDrone(int id) {
        droneRepository.deleteById(id);
    }

    @Override
    public void startCharging(int droneId) {
        Drone drone = droneRepository.findById(droneId);
        drone.setStatus(Drone.DroneStatus.CHARGING);
        droneRepository.save(drone);
        notifyObservers("Drone " + droneId + " is gestart met opladen.");
    }

    @Override
    public void chargeBatterij(int droneId) {
        Drone drone = droneRepository.findById(droneId);
        int batteryLevel = drone.getBatterij();

        while (batteryLevel < 100) {
            batteryLevel++;
            drone.setBatterij(batteryLevel);
            droneRepository.save(drone);
        }

        if (drone.getBatterij() == 100) {
            drone.setStatus(Drone.DroneStatus.INACTIVE);
            droneRepository.save(drone);
            notifyObservers("Drone " + droneId + " is volledig opgeladen.");
        }
    }

    @Override
    public void startDroneVlucht(Drone drone) {
        if (drone.getStatus() == Drone.DroneStatus.RESERVED) {
            drone.setStatus(Drone.DroneStatus.INFLIGHT);
        }
    }

    @Override
    public void reserveerDrone(Drone drone) {
        if (drone.getStatus() == Drone.DroneStatus.INACTIVE || drone.getStatus() == Drone.DroneStatus.DEFECT) {
            drone.setStatus(Drone.DroneStatus.RESERVED);
        }
    }

    @Override
    public void resetDroneStatus(Drone drone) {
        drone.resetStatus();
    }

    @Override
    public String verwerkDroneLocatie(Drone drone) {
        return drone.getStatus() == Drone.DroneStatus.INFLIGHT ? drone.getLocatie() : null;
    }

    @Override
    public void startReparatie(Drone drone, Melding melding) {
        if (melding.getStatus() == Melding.MeldingStatus.BATTERY_LOW) {
            startCharging(drone.getDroneID());
        } else {
            drone.setStatus(Drone.DroneStatus.MAINTENANCE);
            droneRepository.save(drone);
            notifyObservers("Drone " + drone.getDroneID() + " is in onderhoud.");
        }
    }

    /**
     * Laat een drone vliegen met een specifieke batterijstrategie.
     *
     * @param drone    de drone die vliegt
     * @param distance de gevlogen afstand
     * @param strategy de strategie die bepaalt hoeveel batterij verbruikt wordt
     */
    public void flyDrone(Drone drone, int distance, BatteryConsumptionStrategy strategy) {
        if (drone.getStatus() != Drone.DroneStatus.INFLIGHT) {
            startDroneVlucht(drone);
        }

        int consumption = strategy.calculateConsumption(drone, distance);
        int newBattery = drone.getBatterij() - consumption;
        drone.setBatterij(Math.max(newBattery, 0));
        droneRepository.save(drone);

        System.out.printf("Drone %d: %s, batterij: %d%%%n", drone.getDroneID(), drone.getType(), drone.getBatterij());
        checkBatterij(drone);
    }

    /**
     * Laat een drone vliegen met de standaardstrategie (eco).
     */
    public void flyDrone(Drone drone, int distance) {
        flyDrone(drone, distance, defaultStrategy);
    }

    /**
     * Controleert de batterijstatus van een drone en stuurt indien nodig meldingen.
     */
    private void checkBatterij(Drone drone) {
        if (drone.getBatterij() < 20) {
            notifyObservers("battery_low");
        }
        if (drone.getBatterij() < 10) {
            notifyObservers("maintenance_required");
        }
    }

    @Override
    public int[][] genereerMatrix(int size) {
        int[][] matrix = new int[size][size];
        Random random = new Random();
        int centre = size / 2;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double distance = Math.sqrt(Math.pow(i - centre, 2) + Math.pow(j - centre, 2));
                int baseValue = (int) ((size - distance) * 10) + random.nextInt(10);
                matrix[i][j] = Math.max(baseValue, 0);
            }
        }
        return matrix;
    }

    /**
     * Configuratiepunt voor automatische registratie van observers (zoals de MeldingService).
     *
     * @param meldingService de observer die moet worden toegevoegd
     */
    @Autowired
    public void configureObservers(MeldingServiceImpl meldingService) {
        addObserver(meldingService);
    }
}

