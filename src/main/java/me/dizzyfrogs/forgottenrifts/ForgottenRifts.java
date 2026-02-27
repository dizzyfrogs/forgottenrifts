package me.dizzyfrogs.forgottenrifts;

import me.dizzyfrogs.forgottenrifts.core.RiftKeys;
import me.dizzyfrogs.forgottenrifts.core.RiftManager;
import me.dizzyfrogs.forgottenrifts.listener.RiftCreationListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ForgottenRifts extends JavaPlugin {

    private RiftManager riftManager;

    @Override
    public void onEnable() {
        RiftKeys.init(this);
        this.riftManager = new RiftManager();

        getServer().getPluginManager().registerEvents(new RiftCreationListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public RiftManager getRiftManager() {
        return this.riftManager;
    }
}
