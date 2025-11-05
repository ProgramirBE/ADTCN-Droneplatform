package be.odisee.citymesh.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "drones")
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 150)
    private String model;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String status = "Vliegklaar";

    @Column(name = "battery_level")
    private Short batteryLevel = 100;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Drone() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Short getBatteryLevel() { return batteryLevel; }
    public void setBatteryLevel(Short batteryLevel) { this.batteryLevel = batteryLevel; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

