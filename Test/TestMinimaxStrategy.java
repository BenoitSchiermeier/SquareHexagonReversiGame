import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiBoard;
import cs3500.reversi.strategy.AggressiveReversiStrategy;
import cs3500.reversi.strategy.GoForCornersStrategy;
import cs3500.reversi.strategy.MinimaxStrategy;
import cs3500.reversi.strategy.Move;
import cs3500.reversi.strategy.ReversiStrategy;
import cs3500.reversi.strategy.TryTwo;
import cs3500.reversi.textualview.ReversiModelTextView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the MinimaxStrategy in a Reversi game context.
 */
public class TestMinimaxStrategy {

  ReversiBoard model;
  ReversiStrategy minimaxStrat;
  ReversiStrategy aggressiveStrat;
  ReversiStrategy minimaxStratVsCornerStrat;

  ReversiStrategy tryTwo;

  ReversiModelTextView view;


  /**
   * Initializes components for Reversi game testing before each test case.
   */
  @Before
  public void setup() {
    model = new ReversiBoard(4);
    model.startGame();
    view = new ReversiModelTextView(model);
    aggressiveStrat = new AggressiveReversiStrategy();
    minimaxStrat = new MinimaxStrategy(aggressiveStrat);
    tryTwo = new TryTwo(new GoForCornersStrategy(), aggressiveStrat);
    minimaxStratVsCornerStrat = new MinimaxStrategy(tryTwo);

  }


  @Test
  public void testMinimaxFindsOpponentBestMove() {
    // if black passes, the best move for white is 4, 1
    Move move = minimaxStrat.chooseMove(model, HexagonPlayer.BLACK);
    Assert.assertEquals(4, move.getQ());
    Assert.assertEquals(1, move.getR());
  }

  @Test
  public void testMinimaxFindsOpponentBestMoveMidGame() {
    model.play(4, 1, HexagonPlayer.BLACK);
    // pass white's turn
    model.pass();

    Assert.assertEquals(view.arrayHexString(),
        "      _   _   _   _   \n"
            + "    _   _   B   _   _   \n"
            + "  _   _   B   B   _   _   \n"
            + "_   _   W   _   B   _   _   \n"
            + "  _   _   B   W   _   _   \n"
            + "    _   _   _   _   _   \n"
            + "      _   _   _   _   \n"
            + "Black Score = 5\n"
            + "White Score = 2\n");
    // if it were white's turn, the best move for white would be (5, 0)
    // but black cannot move to 5, 0 so an exception will be thrown because black cannot move there
    // if black passes, the best move for white is 4, 1
    Move move = minimaxStrat.chooseMove(model, HexagonPlayer.BLACK);
    Assert.assertEquals(2, move.getQ());
    Assert.assertEquals(2, move.getR());

    // play the move so that it can be visually represented :
    model.play(move.getQ(), move.getR(), move.getPlayer());

    // the best move that disrupts white's best move is when black moves to 2,2
    Assert.assertEquals(view.arrayHexString(),
        "      _   _   _   _   \n"
            + "    _   _   B   _   _   \n"
            + "  _   B   B   B   _   _   \n"
            + "_   _   B   _   B   _   _   \n"
            + "  _   _   B   W   _   _   \n"
            + "    _   _   _   _   _   \n"
            + "      _   _   _   _   \n"
            + "Black Score = 7\n"
            + "White Score = 1\n");
  }

  @Test
  public void testMinimaxGivenGoForStrategyAsOpponent() {
    model.play(4, 1, HexagonPlayer.BLACK);
    model.play(5, 0, HexagonPlayer.WHITE);
    model.play(4, 0, HexagonPlayer.BLACK);
    model.play(1, 4, HexagonPlayer.WHITE);
    model.play(0, 5, HexagonPlayer.BLACK);
    model.play(0, 4, HexagonPlayer.WHITE);
    model.play(0, 3, HexagonPlayer.BLACK);
    model.play(2, 2, HexagonPlayer.WHITE);
    model.play(4, 4, HexagonPlayer.BLACK);

    Assert.assertEquals(view.arrayHexString(),
        "      _   B   W   _   \n"
            + "    _   _   B   _   _   \n"
            + "  _   W   B   B   _   _   \n"
            + "B   _   W   _   B   _   _   \n"
            + "  B   B   B   B   B   _   \n"
            + "    B   _   _   _   _   \n"
            + "      _   _   _   _   \n"
            + "Black Score = 12\n"
            + "White Score = 3\n");
    Move move = minimaxStratVsCornerStrat.chooseMove(model, HexagonPlayer.WHITE);

    // finds the best move that disrupts black's best move:
    // black's best move is at 6,0 because it is using the corner strat
    // by choosing (3, 0) white prevents black from playing at (6,0)
    Assert.assertEquals(3, move.getQ());
    Assert.assertEquals(0, move.getR());

    model.play(move.getQ(), move.getR(), move.getPlayer());

    Assert.assertEquals(view.arrayHexString(),
        "      W   W   W   _   \n"
            + "    _   _   B   _   _   \n"
            + "  _   W   B   B   _   _   \n"
            + "B   _   W   _   B   _   _   \n"
            + "  B   B   B   B   B   _   \n"
            + "    B   _   _   _   _   \n"
            + "      _   _   _   _   \n"
            + "Black Score = 11\n"
            + "White Score = 5\n");
  }


  @Test
  public void testMinimaxDifferentBasedOnOpponent() {
    model.play(4, 1, HexagonPlayer.BLACK);
    model.play(5, 0, HexagonPlayer.WHITE);
    model.play(4, 0, HexagonPlayer.BLACK);
    model.play(1, 4, HexagonPlayer.WHITE);
    model.play(0, 5, HexagonPlayer.BLACK);
    model.play(0, 4, HexagonPlayer.WHITE);
    model.play(0, 3, HexagonPlayer.BLACK);
    model.play(2, 2, HexagonPlayer.WHITE);
    model.play(4, 4, HexagonPlayer.BLACK);

    // here for visual representation:
    Assert.assertEquals(view.arrayHexString(),
        "      _   B   W   _   \n"
            + "    _   _   B   _   _   \n"
            + "  _   W   B   B   _   _   \n"
            + "B   _   W   _   B   _   _   \n"
            + "  B   B   B   B   B   _   \n"
            + "    B   _   _   _   _   \n"
            + "      _   _   _   _   \n"
            + "Black Score = 12\n"
            + "White Score = 3\n");
    //    Assert.assertEquals(view.arrayHexString());
    Move move1 = minimaxStrat.chooseMove(model, HexagonPlayer.WHITE);
    Move move2 = minimaxStratVsCornerStrat.chooseMove(model, HexagonPlayer.WHITE);

    // move is different because opponent is using GoForCornerStrategy
    Assert.assertEquals(3, move2.getQ());
    Assert.assertEquals(0, move2.getR());

    // move is different because the opponent is using Aggressive strategy:
    Assert.assertEquals(2, move1.getQ());
    Assert.assertEquals(5, move1.getR());
  }

}
