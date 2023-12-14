package cs3500.reversi.player;

import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.strategy.Move;

/**
 * Represents a player in the Reversi game.
 * <p>
 * This interface provides methods for a player to make a move or pass their turn.
 * Implementations of this interface might include human players, AI players, or any other
 * strategy-driven player types.
 * </p>
 */
public interface Player {

  /**
   * Performs a move in the Reversi game.
   *
   * @param q The horizontal coordinate for the move.
   * @param r The vertical coordinate for the move.
   * @return The move executed by the player.
   */
  Move play(int q, int r);

  /**
   * Passes the turn without making a move.
   * This method is used when a player cannot make a valid move.
   */
  Move pass();

  /**
   * Retrieves the color (BLACK or WHITE) associated with this player in the Reversi game.
   *
   * @return The HexagonPlayer enumeration value representing the player's color.
   */
  HexagonPlayer getPlayerColor();

  /**
   * Determines whether it is currently this player's turn in the game.
   *
   * @return true if it is the player's turn, false otherwise.
   */
  boolean isPlayerTurn();
}

