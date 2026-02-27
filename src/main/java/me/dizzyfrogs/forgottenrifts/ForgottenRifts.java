package me.dizzyfrogs.forgottenrifts;

import me.dizzyfrogs.forgottenrifts.core.RiftKeys;
import me.dizzyfrogs.forgottenrifts.core.RiftManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ForgottenRifts extends JavaPlugin {

    private RiftManager riftManager;

    @Override
    public void onEnable() {
        RiftKeys.init(this);
        this.riftManager = new RiftManager();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
