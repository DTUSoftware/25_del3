package dk.dtu.cdio3.objects.chancecards;

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
    MoveOrDrawCC() {
        super("moveordraw");
    }

    @Override
    public void doCardAction(UUID playerID) {

    }
}