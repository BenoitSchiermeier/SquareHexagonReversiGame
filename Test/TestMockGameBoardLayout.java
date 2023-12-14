import cs3500.reversi.controller.ReversiController;
import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiBoard;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.Player;
import cs3500.reversi.view.MockGameBoardLayout;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains unit tests for the MockGameBoardLayout in a Reversi game.
 * It tests the interaction of the mock game board layout with the game model and controller,
 * ensuring the correct behavior of the mock layout during the game lifecycle.
 */
public class TestMockGameBoardLayout {
  private ReversiBoard model;
  private MockGameBoardLayout mock;

  /**
   * Setting up the test class.
   */
  @Before
  public void setup() {
    model = new ReversiBoard(4);
    model.startGame();
    mock = new MockGameBoardLayout(model);
  }

  @Test
  public void testLogEmptyOnView() {
    Assert.assertTrue(mock.getLog().isEmpty());
  }

  @Test
  public void testControllerCallsViewMockOnStart() {
    Player p1 = new HumanPlayer(model, HexagonPlayer.BLACK);
    ReversiController controller = new ReversiController(model, mock, p1);

    // make sure the log is not empty
    Assert.assertFalse(mock.getLog().isEmpty());

    Assert.assertTrue(mock.getLog().contains("setBackgroundColor"));
    Assert.assertTrue(mock.getLog().contains("setFocusable"));
  }

  @Test
  public void testControllerCallsViewMockOnMove() {
    model.play(4, 1, HexagonPlayer.BLACK);
    Player p1 = new HumanPlayer(model, HexagonPlayer.WHITE);
    ReversiController controller = new ReversiController(model, mock, p1);
    controller.startObserverGame();
    // make sure the log is not empty
    Assert.assertFalse(mock.getLog().isEmpty());

    Assert.assertTrue(mock.getLog().contains("setBackgroundColor"));
    Assert.assertTrue(mock.getLog().contains("setFocusable"));
    Assert.assertTrue(mock.getLog().contains("makeVisible"));

    System.out.println(mock.getLog());
  }
}
