package dk.dtu.cdio3.managers;

import dk.dtu.cdio3.objects.fields.Field;
import gui_fields.GUI_Car;
import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_main.GUI;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

/**
 * The GUIManager class, for managing the GUI. The
 * GUIManager is a constructor which initializes a
 * single instance of the dtudiplom GUI interface.
 * <p>
 * Note: Based on https://github.com/DTUSoftware/25_del1/blob/master/src/main/java/dk/dtu/spil/GUIManager.java
 * and on https://github.com/DTUSoftware/25_del2/blob/master/src/main/java/dk/dtu/cdio2/managers/GUIManager.java
 *
 * @author DTUSoftware, Gruppe 25
 * @version %I%, %G%
 * @since v0.0.1
 */
public class GUIManager {
    private static GUIManager guiManager;
    private GUI gui;

    /**
     * The GUIManager constructor initializes the GUI instance, and
     * create a new instance of the dtudiplom GUI.
     */
    private GUIManager() {
    }

    public void initializeGUI() {
        GUI_Field[] fields = GameManager.getInstance().getGameBoard().getGUIFields();

        gui = new GUI(fields, Color.decode("0x" + "3E6990"));

        gui.setDice(6, 6);
    }

    public boolean guiInitialized() {
        return (gui != null);
    }

    public static GUIManager getInstance() {
        if (guiManager == null) {
            guiManager = new GUIManager();
        }

        return guiManager;
    }


    /**
     * Closes the GUI after usage.
     */
    public void closeGUI() {
        gui.close();
    }

    /**
     * Function to update the two dice on the GUI.
     *
     * @param faceValue1 The value of the first die
     * @param faceValue2 The value of the second die
     */
    public void updateDice(int faceValue1, int faceValue2) {
        gui.setDice(faceValue1, faceValue2);
    }

    /**
     * Shows a message to the player(s), which they will have
     * to accept to continue playing (stops the current thread).
     *
     * @param message The message to be shown to the player(s)
     */
    public void showMessage(String message) {
        gui.showMessage(message);
    }

    /**
     * Helper function to ask a prompt to a player and return the
     * result.
     *
     * @param question The question to ask the player(s).
     * @return <code>true</code> if the player(s) agree to
     * the question, else <code>false</code>.
     */
    public boolean askPrompt(String question) {
        return gui.getUserLeftButtonPressed(question, LanguageManager.getInstance().getString("yes"), LanguageManager.getInstance().getString("no"));
    }

    /**
     * Helper function to ask for Language.
     */
    public void askLanguage() {
        HashMap<String, Locale> localeMap = LanguageManager.getInstance().getLocalesMap();

        if (localeMap.isEmpty()) {
            return;
        }

        String language = gui.getUserSelection("Choose a language", localeMap.keySet().toArray(new String[0]));

        Locale locale = localeMap.get(language);
        LanguageManager.getInstance().setLocale(locale);

//        return gui.getUserLeftButtonPressed("Choose a Language // V??lg et sprog", "English", "Danish");
    }

    /**
     * Method that asks how many players you want and add that to players
     * @return returns the amount of players as and integer
     */
    public int askPlayers() {
        String[] playerammountlist = {"2", "3", "4"};
        String player_Ammount = gui.getUserSelection(LanguageManager.getInstance().getString("choose_player_amount"), playerammountlist);
        return Integer.parseInt(player_Ammount);
    }

    /**
     * Asks the player to pick a number between min and max.
     *
     * @return The number the player chose.
     */
    public int askNumber(int min, int max) {
        String[] numbers = new String[max+1-min];
        for (int i = min; i <= numbers.length; i++) {
            numbers[i-1] = Integer.toString(i);
        }
        String number = gui.getUserSelection(LanguageManager.getInstance().getString("choose_a_number"), numbers);
        return Integer.parseInt(number);
    }

    /**
     * Asks the player which car type they want.
     *
     * @return The GUI_Car.Type that the player chose.
     */
    public GUI_Car.Type askCarType() {
        String[] carTypes = Arrays.stream(GUI_Car.Type.class.getEnumConstants()).map(Enum::name).toArray(String[]::new); // https://stackoverflow.com/a/13783744/12418245
        for (int i = 0; i < carTypes.length; i++) {
            if (!carTypes[i].equals("UFO")) {
                carTypes[i] = carTypes[i].substring(0,1).toUpperCase() + carTypes[i].substring(1).toLowerCase();
            }
        }
        String carType = gui.getUserSelection(LanguageManager.getInstance().getString("choose_player_car"), carTypes);
        return GUI_Car.Type.getTypeFromString(carType.toUpperCase());
    }

    /**
     * Helper function to ask a prompt to a player and return the
     * response.
     *
     * @param question The question to ask the player(s).
     * @return The string the player(s) wrote in response.
     */
    public String getUserString(String question) {
        return gui.getUserString(question);
    }

    /**
     * Function to wait for the user to roll their dice (clicking
     * a button). The loop won't continue before they click.
     */
    public void waitUserRoll() {
        gui.showMessage(LanguageManager.getInstance().getString("click_to_roll"));
    }

    /**
     * Function to create a new GUIPlayer. This function is
     * for example used in the PlayerManager, where it is passed
     * an instance of the GUIManager by the main function.
     *
     * @param startingBalance The starting balance of a player.
     * @return A GUI_Player object, linked to the
     * player in question.
     */
    public GUI_Player createGUIPlayer(String playerName, double startingBalance) {
        GUI_Car.Type carType = askCarType();
        GUI_Car car = new GUI_Car((Color)null, (Color)null, carType, GUI_Car.Pattern.FILL);

        GUI_Player player = new GUI_Player(playerName, (int) startingBalance, car); // the GUI takes int, so typecast

        gui.addPlayer(player);

        return player;
    }

    /**
     * Moves a player to a designated field.
     *
     * @param playerID    The ID of the player.
     * @param fieldNumber The number of the field to move the player to.
     * @return The Field which the Player landed on.
     */
    public Field movePlayerField(UUID playerID, int fieldNumber) {
        GUI_Player player = PlayerManager.getInstance().getGUIPlayer(playerID);
        assert (player != null);

        // The given fieldNumber may be 12, but the field element number is one less
        Field field = GameManager.getInstance().getGameBoard().getField(fieldNumber);
        assert (field != null);

        player.getCar().setPosition(field.getGUIField());
        return field;
    }

    /**
     * Sets the balance of a player on the GUI.
     *
     * @param playerID The ID of the player.
     * @param balance  The balance to set to the player.
     */
    public void setPlayerBalance(UUID playerID, double balance) {
        GUI_Player player = PlayerManager.getInstance().getGUIPlayer(playerID);
        assert (player != null);

        player.setBalance((int) balance); // The GUI only accepts integers, so typecasting
    }

    /**
     * Shows a chance card with the given text, and stops taking control of thread when user clicks ok.
     *
     * @param cardText  The text of the chance card.
     */
    public void showChanceCard(String cardText) {
        gui.setChanceCard(cardText);
        gui.displayChanceCard();
        showMessage(LanguageManager.getInstance().getString("got_a_chance_card"));
        gui.displayChanceCard();
        gui.setChanceCard(LanguageManager.getInstance().getString("no_chance_card"));
    }
}

