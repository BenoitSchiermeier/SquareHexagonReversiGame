package cs3500.reversi.model;

import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.view.BoardObserver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Represents the game board for the game Reversi.
 *
 * <p>This class encapsulates all the functionalities of the Reversi board,
 * including checking the validity of moves, making moves, changing the occupancy of hexagons, and
 * keeping track of the game state.</p>
 */
public class ReversiBoard implements ReversiMutableModel, ReversiReadOnlyModel {

  // represents the model's board size
  protected final int boardSize;
  // represents a 2d list of Hexagon
  protected final Hexagon[][] hexList;

  // max number of columns / rows possible in 2d array representation
  protected int maxColRow;

  // determine which player's turn it is
  private HexagonPlayer currentPlayer;

  // determines if the game has been ended
  private boolean gameEnded;

  // counts the number of consecutive passes made
  private int countPasses;

  // List to keep track of all observers
  private List<BoardObserver> observers = new ArrayList<>();


  /**
   * Constructs a Reversi board with the given size.
   *
   * @param boardSize The size of the board.
   * @throws IllegalArgumentException if the boardSize is less than 3.
   */
  public ReversiBoard(int boardSize) {
    // board size must be at least of size 3 to play the game
    if (boardSize < 3) {
      throw new IllegalArgumentException("boardSize is too small");
    }
    this.boardSize = boardSize;
    // compute the maxColRowBased on the boardSize
    this.maxColRow = boardSize * 2 - 1;
    // initialize the first player as HexagonPlayer.BLACK:
    this.currentPlayer = HexagonPlayer.BLACK;
    // create the 2d array of hexagons
    this.hexList = createBoard();
    // add the starting players of the game:
    this.addStartingPlayers();
    // set the game ended status to false because game is starting
    this.gameEnded = true;
    // sets the number of passes to 0
    this.countPasses = 0;
  }


  /**
   * Constructs a deep copy Reversi board with the given size.
   *
   * @param boardSize The size of the board.
   * @throws IllegalArgumentException if the boardSize is less than 3.
   */
  protected ReversiBoard(int boardSize, int maxColRow, HexagonPlayer currPlayer,
      Hexagon[][] hexList, boolean gameEnded, int countPasses) {
    // board size must be at least of size 3 to play the game
    if (boardSize < 3) {
      throw new IllegalArgumentException("boardSize is too small");
    }
    this.boardSize = boardSize;
    // compute the maxColRowBased on the boardSize
    this.maxColRow = maxColRow;
    // initialize the first player as HexagonPlayer.BLACK:
    this.currentPlayer = currPlayer;
    // create the 2d array of hexagons
    this.hexList = hexList;
    // set the game ended status to false because game is starting
    this.gameEnded = false;
    // sets the number of passes to 0
    this.countPasses = countPasses;
  }

  /**
   * Constructs a new ReversiBoard as a deep copy of the specified board. This constructor
   * is used to duplicate the state of the game, including the board size, the arrangement
   * of hexes on the board (copied to prevent mutual modifications), the current player,
   * the game end status, and the count of consecutive passes.
   *
   * @param board The ReversiBoard to be copied. Must not be null.
   * @throws IllegalArgumentException if the provided board is null, to prevent creating a copy
   *                                  of a non-existent board.
   */
  public ReversiBoard(ReversiReadOnlyModel board) {
    if (board == null) {
      throw new IllegalArgumentException("not a valid board to be copied");
    }
    this.boardSize = board.getBoardSize();
    // creates a copy of the hex list so that mutating copy of board does not mutate original
    this.hexList = board.getHexList();
    this.maxColRow = board.getBoardSize() * 2 - 1;
    this.currentPlayer = board.getCurrentPlayer();
    this.gameEnded = board.isGameOver();
    this.countPasses = 0;
  }


  /**
   * Starts the game and the observers.
   */
  @Override
  public void startGame() {
    this.gameEnded = false;
    for (BoardObserver observer : observers) {
      observer.startObserverGame();
    }
  }

  /**
   * Adds an observer to the list of observers.
   * @param observer The observer to be added.
   */
  public void addObserver(BoardObserver observer) {
    observers.add(observer);
  }

  /**
   * Creates and returns a read-only copy of the game board.
   *
   * @return a read-only copy of the board
   */
  @Override
  public ReversiReadOnlyModel readOnlyCopy() {
    return this;
  }


  /**
   * Creates and returns a mutable copy of the game board.
   *
   * @return a mutable copy of the game board
   */
  @Override
  public ReversiMutableModel mutableCopy() {
    return new ReversiBoard(getBoardSize(), getBoardSize() * 2 - 1,
        this.getCurrentPlayer(), this.getHexList(),
        this.gameEnded, 0);
  }

  @Override
  public int getArrayWidth() {
    return this.maxColRow;
  }

