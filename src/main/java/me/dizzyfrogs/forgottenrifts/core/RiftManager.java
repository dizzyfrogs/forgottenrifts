package me.dizzyfrogs.forgottenrifts.core;

import me.dizzyfrogs.forgottenrifts.ForgottenRifts;
import me.dizzyfrogs.forgottenrifts.model.Rift;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class RiftManager {
    private final ForgottenRifts plugin;
    private final Map<Location, Rift> activeRifts = new HashMap<>();
    private final File file;
    private FileConfiguration config;

    public RiftManager(ForgottenRifts plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "rifts.yml");
        load();
    }

    public void registerRift(Rift rift) {
        Location start = rift.getLocation();
        Vector right = rift.getRightVector();

        // air
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 2; x++) {
                Location blockLoc = start.clone().add(right.clone().multiply(x)).add(0, -y, 0);
                activeRifts.put(blockLoc, rift);
            }
        }

        // frame
        int[] yOffsets = {1, -3}; // top/bottom rows
        for (int yOff : yOffsets) {
            for (int x = -1; x < 3; x++) {
                Location frameLoc = start.clone().add(right.clone().multiply(x)).add(0, yOff, 0);
                activeRifts.put(frameLoc, rift);
            }
        }
        for (int y = 0; y < 3; y++) { // side pillars
            Location leftLoc = start.clone().add(right.clone().multiply(-1)).add(0, -y, 0);
            Location rightLoc = start.clone().add(right.clone().multiply(2)).add(0, -y, 0);
            activeRifts.put(leftLoc, rift);
            activeRifts.put(rightLoc, rift);
        }
    }

    public void removeRift(Location location) {
        Rift rift = activeRifts.get(location);
        if (rift == null) return;

        activeRifts.values().removeIf(r -> r.getId().equals(rift.getId()));
    }

    public Rift getRiftAt(Location location) {
        return activeRifts.get(location);
    }

    public Rift getPartner(Rift source) {
        for (Rift other : activeRifts.values()) {
            if (!other.getId().equals(source.getId()) && other.getFrequency().equals(source.getFrequency())) {
                return other;
            }
        }
        return null;
    }

    public Collection<Rift> getUniqueRifts() {
        return activeRifts.values().stream().distinct().collect(Collectors.toList());
    }

    public void save() {
        config = new YamlConfiguration();
        int i = 0;
        for (Rift rift : getUniqueRifts()) {
            String path = "rifts." + i + ".";
            config.set(path + "id", rift.getId().toString());
            config.set(path + "world", rift.getLocation().getWorld().getName());
            config.set(path + "pos", rift.getLocation().toVector());
            config.set(path + "freq", rift.getFrequency());
            config.set(path + "face", rift.getFacing().name());
            config.set(path + "right", rift.getRightVector());
            i++;
        }
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save rifts.yml!");
            e.printStackTrace();
        }
    }

    public void load() {
        if (!file.exists()) return;
        config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection("rifts");
        if (section == null) return;

        for (String key : section.getKeys(false)) {
            try {
                UUID id = UUID.fromString(section.getString(key + ".id"));
                String worldName = section.getString(key + ".world");
                Vector pos = section.getVector(key + ".pos");
                String freq = section.getString(key + ".freq");
                BlockFace face = BlockFace.valueOf(section.getString(key + ".face"));
                Vector right = section.getVector(key + ".right");

                Location loc = pos.toLocation(org.bukkit.Bukkit.getWorld(worldName));
                Rift rift = new Rift(id, loc, freq, face, right);
                registerRift(rift);
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to load a rift from rifts.yml: " + e.getMessage());
            }
        }
    }
}
