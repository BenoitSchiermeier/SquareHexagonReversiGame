import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiSquareBoard;
import cs3500.reversi.textualview.ReversiModelTextView;
import cs3500.reversi.textualview.TextualView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestReversiSquareBoard {
  ReversiSquareBoard board;
  TextualView view;

  @Before
  public void setup() {
    board = new ReversiSquareBoard(4);
    board.startGame();
    view = new ReversiModelTextView(board);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidInputInSquareBoard() {
    ReversiSquareBoard board = new ReversiSquareBoard(5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidInputInSquareBoard2() {
    ReversiSquareBoard board = new ReversiSquareBoard(3);
  }


  @Test
  public void testStartingView() {
    Assert.assertEquals(view.arrayTo2dArrayString(),
        "_  _  _  _  \n"
        + "_  B  W  _  \n"
        + "_  W  B  _  \n"
        + "_  _  _  _  \n"
        + "Black Score = 2\n"
        + "White Score = 2\n");

    Assert.assertEquals(board.getScore(HexagonPlayer.WHITE), 2);
    Assert.assertEquals(board.getScore(HexagonPlayer.BLACK), 2);
  }

  @Test
  public void testPassAndGameOver() {
    Assert.assertEquals(board.getCurrentPlayer(), HexagonPlayer.BLACK);
    board.pass();
    Assert.assertEquals(board.getCurrentPlayer(), HexagonPlayer.WHITE);
    board.pass();
    Assert.assertTrue(board.isGameOver());
  }

  @Test
  public void testPlayMoveLeft() {
    board.play(0, 2, HexagonPlayer.BLACK);
    Assert.assertEquals(view.arrayTo2dArrayString(),
        "_  _  _  _  \n"
            + "_  B  W  _  \n"
            + "B  B  B  _  \n"
            + "_  _  _  _  \n"
            + "Black Score = 4\n"
            + "White Score = 1\n");

  }

  @Test
  public void testPlayMoveRight() {
    board.play(3, 1, HexagonPlayer.BLACK);
    Assert.assertEquals(view.arrayTo2dArrayString(),
        "_  _  _  _  \n"
            + "_  B  B  B  \n"
            + "_  W  B  _  \n"
            + "_  _  _  _  \n"
            + "Black Score = 4\n"
            + "White Score = 1\n");

  }

  @Test
  public void testPlayMoveDown() {
    board.play(1, 3, HexagonPlayer.BLACK);
    Assert.assertEquals(view.arrayTo2dArrayString(),
        "_  _  _  _  \n"
            + "_  B  W  _  \n"
            + "_  B  B  _  \n"
            + "_  B  _  _  \n"
            + "Black Score = 4\n"
            + "White Score = 1\n");

  }


  @Test
  public void testPlayMoveDiagonal() {
    board.play(3, 1, HexagonPlayer.BLACK);
    Assert.assertEquals(view.arrayTo2dArrayString(),
        "_  _  _  _  \n"
            + "_  B  B  B  \n"
            + "_  W  B  _  \n"
            + "_  _  _  _  \n"
            + "Black Score = 4\n"
            + "White Score = 1\n");

    board.play(3, 0, HexagonPlayer.WHITE);
    Assert.assertEquals(view.arrayTo2dArrayString(),
        "_  _  _  W  \n"
            + "_  B  W  B  \n"
            + "_  W  B  _  \n"
            + "_  _  _  _  \n"
            + "Black Score = 3\n"
            + "White Score = 3\n");
  }


  @Test
  public void testPlayMoveLeftDiagonal() {
    board.pass();
    board.play(0, 1, HexagonPlayer.WHITE);
    Assert.assertEquals(view.arrayTo2dArrayString(),
        "_  _  _  _  \n"
            + "W  W  W  _  \n"
            + "_  W  B  _  \n"
            + "_  _  _  _  \n"
            + "Black Score = 1\n"
            + "White Score = 4\n");
    board.play(0, 0, HexagonPlayer.BLACK);
    Assert.assertEquals(view.arrayTo2dArrayString(),
        "B  _  _  _  \n"
            + "W  B  W  _  \n"
            + "_  W  B  _  \n"
            + "_  _  _  _  \n"
            + "Black Score = 3\n"
            + "White Score = 3\n");

  }



  @Test
  public void testPlayMoveDifferent() {
    board.pass();
    board.play(1, 0, HexagonPlayer.WHITE);

    System.out.println(view.arrayTo2dArrayString());
  }


  // move onto other piece
  // move on random
  // make move that should work only if other piece
  // make move out of turn



}
