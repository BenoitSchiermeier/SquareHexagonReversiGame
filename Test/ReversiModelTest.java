import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import cs3500.reversi.model.Hexagon;
import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiBoard;
import cs3500.reversi.model.ReversiMutableModel;
import cs3500.reversi.textualview.ReversiModelTextView;
import cs3500.reversi.textualview.TextualView;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the ReversiBoard class, testing various game scenarios and
 * ensuring the game adheres to the established rules of Reversi.
 */
public class ReversiModelTest {

  ReversiBoard board;
  TextualView view;

  /**
   * Initializes the testing environment before each test.
   * Sets up a new ReversiBoard with a size of 4 and creates a textual view
   * of the board to assist in visualizing the game state during tests.
   */
  @Before
  public void setup() {
    board = new ReversiBoard(4);
    board.startGame();
    view = new ReversiModelTextView(board);
  }

  @Test
  public void testGetBoardSize() {
    assertEquals(board.getBoardSize(), 4);
  }

  @Test
  public void testInvalidBoardSize() {
    assertThrows(IllegalArgumentException.class,
        () -> new ReversiBoard(2));
  }

  @Test
  public void testNegativeInvalidBoardSize() {
    assertThrows(IllegalArgumentException.class,
        () -> new ReversiBoard(-1));
    assertThrows(IllegalArgumentException.class,
        () -> new ReversiBoard(0));
  }

  @Test
  public void testNegativeMoveCoordinate() {
    assertThrows(IllegalStateException.class,
        () -> board.play(-1, -1, HexagonPlayer.BLACK));
  }


  @Test
  public void makeBoardCopyCorrectlyCopies() {
    board.play(4, 1, HexagonPlayer.BLACK);

    ReversiBoard newBoard = new ReversiBoard(board);
    TextualView textualNewBoard = new ReversiModelTextView(newBoard);

    // makes sure that the board was copied correctly, after a move was played on it:
    assertEquals(textualNewBoard.arrayHexString(),
        "      _   _   _   _   \n"
        + "    _   _   B   _   _   \n"
        + "  _   _   B   B   _   _   \n"
        + "_   _   W   _   B   _   _   \n"
        + "  _   _   B   W   _   _   \n"
        + "    _   _   _   _   _   \n"
        + "      _   _   _   _   \n"
        + "Black Score = 5\n"
        + "White Score = 2\n");
  }

  @Test
  public void copyInvalidBoard() {
    assertThrows(IllegalArgumentException.class, () -> new ReversiBoard(null));
  }


  @Test
  public void copyBoardDoesNotChangeOriginal() {
    ReversiBoard newBoard = new ReversiBoard(board);
    TextualView textualNewBoard = new ReversiModelTextView(newBoard);
    newBoard.play(4, 1, HexagonPlayer.BLACK);

    // assert that the original view has not changed when the copy gets changed
    assertEquals(view.arrayHexString(),
        "      _   _   _   _   \n"
            + "    _   _   _   _   _   \n"
            + "  _   _   B   W   _   _   \n"
            + "_   _   W   _   B   _   _   \n"
            + "  _   _   B   W   _   _   \n"
            + "    _   _   _   _   _   \n"
            + "      _   _   _   _   \n"
            + "Black Score = 3\n"
            + "White Score = 3\n");
  }


  @Test
  public void testGameStartsProperlyUsingTextualView() {

    assertEquals(view.arrayTo2dArrayString(),
        "n  n  n  _  _  _  _  \n"
            + "n  n  _  _  _  _  _  \n"
            + "n  _  _  B  W  _  _  \n"
            + "_  _  W  _  B  _  _  \n"
            + "_  _  B  W  _  _  n  \n"
            + "_  _  _  _  _  n  n  \n"
            + "_  _  _  _  n  n  n  \n"
            + "Black Score = 3\n"
            + "White Score = 3\n");

    assertEquals(view.arrayHexString(),
        "      _   _   _   _   \n"
            + "    _   _   _   _   _   \n"
            + "  _   _   B   W   _   _   \n"
            + "_   _   W   _   B   _   _   \n"
            + "  _   _   B   W   _   _   \n"
            + "    _   _   _   _   _   \n"
            + "      _   _   _   _   \n"
            + "Black Score = 3\n"
            + "White Score = 3\n");

  }

