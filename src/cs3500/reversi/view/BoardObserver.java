package cs3500.reversi.view;

/**
 * Interface for an observer in the Observer pattern, specifically for observing
 * changes in the game board of Reversi. Classes implementing this interface can
 * respond to notifications about changes in the game state.
 */
public interface BoardObserver {

  /**
   * Called when the game board has changed. Implementations of this method
   * should contain the logic to respond to updates in the game state, such as
   * refreshing the view or handling game over scenarios.
   */
  void onBoardChanged();

  /**
   * Makes the game view visible. Typically called at the start of the game.
   */
  void startObserverGame();
}
