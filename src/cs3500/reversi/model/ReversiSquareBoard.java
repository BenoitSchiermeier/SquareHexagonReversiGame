package cs3500.reversi.model;

import cs3500.reversi.model.Hexagon.HexagonPlayer;
import java.util.Arrays;
import java.util.List;

public class ReversiSquareBoard extends ReversiBoard implements ReversiMutableModel,
    ReversiReadOnlyModel {

  public ReversiSquareBoard(int size) {
    super(size);
    this.maxColRow = size;
    if (size % 2 != 0 || size < 4) {
      throw new IllegalArgumentException("size has to be divisibly by 2");
    }
  }

  public ReversiSquareBoard(int boardSize, int maxColRow, HexagonPlayer currPlayer,
      Hexagon[][] hexList, boolean gameEnded, int countPasses) {
    super(boardSize, maxColRow, currPlayer, hexList, gameEnded, countPasses);
    this.maxColRow = boardSize; // Additional subclass-specific initialization
  }


  @Override
  protected Hexagon[][] createBoard() {
    Hexagon[][] hexList = new Hexagon[this.getBoardSize()][getBoardSize()];
    // determines the max number of rows / columns in 2d array
    for (int r = 0; r < boardSize; r++) {
      for (int q = 0; q < boardSize; q++) {
        if (validHexPosition(r, q)) {
          hexList[r][q] = new Hexagon();
        }
      }
    }
    return hexList;
  }

  @Override
  protected void addStartingPlayers() {
    this.changeHexOccupancy((boardSize / 2 - 1), (boardSize / 2 - 1), HexagonPlayer.BLACK);
    this.changeHexOccupancy((boardSize / 2 - 1), (boardSize / 2), HexagonPlayer.WHITE);
    this.changeHexOccupancy((boardSize / 2), (boardSize / 2), HexagonPlayer.BLACK);
    this.changeHexOccupancy((boardSize / 2), (boardSize / 2 - 1), HexagonPlayer.WHITE);
  }

  @Override
  protected boolean validHexPosition(int q, int r) {
    return q <= boardSize - 1 && q >= 0 && r <= boardSize - 1 && r >= 0;
  }

  @Override
  protected List<Pair<Integer, Integer>> hexGridSearchPattern() {
    return Arrays.asList(
        new Pair<>(-1, -1),    // Up-left
        new Pair<>(1, -1),    // Up-right
        new Pair<>(1, 0),     // Right
        new Pair<>(-1, 1),     // Down-left
        new Pair<>(1, 1),    // Down-right
        new Pair<>(-1, 0),     // Left
        new Pair<>(0, -1),    // up
        new Pair<>(0, 1)     // down
    );
  }

  @Override
  public ReversiMutableModel mutableCopy() {
    return new ReversiSquareBoard(getBoardSize(), getBoardSize() * 2 - 1,
        this.getCurrentPlayer(), this.getHexList(),
        this.isGameOver(), 0);
  }

  @Override
  public int getArrayWidth() {
    return getBoardSize();
  }
}