  /**
   * Notifies all registered observers of a change in the board state.
   */
  protected void notifyObserver() {
    for (BoardObserver observer : observers) {
      observer.onBoardChanged();
    }
  }

  /**
   * Gets the boardSize.
   *
   * @return the board size.
   */
  @Override
  public int getBoardSize() {
    return this.boardSize;
  }

  /**
   * Creates the board with Hexagon objects, initializing their positions.
   *
   * @return a 2D array of Hexagon objects representing the board
   */
  protected Hexagon[][] createBoard() {
    Hexagon[][] hexList = new Hexagon[maxColRow][maxColRow];
    // determines the max number of rows / columns in 2d array
    for (int r = 0; r < maxColRow; r++) {
      for (int q = 0; q < maxColRow; q++) {
        if (validHexPosition(r, q)) {
          hexList[r][q] = new Hexagon();
        }
        // if the hex is not at a valid position then it is null in the 2d array:
        else {
          hexList[r][q] = null;
        }
      }
    }

    return hexList;
  }

  /**
   * Places the initial players on the board.
   */
  protected void addStartingPlayers() {
    // index of the middle of the board:
    int middle = ((maxColRow + 1) / 2) - 1;
    this.changeHexOccupancy(middle, middle - 1, HexagonPlayer.BLACK); // up left
    this.changeHexOccupancy(middle + 1, middle - 1, HexagonPlayer.WHITE); // up right
    this.changeHexOccupancy(middle - 1, middle, HexagonPlayer.WHITE); // left
    this.changeHexOccupancy(middle + 1, middle, HexagonPlayer.BLACK); // right
    this.changeHexOccupancy(middle - 1, middle + 1, HexagonPlayer.BLACK); // down left
    this.changeHexOccupancy(middle, middle + 1, HexagonPlayer.WHITE); // down right
  }

  /**
   * Checks if a given position is a valid position on the hexagon grid.
   *
   * @param q the q coordinate of the position
   * @param r the r coordinate of the position
   * @return true if the position is valid, false otherwise
   */
  protected boolean validHexPosition(int q, int r) {
    if (q > maxColRow - 1 || q < 0 || r > maxColRow - 1 || r < 0) {
      return false;
    }
    int numberOfNulls = boardSize - 1;
    // determines if the position is not valid on the hexagon grid:
    boolean isNull = (q + r) < numberOfNulls;
    // determine if the position is invalid (top left of 2d array):
    if ((r < numberOfNulls) && (q < numberOfNulls) && isNull) {
      return false;
    }
    // make sure that r and q are not less than 0
    if (r < 0 || q < 0) {
      return false;
    }
    // determine if the position is invalid (bottom right of 2d array):
    return r <= numberOfNulls || q <= numberOfNulls || ((r + q) <= numberOfNulls * 3);
  }

  /**
   * Changes the occupancy of a hexagon at a given position.
   *
   * @param q         the q coordinate of the position
   * @param r         the r coordinate of the position
   * @param occupancy the HexagonPlayer to be set at the position
   * @throws IllegalArgumentException if the given position is invalid
   */
  protected void changeHexOccupancy(int q, int r, HexagonPlayer occupancy)
      throws IllegalArgumentException {
    if (!validHexPosition(q, r)) {
      throw new IllegalArgumentException("cannot change occupancy of invalid hex position");
    }
    this.hexList[r][q].changeHexOccupancy(occupancy);
  }


  /**
   * Retrieves a deep copy of the current state of the game board represented as a 2D array.
   *
   * @return a deep copy of the game board as a 2d array
   */
  @Override
  public Hexagon[][] getHexList() {
    Hexagon[][] copy = new Hexagon[maxColRow][maxColRow];
    for (int i = 0; i < maxColRow; i++) {
      for (int j = 0; j < maxColRow; j++) {
        if (hexList[i][j] != null) {
          // Create a new hexagon with the same occupancy state
          copy[i][j] = new Hexagon();
          copy[i][j].changeHexOccupancy(hexList[i][j].getOccupancy());
        } else {
          copy[i][j] = null;
        }
      }
    }
    return copy;
  }


  /**
   * Returns a list of search directions for the hexagonal grid.
   * The directions are: Up-left, Up-right, Right, Down-right, Down-left, and Left.
   *
   * @return List of search directions in (q, r) format.
   */
  protected List<Pair<Integer, Integer>> hexGridSearchPattern() {
    return Arrays.asList(
        new Pair<>(0, -1),    // Up-left
        new Pair<>(1, -1),    // Up-right
        new Pair<>(1, 0),     // Right
        new Pair<>(0, 1),     // Down-right
        new Pair<>(-1, 1),    // Down-left
        new Pair<>(-1, 0)     // Left
    );
  }


