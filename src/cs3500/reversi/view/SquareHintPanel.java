package cs3500.reversi.view;

import cs3500.reversi.model.Hexagon;
import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiReadOnlyModel;

public class SquareHintPanel extends ReversiSquarePanel {

  public SquareHintPanel(int maxColRow, ReversiReadOnlyModel model,
      HexagonPlayer player) {
    super(maxColRow, model, player);
  }

  @Override
  protected HexagonButton[][] addHexButtonsToArray(Hexagon[][] hexList) {
    int boardSize = model.getBoardSize();
    HexagonButton[][] hexButtonList = new HexagonButton[boardSize][boardSize];
    // add a button for every hexagon in the game
    for (int r = 0; r < boardSize; r++) {
      for (int q = 0; q < boardSize; q++) {
        if (hexList[r][q] == null) {
          hexButtonList[r][q] = null;
        } else {
          HexagonPlayer hexPlayer = hexList[r][q].getOccupancy();
          HexagonButton square = new SquareButton(q, r, hexPlayer);
          HexagonButton hexButton = new SquareButtonHint(square, model, player);

          hexButtonList[r][q] = hexButton;
        }
      }
    }
    return hexButtonList;
  }


}
