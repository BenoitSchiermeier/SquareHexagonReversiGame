import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import cs3500.reversi.controller.MockReversiController;
import cs3500.reversi.model.Hexagon;
import cs3500.reversi.model.ReversiBoard;
import cs3500.reversi.player.AIPlayer;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.Player;
import cs3500.reversi.strategy.AggressiveReversiStrategy;
import cs3500.reversi.view.MockGameBoardLayout;
import cs3500.reversi.view.ViewFeatures;


/**
 * Test class for MockReversiController and MockGameBoardLayout. It shows their interaction
 * in various game scenarios with human and AI players. The tests cover the controller's
 * response to game events and the updating of the view.
 */
public class TestMockControllerAndView {
  private ReversiBoard model;
  private MockReversiController mock;
  private MockGameBoardLayout mockView;
  private MockReversiController mockController2;
  private MockGameBoardLayout mockViewAnother;

  private List<String> modelLog;

  private List<String> modelLog2;

  private ViewFeatures mockViewFeatures;

  /**
   * Sets up the testing for the class.
   */
  @Before
  public void setup() {
    ReversiBoard model = new ReversiBoard(4);

    mockView = new MockGameBoardLayout(model);
    Player mockPlayer = new HumanPlayer(model, Hexagon.HexagonPlayer.BLACK);
    modelLog = new ArrayList<>();
    mock = new MockReversiController(model, mockView, mockPlayer, modelLog);
    model.startGame();
    //sets up for an AI instance for testing
    ReversiBoard model2 = new ReversiBoard(4);

    modelLog2 = new ArrayList<>();
    mockViewAnother = new MockGameBoardLayout(model2);
    Player mockPlayer2 = new AIPlayer(model2, Hexagon.HexagonPlayer.BLACK,
            new AggressiveReversiStrategy());
    mockController2 = new MockReversiController(model2, mockViewAnother, mockPlayer2, modelLog2);

    model2.startGame();
  }

  @Test
  public void testControllerMethodsOnStart() {
    Assert.assertTrue(modelLog.contains("startObserverGame was used!"));
    Assert.assertTrue(modelLog.contains("onBoardChanged was used!"));
    Assert.assertTrue(modelLog.contains("onBoardChanged was used!"));
    Assert.assertTrue(modelLog2.contains("onBoardChanged was used!"));
    Assert.assertTrue(modelLog2.contains("startObserverGame was used!"));
  }


  @Test
  public void testOnBoardChangedUpdatesView() {
    Assert.assertTrue(modelLog.contains("onBoardChanged was used!"));

    assertEquals(mockView.getLog().toString(), "[setBackgroundColor, setFocusable, "
            + "setFeaturesListener, makeVisible, setBackgroundColor, setFocusable]");
  }


  @Test
  public void testStartObserverGameUpdatesView() {
    mock.startObserverGame();
    Assert.assertTrue(modelLog.contains("startObserverGame was used!"));
    assertEquals(mockView.getLog().toString(), "[setBackgroundColor, setFocusable, "
            + "setFeaturesListener, makeVisible, setBackgroundColor, setFocusable, "
            + "makeVisible, setBackgroundColor, setFocusable]");

  }


  @Test
  public void testHandlePassUpdatesView() {
    mock.handlePass();
    Assert.assertTrue(mock.getLog().contains("handlePass was used!"));
    assertEquals(mockView.getLog().toString(), "[setBackgroundColor, setFocusable, "
            + "setFeaturesListener, makeVisible, setBackgroundColor, setFocusable, "
            + "setFocusable]");

  }

  @Test
  public void testHandlePlay() {
    mock.handlePlay();
    Assert.assertTrue(mock.getLog().contains("handlePlay was used!"));
    assertEquals(mockView.getLog().toString(), "[setBackgroundColor, setFocusable, "
            + "setFeaturesListener, makeVisible, setBackgroundColor, setFocusable]");

  }

  @Test
  public void testHandleExceptions() {
    mock.handleExceptions(new IllegalStateException("Test Exception"));
    Assert.assertTrue(mock.getLog().contains("handleExceptions was used!"));
    assertEquals(mockView.getLog().toString(), "[setBackgroundColor, setFocusable, "
            + "setFeaturesListener, makeVisible, setBackgroundColor, setFocusable, "
            + "setBackgroundColor, showError, refresh called!, setBackgroundColor]");

  }

  @Test
  public void testOnBoardChangedAI() {
    mockController2.onBoardChanged();
    Assert.assertTrue(mockController2.getLog().contains("onBoardChanged was used!"));
  }


  @Test
  public void testStartObserverGameAI() {
    mockController2.startObserverGame();
    Assert.assertTrue(mockController2.getLog().contains("startObserverGame was used!"));
  }

  @Test
  public void testHandleExceptionsAI() {
    mockController2.handleExceptions(new IllegalStateException("Test Exception"));
    Assert.assertTrue(mockController2.getLog().contains("handleExceptions was used!"));
  }

  @Test
  public void testAutoplayIfAIUpdatesView() {
    mockController2.handleExceptions(new IllegalStateException("Test Exception"));
    Assert.assertTrue(mockController2.getLog().contains("handleExceptions was used!"));
    System.out.println(mockViewAnother.getLog());
    assertEquals(mockViewAnother.getLog().toString(), "[setBackgroundColor, setFocusable, "
            + "setFeaturesListener, makeVisible, setBackgroundColor, setFocusable, "
            + "setBackgroundColor, showError, refresh called!, setBackgroundColor]");
  }


  @Test
  public void testMockViewHasCorrectMethodsCalled() {
    List<String> log = mockView.getLog();
    Assert.assertTrue(log.contains("setBackgroundColor"));
    Assert.assertTrue(log.contains("setFocusable"));
    Assert.assertTrue(log.contains("makeVisible"));
    Assert.assertTrue(log.contains("setFocusable"));
    Assert.assertTrue(log.contains("setFeaturesListener"));
  }
}

