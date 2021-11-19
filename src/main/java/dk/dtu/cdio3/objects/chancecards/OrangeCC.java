package dk.dtu.cdio3.objects.chancecards;

import java.util.UUID;

/**
 * Funktion af kort;
 * Ryk frem til et orange felt.
 * Hvis det er ledigt, får du det GRATIS
 * Ellers skal du BETALE leje til ejeren
 */

public class OrangeCC extends ChanceCard {
    /**
     * Initiates a new ChanceCard.
     */
    public OrangeCC() {
        super("orange");
    }

    @Override
    public void doCardAction(UUID playerID) {

    }
}