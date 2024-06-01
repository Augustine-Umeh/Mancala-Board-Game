package mancala;

public class InvalidMoveException extends Exception {
    public InvalidMoveException() {
        super("Invalid move.");
    }

    public InvalidMoveException(String msg) {
        super("Invalid move." + msg);
    }
}
