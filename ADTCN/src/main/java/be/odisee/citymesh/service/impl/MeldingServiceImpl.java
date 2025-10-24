package be.odisee.citymesh.service.impl;

import be.odisee.citymesh.dao.MeldingRepository;
import be.odisee.citymesh.domain.Melding;
import be.odisee.citymesh.domain.Verslag;
import be.odisee.citymesh.service.MeldingService;
import be.odisee.citymesh.service.Observer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implementatie van {@link MeldingService} die ook fungeert als {@link Observer} binnen het observer-patroon.
 * <p>
 * Deze service is verantwoordelijk voor:
 * <ul>
 *   <li>Het ontvangen en opslaan van meldingen (via observer-updates)</li>
 *   <li>Het genereren van meldingen op basis van drukte (overcrowding)</li>
 *   <li>Het verwerken van meldingen tot een tekstueel {@link Verslag}</li>
 * </ul>
 */
@Service
@Transactional
public class MeldingServiceImpl implements MeldingService, Observer {

    @Autowired
    private MeldingRepository meldingRepository;

    /**
     * Reactie op notificaties vanuit het observer-patroon.
     * Maakt een informatieve melding aan en slaat deze op in de database.
     *
     * @param bericht de boodschap die van het subject komt (bv. "battery_low", "maintenance_required", ...)
     */
    @Override
    public void update(String bericht) {
        System.out.println("\n========[OBSERVER]===========");
        System.out.println("MeldingService kreeg bericht: " + bericht + "\n");

        Melding melding = new Melding(1, bericht, new Date(), Melding.MeldingStatus.INFO);
        meldingRepository.save(melding);
    }

    /**
     * Genereert meldingen voor elke locatie in een matrix waar het aantal mensen hoger is dan de limiet.
     * Dit simuleert crowd detection op basis van een dronesimulatie.
     *
     * @param matrix de gesimuleerde crowd density matrix
     * @param limit  de limiet waarna een locatie als overbevolkt beschouwd wordt
     * @return een lijst van gegenereerde {@link Melding} objecten
     */
    public List<Melding> generateOvercrowdingReports(int[][] matrix, int limit) {
        List<Melding> meldingen = new ArrayList<>();
        Date now = new Date();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] > limit) {
                    String beschrijving = "Te veel mensen op locatie (" + i + ", " + j + "): " + matrix[i][j] + " personen.";
                    Melding melding = new Melding(1, beschrijving, now, Melding.MeldingStatus.OVERCROWDING);
                    meldingRepository.save(melding);
                    meldingen.add(melding);
                }
            }
        }
        return meldingen;
    }

//    @Override
//    public Verslag verwerkMeldingen(List<Melding> meldingen) {
//        Verslag verslag = new Verslag();
//        for (Melding melding : meldingen) {
//            verslag.setInhoud(verslag.getInhoud() + " " + melding.getBeschrijving());
//        }
//        return verslag;
//    }
}
