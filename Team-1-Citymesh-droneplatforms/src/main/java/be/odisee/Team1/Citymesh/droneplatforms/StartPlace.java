package be.odisee.Team1.Citymesh.droneplatforms;

public class StartPlace {
    private String id;
    private double lat;
    private double lon;
    private StartPlaceStatus status;
    private int distanceFromNoFlyZoneMeters;

    public StartPlace(String id, double lat, double lon, int distanceFromNoFlyZoneMeters) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.status = StartPlaceStatus.AVAILABLE;
        this.distanceFromNoFlyZoneMeters = distanceFromNoFlyZoneMeters;
    }

    public String getId() { return id; }
    public StartPlaceStatus getStatus() { return status; }
    public void setStatus(StartPlaceStatus status) { this.status = status; }
    public int getDistanceFromNoFlyZoneMeters() { return distanceFromNoFlyZoneMeters; }
}
