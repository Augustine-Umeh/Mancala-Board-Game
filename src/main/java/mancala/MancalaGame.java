package mancala;

import java.util.ArrayList;

public class MancalaGame {
    private Board board;
    private ArrayList<Player> players;
    private Player currentPlayer;
    // private Player winner;

    public MancalaGame() {
        // Constructor implementation
        board = new Board();
        players = new ArrayList<>(2);
    }

    public void setPlayers(Player onePlayer, Player twoPlayer) {
        // Set the players for the game
        players.add(onePlayer);
        players.add(twoPlayer);

        board.registerPlayers(onePlayer, twoPlayer);
        currentPlayer = players.get(0);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player player) {
        // Set the current player
        currentPlayer = player;
    }

    public void setBoard(Board theBoard) {
        // Set the game board
        board = theBoard;
    }

    public Board getBoard() {
        return board;
    }

    public int getNumStones(int pitNum) throws PitNotFoundException{
        // Get the number of stones in a specific pit
        pitNum--;
        if(pitNum >= 0 && pitNum < board.getPits().size()){
            return board.getNumStones(pitNum+1);
        }else{
            throw new PitNotFoundException();
        }
    }

    public int move(int startPit) throws InvalidMoveException {
        
        if(isGameOver()){
            throw new InvalidMoveException("Game is over");
        }

        try{
            if(board.getNumStones(startPit) == 0){
                throw new InvalidMoveException("Selected pit is empty.");
            }
        }catch (PitNotFoundException e){
            System.err.println("Error: " + e.getMessage());
            return board.stoneTotalSide(startPit);
        }

        try{
            board.moveStones(startPit, currentPlayer);
        } catch (InvalidMoveException e){
            System.out.println("Error1: " + e.getMessage());
            return board.stoneTotalSide(startPit);
        }

        if(currentPlayer == players.get(0) && !board.isBonus()){
            currentPlayer = players.get(1);
        }else if (currentPlayer == players.get(1) && !board.isBonus()){
            currentPlayer = players.get(0);
        }

        return board.stoneTotalSide(startPit);
    }

    public int getStoreCount(Player player) throws NoSuchPlayerException{
        // Get the total number of stones in a player's store
        
        if (player != null && player.getStore() != null) {
            return player.getStore().getTotalStones();
        } else {
            throw new NoSuchPlayerException();
        }
    }

    public Player getWinner() throws GameNotOverException {

        int playerOneStoreCount = 0;
        int playerTwoStoreCount = 0;

        try{
            playerOneStoreCount = getStoreCount(players.get(0));
            playerTwoStoreCount = getStoreCount(players.get(1));
        }catch (NoSuchPlayerException e){
            System.err.println("Error: " + e.getMessage());
        }

        playerOneStoreCount += board.stoneTotalSide(2);
        playerTwoStoreCount += board.stoneTotalSide(10);



        if(!isGameOver()){
            throw new GameNotOverException();
        }else{
            if (playerOneStoreCount > playerTwoStoreCount){
                return players.get(0);
            }else if(playerTwoStoreCount > playerOneStoreCount){
                return players.get(1);
            }
            return null;
        }     
    }

    public boolean isGameOver() {
        // Check if the game is over
        try {
            if(board.isSideEmpty(3) || board.isSideEmpty(8)){
                return true;
            }
        } catch (PitNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return false;
    }

    public void startNewGame() {
        // Start a new game
        board.resetBoard();
    }

    @Override
    public String toString() {
        // Generate a string representation of the game
        return board.toString();
    }
}
