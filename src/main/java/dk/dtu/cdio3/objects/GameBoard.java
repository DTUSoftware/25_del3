package dk.dtu.cdio3.objects;

import com.google.common.io.Resources;
import dk.dtu.cdio3.Game;
import dk.dtu.cdio3.objects.fields.*;
import gui_fields.GUI_Field;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

public class GameBoard {
    private final Field[] fields;
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

    public GameBoard() {
        loadGameBoardConfig();

        JSONArray jsonFields = gameBoardJSON.getJSONArray("fields");
        fields = new Field[jsonFields.length()];

        for (int i = 0; i < jsonFields.length(); i++) {
            JSONObject jsonField = jsonFields.getJSONObject(i);
            String fieldType = jsonField.getString("field_type");

            switch (fieldType) {
                case "StartField":
                    fields[i] = new StartField();
                    break;
                case "PropertyField":
                    String fieldColorString = jsonField.getString("field_color");
                    Color fieldColor = Color.BLACK;
                    if (fieldColorString.startsWith("#")) {
                        fieldColor = Color.decode(fieldColorString);
                    }
                    else {
                        try {
                            fieldColor = (Color) Color.class.getField(fieldColorString).get(null);
                        }
                        catch (Exception e) {
                            System.out.println(e.toString());
                        }
                    }
                    fields[i] = new PropertyField(fieldColor, jsonField.getString("field_name"));
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
            guiFields.put(field.getID(), field.getGUIStreet());
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

    public Field getField(int fieldPosition) {
        return fields[fieldPosition];
    }

    public GUI_Field[] getGUIFields() {
        return guiFields.values().toArray(new GUI_Field[0]);
    }
}
