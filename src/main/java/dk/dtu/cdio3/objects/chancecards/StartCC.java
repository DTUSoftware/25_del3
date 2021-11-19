package dk.dtu.cdio3.objects.chancecards;

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

    }
}