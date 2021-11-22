package dk.dtu.cdio3.objects.chancecards;

import dk.dtu.cdio3.managers.GUIManager;
import dk.dtu.cdio3.managers.GameManager;

import java.util.UUID;

/**
 * Funktion af kort;
 * Ryk op til
 * 5 felter frem.
 */

public class MoveFieldsCC  extends ChanceCard {
    /**
     * Initiates a new ChanceCard.
     */
    public MoveFieldsCC() {
        super("movefields");
    }

    @Override
    public void doCardAction(UUID playerID) {
        int moveAmount = GUIManager.getInstance().askNumber(1, 5);
        GameManager gm = GameManager.getInstance();
        gm.setPlayerBoardPosition(playerID, (gm.getPlayerPosition(playerID)+moveAmount) % gm.getGameBoard().getFieldAmount(), true);
    }
}