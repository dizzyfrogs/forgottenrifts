package me.dizzyfrogs.forgottenrifts.core;

import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public class VectorHelper {
    public static Vector getRightVector(BlockFace face) {
        return switch (face) {
            case NORTH -> new Vector(-1, 0, 0);
            case SOUTH -> new Vector(1, 0, 0);
            case EAST -> new Vector(0, 0, -1);
            case WEST -> new Vector(0, 0, 1);
            default ->new Vector(0, 0, 0);
        };
    }
}
