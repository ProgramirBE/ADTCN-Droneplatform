package be.odisee.Team1.Citymesh.droneplatforms;

public class StartPlace {
    private final String id;
    private final int distanceMeters;
    private Status status;

    public enum Status { AVAILABLE, RESERVED }

    public StartPlace(String id, int distanceMeters) {
        this.id = id;
        this.distanceMeters = distanceMeters;
        this.status = Status.AVAILABLE;
    }

    public String getId() { return id; }
    public int getDistanceMeters() { return distanceMeters; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}

