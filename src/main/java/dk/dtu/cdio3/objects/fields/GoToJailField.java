package dk.dtu.cdio3.objects.fields;

import dk.dtu.cdio3.managers.GUIManager;
import dk.dtu.cdio3.managers.GameManager;
import dk.dtu.cdio3.managers.LanguageManager;

import java.awt.*;
import java.util.UUID;

public class GoToJailField extends Field {
    public GoToJailField() {
        super(Color.BLUE, "go_to_jail", true);
        super.getGUIStreet().setBorder(Color.BLACK);
    }

    @Override
    public void doLandingAction(UUID playerID) {
        // Move the player to jail
        GUIManager.getInstance().showMessage(LanguageManager.getInstance().getString("sent_to_jail"));
        GameManager.getInstance().setPlayerBoardPosition(playerID, GameManager.getInstance().getGameBoard().getFieldPosition(GameManager.getInstance().getGameBoard().getJailField()), false);
    }

    @Override
    public void reloadLanguage() {
        super.getGUIStreet().setTitle(LanguageManager.getInstance().getString("field_"+super.getFieldName()+"_name"));
        super.getGUIStreet().setDescription(LanguageManager.getInstance().getString("field_"+super.getFieldName()+"_description"));
    }
}