  /**
   * Returns the currentplayer.
   *
   * @return The HexagonPlayer representing the current player in the game.
   */
  @Override
  public HexagonPlayer getCurrentPlayer() {
    return this.currentPlayer;
  }



  /**
   * Validates  move, checks directions, and flips hexagon occupancies if a valid move is found.
   * Updates the game state as well as everything associated to it.
   *
   * @param q      The column index of the hexagon where the move is to be made.
   * @param r      The row index of the hexagon where the move is to be made.
   * @param player The HexagonPlayer making the move.
   * @throws IllegalArgumentException If the provided arguments are invalid.
   * @throws IllegalStateException    If the move is not allowable by game rules.
   */
  @Override
  public void play(int q, int r, HexagonPlayer player)
      throws IllegalArgumentException, IllegalStateException {
    // VALIDATE THE PLAY:
    validatePlay(player);

    // create directions in which to search as a list of Pair:
    List<Pair<Integer, Integer>> directions = hexGridSearchPattern();

    // boolean that represents if a valid move was found
    boolean validMoveFound = false;

    // check direction for every pair of directions and flip other occupancy if valid move is found:
    validMoveFound = validateAndFlip(q, r, player, directions, validMoveFound, true);

    // changes the occupancy of the cell where player is making move
    if (validMoveFound) {
      this.changeHexOccupancy(q, r, player);
    } else {
      throw new IllegalStateException("not allowable by game rules");
    }

    // change the currentPlayer
    switchPlayer();
    this.countPasses = 0;
    notifyObserver();
  }


  /**
   * Validates if the specified player can make a move.
   *
   * <p>Checks:
   * - The game hasn't ended.
   * - The player isn't null or NONE.
   * - It's the player's turn. </p>
   *
   * @param player The player attempting the move.
   * @throws IllegalStateException If the game is over or not the player's turn.
   * @throws IllegalArgumentException If player is null or NONE.
   */
  private void validatePlay(HexagonPlayer player) {
    // STEP 1: MAKE SURE THE GAME HAS NOT BEEN ENDED:
    if (this.gameEnded) {
      throw new IllegalStateException("The game has been ended");
    }
    // check if the player is null or NONE and throw exception
    if (player == null || player == HexagonPlayer.NONE) {
      throw new IllegalArgumentException("invalid player in makeMove");
    }

    // make sure it is the player's turn:
    if (player != currentPlayer) {
      throw new IllegalStateException("It's not " + player + "'s turn.");
    }
  }




  /**
   * Allows a pass to be made, as well as switching the player automatically.
   * It also handles the observers.
   */
  @Override
  public void pass() throws IllegalStateException {
    if (gameEnded) {
      throw new IllegalStateException("The game has been ended");
    }
    // switch the player whose turn it is to play:
    switchPlayer();
    // add one to the number of passes made
    this.countPasses += 1;
    // if there are 2 consecutive passes, the game has been ended:
    if (countPasses == 2) {
      this.gameEnded = true;
    }
    notifyObserver();
  }

  /**
   * Switches the current player between BLACK and WHITE.
   */
  private void switchPlayer() {
    currentPlayer =
        (currentPlayer == HexagonPlayer.BLACK) ? HexagonPlayer.WHITE : HexagonPlayer.BLACK;
  }

  /**
   * Validates a potential move and flips the occupancy of sandwiched hexagons.
   *
   * <p>This method checks if a move made by a player at a given position is valid in the
   * provided directions. If the move is valid and the flipCards parameter is set to true,
   * the method will flip the occupancy of sandwiched hexagons to the player's color.
   * Otherwise, it will only validate the move without flipping the hexagons.</p>
   *
   * @param q The q-coordinate of the move.
   * @param r The r-coordinate of the move.
   * @param player The player making the move.
   * @param directions A list of directions in which to validate the move.
   * @param validMoveFound Initial state indicating if a valid move has been found.
   * @param flipCards Boolean flag to determine whether to flip sandwiched hexagons.
   * @return True if the move is valid in any of the provided directions, false otherwise.
   */
  protected boolean validateAndFlip(int q, int r, HexagonPlayer player,
      List<Pair<Integer, Integer>> directions, boolean validMoveFound, boolean flipCards) {
    if (player == null) {
      return false;
    }
    // check that the coordinates are valid
    if (!validHexPosition(q, r)) {
      return false;
    }
    // check if the tile being moved to is null :
    if (hexList[r][q] == null) {
      return false;
    }
    // move can only be made if position to be moved onto is HexagonPlayer.NONE
    if (hexList[r][q].getOccupancy() != HexagonPlayer.NONE) {
      return false;
    }

    for (Pair<Integer, Integer> direction : directions) {
      // creates a Move
      List<Pair<Integer, Integer>> move =
          checkDirection(q, r, player, direction.getKey(), direction.getValue());
      // checkDirection sets move == null if no move is found
      if (move != null) {
        validMoveFound = true;
        // change the occupancy of the other sandwiched Hexagons to the occupancy of the player
        for (Pair<Integer, Integer> pair : move) {
          if (flipCards) {
            hexList[pair.getValue()][pair.getKey()].changeHexOccupancy(player);
          }
        }
      }
    }
    return validMoveFound;
  }

