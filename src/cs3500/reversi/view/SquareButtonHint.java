package cs3500.reversi.view;

import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiReadOnlyModel;
import java.awt.Polygon;

public class SquareButtonHint extends HexHintButton {
  public SquareButtonHint(HexagonButton button, ReversiReadOnlyModel model,
      HexagonPlayer playerTurn) {
    super(button, model, playerTurn);
  }

  @Override
  public Polygon calculateHexagon(int centerX, int centerY, int radius) {
    Polygon square = new Polygon();
    // Top-left corner
    square.addPoint(centerX - radius, centerY - radius);
    // Top-right corner
    square.addPoint(centerX + radius, centerY - radius);
    // Bottom-right corner
    square.addPoint(centerX + radius, centerY + radius);
    // Bottom-left corner
    square.addPoint(centerX - radius, centerY + radius);
    return square;
  }

}
