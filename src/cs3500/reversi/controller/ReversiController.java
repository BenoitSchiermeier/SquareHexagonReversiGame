package cs3500.reversi.controller;

import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiMutableModel;
import cs3500.reversi.player.AIPlayer;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.Player;
import cs3500.reversi.strategy.Move;
import cs3500.reversi.view.BoardObserver;
import cs3500.reversi.view.HexagonButton;
import cs3500.reversi.view.IReversiView;
import cs3500.reversi.view.ViewFeatures;
import java.awt.Color;

/**
 * Controller for the Reversi game. It coordinates interactions between the model and the view,
 * handling game logic, player inputs, and updates to the view. Implements both IController and
 * BoardObserver interfaces.
 */
public class ReversiController implements BoardObserver, ViewFeatures {

  private ReversiMutableModel model;
  private final Player player;
  private IReversiView view;

  /**
   * Constructs a ReversiController with a given model, view, and player.
   * It sets up the key listener and registers itself as an observer to the model.
   *
   * @param model  The game model.
   * @param view   The game view.
   * @param player The player associated with this controller.
   */
  public ReversiController(ReversiMutableModel model, IReversiView view, Player player) {
    this.model = model;
    model.addObserver(this);
    setPlayerTurn(player, view);
    view.setFocusable();
    this.player = player;
    this.view = view;
    // adds the controller as a fea
    this.view.setFeaturesListener(this);
  }

  /**
   * Handles the 'pass' key press event.
   * If the current player is a human, it triggers the logic to handle a pass action.
   */
  @Override
  public void onPassKeyPressed() {
    if (player instanceof HumanPlayer) {
      handlePass();
    }
  }

  /**
   * Handles the 'play' key press event.
   * If the current player is a human, it triggers the logic to handle a play action.
   */
  @Override
  public void onPlayKeyPressed() {
    if (player instanceof HumanPlayer) {
      handlePlay();
    }
  }

  @Override
  public void makeHints() {
    view.addHints(player.getPlayerColor());
    view.refresh();
    setPlayerTurn(player, view);
    view.setFocusable();
  }

  /**
   * Called when the game board changes. It updates the player's turn display and
   * automatically plays a move if the current player is an AI.
   */
  @Override
  public void onBoardChanged() {
    setPlayerTurn(player, view);
    autoPlayIfAI();
    view.setFocusable();
  }

  /**
   * Sets the background color of the view to indicate the current player's turn.
   *
   * @param player The current player.
   * @param view   The game view.
   */
  private void setPlayerTurn(Player player, IReversiView view) {
    if (player.isPlayerTurn()) {
      // set a message in the view saying, its your turn ...
      view.setBackgroundColor(Color.YELLOW);
    }
  }

  /**
   * Automatically plays a move if the current player is an AI player.
   * It handles the game logic and updates the model and view accordingly.
   */
  protected void autoPlayIfAI() {
    if (player instanceof AIPlayer && player.isPlayerTurn()) {
      setPlayerTurn(player, view);

      try {
        Move move = player.play(0, 0);
        if (move.getPass()) {
          model.pass();
        }
        else {
          model.play(move.getQ(), move.getR(), move.getPlayer());
        }

      } catch (IllegalStateException e) {
        handleExceptions(e);
      }
    }
  }

  /**
   * Makes the game view visible. Typically called at the start of the game.
   */
  @Override
  public void startObserverGame() {
    view.makeVisible();
    onBoardChanged();
  }

  /**
   * Handles a pass action from the player. It validates the player's turn
   * and updates the model to indicate a pass move.
   */
  protected void handlePass() {
    if (!player.isPlayerTurn()) {
      handleExceptions(
          new IllegalStateException("it's not " + player.getPlayerColor() + "'s turn"));
    } else {
      try {
        Move passMove = player.pass();
        if (passMove.getPass()) {
          model.pass();
        }
      } catch (IllegalStateException e) {
        handleExceptions(e);
      }
    }
  }

  /**
   * Handles a play action from the player. It gets the selected button's coordinates
   * and makes a move in the model.
   */
  protected void handlePlay() {
    try {
      HexagonButton selectedButton = view.getSelectedButton();
      if (selectedButton != null) {
        int q = selectedButton.getQ();
        int r = selectedButton.getR();
        Move move = player.play(q, r);
        model.play(move.getQ(), move.getR(), move.getPlayer());
      }
    } catch (IllegalStateException e) {
      handleExceptions(e);
    }
  }

  /**
   * Handles exceptions by displaying an error message in
   * the view and changing the background color.
   *
   * @param e The exception to handle.
   */
  protected void handleExceptions(IllegalStateException e) {
    String s = e.getMessage();
    view.setBackgroundColor(Color.RED);

    if (!s.contains("The game has been ended")) {
      this.view.showError(s);
      // refresh the view so the background is no longer red:
      view.refresh();
    }
    setPlayerTurn(player, view);
  }
}
