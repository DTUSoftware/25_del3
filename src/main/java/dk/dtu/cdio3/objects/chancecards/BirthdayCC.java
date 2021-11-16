package dk.dtu.cdio3.objects.chancecards;

import dk.dtu.cdio3.managers.PlayerManager;

import java.util.UUID;

/**
 * Det er din fødselsdag!
 * Alle giver dig M1
 * TILYKKE MED FØDSELDAGEN!
 */

public class BirthdayCC extends ChanceCard {
    BirthdayCC(String cardName) {
        super(cardName);
    }

    @Override
    public void doCardAction(UUID playerID) {

    }
}
