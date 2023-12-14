import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiBoard;
import cs3500.reversi.strategy.AggressiveReversiStrategy;
import cs3500.reversi.strategy.AvoidCornersStrategy;
import cs3500.reversi.strategy.GoForCornersStrategy;
import cs3500.reversi.strategy.Move;
import cs3500.reversi.strategy.ReversiStrategy;
import cs3500.reversi.strategy.TryTwo;
import cs3500.reversi.textualview.ReversiModelTextView;
import cs3500.reversi.textualview.TextualView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the GoForCornersStrategy in Reversi. This class tests the strategy's
 * effectiveness in prioritizing corner moves. It validates the strategy's decisions
 * in various game scenarios to ensure that it consistently opts for corner positions,
 * which are typically advantageous in Reversi.
 */
public class TestTryTwoGoForCorners {
  ReversiBoard model;
  TextualView view;
  ReversiStrategy strat;

  /**
   * Initializes the testing setup for a 4x4 Reversi board game.
   * Sets up the game board, its textual view, and a series of strategies
   * including corner-focused, corner-avoiding, aggressive, and composite strategies
   * for testing various gameplay scenarios.
   */
  @Before
  public void setup() {
    model = new ReversiBoard(4);
    model.startGame();
    view = new ReversiModelTextView(model);


    ReversiStrategy strat1 = new GoForCornersStrategy();
    ReversiStrategy strat3 = new AvoidCornersStrategy();
    ReversiStrategy strat4 = new AggressiveReversiStrategy();

    ReversiStrategy strat5 = new TryTwo(strat3, strat4);//new AvoidCornersStrategy();

    strat = new TryTwo(strat1, strat5);
  }

  @Test
  public void testNullStrategies() {
    ReversiStrategy strat = new TryTwo(null, null);
    // throws an exception because the two strats given are null:
    Assert.assertThrows(IllegalStateException.class,
        () -> strat.chooseMove(model, HexagonPlayer.BLACK));
  }

  @Test
  public void testThrowsBecauseNoCornerFound() {
    ReversiStrategy cornerStrat = new GoForCornersStrategy();
    // throws an exception because there are no corner moves available:
    Assert.assertThrows(IllegalStateException.class,
        () -> cornerStrat.chooseMove(model, HexagonPlayer.BLACK));
  }

  @Test
  public void testGoForCornerBaseChoice() {
    Move move = strat.chooseMove(model, HexagonPlayer.BLACK);
    model.play(move.getQ(), move.getR(), move.getPlayer());
    // makes sure that it chooses the most top right choice:
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
  }


  @Test
  public void testGoForCorner() {
    Move move = strat.chooseMove(model, model.getCurrentPlayer());
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

    Move move6 = strat.chooseMove(model, model.getCurrentPlayer());
    model.play(move6.getQ(), move6.getR(), move6.getPlayer());


    Move move7 = strat.chooseMove(model, model.getCurrentPlayer());
    model.play(move7.getQ(), move7.getR(), move7.getPlayer());

    model.play(6, 1, HexagonPlayer.WHITE);

    Assert.assertEquals(view.arrayHexString(),
        "      _   _   _   _   \n"
            + "    _   _   B   _   W   \n"
            + "  _   W   B   B   W   B   \n"
            + "_   _   W   _   W   _   _   \n"
            + "  _   B   W   W   B   _   \n"
            + "    _   _   W   _   _   \n"
            + "      _   _   _   _   \n"
            + "Black Score = 6\n"
            + "White Score = 8\n");

    Move move8 = strat.chooseMove(model, HexagonPlayer.BLACK);
    model.play(move8.getQ(), move8.getR(), move8.getPlayer());

    // ENSURES THAT THE MOVE GOES FOR THE CORNER:
    Assert.assertEquals(view.arrayHexString(),
        "      _   _   _   B   \n"
            + "    _   _   B   _   B   \n"
            + "  _   W   B   B   W   B   \n"
            + "_   _   W   _   W   _   _   \n"
            + "  _   B   W   W   B   _   \n"
            + "    _   _   W   _   _   \n"
            + "      _   _   _   _   \n"
            + "Black Score = 8\n"
            + "White Score = 7\n");

  }


}
