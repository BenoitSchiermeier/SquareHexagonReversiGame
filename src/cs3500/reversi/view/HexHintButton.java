package cs3500.reversi.view;

import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiMutableModel;
import cs3500.reversi.model.ReversiReadOnlyModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class HexHintButton extends HexagonButton {
  private final ReversiReadOnlyModel model;
  private final HexagonPlayer playerTurn;

  public HexHintButton(HexagonButton button, ReversiReadOnlyModel model, HexagonPlayer playerTurn) {
    super(button.getQ(), button.getR(), button.getPlayer());
    this.playerTurn = playerTurn;
    this.model = model;
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g.create();
    int centerX = getWidth() / 2;
    int centerY = getHeight() / 2;
    int radius = Math.min(getWidth(), getHeight()) / 2;
    String buttonScore = getButtonScore(this.getQ(), this.getR());


    Polygon hexagon = calculateHexagon(centerX, centerY, radius);

    // Determine the color of the hexagon based on selection and player status
    if (selected) {
      // If the hexagon is selected, use the yellow color
      g2d.setColor(Color.magenta);
    } else {
      // If not selected, use the default not selected color
      g2d.setColor(notSelectedColor);
    }

    g2d.fillPolygon(hexagon);

    // Draw the hexagon outline
    g2d.setColor(Color.BLACK);
    g2d.drawPolygon(hexagon);

    // Calculate the radius of the inner circle
    int circleRadius = radius / 2;

    // Draw a circle in the center of the hexagon based on the player
    if (player == HexagonPlayer.BLACK || player == HexagonPlayer.WHITE) {
      g2d.setColor(player == HexagonPlayer.BLACK ? Color.BLACK : Color.WHITE);
      g2d.fillOval(centerX - circleRadius, centerY - circleRadius,
          circleRadius * 2, circleRadius * 2);
    }

    if (selected) {
      // set the text on top of the button:
      Font font = new Font("Arial", Font.BOLD, 20);
      g2d.setFont(font);
      FontMetrics metrics = g2d.getFontMetrics(font);
      String text = buttonScore;
      int textWidth = metrics.stringWidth(text);
      int textHeight = metrics.getHeight();
      int textX = centerX - textWidth / 2;
      int textY = centerY + textHeight / 4;
      if (selected) {
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, textX, textY);
      }
    }
    g2d.dispose();
  }


  private String getButtonScore(int q, int r) {
    if (this.playerTurn != null) {
      try {
        ReversiMutableModel copyModel = model.mutableCopy();
        copyModel.play(q, r, playerTurn);
        int newScore = copyModel.getScore(playerTurn);
        return Integer.toString(newScore - model.getScore(playerTurn) - 1);
      }
      catch (Exception e) {
        return "0";
      }
    }
    return "0";
  }

}
