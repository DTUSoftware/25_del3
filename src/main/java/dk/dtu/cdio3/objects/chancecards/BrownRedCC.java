package dk.dtu.cdio3.objects.chancecards;

import dk.dtu.cdio3.managers.GUIManager;
import dk.dtu.cdio3.managers.GameManager;
import dk.dtu.cdio3.managers.LanguageManager;

import java.awt.*;
import java.util.UUID;

/**
 * Ryk frem til et brunt eller rødt felt.
 * Hvis det er ledigt, får du det GRATIS!
 * Ellers skal du BETALE leje til ejeren.
 */

public class BrownRedCC  extends ChanceCard {
    /**
     * Initiates a new ChanceCard.
     */
    public BrownRedCC() {
        super("brownred");
    }

    @Override
    public void doCardAction(UUID playerID) {
        UUID fieldID = GameManager.getInstance().getGameBoard().getNextFieldIDWithColor(
                playerID,
                new Color[] { Color.RED, Color.decode("#9a5013") }
        );
        if (fieldID == null) {
            GUIManager.getInstance().showMessage(LanguageManager.getInstance().getString("error_string"));
            System.out.println("Field ID is null!");
            return;
        }

        GameManager.getInstance().setPlayerBoardPosition(
                playerID,
                GameManager.getInstance().getGameBoard().getFieldPosition(fieldID),
                true,
                true
        );
    }
}