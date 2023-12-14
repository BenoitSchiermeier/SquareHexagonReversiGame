package cs3500.reversi.strategy;

import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiBoard;
import cs3500.reversi.model.ReversiReadOnlyModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Minimax strategy in the game of Reversi.
 * This strategy attempts to maximize the player's advantage while minimizing the opponent's.
 */
public class MinimaxStrategy implements ReversiStrategy {
  ReversiStrategy opponentStrat;

  /**
   * Constructs a MinimaxStrategy with a presumed opponent strategy.
   *
   * @param guessOpponentStrategy the strategy assumed to be used by the opponent.
   */
  public MinimaxStrategy(ReversiStrategy guessOpponentStrategy) {
    opponentStrat = guessOpponentStrategy;
  }

  /**
   * Chooses the optimal move based on the Minimax strategy.
   *
   * @param model  the current state of the Reversi game.
   * @param player the player making the move.
   * @return the calculated best move.
   * @throws IllegalStateException if no matching move is found.
   */
  @Override
  public Move chooseMove(ReversiReadOnlyModel model, HexagonPlayer player)
      throws IllegalStateException {
    HexagonPlayer otherPlayer = findOtherPlayer(player);
    Move opponentsBestMove = findOtherPlayerMove(otherPlayer, model);

    AggressiveReversiStrategy strat = new AggressiveReversiStrategy();
    // find all possible moves:
    List<Move> validMoves = strat.getAllValidMoves(model, player);
    List<Move> moveToBeMade = findMatchingMove(model, validMoves, opponentsBestMove);

    if (!moveToBeMade.isEmpty()) {
      return strat.chooseBestMove(moveToBeMade, model, player);
    }
    else {
      throw new IllegalStateException("no matching move in MinOpponentMaxMove");
    }

  }

  /**
   * Finds moves that counter the opponent's best move.
   *
   * @param model            the current state of the game.
   * @param validMoves       list of valid moves for the player.
   * @param opponentsBestMove the best move of the opponent.
   * @return a list of moves that effectively disrupt the opponent's strategy.
   */
  private List<Move> findMatchingMove(ReversiReadOnlyModel model,
      List<Move> validMoves, Move opponentsBestMove) {
    List<Move> disruptiveMoves = new ArrayList<>();
    for (Move move : validMoves) {
      if ((move.getQ() == opponentsBestMove.getQ()) && (move.getR() == opponentsBestMove.getR())) {
        disruptiveMoves.add(move);
      }
      if (disruptsOtherPlayerMove(model, move, opponentsBestMove)) {
        disruptiveMoves.add(move);
      }
    }
    // return the list of disruptive moves
    return disruptiveMoves;
  }

  /**
   * Determines the opposing player.
   *
   * @param player the current player.
   * @return the opposing player.
   * @throws IllegalArgumentException if the player is invalid.
   */
  private HexagonPlayer findOtherPlayer(HexagonPlayer player) {
    if (player == HexagonPlayer.BLACK) {
      return HexagonPlayer.WHITE;
    }
    else if (player == HexagonPlayer.WHITE) {
      return HexagonPlayer.BLACK;
    }
    else {
      throw new IllegalArgumentException("invalid player for player swich");
    }
  }

  /**
   * Checks if a move disrupts the other player's move.
   *
   * @param model          the game model.
   * @param move           the player's move.
   * @param otherPlayerMove the opponent's move.
   * @return true if the move disrupts the opponent's move, false otherwise.
   */
  private boolean disruptsOtherPlayerMove(ReversiReadOnlyModel model,
      Move move, Move otherPlayerMove) {
    ReversiBoard copyModel = new ReversiBoard(model);
    copyModel.play(move.getQ(), move.getR(), move.getPlayer());
    try {
      copyModel.play(otherPlayerMove.getQ(), otherPlayerMove.getR(), otherPlayerMove.getPlayer());
    }
    catch (IllegalStateException e) {
      return true;
    }
    return false;
  }

  /**
   * Finds the best move for the other player based on the assumed opponent strategy.
   *
   * @param otherPlayer the other player.
   * @param model       the current game model.
   * @return the best move for the other player.
   */
  private Move findOtherPlayerMove(HexagonPlayer otherPlayer, ReversiReadOnlyModel model) {
    ReversiBoard copyModel = new ReversiBoard(model);
    // pass so that the other player can find his move without throwing an error:
    copyModel.pass();
    return opponentStrat.chooseMove(copyModel, otherPlayer);
  }

}
