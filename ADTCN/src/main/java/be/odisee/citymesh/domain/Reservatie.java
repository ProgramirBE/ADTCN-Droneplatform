package be.odisee.citymesh.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity-klasse die een reservatie voorstelt van een drone door een gebruiker.
 * <p>
 * Elke reservatie bevat informatie over de drone, locatie, gekozen batterijstrategie,
 * status, reservatiedatum en optioneel het type drone.
 * </p>
 */
@Entity
@Table(name = "reservaties")
public class Reservatie {

    /**
     * De unieke ID van de reservatie (primaire sleutel).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * ID van de gereserveerde drone (foreign key).
     */
    @Column(name = "drone_id")
    private int droneId;

    /**
     * Locatie waarvoor de drone gereserveerd is.
     */
    @Column(name = "locatie")
    private String locatie;

    /**
     * Naam van de gekozen batterijstrategie (bv. Eco of Turbo).
     */
    @Column(name = "consumption_strategy")
    private String consumptionStrategy;

    /**
     * Status van de reservatie (bv. GERESERVEERD, ACTIEF...).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReservatieStatus status;

    /**
     * Datum waarop de reservatie werd geplaatst.
     */
    @Column(name = "reservatie_datum")
    private Date reservatieDatum;

    /**
     * Type van de drone
     */
    private Drone.DroneType droneType;

    /**
     * Lege constructor vereist door JPA.
     */
    public Reservatie() {}

    /**
     * Constructor voor het aanmaken van een nieuwe reservatie.
     *
     * @param droneId             de ID van de gereserveerde drone
     * @param locatie             de locatie van de reservatie
     * @param consumptionStrategy de gekozen strategie voor batterijverbruik
     * @param status              de status van de reservatie
     * @param reservatieDatum     de datum waarop de reservatie werd geplaatst
     */
    public Reservatie(int droneId, String locatie, String consumptionStrategy, ReservatieStatus status, Date reservatieDatum) {
        this.droneId = droneId;
        this.locatie = locatie;
        this.consumptionStrategy = consumptionStrategy;
        this.status = status;
        this.reservatieDatum = reservatieDatum;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDroneId() { return droneId; }
    public void setDroneId(int droneId) { this.droneId = droneId; }

    public String getLocatie() { return locatie; }
    public void setLocatie(String locatie) { this.locatie = locatie; }

    public String getConsumptionStrategy() { return consumptionStrategy; }
    public void setConsumptionStrategy(String consumptionStrategy) { this.consumptionStrategy = consumptionStrategy; }

    public ReservatieStatus getStatus() { return status; }
    public void setStatus(ReservatieStatus status) { this.status = status; }

    public Date getReservatieDatum() { return reservatieDatum; }
    public void setReservatieDatum(Date reservatieDatum) { this.reservatieDatum = reservatieDatum; }

    public Drone.DroneType getDroneType() { return droneType; }
    public void setDroneType(Drone.DroneType droneType) { this.droneType = droneType; }

    /**
     * Enum die de mogelijke statussen van een reservatie voorstelt.
     */
    public enum ReservatieStatus {
        /** De reservatie is geplaatst, maar nog niet geactiveerd. */
        GERESERVEERD,
        /** De reservatie is actief en in gebruik. */
        ACTIEF,
        /** De reservatie is correct afgerond. */
        VOLTOOID,
        /** De reservatie werd geannuleerd. */
        GEANNULEERD
    }
}
