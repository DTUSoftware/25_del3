package dk.dtu.cdio3.objects.chancecards;

import dk.dtu.cdio3.managers.GUIManager;
import dk.dtu.cdio3.managers.GameManager;
import dk.dtu.cdio3.managers.LanguageManager;

import java.util.UUID;

/**
 * Ryk frem til
 * Strandpromenaden.
 */
public class BoardWalkCC extends ChanceCard {
    /**
     * Initiates a new ChanceCard.
     */
    public BoardWalkCC() {
        super("boardwalk");
    }

    @Override
    public void doCardAction(UUID playerID) {
        UUID boardWalk = GameManager.getInstance().getGameBoard().getFieldIDFromType("boardwalk");
        if (boardWalk == null) {
            GUIManager.getInstance().showMessage(LanguageManager.getInstance().getString("error_string"));
            return;
        }

        GameManager.getInstance().setPlayerBoardPosition(
                playerID,
                GameManager.getInstance().getGameBoard().getFieldPosition(boardWalk),
                true
        );
    }
}
