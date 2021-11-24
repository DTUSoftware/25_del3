package dk.dtu.cdio3.objects.fields;

import dk.dtu.cdio3.Game;
import dk.dtu.cdio3.managers.GameManager;
import dk.dtu.cdio3.managers.LanguageManager;
import gui_fields.GUI_Chance;
import gui_fields.GUI_Field;
import gui_fields.GUI_Start;
import gui_fields.GUI_Street;

import java.awt.*;
import java.util.UUID;

public abstract class Field {
    private final UUID fieldID;
    private final String fieldName;
    private final Color fieldColor;
    private final GUI_Field guiField;

    Field(Color fieldColor, String fieldName, boolean description) {
        fieldID = UUID.randomUUID();
        this.fieldName = fieldName;
        this.fieldColor = fieldColor;

        switch (fieldName) {
            case "chance":
                this.guiField = new GUI_Chance();
                break;
            case "start":
                this.guiField = new GUI_Start();
                break;
            case "jail":
                this.guiField = new GUIJailField("GUI_Field.Image.Jail", "", LanguageManager.getInstance().getString("field_"+fieldName+"_name"), "", fieldColor, Color.BLACK);
                break;
            case "go_to_jail":
                this.guiField = new GUIJailField("GUI_Field.Image.GoToJail", LanguageManager.getInstance().getString("field_"+fieldName+"_name"), "", "", fieldColor, Color.BLACK);
                break;
            default:
                this.guiField = new GUI_Street();
                break;
        }
        this.guiField.setBackGroundColor(fieldColor);
        this.guiField.setTitle(LanguageManager.getInstance().getString("field_"+fieldName+"_name"));
        this.guiField.setSubText("");
        if (description) {
            this.guiField.setDescription(LanguageManager.getInstance().getString("field_"+fieldName+"_description")
                    .replace("{start_pass_amount}", Double.toString(Game.getStartPassReward())));
        }
    }

    public abstract void doLandingAction(UUID playerID);

    public abstract void doLeavingAction(UUID playerID);

    public abstract void reloadLanguage();

    public UUID getID() {
        return fieldID;
    }

    public GUI_Field getGUIField() {
        return this.guiField;
    }

    public Color getFieldColor() {
        return fieldColor;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String toString() {
        return "["+getID()+"] - " +
                "(" + String.format("%02d", GameManager.getInstance().getGameBoard().getFieldPosition(getID())) + ") " +
                getFieldName() +
                " [" + "Color: " + getFieldColor().toString().replace("java.awt.Color", "") +
                "]";
    }
}
