package me.dizzyfrogs.forgottenrifts.core;

import me.dizzyfrogs.forgottenrifts.model.Rift;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class RiftManager {
    private final Map<Location, Rift> activeRifts = new HashMap<>();

    public void registerRift(Rift rift) {
        activeRifts.put(rift.getLocation(), rift);
        // save to config
    }

    public void removeRift(Location location) {
        activeRifts.remove(location);
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
}
