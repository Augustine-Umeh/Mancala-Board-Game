package mancala;

public class Store {
    private Player owner;
    private int totalStones;

    public Store() {
        // Constructor implementation
        this.totalStones = 0;
    }

    public void setOwner(Player player) {
        // Set the owner of the store
        owner = player;
    }

    public Player getOwner() {
        return owner;
    }

    public void addStones(int amount) {
        // Add stones to the store
        totalStones += amount;
    }

    public int getTotalStones() {
        return totalStones;
    }

    public int emptyStore() {
        // Empty the store and return the number of stones
        int emptyStore = totalStones;
        totalStones = 0;
        return emptyStore;
    }

    @Override
    public String toString() {
        // Generate a string representation of the store
        return "Store owned by " + owner.getName() + ": " + totalStones + " stones";
    }
}
