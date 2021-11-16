package dk.dtu.cdio3.objects.chancecards;

import dk.dtu.cdio3.managers.GUIManager;
import dk.dtu.cdio3.managers.LanguageManager;

import java.awt.*;
import java.util.UUID;

public abstract class ChanceCard {
    private String cardName;
    ChanceCard(String cardName) {
        this.cardName = cardName;
    }
    public void showCardMessage(){
        String message = LanguageManager.getInstance().getString(cardName+"_message");
        GUIManager.getInstance().showChanceCard(message);
    }
    public abstract void doCardAction(UUID playerID);


}
