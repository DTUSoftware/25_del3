package dk.dtu.cdio3.objects.chancecards;

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

    }
}