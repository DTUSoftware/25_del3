package dk.dtu.cdio3.objects.fields;

import dk.dtu.cdio3.managers.GUIManager;
import dk.dtu.cdio3.managers.GameManager;
import dk.dtu.cdio3.managers.LanguageManager;
import dk.dtu.cdio3.managers.PlayerManager;
import dk.dtu.cdio3.objects.Player;

import java.awt.*;
import java.util.UUID;

public class JailField extends Field {
    private double jailBailOut = 1500.0;

    public JailField() {
        super(Color.ORANGE, "jail", true);
    }
    public JailField(double jailBailOut) {
        super(Color.ORANGE, "jail", true);
        this.jailBailOut = jailBailOut;
    }

    @Override
    public void doLandingAction(UUID playerID) {
        if (PlayerManager.getInstance().getPlayer(playerID).isJailed()) {
            GUIManager.getInstance().showMessage(LanguageManager.getInstance().getString("jailed"));
        }
        else {
            GUIManager.getInstance().showMessage(LanguageManager.getInstance().getString("visiting_jail"));
        }
    }

    @Override
    public void doLeavingAction(UUID playerID) {
        Player player = PlayerManager.getInstance().getPlayer(playerID);
        if (player.isJailed()) {
            // if the player has a bail card
            if (player.takeBailCard()) {
                GUIManager.getInstance().showMessage(LanguageManager.getInstance().getString("card_bailout"));
                player.unJail();
            }
            else {
                // if the player can pay bailout fees
                if (player.withdraw(jailBailOut)) {
                    GUIManager.getInstance().showMessage(LanguageManager.getInstance().getString("paid_bailout").replace("{amount}", Float.toString(Math.round(jailBailOut))));
                    player.unJail();
                }
                else {
                    GUIManager.getInstance().showMessage(LanguageManager.getInstance().getString("could_not_pay_bailout"));
                    GameManager.getInstance().finishGame();
                }
            }

        }
    }

    @Override
    public void reloadLanguage() {
        super.getGUIField().setTitle(LanguageManager.getInstance().getString("field_"+super.getFieldName()+"_name"));
        super.getGUIField().setDescription(LanguageManager.getInstance().getString("field_"+super.getFieldName()+"_description"));
    }
}
