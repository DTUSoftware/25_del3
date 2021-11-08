package dk.dtu.cdio3.objects.fields;

import dk.dtu.cdio3.managers.DeedManager;
import dk.dtu.cdio3.managers.GUIManager;
import dk.dtu.cdio3.managers.LanguageManager;
import dk.dtu.cdio3.managers.PlayerManager;
import dk.dtu.cdio3.objects.Deed;
import gui_main.GUI;

import java.awt.*;
import java.util.UUID;

public class PropertyField extends Field {
    public PropertyField(Color color, String fieldName) {
        super(color, fieldName, false);
    }

    @Override
    public void doLandingAction(UUID playerID) {
        UUID deedID = DeedManager.getInstance().getDeedID(super.getID());
        UUID deedOwnership = DeedManager.getInstance().getDeedOwnership(deedID);
        if (deedOwnership == null) {
            // TODO: Finish this buying stuff and bug test it
            // want to buy and/or have enough money
        }
        else {
            // someone owns it
            if (deedOwnership.equals(playerID)) {
                // same player owns it
                GUIManager.getInstance().showMessage(LanguageManager.getInstance().getString("landed_on_own_property"));
            }
            else {
                PlayerManager.getInstance().getPlayer(playerID).withdraw(DeedManager.getInstance().getDeed(deedID).getCurrentRent());
            }

        }
    }

    @Override
    public void reloadLanguage() {
        super.getGUIStreet().setTitle(LanguageManager.getInstance().getString("field_"+super.getFieldName()+"_name"));
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