  @Test
  public void testStartGameWithDifferentBoardSizeTextualView() {
    ReversiBoard board = new ReversiBoard(10);
    TextualView view = new ReversiModelTextView(board);

    assertEquals(view.arrayHexString(),
        "                  _   _   _   _   _   _   _   _   _   _   \n"
            + "                _   _   _   _   _   _   _   _   _   _   _   \n"
            + "              _   _   _   _   _   _   _   _   _   _   _   _   \n"
            + "            _   _   _   _   _   _   _   _   _   _   _   _   _   \n"
            + "          _   _   _   _   _   _   _   _   _   _   _   _   _   _   \n"
            + "        _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   \n"
            + "      _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   \n"
            + "    _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   \n"
            + "  _   _   _   _   _   _   _   _   B   W   _   _   _   _   _   _   _   _   \n"
            + "_   _   _   _   _   _   _   _   W   _   B   _   _   _   _   _   _   _   _   \n"
            + "  _   _   _   _   _   _   _   _   B   W   _   _   _   _   _   _   _   _   \n"
            + "    _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   \n"
            + "      _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   \n"
            + "        _   _   _   _   _   _   _   _   _   _   _   _   _   _   _   \n"
            + "          _   _   _   _   _   _   _   _   _   _   _   _   _   _   \n"
            + "            _   _   _   _   _   _   _   _   _   _   _   _   _   \n"
            + "              _   _   _   _   _   _   _   _   _   _   _   _   \n"
            + "                _   _   _   _   _   _   _   _   _   _   _   \n"
            + "                  _   _   _   _   _   _   _   _   _   _   \n"
            + "Black Score = 3\n"
            + "White Score = 3\n");

    // uneven board size:
    ReversiBoard board2 = new ReversiBoard(5);
    TextualView view2 = new ReversiModelTextView(board2);
    assertEquals(view2.arrayHexString(),
        "        _   _   _   _   _   \n"
            + "      _   _   _   _   _   _   \n"
            + "    _   _   _   _   _   _   _   \n"
            + "  _   _   _   B   W   _   _   _   \n"
            + "_   _   _   W   _   B   _   _   _   \n"
            + "  _   _   _   B   W   _   _   _   \n"
            + "    _   _   _   _   _   _   _   \n"
            + "      _   _   _   _   _   _   \n"
            + "        _   _   _   _   _   \n"
            + "Black Score = 3\n"
            + "White Score = 3\n");
  }


  @Test
  public void testMakeValidMoveChangesGameState() {
    board.play(4, 1, HexagonPlayer.BLACK);
    board.play(5, 0, HexagonPlayer.WHITE);
    board.play(4, 0, HexagonPlayer.BLACK);

    // try to make valid move but wrong player turn
    assertThrows(IllegalStateException.class,
        () -> board.play(1, 4, HexagonPlayer.BLACK));

    board.play(1, 4, HexagonPlayer.WHITE);

    board.play(0, 5, HexagonPlayer.BLACK);
    board.play(0, 4, HexagonPlayer.WHITE);
    board.play(0, 3, HexagonPlayer.BLACK);
    board.play(2, 2, HexagonPlayer.WHITE);
    board.play(4, 4, HexagonPlayer.BLACK);
    board.play(2, 5, HexagonPlayer.WHITE);
    board.play(1, 6, HexagonPlayer.BLACK);
    board.play(2, 6, HexagonPlayer.WHITE);
    board.play(6, 0, HexagonPlayer.BLACK);
    board.play(5, 2, HexagonPlayer.WHITE);
    board.play(3, 6, HexagonPlayer.BLACK);
    board.play(4, 5, HexagonPlayer.WHITE);
    board.play(2, 1, HexagonPlayer.BLACK);
    board.play(1, 2, HexagonPlayer.WHITE);
    board.play(5, 4, HexagonPlayer.BLACK);
    board.play(3, 0, HexagonPlayer.WHITE);

    // assures that when white cannot move (independent of player turn) because there is no where
    // for white player to go:
    assertFalse(board.canMove(HexagonPlayer.WHITE));

    board.play(6, 1, HexagonPlayer.BLACK);

    board.play(6, 2, HexagonPlayer.WHITE);
    assertTrue(board.canMove(HexagonPlayer.BLACK));

    board.play(6, 3, HexagonPlayer.BLACK);

    // CHECK THAT gameOver IS TRUE :
    assertTrue(board.isGameOver());

    assertFalse(board.canMove(HexagonPlayer.BLACK));
    assertFalse(board.canMove(HexagonPlayer.WHITE));

    assertEquals(board.getScore(HexagonPlayer.BLACK), 22);
    assertEquals(board.getScore(HexagonPlayer.WHITE), 7);
  }


