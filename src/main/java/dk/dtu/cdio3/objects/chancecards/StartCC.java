package dk.dtu.cdio3.objects.chancecards;

import dk.dtu.cdio3.managers.GUIManager;
import dk.dtu.cdio3.managers.GameManager;
import dk.dtu.cdio3.managers.LanguageManager;

import java.util.UUID;

/**
 * Funktion af kort;
 * Ryk frem til START
 * Modtag M2
 */

public class StartCC extends ChanceCard {
    /**
     * Initiates a new ChanceCard.
     */
    public StartCC() {
        super("start");
    }

    @Override
    public void doCardAction(UUID playerID) {
        UUID start = GameManager.getInstance().getGameBoard().getFieldIDFromType("start");
        if (start == null) {
            GUIManager.getInstance().showMessage(LanguageManager.getInstance().getString("error_string"));
            return;
        }

        GameManager.getInstance().setPlayerBoardPosition(
                playerID,
                GameManager.getInstance().getGameBoard().getFieldPosition(start),
                true
        );
    }
}