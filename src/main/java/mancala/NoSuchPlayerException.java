package mancala;

public class NoSuchPlayerException extends Exception {
    public NoSuchPlayerException() {
        super("This Player doesn't exist");
    }
}
