package dk.dtu.cdio3.objects.chancecards;

import dk.dtu.cdio3.managers.*;
import java.util.UUID;


/**
 * Du har spist
 * for meget slik
 * BETAL 2000 til banken
 */
public class EatCandyCC extends ChanceCard{
    @Override
    public void doCardAction(UUID playerID){

        if (!PlayerManager.getInstance().getPlayer(playerID).withdraw(2000)) {
            GameManager.getInstance().finishGame();
        }

    }
}
