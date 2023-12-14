package cs3500.reversi.view;

import cs3500.reversi.model.Hexagon.HexagonPlayer;
import java.awt.Polygon;

public class SquareButton extends HexagonButton {

  public SquareButton(int q, int r, HexagonPlayer player) {
    super(q, r, player);
  }

  @Override
  public Polygon calculateHexagon(int centerX, int centerY, int radius) {
    Polygon square = new Polygon();
    int halfSide = radius;
    // Top-left corner
    square.addPoint(centerX - halfSide, centerY - halfSide);
    // Top-right corner
    square.addPoint(centerX + halfSide, centerY - halfSide);
    // Bottom-right corner
    square.addPoint(centerX + halfSide, centerY + halfSide);
    // Bottom-left corner
    square.addPoint(centerX - halfSide, centerY + halfSide);
    return square;
  }

}
