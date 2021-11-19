package dk.dtu.cdio3.objects.chancecards;

import java.util.UUID;

/**
 * Ryk frem til et lyseblåt felt.
 * Hvis det er ledigt, får du det GRATIS!
 * Ellers skal du BETALE leje til ejeren
 */

public class LightBlueCC  extends ChanceCard {
    /**
     * Initiates a new ChanceCard.
     */
    LightBlueCC() {
        super("lightblue");
    }

    @Override
    public void doCardAction(UUID playerID) {

    }
}