  @Test
  public void testCheckTextualViewAfterGameOver() {
    board.play(4, 1, HexagonPlayer.BLACK);
    board.play(5, 0, HexagonPlayer.WHITE);
    board.play(4, 0, HexagonPlayer.BLACK);
    board.play(1, 4, HexagonPlayer.WHITE);
    board.play(0, 5, HexagonPlayer.BLACK);
    assertEquals(view.arrayHexString(),
        "      _   B   W   _   \n"
            + "    _   _   B   _   _   \n"
            + "  _   _   B   B   _   _   \n"
            + "_   _   B   _   B   _   _   \n"
            + "  _   B   W   W   _   _   \n"
            + "    B   _   _   _   _   \n"
            + "      _   _   _   _   \n"
            + "Black Score = 8\n"
            + "White Score = 3\n");

    board.play(0, 4, HexagonPlayer.WHITE);
    assertEquals(view.arrayHexString(),
        "      _   B   W   _   \n"
            + "    _   _   B   _   _   \n"
            + "  _   _   B   B   _   _   \n"
            + "_   _   B   _   B   _   _   \n"
            + "  W   W   W   W   _   _   \n"
            + "    B   _   _   _   _   \n"
            + "      _   _   _   _   \n"
            + "Black Score = 7\n"
            + "White Score = 5\n");
    assertEquals(board.getScore(HexagonPlayer.BLACK), 7);
    assertEquals(board.getScore(HexagonPlayer.WHITE), 5);

    board.play(0, 3, HexagonPlayer.BLACK);
    assertEquals(view.arrayHexString(),
        "      _   B   W   _   \n"
            + "    _   _   B   _   _   \n"
            + "  _   _   B   B   _   _   \n"
            + "B   _   B   _   B   _   _   \n"
            + "  B   W   W   W   _   _   \n"
            + "    B   _   _   _   _   \n"
            + "      _   _   _   _   \n"
            + "Black Score = 9\n"
            + "White Score = 4\n");

    board.play(2, 2, HexagonPlayer.WHITE);
    assertEquals(view.arrayHexString(),
        "      _   B   W   _   \n"
            + "    _   _   B   _   _   \n"
            + "  _   W   B   B   _   _   \n"
            + "B   _   W   _   B   _   _   \n"
            + "  B   W   W   W   _   _   \n"
            + "    B   _   _   _   _   \n"
            + "      _   _   _   _   \n"
            + "Black Score = 8\n"
            + "White Score = 6\n");

    board.play(4, 4, HexagonPlayer.BLACK);
    board.play(2, 5, HexagonPlayer.WHITE);
    board.play(1, 6, HexagonPlayer.BLACK);
    board.play(2, 6, HexagonPlayer.WHITE);
    board.play(6, 0, HexagonPlayer.BLACK);

    board.play(5, 2, HexagonPlayer.WHITE);
    board.play(3, 6, HexagonPlayer.BLACK);
    board.play(4, 5, HexagonPlayer.WHITE);
    board.play(2, 1, HexagonPlayer.BLACK);
    board.play(1, 2, HexagonPlayer.WHITE);
    board.play(5, 4, HexagonPlayer.BLACK);

    // makes sure that move that is out of bounds throws
    assertThrows(IllegalStateException.class, () -> board.play(2, 0, HexagonPlayer.WHITE));
    board.play(3, 0, HexagonPlayer.WHITE);
    board.play(6, 1, HexagonPlayer.BLACK);
    board.play(6, 2, HexagonPlayer.WHITE);
    board.play(6, 3, HexagonPlayer.BLACK);

    // check the textual view
    assertEquals(view.arrayHexString(),
        "      W   B   B   B   \n"
            + "    W   _   B   _   B   \n"
            + "  W   W   W   W   W   B   \n"
            + "B   _   B   _   B   _   B   \n"
            + "  B   B   B   B   B   B   \n"
            + "    B   _   B   _   B   \n"
            + "      _   B   B   B   \n"
            + "Black Score = 22\n"
            + "White Score = 7\n");

  }


