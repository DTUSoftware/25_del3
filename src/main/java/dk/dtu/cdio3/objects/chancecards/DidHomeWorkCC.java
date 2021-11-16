package dk.dtu.cdio3.objects.chancecards;

import dk.dtu.cdio3.managers.PlayerManager;

import java.util.UUID;

/**
 * Du har lavet alle dine lektier!
 * MODTAG M2
 * Fra banken
 */

public class DidHomeWorkCC extends ChanceCard {
    DidHomeWorkCC(String cardName) {
        super(cardName);
    }

    @Override
    public void doCardAction(UUID playerID) {
        PlayerManager.getInstance().getPlayer(playerID).deposit(2000);
    }
}