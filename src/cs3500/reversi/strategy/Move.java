package cs3500.reversi.strategy;

import cs3500.reversi.model.Hexagon.HexagonPlayer;

/**
 * Represents a move in the Reversi game.
 * A move consists of the coordinates where a player places their piece.
 */
public class Move {
  private int q;
  private int r;
  private HexagonPlayer player;
  private boolean pass;

  /**
   * Constructs a Move with specified coordinates and player.
   *
   * @param q the column coordinate of the move
   * @param r the row coordinate of the move
   * @param player the player making the move
   */
  public Move(int q, int r, HexagonPlayer player) {
    this.q = q;
    this.r = r;
    this.player = player;
    this.pass = false;
  }

  public Move(boolean pass, HexagonPlayer player) {
    this.pass = pass;
    this.player = player;
  }

  /**
   * Gets the column coordinate of this move.
   *
   * @return the column coordinate
   */
  public int getQ() {
    return this.q;
  }

  /**
   * Gets the row coordinate of this move.
   *
   * @return the row coordinate
   */
  public int getR() {
    return this.r;
  }

  /**
   * Gets the player who is making this move.
   *
   * @return the player associated with this move
   */
  public HexagonPlayer getPlayer() {
    return this.player;
  }

  public boolean getPass() {
    return this.pass;
  }
}