  @Test
  public void testMoveOnTopOfOtherPlayer() {
    board.play(4, 1, HexagonPlayer.BLACK);

    // move placed on top of existing player should throw
    assertThrows(IllegalStateException.class,
        () -> board.play(4, 1, HexagonPlayer.WHITE));

    assertFalse(board.isGameOver());
  }

  @Test
  public void testMoveOutOfTurnOnStart() {
    assertThrows(IllegalStateException.class,
        () -> board.play(4, 1, HexagonPlayer.WHITE));
  }

  @Test
  public void testMovesNotAllowableByGame() {
    ReversiMutableModel board = new ReversiBoard(6);
    assertThrows(IllegalStateException.class,
        () -> board.play(0, 0, HexagonPlayer.WHITE));

    // move to a null tile on the 2d list:
    assertThrows(IllegalStateException.class,
        () -> board.play(0, 0, HexagonPlayer.BLACK));

  }

  @Test
  public void testMoveNullPlayer() {
    assertThrows(IllegalArgumentException.class,
        () -> board.play(4, 1, null));

    assertThrows(IllegalArgumentException.class,
        () -> board.play(4, 1, null));

  }

  @Test
  public void testMoveWithInvalidPlayer() {
    assertThrows(IllegalArgumentException.class,
        () -> board.play(4, 1, HexagonPlayer.NONE));
  }


  @Test
  public void testGetScore() {
    assertEquals(board.getScore(HexagonPlayer.BLACK), 3);
    assertEquals(board.getScore(HexagonPlayer.WHITE), 3);
  }

  @Test
  public void testGetCurrentPlayerAfterMove() {
    assertEquals(board.getCurrentPlayer(), HexagonPlayer.BLACK);
    board.play(4, 1, HexagonPlayer.BLACK);
    assertEquals(board.getCurrentPlayer(), HexagonPlayer.WHITE);

  }

  @Test
  public void testInValidMoveDueToGameLogic() {
    board.play(4, 1, HexagonPlayer.BLACK);
    board.play(5, 0, HexagonPlayer.WHITE);
    board.play(4, 0, HexagonPlayer.BLACK);

    // does not sandwich other white tile
    assertThrows(IllegalStateException.class,
        () -> board.play(4, 4, HexagonPlayer.WHITE));

    assertEquals(board.getCurrentPlayer(), HexagonPlayer.WHITE);
    // valid move but not black turn
    assertThrows(IllegalStateException.class,
        () -> board.play(2, 5, HexagonPlayer.BLACK));

    // placed at bottom right, not valid bc does not sandwich anything
    assertThrows(IllegalStateException.class,
        () -> board.play(3, 6, HexagonPlayer.WHITE));

  }

  @Test
  public void testCanMove() {
    board.play(4, 1, HexagonPlayer.BLACK);
    board.play(5, 0, HexagonPlayer.WHITE);
    board.play(4, 0, HexagonPlayer.BLACK);

    assertTrue(board.canMove(HexagonPlayer.BLACK));
    assertTrue(board.canMove(HexagonPlayer.WHITE));

  }


  @Test
  public void testPlaceHexOnOtherHex() {
    board.play(4, 1, HexagonPlayer.BLACK);
    assertThrows(IllegalStateException.class,
        () -> board.play(4, 1, HexagonPlayer.WHITE));
  }


  @Test
  public void testEndGameBecauseOf2PassesInARow() {
    // pass starting black player
    board.pass();
    assertEquals(HexagonPlayer.WHITE, board.getCurrentPlayer());
    // pass white player
    board.pass();
    assertEquals(HexagonPlayer.BLACK, board.getCurrentPlayer());

    // 2 passes in a row the game ends
    assertTrue(board.isGameOver());
  }

