package dk.dtu.cdio3.objects;

import com.google.common.io.Resources;
import dk.dtu.cdio3.Game;
import dk.dtu.cdio3.managers.DeedManager;
import dk.dtu.cdio3.objects.fields.*;
import gui_fields.GUI_Field;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

public class GameBoard {
    private final Field[] fields;
    private HashMap<UUID, Field> fieldMap = new HashMap<>();
    private HashMap<UUID, GUI_Field> guiFields = new LinkedHashMap<>();
    private JSONObject gameBoardJSON;

    private void loadGameBoardConfig() {
        try {
//            URL gameBoardConfigURL = Game.class.getClassLoader().getResource("GameBoard.json");
            URL gameBoardConfigURL = Resources.getResource("GameBoard.json");
            String gameBoardConfigString = Resources.toString(gameBoardConfigURL, StandardCharsets.UTF_8);

            gameBoardJSON = new JSONObject(gameBoardConfigString);
        }
        catch (Exception e) {
            System.out.println("Could not read Game Board JSON from resources... - " + e.toString());
        }
    }

    private Color getColor(String colorString) {
        Color color = Color.BLACK;
        if (colorString.startsWith("#")) {
            color = Color.decode(colorString);
        }
        else {
            try {
                color = (Color) Color.class.getField(colorString).get(null);
            }
            catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return color;
    }

    public GameBoard() {
        loadGameBoardConfig();

        JSONArray jsonFields = gameBoardJSON.getJSONArray("fields");
        fields = new Field[jsonFields.length()];
        JSONObject jsonFieldGroups = gameBoardJSON.getJSONObject("property_field_groups");
        HashMap<Color, ArrayList<UUID>> fieldGroupsMap = new HashMap<>();
        // populate map
        for (String colorString : jsonFieldGroups.keySet()) {
            fieldGroupsMap.put(getColor(colorString), new ArrayList<>());
        }

        // Load fields from Game Board config
        for (int i = 0; i < jsonFields.length(); i++) {
            JSONObject jsonField = jsonFields.getJSONObject(i);
            String fieldType = jsonField.getString("field_type");

            switch (fieldType) {
                case "StartField":
                    fields[i] = new StartField();
                    Game.setStartPassReward(jsonField.getDouble("pass_reward"));
                    break;
                case "PropertyField":
                    String fieldColorString = jsonField.getString("field_color");
                    Color fieldColor = getColor(fieldColorString);
                    fields[i] = new PropertyField(fieldColor, jsonField.getString("field_name"));

                    // Register the rent
                    JSONObject groupRentJSON = jsonFieldGroups.getJSONObject(fieldColorString);
                    double price = groupRentJSON.getDouble("base_price");
                    double rent = groupRentJSON.getDouble("base_rent");
                    double groupRent = groupRentJSON.getDouble("group_rent");
                    Deed fieldDeed = DeedManager.getInstance().createDeed(fields[i].getID(), price, rent, groupRent);
                    ((PropertyField) fields[i]).updatePrices(fieldDeed.getID());

                    // Add the field deed to the group array
                    fieldGroupsMap.get(fieldColor).add(fieldDeed.getID());
                    break;
                case "ChanceField":
                    fields[i] = new ChanceField();
                    break;
                case "JailField":
                    fields[i] = new JailField();
                    break;
                case "BreakField":
                    fields[i] = new BreakField();
                    break;
                case "GoToJailField":
                    fields[i] = new GoToJailField();
                    break;
                default:
                    System.out.println("Ohno, field type doesn't exist...");
                    fields[i] = new ChanceField();
            }

            Field field = fields[i];
            fieldMap.put(field.getID(), fields[i]);
            guiFields.put(field.getID(), field.getGUIStreet());
        }

        // Update the deedmanager with group deeds
        for (Color groupColor : fieldGroupsMap.keySet()) {
            DeedManager.getInstance().createDeedGroup(groupColor, fieldGroupsMap.get(groupColor).toArray(new UUID[0]));
        }
    }

    /**
     * Reloads the language on all fields.
     */
    public void reloadLanguage() {
        for (Field field : fields) {
            field.reloadLanguage();
        }
    }

    public int getFieldAmount() {
        return fields.length;
    }

    public Field getField(int fieldPosition) {
        return fields[fieldPosition];
    }

    public Field getFieldFromID(UUID fieldID) {
        return fieldMap.get(fieldID);
    }

    public GUI_Field[] getGUIFields() {
        return guiFields.values().toArray(new GUI_Field[0]);
    }
}
