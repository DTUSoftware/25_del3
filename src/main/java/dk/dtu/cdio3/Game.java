package dk.dtu.cdio3;

import dk.dtu.cdio3.managers.GUIManager;
import dk.dtu.cdio3.managers.GameManager;
import dk.dtu.cdio3.managers.LanguageManager;
import dk.dtu.cdio3.managers.PlayerManager;
import dk.dtu.cdio3.objects.Player;

/**
 * Main class for the program
 */
public class Game {
    private final static int startingBalance = 1000;
    public final static boolean debug = ((System.getenv("debug") != null) || (System.getProperty("debug") != null));

    public static void main(String[] args) {
        // Assign GUI to variable, since we will be using it again
        GUIManager gm = GUIManager.getInstance();

        // Initialize the GUI
        gm.initializeGUI();

        // Ask for language and reload the GameBoard
        gm.askLanguage();
        GameManager.getInstance().getGameBoard().reloadLanguage();

        // Create players
        for (int i = 1; i <= 4; i++) {
            // After adding the first two players, ask if they want to add more players
            if (i > 2) {
                if (!gm.askPrompt(LanguageManager.getInstance().getString("continue_adding_players"))) {
                    break;
                }
            }
            String playerName = gm.getUserString(LanguageManager.getInstance().getString("enter_player_name").replace("{player_number}", Integer.toString(i)));
            Player player = PlayerManager.getInstance().createPlayer(playerName, startingBalance);

            // Place player at start
            gm.movePlayerField(player.getID(), 0);
        }

        // Add players to the game queue
        GameManager.getInstance().setupGame(PlayerManager.getInstance().getPlayerIDs());

        // Play the game
        GameManager.getInstance().play();
    }
}
