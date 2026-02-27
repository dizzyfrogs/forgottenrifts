package me.dizzyfrogs.forgottenrifts.core;

import me.dizzyfrogs.forgottenrifts.ForgottenRifts;
import me.dizzyfrogs.forgottenrifts.model.Rift;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import static me.dizzyfrogs.forgottenrifts.core.VectorHelper.getRightVector;

public class RiftAmbientTask extends BukkitRunnable {
    private final ForgottenRifts plugin;

    public RiftAmbientTask(ForgottenRifts plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Rift rift : plugin.getRiftManager().getUniqueRifts()) {
            // only play if rift has partner
            if (plugin.getRiftManager().getPartner(rift) == null) continue;

            Location loc = rift.getLocation().clone();
            BlockFace facing = rift.getFacing();

            // find center
            Vector rightVec = getRightVector(facing);

            // particles
            Location center = loc.add(rightVec.clone().multiply(0.5)).add(0, -1.5, 0);
            double offX = (facing == BlockFace.NORTH || facing == BlockFace.SOUTH) ? 0.7 : 0.1;
            double offZ = (facing == BlockFace.EAST || facing == BlockFace.WEST) ? 0.7 : 0.1;

            rift.getLocation().getWorld().spawnParticle(
                    Particle.WITCH,
                    center.add(0.5, 0.5, 0.5),
                    20,
                    offX, 1.5, offZ,
                    0.02
            );

        }
    }
}
