package me.dizzyfrogs.forgottenrifts.model;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import java.util.UUID;

public class Rift {
    private final UUID id;
    private final Location location;
    private final String frequency;
    private final BlockFace facing;

    public Rift(UUID id, Location location, String frequency, BlockFace facing) {
        this.id = id;
        this.location = location;
        this.frequency = frequency;
        this.facing = facing;
    }

    public UUID getId() { return id; }
    public Location getLocation() { return location; }
    public String getFrequency() { return frequency; }
    public BlockFace getFacing() { return facing; }
}
