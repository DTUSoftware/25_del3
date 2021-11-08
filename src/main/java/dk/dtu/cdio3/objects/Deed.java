package dk.dtu.cdio3.objects;

import java.util.UUID;

public class Deed {
    private UUID deedID;
    private double price = 0.0;
    private double rent = 0.0;
    private double groupRent = 0.0;

    public Deed() {
        deedID = UUID.randomUUID();
    }

    public UUID getID() {
        return this.deedID;
    }

    public double getPrice() {
        return this.price;
    }

    public double getRent() {
        // TODO: get rent from DeedManager
        return this.rent;
    }

    public void payRent(Player player) {

    }
}
