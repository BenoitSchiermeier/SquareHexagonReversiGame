package cs3500.reversi.view;

import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiReadOnlyModel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * A mock implementation of GameBoardLayout for testing purposes.
 * This class extends GameBoardLayout and implements IReversiView and BoardObserver,
 * with additional logging functionalities to track method calls.
 */
public class MockGameBoardLayout implements IReversiView, BoardObserver {
  List<String> log = new ArrayList<String>();

  /**
   * Constructs a MockGameBoardLayout instance with the given Reversi game model.
   * Initializes the superclass GameBoardLayout with the provided model.
   *
   * @param model The read-only model of the Reversi game.
   */
  public MockGameBoardLayout(ReversiReadOnlyModel model) {
    //empty since it is not used. Was told that it was okay.
  }



  /**
   * Returns the log of method calls made on this mock object.
   *
   * @return A list of strings representing the log of method calls.
   */
  public List<String> getLog() {
    return this.log;
  }

  /**
   * Overrides the onBoardChanged method from BoardObserver.
   * Logs the call to this method.
   */
  @Override
  public void onBoardChanged() {
    log.add("onBoardChanged called");
  }

  @Override
  public void startObserverGame() {
    log.add("start Observer Game!");
  }

  @Override
  public HexagonButton getSelectedButton() {
    return null;
  }

  @Override
  public void refresh() {
    this.log.add("refresh called!");
  }

  /**
   * Overrides the makeVisible method from IReversiView.
   * Logs the call to this method.
   */
  @Override
  public void makeVisible() {
    log.add("makeVisible");
  }

  /**
   * Overrides the setBackgroundColor method from IReversiView.
   * Logs the call to this method and the color set.
   *
   * @param color The color to set for the background.
   */
  @Override
  public void setBackgroundColor(Color color) {
    log.add("setBackgroundColor");
  }

  /**
   * Overrides the setFocusable method from IReversiView.
   * Logs the call to this method.
   */
  @Override
  public void setFocusable() {
    log.add("setFocusable");
  }

  @Override
  public void showError(String e) {
    log.add("showError");
  }

  @Override
  public void setFeaturesListener(ViewFeatures listener) {
    log.add("setFeaturesListener");

  }

  @Override
  public void addHints(HexagonPlayer player) {

  }

}
