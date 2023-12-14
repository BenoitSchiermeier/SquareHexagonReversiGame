package cs3500.reversi.view;

import cs3500.reversi.model.Hexagon;
import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiReadOnlyModel;

public class HexHintPanel extends HexagonPanel {
  public HexHintPanel(int maxColRow,
      ReversiReadOnlyModel model, HexagonPlayer player) {
    super(maxColRow, model, player);
  }

  @Override
  protected HexagonButton[][] addHexButtonsToArray(Hexagon[][] hexList) {
    HexagonButton[][] hexButtonList = new HexagonButton[maxColRow][maxColRow];
    // add a button for every hexagon in the game
    for (int r = 0; r < maxColRow; r++) {
      for (int q = 0; q < maxColRow; q++) {
        if (hexList[r][q] == null) {
          hexButtonList[r][q] = null;
        } else {
          HexagonPlayer hexPlayer = hexList[r][q].getOccupancy();
          HexagonButton button = new HexagonButton(q, r, hexPlayer);
          HexagonButton hexButton = new HexHintButton(button, model, player);
          hexButtonList[r][q] = hexButton;
        }
      }
    }
    return hexButtonList;
  }
}
