package cs3500.reversi.view;

import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.Pair;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import javax.swing.JButton;

/**
 * Represents a custom button in the shape of a hexagon for the Reversi game. This class extends
 * JButton and is used to represent each cell on the Reversi game board.
 */
public class HexagonButton extends JButton {

  private final int q;
  private final int r;
  protected final HexagonPlayer player;
  protected boolean selected;
  protected final Color notSelectedColor;

  /**
   * Constructs a new HexagonButton with specified coordinates and player.
   *
   * @param q      The column index of this hexagon in the game board.
   * @param r      The row index of this hexagon in the game board.
   * @param player The player (BLACK, WHITE, or NONE) occupying this hexagon.
   */
  public HexagonButton(int q, int r, HexagonPlayer player) {
    this.q = q;
    this.r = r;
    this.player = player;
    selected = false;
    notSelectedColor = Color.LIGHT_GRAY;
  }


  /**
   * Returns the column index of this hexagon.
   *
   * @return The column index.
   */
  public int getQ() {
    return this.q;
  }

  /**
   * Returns the row index of this hexagon.
   *
   * @return The row index.
   */
  public int getR() {
    return this.r;
  }

  public HexagonPlayer getPlayer() {
    return this.player;
  }


  /**
   * Marks this hexagon as selected, changing its background color if it's unoccupied.
   */
  protected void select() {
    if (player == HexagonPlayer.NONE) {
      selected = true;
      setBackground(Color.YELLOW);
      System.out.println("Hexagon SELECTED at coordinates: (" + q + ", " + r + ")");
    }
  }

  /**
   * Deselects this hexagon, reverting its background color.
   */
  protected void deselect() {
    if (selected) {
      selected = false;
      setBackground(notSelectedColor);
      System.out.println("Hexagon at coordinates: (" + q + ", " + r + ") deselected");
    }
  }

  /**
   * Determines if a point is within this hexagon.
   *
   * @param x The x-coordinate of the point.
   * @param y The y-coordinate of the point.
   * @return True if the point is inside the hexagon, false otherwise.
   */
  public boolean wasClickedOn(int x, int y) {
    return this.contains(x, y);
  }

  /**
   * Calculates the vertices of a hexagon based on its center and radius.
   *
   * @param centerX The x-coordinate of the hexagon's center.
   * @param centerY The y-coordinate of the hexagon's center.
   * @param radius  The radius of the hexagon.
   * @return A Polygon representing the hexagon.
   */
  public Polygon calculateHexagon(int centerX, int centerY, int radius) {
    Polygon hexagon = new Polygon();
    for (int i = 0; i < 6; i++) {
      // 60 degrees for each point on the hexagon (360 / 6)
      // Subtract 30 to rotate the hexagon for pointy top
      double angle = Math.toRadians(60 * i - 30);
      int x = (int) (centerX + radius * Math.cos(angle));
      int y = (int) (centerY + radius * Math.sin(angle));
      hexagon.addPoint(x, y);
    }
    return hexagon;
  }

  /**
   * Paints this component with its hexagonal shape and color based on its state and player.
   *
   * @param g The Graphics object to protect.
   */
  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g.create();
    int centerX = getWidth() / 2;
    int centerY = getHeight() / 2;
    int radius = Math.min(getWidth(), getHeight()) / 2;

    Polygon hexagon = calculateHexagon(centerX, centerY, radius);

    // Determine the color of the hexagon based on selection and player status
    if (selected) {
      // If the hexagon is selected, use the yellow color
      g2d.setColor(Color.YELLOW);
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
    g2d.dispose();
  }

  @Override
  public boolean contains(int x, int y) {
    // Get the center and radius to calculate the current hexagon shape
    int centerX = getWidth() / 2;
    int centerY = getHeight() / 2;
    int radius = Math.min(getWidth(), getHeight()) / 2;

    // Use the calculateHexagon method to get the current hexagon shape
    Polygon hexagon = calculateHexagon(centerX, centerY, radius);

    // Check if the point is inside the hexagon
    return hexagon.contains(x, y);
  }

}
