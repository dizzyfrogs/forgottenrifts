package me.dizzyfrogs.forgottenrifts.listener;

import me.dizzyfrogs.forgottenrifts.ForgottenRifts;
import me.dizzyfrogs.forgottenrifts.model.Rift;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

public class RiftPhysicsListener implements Listener {
    private final ForgottenRifts plugin;

    public RiftPhysicsListener(ForgottenRifts plugin) {
        this.plugin = plugin;
    }

    // handle player mining
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        checkAndCollapse(event.getBlock());
    }

    // handle TNT/creeper explosions
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent event) {
        handleExplosion(event.blockList());
    }

    // handle bed/respawn anchor explosions
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockExplode(BlockExplodeEvent event) {
        handleExplosion(event.blockList());
    }

    // handle placing block inside of rift
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (plugin.getRiftManager().getRiftAt(event.getBlock().getLocation()) != null) {
            event.setCancelled(true);
            event.getPlayer().playSound(event.getBlock().getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 0.5f, 0.1f);
        }
    }

    // handle water/lava flowing into rift
    @EventHandler
    public void onFluidFlow(BlockFromToEvent event) {
        if (plugin.getRiftManager().getRiftAt(event.getToBlock().getLocation()) != null) {
            event.setCancelled(true);
        }
    }

    private void handleExplosion(List<Block> blocks) {
        for (Block block : blocks) {
            checkAndCollapse(block);
        }
    }

    private boolean checkAndCollapse(Block block) {
        Rift rift = plugin.getRiftManager().getRiftAt(block.getLocation());
        if (rift != null) {
            collapseRift(rift);
            return true;
        }
        return false;
    }

    private void collapseRift(Rift rift) {
        plugin.getRiftManager().removeRift(rift.getLocation());

        rift.getLocation().getWorld().playSound(rift.getLocation(), Sound.BLOCK_GLASS_BREAK, 1.0f, 0.5f);
        rift.getLocation().getWorld().playSound(rift.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 0.8f, 0.5f);

        Rift partner = plugin.getRiftManager().getPartner(rift);
        if (partner != null) collapseRift(partner);
    }
}
