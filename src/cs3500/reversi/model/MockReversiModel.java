package cs3500.reversi.model;

import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.view.BoardObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * A mock model for the Reversi game, extending the ReversiBoard.
 * This mock model is designed for testing purposes, to track and inspect moves
 * during the gameplay without affecting the actual game logic.
 */
public class MockReversiModel implements ReversiReadOnlyModel {
  private List<Pair<Integer, Integer>> inspectedPossibleMoves;
  private ReversiBoard board;



  /**
   * Constructs a new MockReversiModel with the specified board size.
   *
   * @param board A ReversiBoard.
   */
  public MockReversiModel(ReversiBoard board) {
    inspectedPossibleMoves = new ArrayList<>();
    this.board = board;
  }


  /**
   * Overrides the method in the parent class for the mock.
   *
   * @return The 2D array of Hexagons representing the current state of the game board.
   */

  @Override
  public Hexagon[][] getHexList() {
    return board.getHexList();
  }

  /**
   * Overrides the method in the parent class for the mock.
   *
   * @param player The HexagonPlayer for whom the score is to be obtained from.
   * @return The score of the specified player based of the state of the game.
   */

  @Override
  public int getScore(HexagonPlayer player) {
    return board.getScore(player);
  }

  /**
   * Overrides the method in the parent class for the mock.
   *
   * @return true if the game is over, false otherwise
   */

  @Override
  public boolean isGameOver() {
    return board.isGameOver();
  }

  /**
   * Overrides the method in the parent class for the mock.
   *
   * @return The  current hex player with respect to the game state.
   */

  @Override
  public HexagonPlayer getCurrentPlayer() {
    return board.getCurrentPlayer();
  }

  /**
   * Overrides the method in the parent class for the mock.
   *
   * @return The size of the game board.
   */

  @Override
  public int getBoardSize() {
    return board.getBoardSize();
  }

  /**
   * Overrides the method in the parent class for the mock.
   *
   * @return The 2D array of Hexagons representing the current state of the game board.
   */

  @Override
  public HexagonPlayer getOccupancy(int q, int r) {
    return board.getOccupancy(q, r);
  }

  /**
   * Overrides the canMove method to add functionality for tracking inspected coordinates.
   * This method determines if a move is valid at the specified coordinates for a given player
   * and records the inspected coordinates if the move is valid.
   *
   * @param q       The column index (q-axis) of the hexagon position.
   * @param r       The row index (r-axis) of the hexagon position.
   * @param player  The player (either BLACK or WHITE) attempting the move.
   * @return true if the move is valid, false otherwise.
   * @throws IllegalArgumentException If the provided hexagon position is invalid.
   */

  @Override
  public boolean canMove(int q, int r, HexagonPlayer player) {
    if (!board.validHexPosition(q, r)) {
      throw new IllegalArgumentException("not a valid hexagon position");
    }

    // determine if the move is possible:
    boolean bool = board.validateAndFlip(q, r, player,
        board.hexGridSearchPattern(), false, false);

    // add the coordinates to the list of Pair to the inspected coordinates transcript:
    if (bool) {
      // adds the inspected coordinates to the pair;
      this.inspectedPossibleMoves.add(new Pair<>(q, r));
    }
    // return if this move is possible
    return bool;
  }

  /**
   * Overrides the method in the parent class for the mock.
   *
   * @return The size of the game board.
   */

  @Override
  public boolean canMove(HexagonPlayer player) {
    return board.canMove(player);
  }

  /**
   * Overrides the method in the parent class for the mock.
   *
   * @param observer The BoardObserver to be added for changes in the game board.
   */

  @Override
  public void addObserver(BoardObserver observer) {
    board.addObserver(observer);
  }

  /**
   * Overrides the method in the parent class for the mock.
   *
   * @return The copy of the readOnly.
   */

  @Override
  public ReversiReadOnlyModel readOnlyCopy() {
    return this;
  }

  /**
   * Overrides the method in the parent class for the mock.
   *
   * @return the null because it is not used.
   */

  @Override
  public ReversiMutableModel mutableCopy() {
    return board.mutableCopy();
  }

  @Override
  public int getArrayWidth() {
    return board.getArrayWidth();
  }

  /**
   * Retrieves the list of coordinates that were inspected during the validity checks of moves.
   * This method provides access to the internal tracking of inspected coordinates,
   * useful for analyzing and verifying the behavior of game strategies.
   *
   * @return A list of pairs, each representing the q and r coordinates of
   *          inspected hexagon positions.
   */

  public List<Pair<Integer, Integer>> getInspectedPossibleMoves() {
    return this.inspectedPossibleMoves;
  }
}
