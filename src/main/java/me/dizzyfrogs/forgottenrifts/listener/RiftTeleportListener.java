package me.dizzyfrogs.forgottenrifts.listener;

import me.dizzyfrogs.forgottenrifts.ForgottenRifts;
import me.dizzyfrogs.forgottenrifts.model.Rift;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RiftTeleportListener implements Listener {
    private final ForgottenRifts plugin;
    // cooldown map to prevent infinite teleport loops
    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public RiftTeleportListener(ForgottenRifts plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        // only run if the player actually crosses a block boundary
        if (event.getFrom().getBlockX() == event.getTo().getBlockX() &&
                event.getFrom().getBlockY() == event.getTo().getBlockY() &&
                event.getFrom().getBlockZ() == event.getTo().getBlockZ()) return;

        Player player = event.getPlayer();

        // check cooldown (2 sec)
        if (cooldowns.containsKey(player.getUniqueId())) {
            if (System.currentTimeMillis() - cooldowns.get(player.getUniqueId()) < 2000) return;
        }

        Location loc = event.getTo().getBlock().getLocation();
        Rift sourceRift = plugin.getRiftManager().getRiftAt(loc);

        if (sourceRift != null) {
            Rift destination = plugin.getRiftManager().getPartner(sourceRift);

            if (destination != null) {
                performRiftJump(player, destination);
            }
        }
    }

    private void performRiftJump(Player player, Rift dest) {
        Location target = dest.getLocation().clone();
        Vector right = dest.getRightVector();
        BlockFace face = dest.getFacing();

        //center player, move to floor
        target.add(right.clone().multiply(0.5));
        target.add(0.5, -2.0, 0.5);

        target.setYaw(player.getLocation().getYaw()); // keep yaw for now

        // apply the blind effects
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 20, 1));

        // play the jump sound at the source and destination
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1f, 0.5f);

        player.teleport(target);

        // set cooldown to prevent being sucked back in
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());

        // play exit sound
        target.getWorld().playSound(target, Sound.BLOCK_PORTAL_TRAVEL, 0.2f, 1.5f);
    }
}