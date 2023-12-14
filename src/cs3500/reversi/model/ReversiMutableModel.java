package cs3500.reversi.model;

import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.view.BoardObserver;

/**
 * Represents the model for the Reversi game. It offers methods to
 * manipulate the state of the game board.
 */
public interface ReversiMutableModel {

  /**
   * Allows a player to make a move at a specified position on the board.
   *
   * @param q The q-coordinate of the move.
   * @param r The r-coordinate of the move.
   * @param player The player making the move.
   * @throws IllegalArgumentException if invalid player type is given.
   * @throws IllegalStateException if the move is not allowable by rules of game.
   * @throws IllegalStateException if it's not the turn of the player
   *          specified by the {@code player} parameter.
   */
  void play(int q, int r, HexagonPlayer player) throws IllegalArgumentException;

  /**
   * Switches the current player's turn.
   *
   * <p>If the current player is BLACK, it will be changed to WHITE. If the current player is WHITE,
   * it will be changed to BLACK.</p>
   */
  void pass();

  /**
   * Starts the Reversi game.
   */
  void startGame();

  /**
   * Calculates and returns the score of the specified player.
   *
   * <p>The score is determined by counting the number of hexagons occupied by
   * the player's discs on the board.</p>
   *
   * @param player The player (either BLACK or WHITE) for whom to calculate the score.
   * @return The score of the specified player, represented as an integer.
   */
  int getScore(HexagonPlayer player);


  /**
   * Adds an observer to the list of observers.
   * @param observer The observer to be added.
   */
  void addObserver(BoardObserver observer);

  /**
   * Creates and returns a read-only copy of the current Reversi game model.
   * @return a read-only copy of the current Reversi game model.
   */
  ReversiReadOnlyModel readOnlyCopy();

  /**
   * Creates and returns a mutable copy of the current Reversi game model.
   * @return a mutable copy of the current Reversi game model.
   */
  ReversiMutableModel mutableCopy();
}
