package cs3500.reversi.strategy;

import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiReadOnlyModel;

/**
 * A strategy that attempts to use two different strategies for making a move in a Reversi game.
 * It first tries the primary strategy and, if it fails, resorts to the secondary strategy.
 */
public class TryTwo implements ReversiStrategy {
  ReversiStrategy first;
  ReversiStrategy second;

  /**
   * Constructs a TryTwo strategy using two different strategies.
   *
   * @param first The primary strategy to be attempted first.
   * @param second The secondary strategy to be used if the first one fails.
   */
  public TryTwo(ReversiStrategy first, ReversiStrategy second) {
    this.first = first;
    this.second = second;
  }

  /**
   * Chooses a move for a player in a Reversi game using the primary strategy.
   * If the primary strategy fails (no valid moves), it tries the secondary strategy.
   *
   * @param model The read-only model of the Reversi game, providing the current game state.
   * @param player The player (BLACK or WHITE) for whom the move is being chosen.
   * @return A Move representing the chosen move for the player.
   * @throws IllegalStateException If no valid moves are available using either strategy.
   */
  @Override
  public Move chooseMove(ReversiReadOnlyModel model, HexagonPlayer player) {
    // try the first strategy
    try {
      if (this.first == null) {
        throw new IllegalStateException("null first Player");
      }
      return this.first.chooseMove(model, player);
    }
    // if first strategy throws an exception because no move is available using that strategy,
    // try the second strategy:
    catch (IllegalStateException e) {
      try {
        if (this.second == null) {
          throw new IllegalStateException("null second Player");
        }
        return second.chooseMove(model, player);
      }
      // if the second strategy has no available moves, throw an exception
      catch (IllegalStateException exception) {
        throw new IllegalStateException("no move available, must be passed");
      }
    }
  }
}
