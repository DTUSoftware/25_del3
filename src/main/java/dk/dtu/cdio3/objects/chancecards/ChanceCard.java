package dk.dtu.cdio3.objects.chancecards;

import java.util.UUID;

public abstract class ChanceCard {
    String cardname;

    public abstract void doCardAction(UUID playerID);
}
