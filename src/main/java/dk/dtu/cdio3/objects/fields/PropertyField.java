package dk.dtu.cdio3.objects.fields;

import dk.dtu.cdio3.managers.DeedManager;
import dk.dtu.cdio3.managers.PlayerManager;
import dk.dtu.cdio3.objects.Deed;

import java.awt.*;
import java.util.UUID;

public class PropertyField extends Field {
    public PropertyField(Color color, String fieldName) {
        super(color, fieldName, false);
    }

    @Override
    public void doLandingAction(UUID playerID) {

    }

    @Override
    public void reloadLanguage() {

    }

    public void updatePrices(UUID deedID) {
        Deed fieldDeed = DeedManager.getInstance().getDeed(deedID);
        super.getGUIStreet().setSubText(Double.toString(fieldDeed.getPrice()));
        super.getGUIStreet().setDescription("Price: " + Double.toString(fieldDeed.getPrice()) + " | Rent: " + Double.toString(fieldDeed.getRent()) + " | Group Rent: " + Double.toString(fieldDeed.getGroupRent()));
    }

    public void setPropertyOwner(UUID playerID) {
        super.getGUIStreet().setOwnerName(PlayerManager.getInstance().getPlayer(playerID).getName());
    }
}
