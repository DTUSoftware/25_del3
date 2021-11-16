package dk.dtu.cdio3.objects.fields;

import dk.dtu.cdio3.Game;
import dk.dtu.cdio3.managers.LanguageManager;
import gui_fields.GUI_Ownable;

import java.awt.*;
import java.util.UUID;

public class StartField extends Field {
    public StartField() {
        super(Color.WHITE, "start", true);
    }

    @Override
    public void doLandingAction(UUID playerID) {

    }

    @Override
    public void doLeavingAction(UUID playerID) {

    }

    @Override
    public void reloadLanguage() {
        super.getGUIField().setTitle(LanguageManager.getInstance().getString("field_"+super.getFieldName()+"_name"));
        super.getGUIField().setDescription(LanguageManager.getInstance().getString("field_"+super.getFieldName()+"_description").replace("{start_pass_amount}", Double.toString(Game.getStartPassReward())));
    }
}
