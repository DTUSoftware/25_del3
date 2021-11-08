package dk.dtu.cdio3.objects.fields;

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
    public void reloadLanguage() {

    }
}
