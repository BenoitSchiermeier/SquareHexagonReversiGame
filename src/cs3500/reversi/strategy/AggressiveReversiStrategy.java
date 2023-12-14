package cs3500.reversi.strategy;

import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.Pair;
import cs3500.reversi.model.ReversiMutableModel;
import cs3500.reversi.model.ReversiReadOnlyModel;
import java.util.ArrayList;
import java.util.List;

/**
 * An aggressive strategy implementation for choosing moves in the Reversi game.
 * This strategy aims to maximize the number of opponent pieces flipped with each move.
 */
public class AggressiveReversiStrategy implements ReversiStrategy {

  /**
   * Chooses the next move for a given player in the Reversi game based on an aggressive strategy.
   *
   * @param model The read-only model of the Reversi game, providing the current game state.
   * @param player The player (BLACK or WHITE) for whom the move is being chosen.
   * @return A Move representing the chosen move for the player.
   * @throws IllegalStateException If no valid moves are available.
   */
  @Override
  public Move chooseMove(ReversiReadOnlyModel model, HexagonPlayer player) {
    List<Move> validMoves = getAllValidMoves(model, player);
    if (!validMoves.isEmpty()) {
      return chooseBestMove(validMoves, model, player);
    } else {
      throw new IllegalStateException("no valid moves");
    }
  }

  /**
   * Calculates the number of opponent pieces that would be flipped by playing a specific move.
   *
   * @param move The move to be played.
   * @param model The game model for context.
   * @param player The player making the move.
   * @return The number of flipped pieces if the move is played.
   */
  protected static int calculateFlippedPieces(Move move,
      ReversiReadOnlyModel model, HexagonPlayer player) {
    ReversiMutableModel copyModel = model.mutableCopy();
    copyModel.play(move.getQ(), move.getR(), player);
    int newScore = copyModel.getScore(player);
    return newScore - model.getScore(player);
  }

  /**
   * Chooses the upper leftmost move in case of a tie in the number of flipped pieces.
   *
   * @param bestMove The current best move and its flipped piece count.
   * @param increase The number of pieces flipped by the new move.
   * @param r Row index of the new move.
   * @param q Column index of the new move.
   * @param move The new move being considered.
   * @return The updated best move after considering the new move.
   */
  protected static Pair<Move, Integer> chooseUpperLeftInTie(Pair<Move, Integer> bestMove,
      int increase,
      int r, int q, Move move) {
    if (increase == bestMove.getValue()) {
      if (bestMove.getKey() == null || r < bestMove.getKey().getR() ||
          (r == bestMove.getKey().getR() && q < bestMove.getKey().getQ())) {
        // update the move to the one with the uppermost-leftmost coordinate
        bestMove = new Pair<>(move, increase);
      }
    }
    return bestMove;
  }

  /**
   * Chooses the best move from a list of valid moves based on the aggressive strategy.
   *
   * @param moves The list of valid moves.
   * @param model The game model for context.
   * @param player The player making the move.
   * @return The move that maximizes the number of flipped opponent pieces.
   */
  public Move chooseBestMove(List<Move> moves, ReversiReadOnlyModel model, HexagonPlayer player) {
    Pair<Move, Integer> bestMove = new Pair<>(null, 0);

    for (Move move : moves) {
      int flipped = calculateFlippedPieces(move, model, player);
      if (flipped > bestMove.getValue()) {
        bestMove = new Pair<>(move, flipped);
      }
      if (flipped == bestMove.getValue()) {
        bestMove = chooseUpperLeftInTie(bestMove, flipped, move.getR(), move.getQ(), move);
      }
    }
    return bestMove.getKey();
  }

  /**
   * Gets a list of all valid moves for a given player based on the current game state.
   *
   * @param model The read-only game model.
   * @param player The player for whom to find valid moves.
   * @return A list of valid moves for the player.
   */
  protected List<Move> getAllValidMoves(ReversiReadOnlyModel model, HexagonPlayer player) {
    List<Move> moves = new ArrayList<>();
    int maxColRow = model.getBoardSize() * 2 - 1;
    for (int r = 0; r < model.getArrayWidth(); r++) {
      for (int q = 0; q < model.getArrayWidth(); q++) {
        if (model.getHexList()[q][r] != null && model.canMove(q, r, player)) {
          moves.add(new Move(q, r, player));
        }
      }
    }
    return moves;
  }


}

