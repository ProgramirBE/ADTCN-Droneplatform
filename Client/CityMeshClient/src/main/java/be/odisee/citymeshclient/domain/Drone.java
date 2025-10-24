package be.odisee.citymeshclient.domain;

public class Drone {
    public enum DroneStatus {
        INACTIVE,
        INFLIGHT,
        CHARGING,
        RESERVED,
        DEFECT,
        MAINTENANCE
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

    private int droneID;
    private DroneStatus status;
    private String locatie;
    private DroneType type;
    private int batterij;

    public Drone() {}

    public Drone(DroneStatus status, String locatie, DroneType type, int batterij) {
        this.status = status;
        this.locatie = locatie;
        this.type = type;
        this.batterij = batterij;
    }

    public int getDroneID() { return droneID; }
    public void setDroneID(int droneID) { this.droneID = droneID; }

    public DroneStatus getStatus() { return status; }
    public void setStatus(DroneStatus status) { this.status = status; }

    public String getLocatie() { return locatie; }
    public void setLocatie(String locatie) { this.locatie = locatie; }

    public DroneType getType() { return type; }
    public void setType(DroneType type) { this.type = type; }

    public int getBatterij() { return batterij; }
    public void setBatterij(int batterij) { this.batterij = batterij; }

    @Override
    public String toString() {
        return "Drone{" +
                "droneID=" + droneID +
                ", status=" + status +
                ", locatie='" + locatie + '\'' +
                ", type='" + type + '\'' +
                ", batterij=" + batterij +
                '}';
    }
}
