package dk.dtu.cdio3.objects.fields;

import dk.dtu.cdio3.managers.DeedManager;
import dk.dtu.cdio3.managers.GUIManager;
import dk.dtu.cdio3.managers.LanguageManager;
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
        UUID deedID = DeedManager.getInstance().getDeedID(super.getID());
        UUID deedOwnership = DeedManager.getInstance().getDeedOwnership(deedID);
        String propertyName = LanguageManager.getInstance().getString("field_" + super.getFieldName() + "_name");
        if (deedOwnership == null) {
            // want to buy and/or have enough money
            double deed_price = DeedManager.getInstance().getDeed(deedID).getPrice();
            if (PlayerManager.getInstance().getPlayer(playerID).getBalance() >= deed_price) {
                boolean want_to_buy = GUIManager.getInstance().askPrompt(
                        LanguageManager.getInstance().getString("want_to_buy")
                                .replace("{property_name}", propertyName)
                                .replace("{property_price}", Float.toString(Math.round(deed_price)))
                );
                if (want_to_buy) {
                    PlayerManager.getInstance().getPlayer(playerID).withdraw(deed_price);
                    DeedManager.getInstance().setDeedOwnership(DeedManager.getInstance().getDeedID(super.getID()), playerID);
                    DeedManager.getInstance().updatePlayerDeedPrices(playerID);
                }
            }
        } else {
            // someone owns it
            if (deedOwnership.equals(playerID)) {
                // same player owns it
                GUIManager.getInstance().showMessage(LanguageManager.getInstance().getString("landed_on_own_property"));
            } else {
                GUIManager.getInstance().showMessage(
                        LanguageManager.getInstance().getString("paid_rent")
                                .replace("{property_rent}", Float.toString(Math.round(DeedManager.getInstance().getDeed(deedID).getCurrentRent())))
                                .replace("{property_owner}", PlayerManager.getInstance().getPlayer(deedOwnership).getName())
                                .replace("{property_name}", propertyName)
                );
                DeedManager.getInstance().getDeed(deedID).payRent(playerID);
            }

        }
    }

    @Override
    public void reloadLanguage() {
        super.getGUIStreet().setTitle(LanguageManager.getInstance().getString("field_" + super.getFieldName() + "_name"));
        super.getGUIStreet().setRentLabel(LanguageManager.getInstance().getString("property_rent_label"));
        super.getGUIStreet().setOwnableLabel(LanguageManager.getInstance().getString("property_owner_label"));
    }

    public void updatePrices(UUID deedID) {
        Deed fieldDeed = DeedManager.getInstance().getDeed(deedID);
        super.getGUIStreet().setRent(Double.toString(fieldDeed.getCurrentRent()));
        super.getGUIStreet().setSubText(Double.toString(fieldDeed.getPrice()));
        super.getGUIStreet().setDescription("Price: " + Double.toString(fieldDeed.getPrice()) + " | Rent: " + Double.toString(fieldDeed.getRent()) + " | Group Rent: " + Double.toString(fieldDeed.getGroupRent()));
    }

    public void setPropertyOwner(UUID playerID) {
        super.getGUIStreet().setOwnerName(PlayerManager.getInstance().getPlayer(playerID).getName());
    }
}
