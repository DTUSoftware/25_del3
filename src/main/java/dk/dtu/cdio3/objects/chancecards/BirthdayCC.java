package dk.dtu.cdio3.objects.chancecards;

import dk.dtu.cdio3.managers.GUIManager;
import dk.dtu.cdio3.managers.GameManager;
import dk.dtu.cdio3.managers.LanguageManager;
import dk.dtu.cdio3.managers.PlayerManager;

import java.util.UUID;

/**
 * Det er din fødselsdag!
 * Alle giver dig M1
 * TILYKKE MED FØDSELDAGEN!
 */
public class BirthdayCC extends ChanceCard {
    private double birthdayWithdrawalAmount = 500.0;

    public BirthdayCC() {
        super("birthday");
    }
    public BirthdayCC(double birthdayWithdrawalAmount) {
        super("birthday");
        this.birthdayWithdrawalAmount = birthdayWithdrawalAmount;
    }

    @Override
    public void doCardAction(UUID playerID) {
        double money = 0.0;
        for (UUID otherPlayerID : PlayerManager.getInstance().getPlayerIDs()) {
            if (otherPlayerID != playerID) {
                if (PlayerManager.getInstance().getPlayer(otherPlayerID).withdraw(birthdayWithdrawalAmount)) {
                    money = money + birthdayWithdrawalAmount;
                }
                else {
                    GUIManager.getInstance().showMessage(LanguageManager.getInstance().getString("could_not_pay_birthday")
                            .replace("{player_name}", PlayerManager.getInstance().getPlayer(otherPlayerID).getName())
                            .replace("{birthday_player_name}", PlayerManager.getInstance().getPlayer(playerID).getName())
                    );
                    GameManager.getInstance().finishGame();
                }
            }
        }
        PlayerManager.getInstance().getPlayer(playerID).deposit(money);
    }
}
