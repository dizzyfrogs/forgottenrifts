package me.dizzyfrogs.forgottenrifts.core;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

public class RiftKeys {
    public static NamespacedKey RIFT_ID;

    public static void init(Plugin plugin) {
        RIFT_ID = new NamespacedKey(plugin, "rift_id");
    }
}
