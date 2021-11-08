package dk.dtu.cdio3.managers;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import dk.dtu.cdio3.objects.Deed;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class DeedManager {
    private HashMap<UUID, Deed> deedMap = new HashMap<>();
    private BiMap<UUID, UUID> deeds = HashBiMap.create();
    private HashMap<UUID, UUID> deedOwnership = new HashMap<>();
    private HashMap<Color, UUID[]> deedGroups = new HashMap<>();

    public void createDeed(UUID fieldID) {
        Deed deed = new Deed();
        UUID deedID = deed.getID();
        deedMap.put(deedID, deed);
        deeds.put(deedID, fieldID);
        deedOwnership.put(deedID, null);
    }

    public Deed getDeed(UUID deedID) {
        return deedMap.get(deedID);
    }

    public Deed[] getDeeds(UUID[] deedIDs) {
        ArrayList<Deed> deeds = new ArrayList<>();
        for (UUID deedID : deedIDs) {
            deeds.add(getDeed(deedID));
        }
        return deeds.toArray(new Deed[0]);
    }

    public UUID[] getPlayerDeeds(UUID playerID) {
        ArrayList<UUID> playerDeeds = new ArrayList<>();
        for (UUID deedID : deedOwnership.keySet()) {
            if (deedOwnership.get(deedID) == playerID) {
                playerDeeds.add(deedID);
            }
        }
        return playerDeeds.toArray(new UUID[0]);
    }

    public UUID getDeedID(UUID fieldID) {
        return deeds.inverse().get(fieldID);
    }

    public UUID getFieldID(UUID deedID) {
        return deeds.get(deedID);
    }

    public void createDeedGroup(Color groupColor, UUID deed1, UUID deed2) {
        UUID[] deeds = new UUID[]{deed1, deed2};
        deedGroups.put(groupColor, deeds);
    }

    public UUID[] getDeedGroupDeeds(Color groupColor) {
        return deedGroups.get(groupColor);
    }
}
