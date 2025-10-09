package be.odisee.Team1.Citymesh.droneplatforms;

import java.time.Instant;

public class Reservation {
    private String id;
    private String startPlaceId;
    private String pilotId;
    private Instant createdAt;
    private long expiresAfterMinutes;

    public Reservation(String id, String startPlaceId, String pilotId, long expiresAfterMinutes) {
        this.id = id;
        this.startPlaceId = startPlaceId;
        this.pilotId = pilotId;
        this.createdAt = Instant.now();
        this.expiresAfterMinutes = expiresAfterMinutes;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(createdAt.plusSeconds(expiresAfterMinutes * 60));
    }

    public String getStartPlaceId(){ return startPlaceId; }
    public String getPilotId(){ return pilotId; }
}
