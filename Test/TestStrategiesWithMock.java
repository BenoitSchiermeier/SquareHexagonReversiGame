
import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.MockReversiModel;
import cs3500.reversi.model.Pair;
import cs3500.reversi.model.ReversiBoard;
import cs3500.reversi.strategy.AggressiveReversiStrategy;
import cs3500.reversi.strategy.AvoidCornersStrategy;
import cs3500.reversi.strategy.GoForCornersStrategy;
import cs3500.reversi.strategy.MinimaxStrategy;
import cs3500.reversi.strategy.Move;
import cs3500.reversi.strategy.ReversiStrategy;
import cs3500.reversi.textualview.ReversiModelTextView;
import cs3500.reversi.textualview.TextualView;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Provides unit tests for different strategies in a mock Reversi game.
 * Tests the functionality and decision-making of AI strategies in various game scenarios.
 */
public class TestStrategiesWithMock {
  ReversiBoard board;
  TextualView view;
  ReversiStrategy aggressiveStrat;
  ReversiStrategy avoidNearCorners;
  ReversiStrategy goforCorners;
  ReversiStrategy minimaxStrategy;

  MockReversiModel mock;

  /**
   * Sets up the testing environment before each test case.
   * This method initializes various components essential for testing the Reversi game.
   * It includes the game board, a text view of the model, a strategy for playing the game,
   * and a mock model for testing purposes. It also prepares a list of possible moves that can
   * be made in the game, extracted from the mock model.
   */
  @Before
  public void setup() {
    board = new ReversiBoard(4);
    board.startGame();
    view = new ReversiModelTextView(board);
    aggressiveStrat = new AggressiveReversiStrategy();
    avoidNearCorners = new AvoidCornersStrategy();
    goforCorners = new GoForCornersStrategy();
    // create a minimax strategy that guesses that the other opponent is using aggressiveStrat
    minimaxStrategy = new MinimaxStrategy(aggressiveStrat);
    mock = new MockReversiModel(board);
  }


  @Test
  public void testAvoidCornersMockTranscript() {
    Move move = avoidNearCorners.chooseMove(mock, HexagonPlayer.BLACK);
    List<String> los = new ArrayList<>();
    for (Pair pair : mock.getInspectedPossibleMoves()) {
      los.add(pair.toString());
    }
    Assert.assertEquals(los.toString(), "[(q=4,r=1)\n"
        + ", (q=2,r=2)\n"
        + ", (q=5,r=2)\n"
        + ", (q=1,r=4)\n"
        + ", (q=4,r=4)\n"
        + ", (q=2,r=5)\n"
        + "]");
  }

  @Test
  public void testAvoidCornersMockTranscriptNotEmpty() {
    board.play(4, 1, HexagonPlayer.BLACK);
    board.play(5, 0, HexagonPlayer.WHITE);
    board.play(4, 0, HexagonPlayer.BLACK);
    board.play(1, 4, HexagonPlayer.WHITE);

    board.play(0, 5, HexagonPlayer.BLACK);
    board.play(0, 4, HexagonPlayer.WHITE);
    board.play(0, 3, HexagonPlayer.BLACK);
    board.play(2, 2, HexagonPlayer.WHITE);
    board.play(4, 4, HexagonPlayer.BLACK);
    board.play(2, 5, HexagonPlayer.WHITE);
    Move move = avoidNearCorners.chooseMove(mock, HexagonPlayer.BLACK);
    List<String> los = new ArrayList<>();
    for (Pair pair : mock.getInspectedPossibleMoves()) {
      los.add(pair.toString());
    }

    Assert.assertEquals(los.toString(),
        "[(q=6,r=0)\n"
        + ", (q=1,r=2)\n"
        + ", (q=1,r=6)\n"
        + "]");

    // the only move that is not adjacent to a corner is the corner :
    Assert.assertEquals(6, move.getQ());
    Assert.assertEquals(0, move.getR());

  }

  @Test
  public void testGoForCorners() {
    board.play(4, 1, HexagonPlayer.BLACK);
    board.play(5, 0, HexagonPlayer.WHITE);
    board.play(4, 0, HexagonPlayer.BLACK);
    board.play(1, 4, HexagonPlayer.WHITE);

    board.play(0, 5, HexagonPlayer.BLACK);
    board.play(0, 4, HexagonPlayer.WHITE);
    board.play(0, 3, HexagonPlayer.BLACK);
    board.play(2, 2, HexagonPlayer.WHITE);
    board.play(4, 4, HexagonPlayer.BLACK);
    board.play(2, 5, HexagonPlayer.WHITE);
    Move move = goforCorners.chooseMove(mock, HexagonPlayer.BLACK);
    List<String> los = new ArrayList<>();
    for (Pair pair : mock.getInspectedPossibleMoves()) {
      los.add(pair.toString());
    }

    // has the same move possibilities as avoid corners but chooses different move
    Assert.assertEquals(los.toString(),
        "[(q=6,r=0)\n"
            + ", (q=1,r=2)\n"
            + ", (q=1,r=6)\n"
            + "]");

    Assert.assertEquals(view.arrayHexString(),
        "      _   B   W   _   \n"
        + "    _   _   B   _   _   \n"
        + "  _   W   B   B   _   _   \n"
        + "B   _   W   _   B   _   _   \n"
        + "  B   B   W   B   B   _   \n"
        + "    B   _   W   _   _   \n"
        + "      _   _   _   _   \n"
        + "Black Score = 11\n"
        + "White Score = 5\n");

    // should go for the corner move (which is the top right corner as can be seen above:
    Assert.assertEquals(6, move.getQ());
    Assert.assertEquals(0, move.getR());

  }



