package dk.dtu.cdio3.objects.chancecards;

import dk.dtu.cdio3.managers.PlayerManager;

import java.util.UUID;

/**
 * Du løslades uden omkostninger.
 * Behold dette kort, indtil du får brug for det.
 */
public class BailCC extends ChanceCard {
    /**
     * Initiates a new Bail ChanceCard.
     */
    BailCC() {
        super("bail");
    }

    @Override
    public void doCardAction(UUID playerID) {
        PlayerManager.getInstance().getPlayer(playerID).giveBailCard();
    }
}
