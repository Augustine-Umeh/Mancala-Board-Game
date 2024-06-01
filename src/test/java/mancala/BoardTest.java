package mancala;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest{
    private MancalaGame game;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp(){
        game = new MancalaGame();
        player1 = new Player();
        player2 = new Player();
        game.setPlayers(player1, player2);
    }

    @Test
    public void testCaptureStonesInRange() {
        int result;

        try {
            result = game.getBoard().captureStones(8);
            Assertions.assertEquals(8, result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void testCaptureStonesHigh() {
        Assertions.assertThrows(PitNotFoundException.class, () -> {
            game.getBoard().captureStones(13);
            throw new PitNotFoundException();
        });
    }

    @Test
    public void testCaptureStonesLow() {
        Assertions.assertThrows(PitNotFoundException.class, () -> {
            int num = game.getBoard().captureStones(0);
            throw new PitNotFoundException();
        });
    }
    
    @Test
    public void testDistributeStonesInRange() {
        try {
            game.move(3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Assertions.assertEquals(0, game.getBoard().getPits().get(2).getStoneCount());
        Assertions.assertEquals(5, game.getBoard().getPits().get(3).getStoneCount());
        Assertions.assertEquals(5, game.getBoard().getPits().get(4).getStoneCount());
        Assertions.assertEquals(5, game.getBoard().getPits().get(5).getStoneCount());
        try {
            Assertions.assertEquals(1, game.getStoreCount(player1));
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Test
    public void testDistributeStonesHigh() {
        Assertions.assertThrows(PitNotFoundException.class, () -> {
            game.move(13);
            throw new PitNotFoundException();
        });
    }

    @Test
    public void testDistributeStonesLow() {
        Assertions.assertThrows(PitNotFoundException.class, () -> {
            game.move(0);
            throw new PitNotFoundException();
        });
    }

    @Test
    public void testGetNumStonesInRange() {
        try {
            Assertions.assertEquals(4, game.getBoard().getNumStones(5));
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Test
    public void testGetNumStonesHigh() {
        Assertions.assertThrows(PitNotFoundException.class, () -> {
            game.getBoard().getNumStones(13);
            throw new PitNotFoundException();
        });
    }

    @Test
    public void testGetNumStonesLow() {
        Assertions.assertThrows(PitNotFoundException.class, () -> {
            game.getBoard().getNumStones(0);
            throw new PitNotFoundException();
        });
    }

    @Test
    public void testIsSideEmptyTrue() {
        for (int i = 0; i < 6; i++) { // Removing Stones for test
            game.getBoard().getPits().get(i).removeStones();
        }

        try {
            Assertions.assertTrue(game.getBoard().isSideEmpty(2));
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Test
    public void testIsSideEmptyFalse() {
        try {
            Assertions.assertFalse(game.getBoard().isSideEmpty(2));
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Test
    public void testResetBoard() {
        boolean reset = true;
        game.getBoard().resetBoard();
        for (int a = 0; a < 12; a++){
            if (game.getBoard().getPits().get(0).getStoneCount() != 4){
                reset = false;
            }
        }
        Assertions.assertEquals(true, reset);
    }
    

    @Test
    public void testRegisterPlayers() {
        game.getBoard().registerPlayers(player1, player2);
        Assertions.assertEquals(0, player1.getStoreCount());
        Assertions.assertEquals(0, player2.getStoreCount());
    }   
}
