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
            String propertyName = LanguageManager.getInstance().getString("field_" + super.getFieldName() + "_name");
            double temp_deed_price = DeedManager.getInstance().getDeed(deedID).getPrice();
            if (PlayerManager.getInstance().getPlayer(playerID).getBalance() >= temp_deed_price) {
                boolean want_to_buy = GUIManager.getInstance().askPrompt(
                        LanguageManager.getInstance().getString("want_to_buy")
                                .replace("{property_name}", propertyName)
                                .replace("{property_price}", Float.toString(Math.round(temp_deed_price)))
                );
                if (want_to_buy) {
                    PlayerManager.getInstance().getPlayer(playerID).withdraw(temp_deed_price);
                    setPropertyOwner(playerID);
                }
            }
        } else {
            // someone owns it
            if (deedOwnership.equals(playerID)) {
                // same player owns it
                GUIManager.getInstance().showMessage(LanguageManager.getInstance().getString("landed_on_own_property"));
            } else {
                PlayerManager.getInstance().getPlayer(playerID).withdraw(DeedManager.getInstance().getDeed(deedID).getCurrentRent());
            }

        }
    }

    @Override
    public void reloadLanguage() {
        super.getGUIStreet().setTitle(LanguageManager.getInstance().getString("field_" + super.getFieldName() + "_name"));
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
