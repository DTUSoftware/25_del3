package dk.dtu.cdio3.objects.chancecards;

import java.util.UUID;

/**
 * Giv dette kort til BILEN, og tag et chancekort mere.
 * BIL På din næste tur skal du drøne frem til et hvilket som helst ledigt felt og købe det.
 * Hvis der ikke er nogen ledige felter, skal du købe et fra en anden spiller!
 */

public class CarCC extends ChanceCard {
    /**
     * Initiates a new ChanceCard.
     */
    CarCC() {
        super("car");
    }

    @Override
    public void doCardAction(UUID playerID) {

    }
}

