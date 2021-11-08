package dk.dtu.cdio3.managers;

import dk.dtu.cdio3.Game;
import dk.dtu.cdio3.objects.DiceCup;
import dk.dtu.cdio3.objects.GameBoard;
import dk.dtu.cdio3.objects.fields.Field;

import java.util.*;

public class GameManager {
    private static GameManager gameManager;
    private static GameBoard gameBoard;
    private static DiceCup diceCup;

    private Deque<UUID> playerQueue;
    private HashMap<UUID, Integer> playerPositions;
    private boolean gameFinished = false;

    private GameManager() {
        gameBoard = new GameBoard();
        diceCup = new DiceCup();
    }

    public static GameManager getInstance() {
        if (gameManager == null) {
            gameManager = new GameManager();
        }

        return gameManager;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public DiceCup getDiceCup() {
        return diceCup;
    }

    public void setupGame(UUID[] playerIDs) {
        playerQueue = new ArrayDeque<>();
        playerQueue.addAll(Arrays.asList(playerIDs));

        playerPositions = new HashMap<>();
        for (UUID playerID : playerIDs) {
            playerPositions.put(playerID, 0);
        }
    }

    public void play() {
        UUID currentPlayer;
        while (!gameFinished) {
            // Get next player in queue
            currentPlayer = playerQueue.getFirst();

            // Let the player play
            playerPlay(currentPlayer);

            // TODO: check win conditions and change state of gameFinished

            // Remove player from first in queue and put to end
            playerQueue.removeFirst();
            playerQueue.addLast(currentPlayer);
        }
    }

    private void playerPlay(UUID playerID) {
        if (!Game.debug) {
            GUIManager.getInstance().waitUserRoll(PlayerManager.getInstance().getPlayer(playerID).getName());
        }
        diceCup.raffle();

        int[] diceValues = diceCup.getValues();
        GUIManager.getInstance().updateDice(diceValues[0], diceValues[1]);

        // Positions
        int oldPlayerPosition = playerPositions.get(playerID);

        int newPlayerPosition = oldPlayerPosition+diceCup.getSum();
        if (newPlayerPosition >= gameBoard.getFieldAmount()) {
            newPlayerPosition = Math.abs(newPlayerPosition-gameBoard.getFieldAmount());
        }

        playerPositions.put(playerID, newPlayerPosition);
        // TODO: passing start check

        Field field = GUIManager.getInstance().movePlayerField(playerID, playerPositions.get(playerID));
        field.doLandingAction(playerID);
    }
}
