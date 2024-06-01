package mancala;

import java.util.ArrayList;

public class Board {
    private ArrayList<Pit> pits;
    private ArrayList<Store> stores;
    private boolean isBonus = false;
    private int currentPlayer = 2;
    private int knowPlayer = 0;
    // private Player playerOne;
    // private Player playerTwo;

    public Board() {
        // Constructor implementation
        pits = new ArrayList<>(12);
        stores = new ArrayList<>(2);
        setUpPits();
        setUpStores();
        initializeBoard();
    }

    public void setUpPits() {
        // Initialize 12 empty pits
        for(int i = 0; i < 12; i++){
            pits.add(new Pit());
        }
    }

    public void setUpStores() {
        // Initialize 2 empty stores
        for(int i = 0; i < 2; i++){
            stores.add(new Store());
        }

    }

    public ArrayList<Pit> getPits() {
        return pits;
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public boolean isBonus(){
        return isBonus;
    }

    public void initializeBoard() {
        // Distribute stones in pits

        for (Pit pit : pits) {
            pit.setStoneCount();
        }
    }

    public void resetBoard() {
        // Reset the board by redistributing stones
        
        for(int i = 0; i < 2; i++){
            stores.get(i).emptyStore();
        } 
        initializeBoard();
    }

    public void registerPlayers(Player one, Player two) {
        // Connect players to their stores
        stores.get(0).setOwner(one);
        one.setStore(stores.get(0));

        stores.get(1).setOwner(two);
        two.setStore(stores.get(1));
    }

    public int stoneTotalSide(int pitSide){

        pitSide--;
        int amount = 0;
        if (pitSide >= 0 && pitSide < 6){
            for(int i = 0; i < 6; i++){
                amount += pits.get(i).getStoneCount();
            }
        }else if(pitSide > 6 && pitSide < 13){
            for(int i = 6; i < 12; i++){
                amount += pits.get(i).getStoneCount();
            }
        }
        return amount;
    }
    
    public int moveStones(int startPit, Player player)  throws InvalidMoveException{
        // Move stones for the player starting from a specific pit
        // Determine the current player's side

        startPit--;
        if (startPit < 0 || startPit > 11){
            throw new InvalidMoveException();
        }
        if (player.getStore() == stores.get(0) && (startPit < 0 || startPit > 5)){
            throw new InvalidMoveException("Invalid pit selection.");
        }
        if (player.getStore() == stores.get(1) && (startPit < 6 || startPit > 11)){
            throw new InvalidMoveException("Invalid pit selection.");
        }

        if (player.getStore() == stores.get(0)){
            currentPlayer = 0; //p1
        }else{  
            currentPlayer = 1; //p2
        }
        
        int previousValueOfStore = player.getStoreCount();

        // Distribute stones from the starting pit

        try{
            distributeStones(startPit+1);
        }catch (PitNotFoundException e){
            System.err.println("Error: " + e.getMessage());
        }

        int currentValueOfStore = player.getStoreCount();
        return currentValueOfStore - previousValueOfStore;

    }

    public int distributeStones(int startingPoint)throws PitNotFoundException{
        startingPoint--;
        if (startingPoint < 0 || startingPoint > 11){
            throw new PitNotFoundException();
        }
        int stoneDistribute = pits.get(startingPoint).removeStones();
        int stoneAddedPit = stoneDistribute;
        int i = 0;
        for(i = startingPoint + 1; stoneDistribute > 0; i++){
            i = i % pits.size();

            if (currentPlayer == 0 && i == 6){
                stores.get(currentPlayer).addStones(1);
                stoneDistribute-=1;
            }else if(currentPlayer == 1 && i == 0){
                stores.get(currentPlayer).addStones(1);
                stoneDistribute-=1;
            }
            if (stoneDistribute == 0){
                isBonus = true;
                break;
            } else {
                isBonus = false;
            }
            pits.get(i).addStone();
            stoneDistribute--;

            if (stoneDistribute == 0) {
                break;
            }
            
        }
        try{
            if (pits.get((12 - i) - 1).getStoneCount() > 0){
                if ((currentPlayer == 0 && i >= 0 && i < 6) && (pits.get(i).getStoneCount() == 1)){
                    knowPlayer = 1;
                    captureStones(i+1);
                }else if((currentPlayer == 1 && i >= 6 && i < 12) && pits.get(i).getStoneCount() == 1){
                    knowPlayer = 2;
                    captureStones(i+1);
                }
            }

        }catch (PitNotFoundException e){
            System.err.println(e.getMessage());
        }
        return stoneAddedPit;
    }

    public int captureStones(int stoppingPoint) throws PitNotFoundException{
        // Capture stones from the opponent's pits
        // Get the stopping pit (opponent's pit)
        stoppingPoint--;
        if (stoppingPoint < 0 || stoppingPoint > 11){
            throw new PitNotFoundException();
        }
        int removed = 0;
        int removeOpps = 0;
        // Capture stones from the opponent's pits
        int capturedStones = 0;
        
        removed = pits.get(stoppingPoint).removeStones();
        removeOpps = pits.get((12 - stoppingPoint) - 1).removeStones();
        capturedStones = removeOpps + removed;

        
        if (knowPlayer == 1){
           stores.get(0).addStones(capturedStones);
        }else if (knowPlayer == 2){
            stores.get(1).addStones(capturedStones);
        }
    
        return capturedStones;
    }

    public int getNumStones(int pitNum) throws PitNotFoundException{
        // Get the number of stones in a specific pit
        
        pitNum--;
        if(pitNum >= 0 && pitNum < 12){
            return pits.get(pitNum).getStoneCount();
        }else{
            throw new PitNotFoundException();
        }
    }

    public boolean isSideEmpty(int pitNum) throws PitNotFoundException{
        // Check if one side of the board is empty
        pitNum-=1;
        if(pitNum < 0 || pitNum > 12){
            throw new PitNotFoundException();
        }

        if (pitNum >= 0 && pitNum < 6){
            for(int i = 0; i < 6; i++){
                if (pits.get(i).getStoneCount() != 0){
                    return false;
                }
            }
        }else if(pitNum >= 6 && pitNum < 12){
            for(int i = 6; i < 12; i++){
                if (pits.get(i).getStoneCount() != 0){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        // Generate a string representation of the board
        StringBuilder boardString = new StringBuilder();

        // Player One's Pits
        boardString.append("\t\t\t");
        for (int i = 12; i > 6; i--) {
            boardString.append(" Pit ").append(i).append(": ");
            boardString.append(pits.get(i - 1).getStoneCount()).append(" stones | ");
        }

        boardString.append("\nPlayer Two's Store: ").append(stores.get(1).getTotalStones()).append(" stones |");
        boardString.append("\t----------------------------------------------------------------"
        +"--------------------------------");

        // Player Two's Store
        boardString.append(" | Player One's Store: ").append(stores.get(0).getTotalStones()).append(" stones\n");

        // Player Two's Pits
        boardString.append("\t\t\t");
        for(int i = 1; i<=6; i++){
            boardString.append(" Pit ").append(i).append(": ")
            .append(pits.get(i - 1).getStoneCount()).append(" stones | ");
        }
        return boardString.toString();
    }
}
