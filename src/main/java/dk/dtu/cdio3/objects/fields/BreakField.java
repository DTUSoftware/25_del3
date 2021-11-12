package dk.dtu.cdio3.objects.fields;

import dk.dtu.cdio3.managers.LanguageManager;

import java.awt.*;
import java.util.UUID;

public class BreakField extends Field {
    public BreakField() {
        super(Color.RED, "break", true);
        super.getGUIStreet().setBorder(Color.BLACK);
    }

    @Override
    public void doLandingAction(UUID playerID) {

    }

    @Override
    public void doLeavingAction(UUID playerID) {

    }

    @Override
    public void reloadLanguage() {
        super.getGUIStreet().setTitle(LanguageManager.getInstance().getString("field_"+super.getFieldName()+"_name"));
        super.getGUIStreet().setDescription(LanguageManager.getInstance().getString("field_"+super.getFieldName()+"_description"));
    }
}
