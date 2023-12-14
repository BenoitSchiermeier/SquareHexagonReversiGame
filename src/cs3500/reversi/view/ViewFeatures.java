package cs3500.reversi.view;

/**
 * Interface defining features for handling key press events in the Reversi game view.
 * It allows for the implementation of reactions to specific key presses,
 * facilitating interaction between the view and the controller.
 */
public interface ViewFeatures {

  /**
   * Handles the 'pass' key press event.
   * If the current player is a human, it triggers the logic to handle a pass action.
   */
  void onPassKeyPressed();

  /**
   * Handles the 'play' key press event.
   * If the current player is a human, it triggers the logic to handle a play action.
   */
  void onPlayKeyPressed();

  /**
   * Adds hints to the game, when cell is selected, the number of captures will be shown.
   */
  void makeHints();
}
