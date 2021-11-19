package dk.dtu.cdio3.objects.chancecards;

import java.util.UUID;

/**
 * Ryk frem til et orange eller blåt felt.
 * Hvis det er ledigt, får du det GRATIS!
 * Ellers skal du BETALE leje til ejeren.
 */

public class SalmonGreenCC extends ChanceCard {
    /**
     * Initiates a new ChanceCard.
     */
    public SalmonGreenCC() {
        super("salmongreen");
    }

    @Override
    public void doCardAction(UUID playerID) {

    }
}