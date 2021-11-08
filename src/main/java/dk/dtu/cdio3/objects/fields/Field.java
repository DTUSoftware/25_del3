package dk.dtu.cdio3.objects.fields;

import dk.dtu.cdio3.managers.LanguageManager;
import dk.dtu.cdio3.objects.Player;
import gui_fields.GUI_Street;

import java.awt.*;
import java.util.UUID;

public abstract class Field {
    private UUID fieldID;
    private Color fieldColor;
    private GUI_Street guiStreet;

    Field(Color fieldColor, String fieldName) {
        fieldID = UUID.randomUUID();
        this.fieldColor = fieldColor;

        this.guiStreet = new GUI_Street();
        this.guiStreet.setBackGroundColor(fieldColor);
        this.guiStreet.setTitle(LanguageManager.getInstance().getString("field_"+fieldName+"_name"));
        this.guiStreet.setSubText(LanguageManager.getInstance().getString("field_"+fieldName+"_sub_text"));
    }

    public abstract void doLandingAction(Player player);

    public abstract void reloadLanguage();

    public UUID getID() {
        return fieldID;
    }

    public GUI_Street getGUIStreet() {
        return this.guiStreet;
    }
}
