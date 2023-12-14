import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.controller.MockReversiController;
import cs3500.reversi.controller.ReversiController;
import cs3500.reversi.model.Hexagon;
import cs3500.reversi.model.MockReversiBoard;
import cs3500.reversi.model.ReversiBoard;
import cs3500.reversi.model.ReversiMutableModel;
import cs3500.reversi.player.AIPlayer;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.Player;
import cs3500.reversi.strategy.AggressiveReversiStrategy;
import cs3500.reversi.view.MockGameBoardLayout;
import cs3500.reversi.view.ViewFeatures;



/**
 * Tests the relation call between the controller to view + controller to model.
 * This small tests makes sure of the non-concerete version + just seeing if things were called.
 */
public class TestController {


  private ReversiController mock;
  private ReversiController mockController2;

  private List<String> modelLog;

  private List<String> modelLog2;
  private List<String> boardLog;

  private ViewFeatures mockViewFeatures;

  private Player mockPlayer;

  private Player mockPlayer2;


  /**
   * Sets up the testing environment.
   */
  @Before
  public void setup() {
    boardLog = new ArrayList<>();
    ReversiMutableModel model = new MockReversiBoard(boardLog);

    MockGameBoardLayout mockView = new MockGameBoardLayout(model.readOnlyCopy());
    mockPlayer = new HumanPlayer(model.readOnlyCopy(), Hexagon.HexagonPlayer.BLACK);
    modelLog = new ArrayList<>();
    mock = new MockReversiController(model, mockView, mockPlayer, modelLog);
    model.startGame();
    //sets up for an AI instance for testing
    ReversiBoard model2 = new ReversiBoard(4);

    modelLog2 = new ArrayList<>();
    MockGameBoardLayout mockViewAnother = new MockGameBoardLayout(model2);
    mockPlayer2 = new AIPlayer(model2, Hexagon.HexagonPlayer.WHITE,
            new AggressiveReversiStrategy());
    mockController2 = new MockReversiController(model2, mockViewAnother, mockPlayer2, modelLog2);

    model2.startGame();
  }

  @Test
  public void testControllerOnPass() {
    mock.onPassKeyPressed();

    //testing if once the onPassKey is pressed, it is calling and being reflected in the model
    //this means that the passAction was correctly reflected
    Assert.assertTrue(modelLog.contains("onPassKeyPressed was called!"));

    Assert.assertTrue(boardLog.contains("readOnly was added!"));
    Assert.assertTrue(boardLog.contains("observer was added!"));
    Assert.assertTrue(boardLog.contains("game starts!"));

  }



  @Test
  public void testClickOnView() {
    mockPlayer.play(2, 1);
    mock.onPlayKeyPressed();

    // Ensure that the model is updated accordingly
    Assert.assertTrue(modelLog.contains("onPlayKeyPressed was called!"));
  }



  @Test
  public void testClickOnViewAI() {
    mockPlayer2.play(2, 1);
    mockController2.onPlayKeyPressed();

    // Ensure that the model is updated accordingly
    Assert.assertTrue(modelLog2.contains("onPlayKeyPressed was called!"));
  }


  @Test
  public void testShowingAIWasUsed() {
    mockPlayer2.pass();
    Assert.assertTrue(modelLog2.contains("autoPlayIfAI was used!"));
  }
}
