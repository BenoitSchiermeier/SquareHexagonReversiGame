package cs3500.reversi.strategy;

import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiReadOnlyModel;

/**
 * Represents a strategy for making moves in the Reversi game.
 * Implementations of this interface define various strategies for choosing the next move.
 */
public interface ReversiStrategy {

  /**
   * Chooses the next move for a given player in the Reversi game.
   *
   * @param model The read-only model of the Reversi game, providing the current game state.
   * @param player The player (BLACK or WHITE) for whom the move is being chosen.
   * @return A Move representing the chosen move for the player.
   * @throws IllegalStateException if there is no move to be found for the current player.
   */
  Move chooseMove(ReversiReadOnlyModel model, HexagonPlayer player);
}
