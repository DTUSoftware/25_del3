package dk.dtu.cdio3.objects.fields;

import java.awt.*;
import java.util.UUID;

public class GoToJailField extends Field {
    public GoToJailField() {
        super(Color.BLUE, "go_to_jail", true);
        super.getGUIStreet().setBorder(Color.BLACK);
    }

    @Override
    public void doLandingAction(UUID playerID) {

    }

    @Override
    public void reloadLanguage() {

    }
}
