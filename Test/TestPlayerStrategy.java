import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiBoard;
import cs3500.reversi.player.AIPlayer;
import cs3500.reversi.player.Player;
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
 * Test suite for evaluating different player strategies in Reversi. It includes
 * tests for AI players using various strategies such as GoForCornersStrategy,
 * AggressiveReversiStrategy, and AvoidCornersStrategy. This class aims to assess
 * the effectiveness and game outcomes of different strategic approaches in
 * competitive play scenarios within the Reversi game environment.
 */
public class TestPlayerStrategy {
  ReversiBoard model;
  TextualView view;
  ReversiStrategy strat1;
  ReversiStrategy strat2;
  ReversiStrategy strat3;

  Player player1;
  Player player2;
  Player player3;

  /**
   * Sets up the environment for Reversi game tests with a 6x6 board.
   * Initializes the game board and its textual view, various strategies including
   * corner-focused and aggressive ones,
   * and three AI players with different strategies.
   */
  @Before
  public void setup() {
    model = new ReversiBoard(6);
    model.startGame();
    view = new ReversiModelTextView(model);
    ReversiStrategy stratCorner = new GoForCornersStrategy();
    ReversiStrategy avoidCorner =
        new TryTwo(new AvoidCornersStrategy(), new AggressiveReversiStrategy());

    strat1 = new TryTwo(stratCorner, avoidCorner);
    strat2 = new AggressiveReversiStrategy();
    strat3 = new TryTwo(new AvoidCornersStrategy(), strat2);

    player1 = new AIPlayer(model, HexagonPlayer.BLACK, strat1);
    player2 = new AIPlayer(model, HexagonPlayer.WHITE, strat2);
    player3 = new AIPlayer(model, HexagonPlayer.WHITE, strat3);
  }

  @Test
  public void testPlayerVsPlayerUsingDifferentStrategies() {
    while (!model.isGameOver()) {
      try {
        Move move1 = player1.play(0,0);
        model.play(move1.getQ(), move1.getR(), move1.getPlayer());
      } catch (IllegalStateException e) {
        model.pass();
      }
      try {
        Move move2 = player2.play(0,0);
        model.play(move2.getQ(), move2.getR(), move2.getPlayer());
      } catch (IllegalStateException e) {
        model.pass();
      }
    }
    Assert.assertTrue(model.getScore(HexagonPlayer.BLACK)
        > model.getScore(HexagonPlayer.WHITE));

    Assert.assertEquals(view.arrayHexString(),
        "          B   B   B   B   B   B   \n"
        + "        B   _   W   _   B   _   B   \n"
        + "      B   B   B   W   B   B   B   B   \n"
        + "    B   _   W   _   W   _   B   _   B   \n"
        + "  W   B   W   W   W   B   B   B   B   B   \n"
        + "W   _   B   _   W   _   W   _   B   _   B   \n"
        + "  W   W   B   W   W   W   W   B   W   B   \n"
        + "    W   _   B   _   W   _   B   _   B   \n"
        + "      B   B   B   B   W   B   B   B   \n"
        + "        B   _   B   _   W   _   B   \n"
        + "          B   B   B   B   B   B   \n"
        + "Black Score = 50\n"
        + "White Score = 22\n");
  }

