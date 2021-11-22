package dk.dtu.cdio3.managers;

import dk.dtu.cdio3.Game;
import dk.dtu.cdio3.objects.DiceCup;
import dk.dtu.cdio3.objects.GameBoard;
import dk.dtu.cdio3.objects.fields.Field;
import dk.dtu.cdio3.objects.fields.PropertyField;

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
            setPlayerPosition(playerID, 0, false);
            PlayerManager.getInstance().getPlayer(playerID).setBalance(Game.getStartBalance());
        }
        gameFinished = false;
    }

    public void finishGame() {
        gameFinished = true;
    }

    public void play() {
        UUID currentPlayer;
        // Loop until the game finishes
        while (!gameFinished) {
            // Get next player in queue
            currentPlayer = playerQueue.getFirst();

            // Let the player play
            playerPlay(currentPlayer);

            // Remove player from first in queue and put to end
            playerQueue.removeFirst();
            playerQueue.addLast(currentPlayer);
        }

        // find out which player won
        UUID maxPlayer = null;
        UUID otherPlayer = null;
        double maxValue = 0.0;
        for (UUID playerID : PlayerManager.getInstance().getPlayerIDs()) {
            double balance = PlayerManager.getInstance().getPlayer(playerID).getBalance();
            if (balance > maxValue) {
                maxPlayer = playerID;
                maxValue = balance;
                otherPlayer = null;
            }
            else if (balance == maxValue) {
                otherPlayer = playerID;
            }
        }

        if (otherPlayer == null) {
            GUIManager.getInstance().showMessage(
                    LanguageManager.getInstance().getString("player_won_balance")
                            .replace("{player_name}", PlayerManager.getInstance().getPlayer(maxPlayer).getName())
                            .replace("{balance}", Float.toString(Math.round(maxValue)))
            );
        }
        else {
            maxPlayer = null;
            otherPlayer = null;
            maxValue = 0.0;

            for (UUID playerID : PlayerManager.getInstance().getPlayerIDs()) {
                double wealth = PlayerManager.getInstance().getPlayer(playerID).getBalance();

                for (UUID deedID : DeedManager.getInstance().getPlayerDeeds(playerID)) {
                    wealth = wealth + DeedManager.getInstance().getDeed(deedID).getPrice();
                }

                if (wealth > maxValue) {
                    maxPlayer = playerID;
                    maxValue = wealth;
                    otherPlayer = null;
                }
                else if (wealth == maxValue) {
                    otherPlayer = playerID;
                }
            }

            if (otherPlayer == null) {
                GUIManager.getInstance().showMessage(
                        LanguageManager.getInstance().getString("player_won_wealth")
                                .replace("{player_name}", PlayerManager.getInstance().getPlayer(maxPlayer).getName())
                                .replace("{balance}", Float.toString(Math.round(maxValue)))
                );
            }
            else {
                GUIManager.getInstance().showMessage(
                        LanguageManager.getInstance().getString("game_tie")
                                .replace("{player1_name}", PlayerManager.getInstance().getPlayer(maxPlayer).getName())
                                .replace("{player2_name}", PlayerManager.getInstance().getPlayer(otherPlayer).getName())
                );
            }
        }
    }

    private void playerPlay(UUID playerID) {
        GUIManager.getInstance().showMessage(LanguageManager.getInstance().getString("player_turn").replace("{player_name}", PlayerManager.getInstance().getPlayer(playerID).getName()));

        int playerPosition = playerPositions.get(playerID);
        // Do leaving action
        gameBoard.getField(playerPosition%gameBoard.getFieldAmount()).doLeavingAction(playerID);

        if (!Game.debug) {
            GUIManager.getInstance().waitUserRoll();
        }
        diceCup.raffle();

        int[] diceValues = diceCup.getValues();
        GUIManager.getInstance().updateDice(diceValues[0], diceValues[1]);

        // Positions
        int newPlayerPosition = playerPosition+diceCup.getSum();
        playerPositions.put(playerID, newPlayerPosition);

        Field field = GUIManager.getInstance().movePlayerField(playerID, playerPositions.get(playerID)%gameBoard.getFieldAmount());

        // Check for passing start
        if (((int) (playerPosition/gameBoard.getFieldAmount())) < ((int) (newPlayerPosition/gameBoard.getFieldAmount()))) {
            // passed start
            PlayerManager.getInstance().getPlayer(playerID).deposit(Game.getStartPassReward());
            GUIManager.getInstance().showMessage(
                    LanguageManager.getInstance().getString("passed_start")
                            .replace("{player_name}", PlayerManager.getInstance().getPlayer(playerID).getName())
                            .replace("{start_pass_amount}", Double.toString(Game.getStartPassReward()))
            );
        }

        field.doLandingAction(playerID);
    }

    public int getPlayerPosition(UUID playerID) {
        return playerPositions.get(playerID);
    }

    public void setPlayerBoardPosition(UUID playerID, int boardPosition, boolean giveStartReward) {
        setPlayerBoardPosition(playerID, boardPosition, giveStartReward, false);
    }

    public void setPlayerBoardPosition(UUID playerID, int boardPosition, boolean giveStartReward, boolean buyForFree) {
        int oldPlayerPosition = playerPositions.get(playerID);
        int currentBoardPosition = oldPlayerPosition % gameBoard.getFieldAmount();

        if (boardPosition > currentBoardPosition) {
            setPlayerPosition(playerID, oldPlayerPosition+(boardPosition-currentBoardPosition), buyForFree);
        }
        else {
            setPlayerPosition(playerID, oldPlayerPosition+(gameBoard.getFieldAmount()-currentBoardPosition)+boardPosition, buyForFree);
            if (giveStartReward) {
                PlayerManager.getInstance().getPlayer(playerID).deposit(Game.getStartPassReward());
                GUIManager.getInstance().showMessage(
                        LanguageManager.getInstance().getString("passed_start")
                                .replace("{player_name}", PlayerManager.getInstance().getPlayer(playerID).getName())
                                .replace("{start_pass_amount}", Double.toString(Game.getStartPassReward()))
                );
            }
        }
    }

    public void setPlayerPosition(UUID playerID, int playerPosition, boolean buyForFree) {
        playerPositions.put(playerID, playerPosition);
        if (GUIManager.getInstance().guiInitialized()) {
            Field field = GUIManager.getInstance().movePlayerField(playerID, playerPositions.get(playerID)%gameBoard.getFieldAmount());
            if (buyForFree) {
                if (field instanceof PropertyField) {
                    ((PropertyField) field).doLandingAction(playerID, true);
                }
                else {
                    field.doLandingAction(playerID);
                }
            }
            else {
                field.doLandingAction(playerID);
            }
        }
    }
}
