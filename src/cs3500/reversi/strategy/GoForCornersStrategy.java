package cs3500.reversi.strategy;

import static cs3500.reversi.strategy.AvoidCornersStrategy.getCornerPairs;

import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.Pair;
import cs3500.reversi.model.ReversiReadOnlyModel;
import java.util.ArrayList;
import java.util.List;

/**
 * A strategy implementation for the Reversi game that prioritizes capturing corner positions.
 * Corner positions are often advantageous in Reversi, as they cannot be flipped once captured.
 */
public class GoForCornersStrategy implements ReversiStrategy {

  /**
   * Chooses the next move for a given player in the Reversi game, prioritizing corner moves.
   *
   * @param model The read-only model of the Reversi game, providing the current game state.
   * @param player The player (BLACK or WHITE) for whom the move is being chosen.
   * @return A Move representing the chosen move for the player.
   * @throws IllegalStateException If no valid moves are available.
   */
  @Override
  public Move chooseMove(ReversiReadOnlyModel model, HexagonPlayer player) {
    AggressiveReversiStrategy aggressiveStrat = new AggressiveReversiStrategy();
    List<Pair<Integer, Integer>> corners = getCornerPairs(model.getBoardSize());
    List<Move> validMoves = aggressiveStrat.getAllValidMoves(model, player);

    // if there are corner moves pick from them:
    List<Move> cornerMoves = findCornerMoves(validMoves, corners);

    // choose the best corner move
    if (!cornerMoves.isEmpty()) {
      return aggressiveStrat.chooseBestMove(cornerMoves, model, player);
    }
    else {
      throw new IllegalStateException("there are no valid moves here");
    }

  }

  /**
   * Finds moves that target corner positions.
   *
   * @param moves The list of potential moves.
   * @param corners The list of corner coordinates.
   * @return A list of moves that target the corners.
   */
  private List<Move> findCornerMoves(List<Move> moves, List<Pair<Integer, Integer>> corners) {
    List<Move> cornerMoves = new ArrayList<>();
    for (Move move : moves) {
      for (Pair<Integer, Integer> pair : corners) {
        if ((move.getQ() == pair.getKey()) && (move.getR() == pair.getValue())) {
          cornerMoves.add(new Move(move.getQ(), move.getR(), move.getPlayer()));
        }
      }
    }
    return cornerMoves;
  }

}
