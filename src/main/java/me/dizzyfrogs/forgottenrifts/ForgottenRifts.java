package me.dizzyfrogs.forgottenrifts;

import me.dizzyfrogs.forgottenrifts.core.RiftAmbientTask;
import me.dizzyfrogs.forgottenrifts.core.RiftKeys;
import me.dizzyfrogs.forgottenrifts.core.RiftManager;
import me.dizzyfrogs.forgottenrifts.listener.RiftCreationListener;
import me.dizzyfrogs.forgottenrifts.listener.RiftPhysicsListener;
import me.dizzyfrogs.forgottenrifts.listener.RiftTeleportListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ForgottenRifts extends JavaPlugin {

    private RiftManager riftManager;

    @Override
    public void onEnable() {
        RiftKeys.init(this);
        this.riftManager = new RiftManager(this);

        new RiftAmbientTask(this).runTaskTimer(this, 0L, 50L);

        getServer().getPluginManager().registerEvents(new RiftCreationListener(this), this);
        getServer().getPluginManager().registerEvents(new RiftTeleportListener(this), this);
        getServer().getPluginManager().registerEvents(new RiftPhysicsListener(this), this);
    }

    @Override
    public void onDisable() {
        if (this.riftManager != null) {
            this.riftManager.save();
            getLogger().info("All rifts have been saved to rifts.yml");
        }
    }

    public RiftManager getRiftManager() {
        return this.riftManager;
    }
}
