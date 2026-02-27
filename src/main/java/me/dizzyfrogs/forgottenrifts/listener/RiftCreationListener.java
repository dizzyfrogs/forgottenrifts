package me.dizzyfrogs.forgottenrifts.listener;

import me.dizzyfrogs.forgottenrifts.ForgottenRifts;
import me.dizzyfrogs.forgottenrifts.model.Rift;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.util.UUID;

import static me.dizzyfrogs.forgottenrifts.core.VectorHelper.getRightVector;

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

        if (hitBlock == null || face == null || face == BlockFace.UP || face == BlockFace.DOWN || !isStainedGlass(hitBlock.getType())) return;

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
        Rift rift = new Rift(UUID.randomUUID(), topLeft.getLocation(), frequency, face, right);
        plugin.getRiftManager().registerRift(rift, right);

        Rift partner = plugin.getRiftManager().getPartner(rift);
        if (partner == null) {
            // first
            hitBlock.getWorld().playSound(hitBlock.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1.0f, 0.5f);
        } else {
            activateRiftBlocks(rift, partner, right);

            // found pair, linking sound
            Location loc1 = rift.getLocation();
            Location loc2 = partner.getLocation();

            // high pitch resonance at new portal
            loc1.getWorld().playSound(loc1, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 0.8f);
            loc1.getWorld().playSound(loc1, Sound.BLOCK_BEACON_ACTIVATE, 1.0f, 1.2f);

            // harmonic echo at partner portal
            loc2.getWorld().playSound(loc2, Sound.ENTITY_PLAYER_LEVELUP, 0.8f, 0.8f);
            loc2.getWorld().playSound(loc2, Sound.BLOCK_BEACON_ACTIVATE, 0.8f, 1.2f);

            Player shooter = (Player) event.getEntity().getShooter();
            if (shooter != null) {
                shooter.getWorld().playSound(shooter.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.8f, 1.2f);
                shooter.getWorld().playSound(shooter.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 0.5f, 1.5f);
            }
        }

        event.getEntity().remove();
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

    private void activateRiftBlocks(Rift r1, Rift r2, Vector right) {
        clearArea(r1, r1.getRightVector());
        clearArea(r2, r2.getRightVector());
    }

    private void clearArea(Rift rift, Vector right) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 2; x++) {
                Location b = rift.getLocation().clone().add(right.clone().multiply(x)).add(0, -y, 0);
                b.getBlock().setType(Material.AIR);
            }
        }
    }
}
