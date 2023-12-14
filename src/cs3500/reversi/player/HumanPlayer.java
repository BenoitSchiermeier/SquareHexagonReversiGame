package cs3500.reversi.player;

import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiReadOnlyModel;
import cs3500.reversi.strategy.Move;
import java.util.Objects;

/**
 * Represents a human player in a game of Reversi. This class implements the Player
 * interface, providing the functionality for a human player to interact with the
 * Reversi game board.
 */
public class HumanPlayer implements Player {
  ReversiReadOnlyModel board;
  HexagonPlayer player;

  /**
   * Constructs a HumanPlayer object with a specific game board and player identity.
   *
   * @param board The game board on which the player will play.
   * @param player The identity of the player (e.g., black or white in Reversi).
   * @throws NullPointerException if any of the parameters are null.
   */
  public HumanPlayer(ReversiReadOnlyModel board, HexagonPlayer player) {
    Objects.requireNonNull(board);
    Objects.requireNonNull(player);
    this.board = board;
    this.player = player;
  }

  /**
   * Makes a move on the game board at the specified coordinates.
   *
   * @param q The q-coordinate on the board where the player wants to play.
   * @param r The r-coordinate on the board where the player wants to play.
   */
  @Override
  public Move play(int q, int r) {
    return new Move(q, r, this.player);
  }

  /**
   * Passes the turn without making a move. This method is used in situations where
   * the player cannot make a valid move.
   */
  @Override
  public Move pass() {
    return new Move(true, player);
  }

  @Override
  public HexagonPlayer getPlayerColor() {
    return this.player;
  }

  @Override
  public boolean isPlayerTurn() {
    return board.getCurrentPlayer() == player;
  }
}