  /**
   * Checks if a move made by a player in a specific direction is valid and returns the
   * list of positions that would be flipped if the move is executed.
   *
   * <p>This method determines the validity of a move by verifying if there's a continuous
   * line of opponent's discs between the move's starting position and another disc of the
   * player in the specified direction. If a valid line is found, it returns a list of positions
   * (hexagons) that would be flipped if the move is made. If no valid line is found,
   * it returns null.</p>
   *
   * @param q The q-coordinate of the move's starting position.
   * @param r The r-coordinate of the move's starting position.
   * @param player The player making the move.
   * @param dq The change in q-coordinate representing the direction.
   * @param dr The change in r-coordinate representing the direction.
   * @return A list of positions (hexagons) that would be flipped if the move is valid;
   *         null if the move is invalid in the specified direction.
   */
  protected List<Pair<Integer, Integer>>
  checkDirection(int q, int r, HexagonPlayer player, int dq, int dr) {
    // set the q coordinate to start with
    int currentQ = q + dq;
    // set the r coordinate to start with
    int currentR = r + dr;
    // create new Move
    List<Pair<Integer, Integer>> list = new ArrayList<>();
    // check if coordinates are valid to proceed:
    while (validHexPosition(currentQ, currentR) && currentQ >= 0 && currentQ < hexList[0].length
        && currentR >= 0 && currentR < hexList.length) {

      // set the current player's occupancy:
      HexagonPlayer currentOccupancy = hexList[currentR][currentQ].getOccupancy();

      // safety check : return null if the player's occupancy is null
      if (currentOccupancy == HexagonPlayer.NONE) {
        return null;
      }

      // add the pieces to flip to the Move object
      if (currentOccupancy == player) {
        if (!list.isEmpty()) {
          return list;
        } else {
          return null;
        }
      }

      list.add(new Pair<>(currentQ, currentR));

      // increment the coordinates to check
      currentQ += dq;
      currentR += dr;
    }

    return null;
  }

  /**
   * Checks if the game is over.
   *
   * @return true if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    return this.gameEnded || (!canMove(HexagonPlayer.BLACK) && !canMove(HexagonPlayer.WHITE));
  }

  /**
   * Checks if a move is valid at the specified coordinates for the given player.
   * Validates the move and flips the hexagons if the move is valid.
   *
   * @param q      the column index of the move
   * @param r      the row index of the move
   * @param player the player attempting the move
   * @return true if the move valid, not is false
   * @throws IllegalArgumentException if the  coordinates are not valid
   */
  @Override
  public boolean canMove(int q, int r, HexagonPlayer player) {
    if (!validHexPosition(q, r)) {
      throw new IllegalArgumentException("not a valid hexagon position");
    }
    return validateAndFlip(q, r, player,
        hexGridSearchPattern(), false, false);
  }

  /**
   * Checks if the specified player can make a valid move on the current game board.
   *
   * @param player the player to check for valid moves
   * @return true if the player can make a move, false otherwise
   */
  @Override
  public boolean canMove(HexagonPlayer player) {
    for (int q = 0; q < maxColRow; q++) {
      for (int r = 0; r < maxColRow; r++) {
        if (hexList[q][r] != null) {
          // validate the cards but do not flip them
          boolean valid = canMove(q, r, player);
          if (valid) {
            // game over is false
            return true;
          }
        }
      }
    }
    return false;
  }


  /**
   * Retrieves the score of the specified player based on the current state of the game board.
   *
   * @param player the player whose score is requested
   * @return the score of the specified player
   */
  @Override
  public int getScore(HexagonPlayer player) {
    int score = 0;
    for (int q = 0; q < maxColRow; q++) {
      for (int r = 0; r < maxColRow; r++) {
        if ((hexList[q][r] != null) && hexList[q][r].getOccupancy() == player) {
          score += 1;
        }
      }
    }
    return score;
  }


  /**
   * Retrieves the occupancy state of the hexagon at the specified coordinates on the game board.
   *
   * @param q the column index
   * @param r the row index
   * @return the occupancy state of the hexagon at the specified coordinates
   * @throws IllegalArgumentException if the specified coordinates are not a valid hexagon position
   */
  @Override
  public HexagonPlayer getOccupancy(int q, int r) {
    if (!validHexPosition(q, r)) {
      throw new IllegalArgumentException("invalid position given to getOccupancy");
    }
    return hexList[r][q].getOccupancy();
  }

}
