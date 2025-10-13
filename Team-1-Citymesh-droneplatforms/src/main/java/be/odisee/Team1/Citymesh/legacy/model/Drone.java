package be.odisee.Team1.Citymesh.legacy.model;

public class Drone {

    public enum DroneStatus {
        INACTIVE,
        INFLIGHT,
        CHARGING,
        RESERVED,
        DEFECT
    }

    private int droneID;
    private DroneStatus status;
    private String locatie;
    private String type;

    public int getDroneID() {
        return droneID;
    }

    public void setDroneID(int droneID) {
        this.droneID = droneID;
    }

    public DroneStatus getStatus() {
        return status;
    }

    public void setStatus(DroneStatus status) {
        this.status = status;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
