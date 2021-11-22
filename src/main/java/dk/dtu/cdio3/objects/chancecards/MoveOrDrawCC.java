package dk.dtu.cdio3.objects.chancecards;

import dk.dtu.cdio3.managers.GUIManager;
import dk.dtu.cdio3.managers.GameManager;
import dk.dtu.cdio3.managers.LanguageManager;
import dk.dtu.cdio3.objects.GameBoard;

import java.util.UUID;

/**
 * Funktion af kort;
 * Ryk 1 felt frem,
 * eller tag et chancekort mere.
 */

public class MoveOrDrawCC  extends ChanceCard {
    /**
     * Initiates a new ChanceCard.
     */
    public MoveOrDrawCC() {
        super("moveordraw");
    }

    @Override
    public void doCardAction(UUID playerID) {
        GameManager gm = GameManager.getInstance();
        boolean move = GUIManager.getInstance().askPrompt(LanguageManager.getInstance().getString("moveordraw_prompt"));
        if (!move) {
            ChanceCard cc = gm.getGameBoard().getChanceCard();
            cc.showCardMessage();
            cc.doCardAction(playerID);
        }
        else {
            gm.setPlayerBoardPosition(playerID, (gm.getPlayerPosition(playerID)+1) % gm.getGameBoard().getFieldAmount(), true);
        }
    }
}