package cs3500.reversi.player;

import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiReadOnlyModel;
import cs3500.reversi.strategy.Move;
import cs3500.reversi.strategy.ReversiStrategy;
import java.util.Objects;

/**
 * Represents an AI player in the Reversi game.
 * This class implements the Player interface, providing AI-specific
 * logic for making moves and passing turns. It uses a specified strategy to determine
 * moves based on the current state of the game.
 */
public class AIPlayer implements Player {
  ReversiReadOnlyModel board;
  HexagonPlayer player;
  ReversiStrategy strategy;

  /**
   * Constructs an AI player for the Reversi game.
   * Initializes an AI player with a specific game board, player type,
   * and strategy. Each parameter is required and must not be null. This constructor ensures that
   * all necessary components for the AI player are set up
   * correctly before the game starts.
   *
   * @param board    The current state of the Reversi game board. Must not be null.
   * @param player   The player type (e.g., black or white) that
   *                 this AI will represent. Must not be null.
   * @param strategy The strategy that the AI will use to determine its moves. Must not be null.
   * @throws NullPointerException if any of the parameters are null.
   */
  public AIPlayer(ReversiReadOnlyModel board, HexagonPlayer player, ReversiStrategy strategy) {
    Objects.requireNonNull(board);
    Objects.requireNonNull(player);
    Objects.requireNonNull(strategy);
    this.board = board;
    this.player = player;
    this.strategy = strategy;
  }


  /**
   * Determines the best move to be played by the AI player based on its strategy.
   *
   * @param q The Q coordinate of the move.
   * @param r The R coordinate of the move.
   * @return The chosen move.
   */
  @Override
  public Move play(int q, int r) {
    try {
      Move move = strategy.chooseMove(board, player);
      return move;
    }
    catch (IllegalStateException e) {
      // pass move:
      return new Move(true, player);
    }
  }


  /**
   * Make a pass move for the AI player.
   *
   * @return The pass move.
   */
  @Override
  public Move pass() {
    return new Move(true, player);
  }

  /**
   * Retrieves the player color associated with the AI player.
   *
   * @return The player color.
   */
  @Override
  public HexagonPlayer getPlayerColor() {
    return this.player;
  }


  /**
   * Checks if it is the AI player's turn to make a move.
   *
   * @return True if it's the AI (player) turn, false if not.
   */
  @Override
  public boolean isPlayerTurn() {
    return board.getCurrentPlayer() == player;
  }
}
