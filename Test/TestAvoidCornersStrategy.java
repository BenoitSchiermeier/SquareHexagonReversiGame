import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiBoard;
import cs3500.reversi.strategy.AggressiveReversiStrategy;
import cs3500.reversi.strategy.AvoidCornersStrategy;
import cs3500.reversi.strategy.Move;
import cs3500.reversi.strategy.ReversiStrategy;
import cs3500.reversi.strategy.TryTwo;
import cs3500.reversi.textualview.ReversiModelTextView;
import cs3500.reversi.textualview.TextualView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for evaluating the AvoidCornersStrategy in the game of Reversi.
 * This class verifies the strategy's behavior in various scenarios, ensuring
 * it avoids corner moves unless necessary. It tests the strategy's move selection
 * at different stages of the game and validates correct play behavior, especially
 * in avoiding corners as per the strategy's design.
 */
public class TestAvoidCornersStrategy {

  ReversiBoard model;
  TextualView view;
  ReversiStrategy strat;
  ReversiStrategy strat1;

  /**
   * Initializes components for Reversi game testing before each test case.
   * Sets up a 4x4 Reversi board, a textual view of the board, two strategies
   * (AvoidCorners and Aggressive), a combined strategy (TryTwo), a mock
   * model for test interactions, and a list of possible moves.
   */
  @Before
  public void setup() {
    model = new ReversiBoard(4);
    model.startGame();
    view = new ReversiModelTextView(model);
    strat1 = new AvoidCornersStrategy();
    ReversiStrategy strat2 = new AggressiveReversiStrategy();

    strat = new TryTwo(strat1, strat2);//new AvoidCornersStrategy();
  }

  @Test
  public void testStartingBoardMakeMove() {
    Move move = strat.chooseMove(model, HexagonPlayer.BLACK);
    model.play(move.getQ(), move.getR(), move.getPlayer());

    // expected result
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

    // test coordinates of expected result:
    Assert.assertEquals(4, move.getQ());
    Assert.assertEquals(1, move.getR());
  }


  @Test
  public void testCornerMovePossibleButNotMade() {
    Move move = strat.chooseMove(model, HexagonPlayer.BLACK);
    model.play(move.getQ(), move.getR(), move.getPlayer());
    Move move2 = strat.chooseMove(model, model.getCurrentPlayer());
    model.play(move2.getQ(), move2.getR(), move2.getPlayer());
    Move move3 = strat.chooseMove(model, model.getCurrentPlayer());
    model.play(move3.getQ(), move3.getR(), move3.getPlayer());
    Assert.assertEquals(view.arrayHexString(),
        "      _   _   _   _   \n"
            + "    _   _   B   _   _   \n"
            + "  _   _   B   B   W   _   \n"
            + "_   _   W   _   B   _   _   \n"
            + "  _   _   B   B   B   _   \n"
            + "    _   _   _   _   _   \n"
            + "      _   _   _   _   \n"
            + "Black Score = 7\n"
            + "White Score = 2\n");
    Move move4 = strat.chooseMove(model, model.getCurrentPlayer());
    model.play(move4.getQ(), move4.getR(), move4.getPlayer());
    Assert.assertEquals(view.arrayHexString(),
        "      _   _   _   _   \n"
            + "    _   _   B   _   _   \n"
            + "  _   W   W   W   W   _   \n"
            + "_   _   W   _   B   _   _   \n"
            + "  _   _   B   B   B   _   \n"
            + "    _   _   _   _   _   \n"
            + "      _   _   _   _   \n"
            + "Black Score = 5\n"
            + "White Score = 5\n");
    Move move5 = strat.chooseMove(model, model.getCurrentPlayer());
    model.play(move5.getQ(), move5.getR(), move5.getPlayer());

    // INSTEAD OF GOING TO THE TOP RIGHT LIKE IN THE AGGRESSIVE STRATEGY, (1,4) IS CHOSEN BECAUSE
    // IT AVOIDS THE CORNERS:
    Assert.assertEquals(view.arrayHexString(),
        "      _   _   _   _   \n"
            + "    _   _   B   _   _   \n"
            + "  _   W   B   W   W   _   \n"
            + "_   _   B   _   B   _   _   \n"
            + "  _   B   B   B   B   _   \n"
            + "    _   _   _   _   _   \n"
            + "      _   _   _   _   \n"
            + "Black Score = 8\n"
            + "White Score = 3\n");
    model.pass();
    Move move6 = strat.chooseMove(model, model.getCurrentPlayer());
    model.play(move6.getQ(), move6.getR(), move6.getPlayer());

    // WHEN NO OTHER MOVES NEXT TO CORNERS CAN BE AVOIDED, IT MOVES NEXT TO A CORNER
    Assert.assertEquals(view.arrayHexString(),
        "      _   _   _   _   \n"
            + "    _   _   B   _   _   \n"
            + "  _   W   B   B   B   B   \n"
            + "_   _   B   _   B   _   _   \n"
            + "  _   B   B   B   B   _   \n"
            + "    _   _   _   _   _   \n"
            + "      _   _   _   _   \n"
            + "Black Score = 11\n"
            + "White Score = 1\n");

    model.pass();
    Move move7 = strat.chooseMove(model, model.getCurrentPlayer());
    model.play(move7.getQ(), move7.getR(), move7.getPlayer());

    model.pass();

    // no moves are available anymore so exception should be thrown telling it to pass:
    Assert.assertThrows(IllegalStateException.class,
        () -> strat.chooseMove(model, model.getCurrentPlayer()));
  }

  @Test
  public void testChooseMoveWithWrongPlayer() {
    // game starts with black first
    Assert.assertThrows(IllegalStateException.class,
        () -> strat.chooseMove(model, HexagonPlayer.WHITE));
  }
}
