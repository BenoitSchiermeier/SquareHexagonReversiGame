package cs3500.reversi.view;

import cs3500.reversi.model.Hexagon.HexagonPlayer;
import java.awt.Color;
import java.awt.event.KeyListener;

/**
 * Represents the view component in the Model-View-Controller (MVC)
 * architecture for the Reversi game.
 *
 * <p>The view is responsible for presenting the game state, including the game board,
 * current player, scores, and any relevant messages or prompts to the user.
 * It does not handle any game logic but relies on information provided by the game model
 * to render the game state accurately.</p>
 */
public interface IReversiView {

  /**
   * Retrieves the currently selected hexagon button on the game board.
   *
   * @return the selected HexagonButton, or null if no button is selected.
   */
  HexagonButton getSelectedButton();

  /**
   * Refreshes the view to reflect any changes in the game state.
   * This may include updating the game board, scores, and other visual elements.
   */
  void refresh();

  /**
   * Makes the game view visible to the user. This is typically called at the start
   * of the game to display the game window or interface.
   */
  void makeVisible();

  /**
   * Displays an error message to the user. This is used for communicating
   * errors or invalid actions to the user.
   *
   * @param errorMessage the error message to be displayed.
   */
  void showError(String errorMessage);

  /**
   * Sets the background color of the view.
   *
   * @param color the color to set the background to.
   */
  void setBackgroundColor(Color color);

  /**
   * Enables or disables the focusability of the component, when set to focusable,
   * the component can receive keyboard input.
   */
  void setFocusable();


  /**
   * Sets the listener for various view features, such as key press events.
   * This method is used to register a controller or another component that implements
   * the ViewFeatures interface, enabling the view to notify the listener about specific
   * events like key presses for game actions.
   *
   * @param listener the ViewFeatures listener to be set.
   */
  void setFeaturesListener(ViewFeatures listener);

  /**
   * Adds hints to the game, when cell is selected, the number of captures will be shown.
   *
   * @param player the player for which the hints is calculating.
   */
  void addHints(HexagonPlayer player);
}
