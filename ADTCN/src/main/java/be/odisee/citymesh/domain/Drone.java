package be.odisee.citymesh.domain;

import javax.persistence.*;

@Entity
@Table(name = "drones")
public class Drone {
    // Inclure toutes les constantes utilisées par l'ancien code pour éviter les erreurs de compilation
    public enum DroneStatus {
        INACTIVE,
        INFLIGHT,
        CHARGING,
        RESERVED,
        DEFECT,
        MAINTENANCE,
        Vliegklaar,
        In_Onderhoud,
        In_Gebruik,
        Wacht_op_Verzending
    }

    public enum DroneType {
        QUADCOPTER,
        HEXACOPTER,
        OCTOCOPTER,
        FIXED_WING,
        HYBRID_VTOL,
        SINGLE_ROTOR_HELICOPTER,
        NANO_DRONE,
        MINI_DRONE,
        RACING_DRONE,
        CAMERA_DRONE,
        SURVEYING_DRONE,
        AGRICULTURAL_DRONE,
        TOY_DRONE,
        UNDERWATER_DRONE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "model")
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DroneStatus status;

    @Column(name = "battery_level")
    private Integer batteryLevel;

    // legacy fields are represented via methods delegating to the persisted fields

    public Drone() {}

    public Drone(DroneStatus status, String name, DroneType type, Integer batteryLevel) {
        this.status = status;
        this.name = name;
        this.model = (type != null) ? type.name() : null; // store type name in model if provided
        this.batteryLevel = batteryLevel;
    }

    // New-style getters/setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public DroneStatus getStatus() { return status; }
    public void setStatus(DroneStatus status) { this.status = status; }

    public Integer getBatteryLevel() { return batteryLevel; }
    public void setBatteryLevel(Integer batteryLevel) { this.batteryLevel = batteryLevel; }

    // Legacy compatibility methods expected by existing service/controller code

    // getDroneID / setDroneID
    public int getDroneID() { return (id != null) ? id.intValue() : 0; }
    public void setDroneID(int droneID) { this.id = droneID; }

    // getLocatie / setLocatie : map to name (best effort)
    public String getLocatie() { return this.name; }
    public void setLocatie(String locatie) { this.name = locatie; }

    // getBatterij / setBatterij : map to batteryLevel
    public int getBatterij() { return (this.batteryLevel != null) ? this.batteryLevel.intValue() : 0; }
    public void setBatterij(int batterij) { this.batteryLevel = batterij; }

    // getType / setType : map DroneType to model string
    public DroneType getType() {
        if (this.model == null) return null;
        try {
            return DroneType.valueOf(this.model);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
    public void setType(DroneType type) {
        this.model = (type != null) ? type.name() : null;
    }

    // resetStatus : restore to INACTIVE
    public void resetStatus() {
        this.status = DroneStatus.INACTIVE;
    }
}