  @Test
  public void testMockAddsCorrectCoordinates() {
    Move move = aggressiveStrat.chooseMove(mock, HexagonPlayer.BLACK);

    Assert.assertEquals(4, move.getQ());
    Assert.assertEquals(1, move.getR());

    List<String> los = new ArrayList<>();
    for (Pair pair : mock.getInspectedPossibleMoves()) {
      los.add(pair.toString());
    }
    // makes sure that the strategy checked all possible moves:
    Assert.assertEquals(los.toString(),
        "[(q=4,r=1)\n"
            + ", (q=2,r=2)\n"
            + ", (q=5,r=2)\n"
            + ", (q=1,r=4)\n"
            + ", (q=4,r=4)\n"
            + ", (q=2,r=5)\n"
            + "]");
  }

  @Test
  public void testMockAddsCorrectCoordinatesAfterGameStarted() {
    board.play(4, 1, HexagonPlayer.BLACK);

    Move move = aggressiveStrat.chooseMove(mock, HexagonPlayer.WHITE);

    List<String> los = new ArrayList<>();
    for (Pair pair : mock.getInspectedPossibleMoves()) {
      los.add(pair.toString());
    }
    Assert.assertEquals(los.toString(),
        "[(q=5,r=0)\n"
            + ", (q=5,r=2)\n"
            + ", (q=1,r=4)\n"
            + ", (q=2,r=5)\n"
            + "]");
  }


  @Test
  public void testMockAddsCorrectCoordinatesIntermediatePointInGameAggressive() {
    board.play(4, 1, HexagonPlayer.BLACK);
    board.play(5, 0, HexagonPlayer.WHITE);

    Move move = aggressiveStrat.chooseMove(mock, HexagonPlayer.BLACK);

    List<String> los = new ArrayList<>();
    for (Pair pair : mock.getInspectedPossibleMoves()) {
      los.add(pair.toString());
    }
    Assert.assertEquals(los.toString(),
        "[(q=4,r=0)\n"
            + ", (q=2,r=2)\n"
            + ", (q=4,r=4)\n"
            + ", (q=2,r=5)\n"
            + "]");
  }


  @Test
  public void testMockCorrectlyOutputsCheckedValidCoordinates() {
    mock.canMove(4,1,HexagonPlayer.BLACK);
    mock.canMove(2,2,HexagonPlayer.BLACK);
    mock.canMove(2,5,HexagonPlayer.BLACK);

    List<String> los = new ArrayList<>();
    for (Pair pair : mock.getInspectedPossibleMoves()) {
      los.add(pair.toString());
    }
    Assert.assertTrue(los.get(0).contains("(q=4,r=1)"));
    Assert.assertTrue(los.get(1).contains("(q=2,r=2)"));
    Assert.assertTrue(los.get(2).contains("(q=2,r=5)"));
  }


  @Test
  public void testMinimaxVsAggressiveTranscript() {
    Move move = minimaxStrategy.chooseMove(mock, HexagonPlayer.BLACK);
    List<String> los = new ArrayList<>();
    for (Pair pair : mock.getInspectedPossibleMoves()) {
      los.add(pair.toString());
    }
    Assert.assertEquals(los.toString(), "[(q=4,r=1)\n"
        + ", (q=2,r=2)\n"
        + ", (q=5,r=2)\n"
        + ", (q=1,r=4)\n"
        + ", (q=4,r=4)\n"
        + ", (q=2,r=5)\n"
        + "]");
  }

  @Test
  public void testMinimaxVsAggressiveTranscriptInIntermediatePointGame() {
    board.play(4, 1, HexagonPlayer.BLACK);
    board.play(5, 0, HexagonPlayer.WHITE);
    board.play(4, 0, HexagonPlayer.BLACK);
    board.play(1, 4, HexagonPlayer.WHITE);
    board.play(0, 5, HexagonPlayer.BLACK);
    board.play(0, 4, HexagonPlayer.WHITE);
    board.play(0, 3, HexagonPlayer.BLACK);
    board.play(2, 2, HexagonPlayer.WHITE);
    board.play(4, 4, HexagonPlayer.BLACK);

    ReversiStrategy minimaxStrategyVsCorner = new MinimaxStrategy(goforCorners);
    Move move = minimaxStrategyVsCorner.chooseMove(mock, HexagonPlayer.WHITE);
    List<String> los = new ArrayList<>();
    for (Pair pair : mock.getInspectedPossibleMoves()) {
      los.add(pair.toString());
    }
    Assert.assertEquals(los.toString(), "[(q=3,r=0)\n"
        + ", (q=5,r=2)\n"
        + ", (q=2,r=5)\n"
        + "]");

    Assert.assertEquals(3, move.getQ());
    Assert.assertEquals(0, move.getR());

  }


}
