package me.dizzyfrogs.forgottenrifts.core;

import me.dizzyfrogs.forgottenrifts.model.Rift;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RiftManager {
    private final Map<Location, Rift> activeRifts = new HashMap<>();

    public void registerRift(Rift rift, Vector right) {
        Location start = rift.getLocation();

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 2; x++) {
                Location blockLoc = start.clone().add(right.clone().multiply(x)).add(0, -y, 0);
                activeRifts.put(blockLoc, rift);
            }
        }
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

    public Collection<Rift> getUniqueRifts() {
        return activeRifts.values().stream().distinct().collect(Collectors.toList());
    }
}
