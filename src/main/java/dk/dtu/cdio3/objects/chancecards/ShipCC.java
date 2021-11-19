package dk.dtu.cdio3.objects.chancecards;

import java.util.UUID;

/**
 * Giv dette kort til SKIBET, og tag et chancekort mere.
 * SKIBET På din næste tur skal du drøne frem til et hvilket som helst ledigt felt og købe det.
 * Hvis der ikke er nogen ledige felter, skal du købe et fra en anden spiller!
 */

public class ShipCC extends ChanceCard {
    /**
     * Initiates a new ChanceCard.
     */
    public ShipCC() {
        super("ship");
    }

    @Override
    public void doCardAction(UUID playerID) {

    }
}