  @Test
  public void testPlayerVsPlayerUsingDifferentBoardSize() {
    ReversiBoard model = new ReversiBoard(8);
    model.startGame();
    ReversiModelTextView t = new ReversiModelTextView(model);
    player1 = new AIPlayer(model, HexagonPlayer.BLACK, strat1);
    player2 = new AIPlayer(model, HexagonPlayer.WHITE, strat2);

    while (!model.isGameOver()) {
      try {
        Move move1 = player1.play(0,0);
        model.play(move1.getQ(), move1.getR(), move1.getPlayer());
      } catch (IllegalStateException e) {
        model.pass();
      }
      try {
        Move move2 = player2.play(0,0);
        model.play(move2.getQ(), move2.getR(), move2.getPlayer());
      } catch (IllegalStateException e) {
        model.pass();
      }
    }

    Assert.assertEquals(t.arrayHexString(),
        "              W   W   W   W   B   B   B   B   \n"
        + "            W   _   W   _   B   _   B   _   B   \n"
        + "          W   B   B   B   B   B   B   B   B   B   \n"
        + "        W   _   W   _   B   _   W   _   B   _   B   \n"
        + "      W   W   B   B   B   B   B   B   B   B   W   B   \n"
        + "    W   _   W   _   B   _   W   _   W   _   W   _   B   \n"
        + "  W   W   W   W   B   B   B   B   B   W   W   W   B   B   \n"
        + "W   _   W   _   W   _   W   _   W   _   W   _   B   _   W   \n"
        + "  W   W   W   W   W   W   B   B   B   B   W   B   W   W   \n"
        + "    W   _   W   _   W   _   W   _   B   _   B   _   W   \n"
        + "      W   B   B   B   B   B   W   B   B   B   B   W   \n"
        + "        W   _   B   _   W   _   W   _   B   _   W   \n"
        + "          B   B   B   W   W   W   W   B   B   B   \n"
        + "            B   _   W   _   W   _   W   _   B   \n"
        + "              B   W   B   B   B   B   B   B   \n"
        + "Black Score = 71\n"
        + "White Score = 61\n");
  }


  @Test
  public void testPlayerGoForCornerVsAggressive() {
    player3 = new AIPlayer(model, HexagonPlayer.BLACK, strat3);

    while (!model.isGameOver()) {
      try {
        Move move1 = player3.play(0,0);
        model.play(move1.getQ(), move1.getR(), move1.getPlayer());
      } catch (IllegalStateException e) {
        model.pass();

      }
      try {
        Move move2 = player2.play(0,0);
        model.play(move2.getQ(), move2.getR(), move2.getPlayer());
      } catch (IllegalStateException e) {
        model.pass();
      }
    }
    Assert.assertTrue(model.getScore(HexagonPlayer.BLACK)
        > model.getScore(HexagonPlayer.WHITE));

    Assert.assertEquals(view.arrayHexString(),
        "          B   B   B   B   B   B   \n"
            + "        B   _   W   _   B   _   B   \n"
            + "      B   B   B   W   B   B   B   B   \n"
            + "    B   _   B   _   W   _   B   _   B   \n"
            + "  W   B   W   W   W   B   B   B   B   B   \n"
            + "W   _   B   _   W   _   W   _   B   _   W   \n"
            + "  W   W   B   W   W   B   W   B   B   W   \n"
            + "    W   _   B   _   W   _   W   _   W   \n"
            + "      B   B   B   B   W   B   W   W   \n"
            + "        B   _   B   _   W   _   W   \n"
            + "          B   B   B   B   B   B   \n"
            + "Black Score = 46\n"
            + "White Score = 26\n");
  }


  @Test
  public void testPlayerGoForCornerVsAvoidCorner() {

    while (!model.isGameOver()) {
      try {
        Move move1 = player1.play(0,0);
        model.play(move1.getQ(), move1.getR(), move1.getPlayer());
      } catch (IllegalStateException e) {
        model.pass();
      }
      try {
        Move move2 = player3.play(0,0);
        model.play(move2.getQ(), move2.getR(), move2.getPlayer());
      } catch (IllegalStateException e) {
        model.pass();
      }
    }
    Assert.assertEquals(view.arrayHexString(),
        "          W   W   W   W   W   W   \n"
            + "        W   _   W   _   B   _   W   \n"
            + "      W   B   B   W   B   W   B   W   \n"
            + "    W   _   B   _   B   _   B   _   W   \n"
            + "  W   W   B   W   B   W   B   B   B   W   \n"
            + "B   _   W   _   B   _   B   _   W   _   W   \n"
            + "  B   W   W   B   B   B   B   W   W   W   \n"
            + "    B   _   B   _   B   _   B   _   W   \n"
            + "      B   B   W   B   B   W   B   W   \n"
            + "        B   _   B   _   B   _   W   \n"
            + "          W   W   W   W   W   W   \n"
            + "Black Score = 33\n"
            + "White Score = 39\n");
  }

}
