import static
        org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Boardtest {

    private Board board;
    private Board.Place[][] boardMatrix;
    private Player testPlayer;

    @BeforeEach
    void setUp(){
        testPlayer = new Player("test");
        board = new Board(10);
        boardMatrix = this.board.boardMatrix();
        board.arrayFill();
    }


    @Test
    @DisplayName("Check if a board is full, where a draw would be called.")
    void testisFull(){
        assertFalse(board.isFull());
    }

    @Test
    @DisplayName("Test if a stone is properly placed.")
    void testplaceStone(){
        board.placeStone(0, 0, testPlayer);
        assertTrue(board.isOccupiedBy(0, 0, testPlayer));
    }

    @Test
    @DisplayName("Check if an intersection on a board is empty.")
    void testisEmpty(){
        assertTrue(board.isEmpty(0, 0));
    }

    @Test
    @DisplayName("Check if an intersection on a board is occupied.")
    void testisOccupied(){
        board.placeStone(0, 0, testPlayer);
        assertTrue(board.isOccupied(0, 0));
    }

    @Test
    @DisplayName("Check if an interection on a board is occupied by a certain player.")
    void testisOccupiedBy(){
        board.placeStone(0, 0, testPlayer);
        assertTrue(board.isOccupiedBy(0, 0, testPlayer));
    }

    @Test
    @DisplayName("Check the player at a certain intersection.")
    void testplayerAt(){
        board.placeStone(0, 0, testPlayer);
        assertEquals(testPlayer, board.playerAt(0, 0));
    }

    @Test
    @DisplayName("Check if a winning row exists on the board for testPlayer.")
    void testisWonBy(){
        board.placeStone(0, 0, testPlayer);
        board.placeStone(0, 1, testPlayer);
        board.placeStone(0, 2, testPlayer);
        board.placeStone(0, 3, testPlayer);
        board.placeStone(0, 4, testPlayer);
        assertTrue(board.isWonBy(testPlayer));
        board.placeStone(0, 4, new Player("test2"));
        assertFalse(board.isWonBy(testPlayer));
    }
}

