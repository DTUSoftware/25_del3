package dk.dtu.cdio3.objects.fields;

import dk.dtu.cdio3.managers.*;
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
            } else {
                // End the game
                GUIManager.getInstance().showMessage(LanguageManager.getInstance().getString("could_not_buy").replace("{player_name}", PlayerManager.getInstance().getPlayer(playerID).getName()));
                GameManager.getInstance().finishGame();
            }
        } else {
            // someone owns it
            if (deedOwnership.equals(playerID)) {
                // same player owns it
                GUIManager.getInstance().showMessage(LanguageManager.getInstance().getString("landed_on_own_property"));
            } else {

                if (DeedManager.getInstance().getDeed(deedID).payRent(playerID)) {
                    GUIManager.getInstance().showMessage(
                            LanguageManager.getInstance().getString("paid_rent")
                                    .replace("{property_rent}", Float.toString(Math.round(DeedManager.getInstance().getDeed(deedID).getCurrentRent())))
                                    .replace("{property_owner}", PlayerManager.getInstance().getPlayer(deedOwnership).getName())
                                    .replace("{property_name}", propertyName)
                    );
                } else {
                    GUIManager.getInstance().showMessage(LanguageManager.getInstance().getString("could_not_pay_rent").replace("{player_name}", PlayerManager.getInstance().getPlayer(playerID).getName()));
                    GameManager.getInstance().finishGame();
                }
            }

        }
    }

    @Override
    public void doLeavingAction(UUID playerID) {

    }

    @Override
    public void reloadLanguage() {
        super.getGUIStreet().setTitle(LanguageManager.getInstance().getString("field_" + super.getFieldName() + "_name"));
        super.getGUIStreet().setRentLabel(LanguageManager.getInstance().getString("rent")+": ");
        super.getGUIStreet().setOwnableLabel(LanguageManager.getInstance().getString("owner")+": ");
    }

    public void updatePrices(UUID deedID) {
        Deed fieldDeed = DeedManager.getInstance().getDeed(deedID);
        super.getGUIStreet().setRent(Double.toString(fieldDeed.getCurrentRent()));
        super.getGUIStreet().setSubText(Double.toString(fieldDeed.getPrice()));
        super.getGUIStreet().setDescription(LanguageManager.getInstance().getString("price")+": " + Double.toString(fieldDeed.getPrice()) + " | "+LanguageManager.getInstance().getString("rent")+": " + Double.toString(fieldDeed.getRent()) + " | "+LanguageManager.getInstance().getString("group_rent")+": " + Double.toString(fieldDeed.getGroupRent()));
    }

    public void setPropertyOwner(UUID playerID) {
        super.getGUIStreet().setOwnerName(PlayerManager.getInstance().getPlayer(playerID).getName());
    }
}
