package dk.dtu.cdio3.objects.fields;

import dk.dtu.cdio3.managers.GUIManager;
import dk.dtu.cdio3.managers.GameManager;
import dk.dtu.cdio3.managers.LanguageManager;
import dk.dtu.cdio3.managers.PlayerManager;
import gui_fields.GUI_Jail;
import gui_fields.GUI_Ownable;

import java.awt.*;
import java.util.UUID;

public class GoToJailField extends Field {
    public GoToJailField() {
        super(Color.BLUE, "go_to_jail", true);
    }

    @Override
    public void doLandingAction(UUID playerID) {
        // Move the player to jail
        GUIManager.getInstance().showMessage(LanguageManager.getInstance().getString("go_to_jail_message"));
        PlayerManager.getInstance().getPlayer(playerID).jail();
        GameManager.getInstance().setPlayerBoardPosition(playerID, GameManager.getInstance().getGameBoard().getFieldPosition(GameManager.getInstance().getGameBoard().getJailField()), false);
    }

    @Override
    public void doLeavingAction(UUID playerID) {

    }

    @Override
    public void reloadLanguage() {
        super.getGUIField().setTitle(LanguageManager.getInstance().getString("field_"+super.getFieldName()+"_name"));
        super.getGUIField().setDescription(LanguageManager.getInstance().getString("field_"+super.getFieldName()+"_description"));
    }
}
