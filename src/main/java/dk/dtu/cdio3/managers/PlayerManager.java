package dk.dtu.cdio3.managers;

import dk.dtu.cdio3.objects.Player;
import gui_fields.GUI_Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {
    private static PlayerManager playerManager;

    private HashMap<UUID, Player> players = new HashMap<>();
    private HashMap<UUID, GUI_Player> guiPlayers = new HashMap<>();

    private PlayerManager() {}

    public static PlayerManager getInstance() {
        if (playerManager == null) {
            playerManager = new PlayerManager();
        }

        return playerManager;
    }

    public Player createPlayer(String name) {
        Player player = new Player(name);
        UUID playerID = player.getID();
        players.put(playerID, player);

        GUI_Player guiPlayer = GUIManager.getInstance().createGUIPlayer(player.getName(), player.getBalance());
        guiPlayers.put(playerID, guiPlayer);

        return player;
    }

    public Player createPlayer(String name, int startingBalance) {
        Player player = new Player(name, startingBalance);
        UUID playerID = player.getID();
        players.put(playerID, player);

        GUI_Player guiPlayer = GUIManager.getInstance().createGUIPlayer(player.getName(), player.getBalance());
        guiPlayers.put(playerID, guiPlayer);

        return player;
    }

    public Player getPlayer(UUID playerID) {
        return players.get(playerID);
    }

    public GUI_Player getGUIPlayer(UUID playerID) {
        return guiPlayers.get(playerID);
    }

    public UUID[] getPlayerIDs() {
        return players.keySet().toArray(new UUID[0]);
    }
}
