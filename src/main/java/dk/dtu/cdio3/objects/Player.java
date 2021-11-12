package dk.dtu.cdio3.objects;

import dk.dtu.cdio3.managers.GUIManager;

import java.util.UUID;

public class Player {
    private final String name;
    private final UUID playerID;
    private final Account account;
    private boolean jailed = false;

    public Player(String name) {
        this.name = name;
        this.playerID = UUID.randomUUID();
        this.account = new Account();
    }

    public Player(String name, double startingBalance) {
        this.name = name;
        this.playerID = UUID.randomUUID();
        this.account = new Account(startingBalance);
    }

    public static boolean guiInitialized() {
        return GUIManager.getInstance() != null;
    }

    public String getName() {
        return name;
    }

    public boolean withdraw(double amount) {
        boolean success = account.withdraw(amount);
        // update the GUI
        if (guiInitialized()) {
            GUIManager.getInstance().setPlayerBalance(playerID, getBalance());
        }
        return success;
    }

    public void deposit(double amount) {
        account.deposit(amount);
        // update the GUI
        if (guiInitialized()) {
            GUIManager.getInstance().setPlayerBalance(playerID, getBalance());
        }
    }

    public void setBalance(double balance) {
        account.setBalance(balance);
        // update the GUI
        if (guiInitialized()) {
            GUIManager.getInstance().setPlayerBalance(playerID, getBalance());
        }
    }

    private void setJailed(boolean jailed) {
        this.jailed = jailed;
    }

    public void jail() {
        setJailed(true);
    }

    public void unJail() {
        setJailed(false);
    }

    public boolean isJailed() {
        return jailed;
    }

    public double getBalance() {
        return account.getBalance();
    }

    public UUID getID() {
        return this.playerID;
    }
}