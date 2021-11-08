package dk.dtu.cdio3.objects.fields;

import java.awt.*;
import java.util.UUID;

public class ChanceField extends Field {
    public ChanceField() {
        super(Color.ORANGE, "chance", true);
        super.getGUIStreet().setBorder(Color.MAGENTA);
    }

    @Override
    public void doLandingAction(UUID playerID) {

    }

    @Override
    public void reloadLanguage() {

    }
}
