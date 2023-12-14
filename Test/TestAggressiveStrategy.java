import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.MockReversiModel;
import cs3500.reversi.model.Pair;
import cs3500.reversi.model.ReversiBoard;
import cs3500.reversi.player.AIPlayer;
import cs3500.reversi.player.Player;
import cs3500.reversi.strategy.AggressiveReversiStrategy;
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
 * Test class for evaluating the behavior of the AggressiveReversiStrategy in
 * various scenarios of a Reversi game. It covers tests for game start, mid-game
 * decisions, handling complete games, and correct move selection. The tests make
 * use of a mock Reversi model to assess strategy performance under controlled
 * conditions.
 */
public class TestAggressiveStrategy {
  ReversiBoard model;
  TextualView view;
  ReversiStrategy strat;
  MockReversiModel mock;
  List<String> los;

  /**
   * Sets up the testing environment before each test case.
   * This method initializes various components essential for testing the Reversi game.
   * It includes the game board, a text view of the model, a strategy for playing the game,
   * and a mock model for testing purposes. It also prepares a list of possible moves that can
   * be made in the game, extracted from the mock model.
   */

  @Before
  public void setup() {
    model = new ReversiBoard(4);
    model.startGame();
    view = new ReversiModelTextView(model);
    strat = new AggressiveReversiStrategy();
    mock = new MockReversiModel(model);
    los = new ArrayList<>();
    for (Pair pair : mock.getInspectedPossibleMoves()) {
      los.add(pair.toString());
    }

  }

  @Test
  public void testStartGameChooseMoveResolvesTie() {
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

    // pass white's turn to set up a tie in terms of moves for black
    // black will choose the upper-leftmost tile:
    model.pass();

    Move move2 = strat.chooseMove(model, HexagonPlayer.BLACK);
    model.play(move2.getQ(), move2.getR(), move2.getPlayer());

    // BREAKS THE TIE BY MOVING TO THE TO
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

    Assert.assertEquals(2, move2.getQ());
    Assert.assertEquals(2, move2.getR());
  }



  @Test
  public void testMidGameStratChooseMove() {
    model.play(4, 1, HexagonPlayer.BLACK);
    model.play(5, 0, HexagonPlayer.WHITE);
    model.play(4, 0, HexagonPlayer.BLACK);
    model.play(1, 4, HexagonPlayer.WHITE);
    model.play(0, 5, HexagonPlayer.BLACK);
    model.play(0, 4, HexagonPlayer.WHITE);
    model.play(0, 3, HexagonPlayer.BLACK);
    model.play(2, 2, HexagonPlayer.WHITE);
    model.play(4, 4, HexagonPlayer.BLACK);
    model.play(2, 5, HexagonPlayer.WHITE);
    model.play(1, 6, HexagonPlayer.BLACK);

    // STARTING POSITION:
    Assert.assertEquals(view.arrayHexString(),
        "      _   B   W   _   \n"
        + "    _   _   B   _   _   \n"
        + "  _   W   B   B   _   _   \n"
        + "B   _   W   _   B   _   _   \n"
        + "  B   B   W   B   B   _   \n"
        + "    B   _   B   _   _   \n"
        + "      _   B   _   _   \n"
        + "Black Score = 13\n"
        + "White Score = 4\n");

    // pick the move based on the strategy: 
    Move move = strat.chooseMove(model, HexagonPlayer.WHITE);
    
    // move the board to the picked move: 
    model.play(move.getQ(), move.getR(), move.getPlayer());

    // best move was at q=5 , r = 2, check that board has been changed to best move: 
    Assert.assertEquals(view.arrayHexString(),
        "      _   B   W   _   \n"
            + "    _   _   B   _   _   \n"
            + "  _   W   W   W   W   _   \n"
            + "B   _   W   _   B   _   _   \n"
            + "  B   B   W   B   B   _   \n"
            + "    B   _   B   _   _   \n"
            + "      _   B   _   _   \n"
            + "Black Score = 11\n"
            + "White Score = 7\n");

    Assert.assertEquals(5, move.getQ());
    Assert.assertEquals(2, move.getR());
  }
  
  
  @Test
  public void testCompletedGameChooseMoveThrows() {
    model.play(4, 1, HexagonPlayer.BLACK);
    model.play(5, 0, HexagonPlayer.WHITE);
    model.play(4, 0, HexagonPlayer.BLACK);
    model.play(1, 4, HexagonPlayer.WHITE);
    model.play(0, 5, HexagonPlayer.BLACK);
    model.play(0, 4, HexagonPlayer.WHITE);
    model.play(0, 3, HexagonPlayer.BLACK);
    model.play(2, 2, HexagonPlayer.WHITE);
    model.play(4, 4, HexagonPlayer.BLACK);
    model.play(2, 5, HexagonPlayer.WHITE);
    model.play(1, 6, HexagonPlayer.BLACK);
    model.play(2, 6, HexagonPlayer.WHITE);
    model.play(6, 0, HexagonPlayer.BLACK);
    model.play(5, 2, HexagonPlayer.WHITE);
    model.play(3, 6, HexagonPlayer.BLACK);
    model.play(4, 5, HexagonPlayer.WHITE);
    model.play(2, 1, HexagonPlayer.BLACK);
    model.play(1, 2, HexagonPlayer.WHITE);
    model.play(5, 4, HexagonPlayer.BLACK);
    model.play(3, 0, HexagonPlayer.WHITE);
    model.play(6, 1, HexagonPlayer.BLACK);
    model.play(6, 2, HexagonPlayer.WHITE);
    model.play(6, 3, HexagonPlayer.BLACK);

    // throws exception because game is complete:
    Assert.assertThrows(IllegalStateException.class,
        () -> strat.chooseMove(model, model.getCurrentPlayer()));
  }


  @Test
  public void testChooseMoveWrongPlayer() {
    // black starts first, it is not white player's turn to move:
    Assert.assertThrows(IllegalStateException.class,
        () -> strat.chooseMove(model, HexagonPlayer.WHITE));
  }


  @Test
  public void testGameOverStratFindMove() {
    Player p1 = new AIPlayer(model, HexagonPlayer.BLACK, new AggressiveReversiStrategy());
    Player p2 = new AIPlayer(model, HexagonPlayer.WHITE, new AggressiveReversiStrategy());
    while (!model.isGameOver()) {
      // try to make the move, if an exception is thrown this mean that no move
      // was found and player must pass:
      try {
        Move move1 = p1.play(0,0);
        model.play(move1.getQ(), move1.getR(), move1.getPlayer());
      } catch (IllegalStateException e) {
        model.pass();
      }
      try {
        Move move2 = p2.play(0,0);
        model.play(move2.getQ(), move2.getR(), move2.getPlayer());
      } catch (IllegalStateException e) {
        model.pass();
      }

    }
    // since no move can be played anymore, a strat should throw
    // an exception when trying to find a move
    Assert.assertThrows(IllegalStateException.class,
        () -> strat.chooseMove(model, HexagonPlayer.BLACK));

  }

}
