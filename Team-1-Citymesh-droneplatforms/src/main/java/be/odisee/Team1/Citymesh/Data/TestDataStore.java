package be.odisee.Team1.Citymesh.Data;

import be.odisee.Team1.Citymesh.droneplatforms.StartPlace;
import java.util.HashMap;
import java.util.Map;

public class TestDataStore {
    private static final TestDataStore INSTANCE = new TestDataStore();
    private final Map<String, StartPlace> startPlaces = new HashMap<>();

    public static TestDataStore get() { return INSTANCE; }

    public void clearAll() { startPlaces.clear(); }

    public void addStartPlace(StartPlace sp) {
        startPlaces.put(sp.getId(), sp);
    }

    public StartPlace getStartPlace(String id) {
        return startPlaces.get(id);
    }

    // Business rule: only reserve if available and >=50m
    public boolean reserve(String id) {
        StartPlace sp = startPlaces.get(id);
        if (sp == null) return false;
        if (sp.getStatus() != StartPlace.Status.AVAILABLE) return false;
        if (sp.getDistanceMeters() < 50) return false;
        sp.setStatus(StartPlace.Status.RESERVED);
        return true;
    }
}
