package me.dizzyfrogs.forgottenrifts.model;

import org.bukkit.Location;

import java.util.UUID;

public class Rift {
    private final UUID id;
    private final Location location;
    private final String frequency;
    private final String orientation;

    public Rift(UUID id, Location location, String frequency, String orientation) {
        this.id = id;
        this.location = location;
        this.frequency = frequency;
        this.orientation = orientation;
    }

    public UUID getId() { return id; }
    public Location getLocation() { return location; }
    public String getFrequency() { return frequency; }
    public String getOrientation() { return orientation; }
}
