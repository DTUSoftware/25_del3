package dk.dtu.cdio3.managers;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import dk.dtu.cdio3.objects.Deed;
import dk.dtu.cdio3.objects.fields.PropertyField;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class DeedManager {
    private static DeedManager deedManager;

    private final HashMap<UUID, Deed> deedMap = new HashMap<>();
    private final BiMap<UUID, UUID> deeds = HashBiMap.create();
    private final HashMap<UUID, UUID> deedOwnership = new HashMap<>();
    private final HashMap<Color, UUID[]> deedGroups = new HashMap<>();

    private DeedManager() {

    }

    public static DeedManager getInstance() {
        if (deedManager == null) {
            deedManager = new DeedManager();
        }

        return deedManager;
    }

    public Deed createDeed(UUID fieldID, double price, double rent, double groupRent) {
        Deed deed = new Deed();
        deed.setPrices(price, rent, groupRent);

        UUID deedID = deed.getID();
        deedMap.put(deedID, deed);
        deeds.put(deedID, fieldID);
        deedOwnership.put(deedID, null);

        return deed;
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

    public void updatePlayerDeedPrices(UUID playerID) {
        for (UUID deedID : getPlayerDeeds(playerID)) {
            ((PropertyField) GameManager.getInstance().getGameBoard().getFieldFromID(getFieldID(deedID))).updatePrices(deedID);
        }
    }

    public UUID getDeedID(UUID fieldID) {
        return deeds.inverse().get(fieldID);
    }

    public UUID getFieldID(UUID deedID) {
        return deeds.get(deedID);
    }

    public void createDeedGroup(Color groupColor, UUID[] deeds) {
        deedGroups.put(groupColor, deeds);
    }

    public UUID[] getDeedGroupDeeds(Color groupColor) {
        return deedGroups.get(groupColor);
    }

    public void setDeedOwnership(UUID deedID, UUID playerID) {
        deedOwnership.put(deedID, playerID);
        PropertyField deedField = (PropertyField) GameManager.getInstance().getGameBoard().getFieldFromID(getFieldID(deedID));
        deedField.setPropertyOwner(playerID);
    }

    public UUID getDeedOwnership(UUID deedID) {
        return deedOwnership.get(deedID);
    }

    public void updateDeedPrices(UUID deedID, double price, double rent, double groupRent) {
        Deed deed = getDeed(deedID);
        deed.setPrices(price, rent, groupRent);
        PropertyField deedField = (PropertyField) GameManager.getInstance().getGameBoard().getFieldFromID(getFieldID(deedID));
        deedField.updatePrices(deedID);
    }
}
