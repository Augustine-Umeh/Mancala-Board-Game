package ui;

import java.util.Scanner;
import java.util.ArrayList;

import mancala.MancalaGame;
import mancala.GameNotOverException;
import mancala.Player;
import mancala.InvalidMoveException;

public class TextUI {
    private MancalaGame game = new MancalaGame();
    private Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        TextUI textUI = new TextUI();

        try{
            textUI.start();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        
    }

    public void start() {
        inputPlayerNames();
        while (true) {
            printBoard();

            if (game.isGameOver()) {
                displayGameResult();
                break;
            }

            try {
                makeMove();
            } catch (InvalidMoveException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

    }

    public int makeMove() throws InvalidMoveException {
        int pitNum = getPitNumber();
        int stonesMoved = 0;
        try {
            stonesMoved = game.move(pitNum);
        } catch (InvalidMoveException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return stonesMoved;
    }

    private void inputPlayerNames(){
        System.out.println("Enter player 1 Name: ");
        String one = scanner.nextLine();
        System.out.println("Enter player 2 Name: ");
        String two = scanner.nextLine();

        Player playerOne = new Player(one);
        Player playerTwo = new Player(two);

        initializeBoard(playerOne, playerTwo);
    }

    private void displayGameResult() {
        Player winner = null;
        try {
            winner = game.getWinner();
        } catch (GameNotOverException e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        if (winner != null) {
            System.out.println(winner.getName() + " wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }

    private int getPitNumber() {
        System.out.println("Current player: " + game.getCurrentPlayer().getName());
    
        ArrayList<Player> players = game.getPlayers();
        if (!players.isEmpty()) {
            String prompt = (game.getCurrentPlayer() == players.get(0))
                ? "Enter the pit number to make a move (1-6): "
                : "Enter the pit number to make a move (7-12): ";
    
            while (true) {
                System.out.print(prompt);
    
                if (scanner.hasNextInt()) {
                    int pitNumber = scanner.nextInt();
                    
                    if (pitNumber >= 1 && pitNumber <= 12) {
                        return pitNumber;
                    } else {
                        System.out.println("Invalid pit number. Please enter a number between 1 and 12.");
                    }
                } else {
                    // throw new PitNotFoundException();
                    System.out.println("Invalid input. Please enter a valid integer.");
                    scanner.next(); // Consume the invalid input
                }
            }
        } else {
            System.out.println("No players are available.");
            inputPlayerNames();
            return 0;
        }
    }

    private void initializeBoard(Player playerOne, Player playerTwo){
        game.setPlayers(playerOne, playerTwo);
    }

    private void printBoard() {
        System.out.println("Current Mancala Board:");
        System.out.println(game.toString());
    }
}
