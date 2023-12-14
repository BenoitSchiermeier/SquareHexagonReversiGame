package cs3500.reversi.strategy;

import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.Pair;
import cs3500.reversi.model.ReversiReadOnlyModel;
import java.util.ArrayList;
import java.util.List;

/**
 * A strategy implementation for the Reversi game that avoids making moves adjacent to corners.
 * This strategy is based on the principle that occupying or being adjacent to corners can be
 * strategically disadvantageous in certain situations.
 */
public class AvoidCornersStrategy implements ReversiStrategy {

  /**
   * Chooses the next move for a given player in the Reversi game,
   * avoiding moves adjacent to corners.
   *
   * @param model The read-only model of the Reversi game, providing the current game state.
   * @param player The player (BLACK or WHITE) for whom the move is being chosen.
   * @return A Move representing the chosen move for the player.
   * @throws IllegalStateException If no valid moves are available.
   */
  @Override
  public Move chooseMove(ReversiReadOnlyModel model, HexagonPlayer player) {
    int boardSize = model.getBoardSize();

    // represent the corners of a hexagon grid
    List<Pair<Integer, Integer>> corners = getCornerPairs(boardSize);

    // strategy to build on:
    AggressiveReversiStrategy strat = new AggressiveReversiStrategy();
    List<Move> validMoves = strat.getAllValidMoves(model, player);
    List<Move> nonCornerAdjacentMoves = filterCornerAdjacentMoves(validMoves, corners);
    if (!nonCornerAdjacentMoves.isEmpty()) {
      return strat.chooseBestMove(nonCornerAdjacentMoves, model, player);
    }
    else {
      throw new IllegalStateException("No valid moves available");
    }
  }


  /**
   * Generates a list of pairs representing the corners of a hexagonal board.
   *
   * @param boardSize The size of the board.
   * @return A list of pairs representing the coordinates of the board's corners.
   */
  protected static List<Pair<Integer, Integer>> getCornerPairs(int boardSize) {
    Pair<Integer, Integer> topLeftCorner = new Pair<>(boardSize - 1, 0);
    Pair<Integer, Integer> topRightCorner = new Pair<>(boardSize * 2 - 2, 0);
    Pair<Integer, Integer> bottomLeftCorner = new Pair<>(0, boardSize * 2 - 2);
    Pair<Integer, Integer> bottomRightCorner = new Pair<>(boardSize - 1, boardSize * 2 - 2);

    List<Pair<Integer, Integer>> corners = new ArrayList<>();
    corners.add(topLeftCorner);
    corners.add(topRightCorner);
    corners.add(bottomLeftCorner);
    corners.add(bottomRightCorner);
    return corners;
  }

  /**
   * Filters out moves that are adjacent to corners.
   *
   * @param moves The list of potential moves.
   * @param corners The list of corner coordinates.
   * @return A list of moves that are not adjacent to any corners.
   */
  protected List<Move>
      filterCornerAdjacentMoves(List<Move> moves, List<Pair<Integer, Integer>> corners) {
    List<Move> nonCornerMoves = new ArrayList<>();
    for (Move move : moves) {
      boolean isAdjacentToCorner = false;
      for (Pair<Integer, Integer> corner : corners) {
        if (isAdjacent(move, corner)) {
          isAdjacentToCorner = true;
          break;
        }
      }
      if (!isAdjacentToCorner) {
        nonCornerMoves.add(move);
      }
    }
    return nonCornerMoves;
  }

  /**
   * Determines if a move is adjacent to a corner.
   *
   * @param move The move to check.
   * @param corner The corner coordinates.
   * @return True if the move is adjacent to the corner, false otherwise.
   */
  private boolean isAdjacent(Move move, Pair<Integer, Integer> corner) {
    int qDiff = move.getQ() - corner.getKey();
    int rDiff = move.getR() - corner.getValue();
    if (qDiff + rDiff > 1) {
      return false;
    }
    if (qDiff == 0 && rDiff == 0) {
      return false;
    }
    return (qDiff <= 1 && qDiff >= -1) && (rDiff <= 1 && rDiff >= -1);
  }

}
