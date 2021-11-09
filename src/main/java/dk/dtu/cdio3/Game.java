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
    private final static double startingBalance = 12000.0;
    private static double startPassReward = 3000.0;
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
        int amount_of_players = gm.askPlayers();
        for (int i = 1; i <= amount_of_players; i++) {
            String playerName = gm.getUserString(LanguageManager.getInstance().getString("enter_player_name").replace("{player_number}", Integer.toString(i)));
            Player player = PlayerManager.getInstance().createPlayer(playerName, startingBalance);

            // Place player at start
            gm.movePlayerField(player.getID(), 0);

        }

        do {
            // Add players to the game queue
            GameManager.getInstance().setupGame(PlayerManager.getInstance().getPlayerIDs());

            // Play the game
            GameManager.getInstance().play();
        }
        while (GUIManager.getInstance().askPrompt(LanguageManager.getInstance().getString("play_again")));

    }

    public static void setStartPassReward(double startPassReward) {
        Game.startPassReward = startPassReward;
    }

    public static double getStartPassReward() {
        return startPassReward;
    }

    public static double getStartBalance() {
        return startingBalance;
    }
}
