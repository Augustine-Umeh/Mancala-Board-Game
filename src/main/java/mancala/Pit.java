package mancala;

public class Pit {
    private int stoneCount;

    public Pit() {
        // Constructor implementation
        stoneCount = 0;
    }

    public int getStoneCount() {
        return stoneCount;
    }

    public void addStone() {
        // Add a stone to the pit
        stoneCount += 1;
    }

    public void setStoneCount(){

        stoneCount = 4;
    }
    public int removeStones() {
        // Remove and return the stones from the pit
        int emptyPit = stoneCount;
        stoneCount = 0;
        return emptyPit;
    }

    @Override
    public String toString() {
        // Generate a string representation of the pit
        return "Pit with " + getStoneCount() + " stones";
    }
}
