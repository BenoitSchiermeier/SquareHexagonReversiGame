package cs3500.reversi.model;

import java.util.List;

import cs3500.reversi.view.BoardObserver;


/**
 * Represents a mock implementation of the Reversi game board.
 * This implementation is intended for testing purposes and logs method calls.
 */
public class MockReversiBoard implements ReversiMutableModel, ReversiReadOnlyModel {

  /**
   * The log used to record method calls.
   */
  private List<String> log;

  /**
   * Constructs a MockReversiBoard with the specified log.
   *
   * @param log the list to store method call logs
   */
  public MockReversiBoard(List<String> log) {
    this.log = log;
  }

  /**
   * Makes a move on the game board at the specified coordinates for the given player.
   *
   * @param q      the column index of the move
   * @param r      the row index of the move
   * @param player the player making the move
   * @throws IllegalArgumentException if the move is invalid
   */
  @Override
  public void play(int q, int r, Hexagon.HexagonPlayer player) throws IllegalArgumentException {
    log.add("play was called!");
  }

  /**
   * Passes the turn to the next player.
   */
  @Override
  public void pass() {
    log.add("pass was called!");
  }

  /**
   * Starts a new game, initializing the game board.
   */
  @Override
  public void startGame() {
    log.add("game starts!");
  }

  /**
   * Retrieves the current state of the game board as a 2D array of Hexagons.
   *
   * @return the game board represented as a 2D array of Hexagons
   */
  @Override
  public Hexagon[][] getHexList() {
    log.add("getHexList works!");
    return new Hexagon[0][];
  }

  /**
   * Gets the score for the specified player.
   *
   * @param player the player whose score is requested
   * @return the score of the specified player
   */
  @Override
  public int getScore(Hexagon.HexagonPlayer player) {
    log.add("gets the Score");
    return 0;
  }

  /**
   * Checks if the game is over.
   *
   * @return true if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    return false;
  }

  /**
   * Retrieves the current player whose turn it is.
   *
   * @return the current player
   */
  @Override
  public Hexagon.HexagonPlayer getCurrentPlayer() {
    //log.add("the current Player is " + Hexagon.HexagonPlayer.getCurrentPlayer());
    return null;
  }

  /**
   * Gets the size of the game board.
   *
   * @return the size of the game board
   */
  @Override
  public int getBoardSize() {
    return 0;
  }

  /**
   * Retrieves the player occupying the specified coordinates on the game board.
   *
   * @param q the column index
   * @param r the row index
   * @return the player occupying the specified coordinates
   */
  @Override
  public Hexagon.HexagonPlayer getOccupancy(int q, int r) {
    log.add("logger getOccupancy called with " + q + ", " + r);
    return null;
  }

  /**
   * Checks if a move is valid at the specified coordinates for the given player.
   *
   * @param q      the column index of the move
   * @param r      the row index of the move
   * @param player the player attempting the move
   * @return true if the move is valid, false otherwise
   */
  @Override
  public boolean canMove(int q, int r, Hexagon.HexagonPlayer player) {
    log.add("logger canMove called with " + q + ", " + r + " with " + player);
    return false;
  }

  /**
   * Checks if the specified player can make a valid move.
   *
   * @param player the player to check for valid moves
   * @return true if the player can make a move, false otherwise
   */
  @Override
  public boolean canMove(Hexagon.HexagonPlayer player) {
    log.add("logger canMove called with " + player);
    return false;
  }

  /**
   * Adds a board observer to the list of observers.
   *
   * @param observer the observer to be added
   */
  @Override
  public void addObserver(BoardObserver observer) {
    log.add("observer was added!");
  }

  /**
   * Creates and returns a read-only copy of the current game board.
   *
   * @return a read-only copy of the game board
   */
  @Override
  public ReversiReadOnlyModel readOnlyCopy() {
    log.add("readOnly was added!");
    return this;
  }

  /**
   * Creates and returns a mutable copy of the current game board.
   *
   * @return a mutable copy of the game board
   */
  @Override
  public ReversiMutableModel mutableCopy() {
    log.add("mutableCopy was added!");
    return this;
  }

  @Override
  public int getArrayWidth() {
    return 0;
  }
}