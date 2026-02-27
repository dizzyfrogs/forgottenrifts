package me.dizzyfrogs.forgottenrifts.listener;

import me.dizzyfrogs.forgottenrifts.ForgottenRifts;
import me.dizzyfrogs.forgottenrifts.model.Rift;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import java.util.UUID;

public class RiftCreationListener implements Listener {
    private final ForgottenRifts plugin;

    public RiftCreationListener(ForgottenRifts plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntityType() != EntityType.SPECTRAL_ARROW) return;
        Block hitBlock = event.getHitBlock();
        BlockFace face = event.getHitBlockFace();

        if (hitBlock == null || face == null || !isStainedGlass(hitBlock.getType())) return;

        // get relative vectors
        Vector right = getRightVector(face);
        Vector down = new Vector(0, -1, 0);

        // find top left corner
        Block topLeft = findTopLeft(hitBlock, face, right);
        if (topLeft == null) return;

        // scan for the frequency
        StringBuilder freqBuilder = new StringBuilder();
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 2; x++) {
                Block curr = topLeft.getRelative((int)(right.getX() * x), -y, (int) right.getZ() * x);
                if (!isStainedGlass(curr.getType())) return;
                freqBuilder.append(curr.getType().name());
            }
        }

        String frequency = freqBuilder.toString();
        Rift rift = new Rift(UUID.randomUUID(), topLeft.getLocation(), frequency, face);
        plugin.getRiftManager().registerRift(rift, right);
    }

    private Vector getRightVector(BlockFace face) {
        return switch (face) {
            case NORTH -> new Vector(-1, 0, 0);
            case SOUTH -> new Vector(1, 0, 0);
            case EAST -> new Vector(0, 0, -1);
            case WEST -> new Vector(0, 0, 1);
            default ->new Vector(0, 0, 0);
        };
    }

    private Block findTopLeft(Block hit, BlockFace face, Vector right) {
        Block curr = hit;

        while (isStainedGlass(curr.getType())) {
            curr = curr.getRelative(BlockFace.UP);
        }
        // step back down one to top glass then move left until we hit the frame
        curr = curr.getRelative(BlockFace.DOWN);
        Vector left = right.clone().multiply(-1);

        while (isStainedGlass(curr.getType())) {
            curr = curr.getRelative((int)left.getX(), 0, (int)left.getZ());
        }
        // step back right for the left-most glass
        curr = curr.getRelative((int)right.getX(), 0, (int)right.getZ());

        return curr;
    }
    private boolean isStainedGlass(Material material) {
        return material.name().contains("STAINED_GLASS");
    }
}
