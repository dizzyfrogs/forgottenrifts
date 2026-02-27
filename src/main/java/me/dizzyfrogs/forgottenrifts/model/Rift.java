package me.dizzyfrogs.forgottenrifts.model;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

import java.util.UUID;

public class Rift {
    private final UUID id;
    private final Location location;
    private final String frequency;
    private final BlockFace facing;
    private final Vector rightVector;

    public Rift(UUID id, Location location, String frequency, BlockFace facing, Vector rightVector) {
        this.id = id;
        this.location = location;
        this.frequency = frequency;
        this.facing = facing;
        this.rightVector = rightVector;
    }

    public UUID getId() { return id; }
    public Location getLocation() { return location; }
    public String getFrequency() { return frequency; }
    public BlockFace getFacing() { return facing; }
    public Vector getRightVector() { return rightVector; }
}
