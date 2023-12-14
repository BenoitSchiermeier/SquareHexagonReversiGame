package cs3500.reversi.model;

import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.view.BoardObserver;

/**
 * Represents a read-only model of the Reversi game.
 * Provides methods to retrieve the current state of the game without modifying it.
 */
public interface ReversiReadOnlyModel {

  /**
   * Retrieves the current state of the Reversi board.
   *
   * @return A copy of the 2D array of Hexagons representing the board's current state.
   */
  Hexagon[][] getHexList();

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
   * Checks if the game is over, which occurs when neither player can make a valid move.
   *
   * @return {@code true} if no valid moves are possible for either player; {@code false} otherwise.
   */
  boolean isGameOver();

  /**
   * Retrieves the current player whose turn it is to make a move.
   *
   * @return The {@link HexagonPlayer} representing the current player.
   */
  HexagonPlayer getCurrentPlayer();

  /**
   * Retrieves the size of the game board.
   *
   * @return the size of the board, representing the number of hexes along one edge.
   */
  int getBoardSize();

  /**
   * Retrieves the occupancy status of the hexagon at the specified coordinates.
   * If no player occupies the hexagon, this method returns {@link HexagonPlayer#NONE}.
   *
   * @param q the q-coordinate of the hexagon on the hexagonal grid.
   * @param r the r-coordinate of the hexagon on the hexagonal grid.
   * @return the {@link HexagonPlayer} occupying the hexagon at the given coordinates,
   *         or {@link HexagonPlayer#NONE} if the hexagon is unoccupied.
   * @throws IllegalArgumentException if the coordinates do not correspond to a valid position
   *         on the board.
   */
  HexagonPlayer getOccupancy(int q, int r);


  /**
   * Determines if a given player can make a valid move at the specified (q, r) coordinates.
   * <p>
   * This method checks the validity of a potential move at the given coordinates for the specified
   * player.</p>
   *
   * @param q      The q-coordinate of the position to check for a valid move.
   * @param r      The r-coordinate of the position to check for a valid move.
   * @param player The player for whom the potential move's validity is being checked.
   * @return True if the player can make a valid move at the specified position, false otherwise.
   * @throws IllegalArgumentException if the coordinates do not correspond to a valid position
   *              on the board.
   */
  boolean canMove(int q, int r, HexagonPlayer player);

  /**
   * Determines if the specified player has any valid moves available on the board.
   *
   * <p>This method checks every position on the board to see if the given player
   * can make a valid move at that position. If at least one valid move is found,
   * the method returns true; otherwise, it returns false.</p>
   *
   * @param player The player (either BLACK or WHITE) for whom to check possible moves.
   * @return true if the player has at least one valid move available, false otherwise.
   */
  boolean canMove(HexagonPlayer player);

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

  int getArrayWidth();
}
