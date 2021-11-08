package dk.dtu.cdio3.objects.fields;

import java.awt.*;
import java.util.UUID;

public class StartField extends Field {
    public StartField() {
        super(Color.WHITE, "start", true);
        super.getGUIStreet().setBorder(Color.BLACK);
    }

    @Override
    public void doLandingAction(UUID playerID) {

    }

    @Override
    public void reloadLanguage() {

    }
}
