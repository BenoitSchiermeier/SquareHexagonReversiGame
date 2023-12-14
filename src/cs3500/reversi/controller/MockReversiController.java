package cs3500.reversi.controller;

import java.util.List;

import cs3500.reversi.model.ReversiMutableModel;
import cs3500.reversi.player.Player;
import cs3500.reversi.view.IReversiView;
import cs3500.reversi.view.ViewFeatures;

/**
 * A mock controller for the Reversi game, extending ReversiController.
 * It is designed for testing purposes, logging the usage of overridden methods
 * to track interactions and behaviors in a controlled environment.
 */
public class MockReversiController extends ReversiController implements ViewFeatures {

  private List<String> log;

  /**
   * Constructs a MockReversiController with the specified model, view, and player.
   * Initializes the superclass ReversiController with these components.
   *
   * @param model  The game model.
   * @param view   The game view.
   * @param player The player associated with this controller.
   */
  public MockReversiController(ReversiMutableModel model, IReversiView view, Player player,
                               List<String> log) {
    super(model, view, player);
    this.log = log;
  }


  /**
   * Logs the pass key being pressed.
   */
  @Override
  public void onPassKeyPressed() {
    log.add("onPassKeyPressed was called!");
  }

  /**
   * Logs the play key being pressed.
   */
  @Override
  public void onPlayKeyPressed() {
    log.add("onPlayKeyPressed was called!");
  }


  /**
   * Logs the use of the onBoardChanged method and invokes the superclass implementation.
   */
  @Override
  public void onBoardChanged() {
    log.add("onBoardChanged was used!");
    super.onBoardChanged();
  }

  /**
   * Logs the use of the startObserverGame method and invokes the superclass implementation.
   */
  @Override
  public void startObserverGame() {
    log.add("startObserverGame was used!");
    super.startObserverGame();
  }

  /**
   * Logs the use of the handlePass method and invokes the superclass implementation.
   */
  @Override
  public void handlePass() {
    log.add("handlePass was used!");
    super.handlePass();
  }

  /**
   * Logs the use of the handlePlay method and invokes the superclass implementation.
   */
  @Override
  public void handlePlay() {
    log.add("handlePlay was used!");
    super.handlePlay();
  }

  /**
   * Logs the use of the handleExceptions method and invokes the superclass implementation.
   *
   * @param e The exception to handle.
   */
  @Override
  public void handleExceptions(IllegalStateException e) {
    log.add("handleExceptions was used!");
    super.handleExceptions(e);
  }

  /**
   * Retrieves the log of method usages in the mock controller.
   *
   * @return A list of strings representing the logged method usages.
   */
  public List<String> getLog() {
    return log;
  }

  /**
   * Logs the use of the autoPlayIfAI method.
   */
  @Override
  public void autoPlayIfAI() {
    log.add("autoPlayIfAI was used!");
  }
}
