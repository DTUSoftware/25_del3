package dk.dtu.cdio3.objects.chancecards;

import java.util.UUID;

/**
 * Ryk frem til et gult eller lyseblåt felt.
 * Hvis det er ledigt, får du det GRATIS!
 * Ellers skal du BETALE leje til ejeren.
 */

public class LightblueYellowCC  extends ChanceCard {
    /**
     * Initiates a new ChanceCard.
     */
    public LightblueYellowCC() {
        super("lightblueyellow");
    }

    @Override
    public void doCardAction(UUID playerID) {

    }
}