  @Test
  public void testPassPlayPassDoesNotEndGame() {
    // pass starting black player
    board.pass();
    assertEquals(HexagonPlayer.WHITE, board.getCurrentPlayer());

    // valid move
    board.play(4, 1, HexagonPlayer.WHITE);

    // pass black player
    board.pass();
    assertEquals(HexagonPlayer.WHITE, board.getCurrentPlayer());

    // not 2 passes in a row because move was between passes
    assertFalse(board.isGameOver());
  }


  // test to make sure that game works with different board size:
  @Test
  public void testDifferentBoardSizeEqual6() {
    ReversiBoard board = new ReversiBoard(6);
    board.startGame();
    TextualView tv = new ReversiModelTextView(board);
    board.play(6, 3, HexagonPlayer.BLACK);
    board.play(7, 2, HexagonPlayer.WHITE);
    board.play(6, 2, HexagonPlayer.BLACK);

    // make sure moving to invalid coordinates throws exception
    assertThrows(IllegalStateException.class, () -> board.play(4, 0, HexagonPlayer.WHITE));
    assertThrows(IllegalStateException.class, () -> board.play(1, 3, HexagonPlayer.WHITE));

    assertEquals(tv.arrayHexString(),
        "          _   _   _   _   _   _   \n"
            + "        _   _   _   _   _   _   _   \n"
            + "      _   _   _   B   W   _   _   _   \n"
            + "    _   _   _   _   B   _   _   _   _   \n"
            + "  _   _   _   _   W   B   _   _   _   _   \n"
            + "_   _   _   _   W   _   B   _   _   _   _   \n"
            + "  _   _   _   _   B   W   _   _   _   _   \n"
            + "    _   _   _   _   _   _   _   _   _   \n"
            + "      _   _   _   _   _   _   _   _   \n"
            + "        _   _   _   _   _   _   _   \n"
            + "          _   _   _   _   _   _   \n"
            + "Black Score = 5\n"
            + "White Score = 4\n");

    assertFalse(board.isGameOver());

    // makes sure the getScoreMethodWorks
    assertEquals(5, board.getScore(HexagonPlayer.BLACK));
    assertEquals(4, board.getScore(HexagonPlayer.WHITE));
  }

  @Test
  public void testGetHexListNotSameObject() {
    Hexagon[][] hexListCopy = board.getHexList();
    assertNotSame(board.getHexList(), hexListCopy);
  }


  @Test
  public void testHexagonToStringRepresentation() {
    Hexagon hex = new Hexagon();
    assertEquals("_", hex.toString());

    hex.changeHexOccupancy(HexagonPlayer.BLACK);
    assertEquals("B", hex.toString());

    hex.changeHexOccupancy(HexagonPlayer.WHITE);
    assertEquals("W", hex.toString());

  }


  @Test
  public void testGetOccupancyAfterMoves() {
    assertEquals(board.getOccupancy(4,1), HexagonPlayer.NONE);
    board.play(4, 1, HexagonPlayer.BLACK);
    assertEquals(board.getOccupancy(4,1), HexagonPlayer.BLACK);
    board.play(5, 0, HexagonPlayer.WHITE);
    assertEquals(board.getOccupancy(5,0), HexagonPlayer.WHITE);
  }

  @Test
  public void testGetOccupancyInvalidCoordinates() {
    assertThrows(IllegalArgumentException.class, () -> board.getOccupancy(-1,-1));
  }


  @Test
  public void testCanMoveToPosition() {
    assertTrue(board.canMove(4, 1, HexagonPlayer.BLACK));
    board.play(4, 1, HexagonPlayer.BLACK);
    assertTrue(board.canMove(5, 0, HexagonPlayer.WHITE));
    board.play(5, 0, HexagonPlayer.WHITE);
    assertTrue(board.canMove(4, 0, HexagonPlayer.BLACK));
    board.play(4, 0, HexagonPlayer.BLACK);
  }

  @Test
  public void testCanMoveToInvalidPosition() {
    assertThrows(IllegalArgumentException.class,
        () -> board.canMove(-1, -1, HexagonPlayer.BLACK));
  }

  @Test
  public void testCanMoveFalse() {
    assertFalse(board.canMove(6, 0, HexagonPlayer.BLACK));
  }


}