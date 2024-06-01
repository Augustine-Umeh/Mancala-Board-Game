package mancala;

public class Player {
    private Store store;
    private String playerName;

    public Player() {
        // Constructor implementation
        playerName = "Default Player";
    }

    public Player(String name) {
        // Constructor implementation
        playerName = name;
    }                                           

    public void setStore(Store newStore) {
        // Set the player's store
        this.store = newStore;
    }

    public String getName() {
        return playerName;
    }

    public void setName(String name) {
        // Set the player's name
        playerName = name;
    }

    public Store getStore() {
        return store;
    }

    public int getStoreCount() {
        // Get the count of stones in the player's store
        return store.getTotalStones();
        
    }

    @Override
    public String toString() {
        // Generate a string representation of the player
        return "Player Name: " + playerName;
    }
}
