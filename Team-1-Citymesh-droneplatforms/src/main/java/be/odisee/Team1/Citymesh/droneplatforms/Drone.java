package be.odisee.Team1.Citymesh.droneplatforms;

public class Drone {
    private String id;
    private String type;
    private DroneStatus status;

    public Drone(String id, String type) {
        this.id = id;
        this.type = type;
        this.status = DroneStatus.AVAILABLE;
    }


    public String getId() { return id; }
    public String getType() { return type; }
    public DroneStatus getStatus() { return status; }
    public void setStatus(DroneStatus status) { this.status = status; }
}

