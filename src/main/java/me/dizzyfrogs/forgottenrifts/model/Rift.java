package me.dizzyfrogs.forgottenrifts.model;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

import java.util.UUID;

public class Rift {
    private final UUID id;
    private final String worldName;
    private final Vector position;
    private final String frequency;
    private final BlockFace facing;
    private final Vector rightVector;

    public Rift(UUID id, Location location, String frequency, BlockFace facing, Vector rightVector) {
        this.id = id;
        this.worldName = location.getWorld().getName();
        this.position = location.toVector();
        this.frequency = frequency;
        this.facing = facing;
        this.rightVector = rightVector;
    }

    public UUID getId() { return id; }
    public Location getLocation() {
        World world = Bukkit.getWorld(worldName);
        return position.toLocation(world);
    }
    public String getFrequency() { return frequency; }
    public BlockFace getFacing() { return facing; }
    public Vector getRightVector() { return rightVector; }
}
