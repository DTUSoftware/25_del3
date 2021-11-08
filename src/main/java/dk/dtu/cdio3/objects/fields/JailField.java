package dk.dtu.cdio3.objects.fields;

import dk.dtu.cdio3.managers.LanguageManager;

import java.awt.*;
import java.util.UUID;

public class JailField extends Field {
    public JailField() {
        super(Color.ORANGE, "jail", true);
        super.getGUIStreet().setBorder(Color.BLACK);
    }

    @Override
    public void doLandingAction(UUID playerID) {

    }

    @Override
    public void reloadLanguage() {
        super.getGUIStreet().setTitle(LanguageManager.getInstance().getString("field_"+super.getFieldName()+"_name"));
        super.getGUIStreet().setDescription(LanguageManager.getInstance().getString("field_"+super.getFieldName()+"_description"));
    }
}
