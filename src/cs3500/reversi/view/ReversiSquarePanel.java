package cs3500.reversi.view;

import cs3500.reversi.model.Hexagon;
import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiReadOnlyModel;
import java.awt.Dimension;
import java.awt.Graphics;

public class ReversiSquarePanel extends HexagonPanel {

  public ReversiSquarePanel(int maxColRow, ReversiReadOnlyModel model, HexagonPlayer player) {
    super(maxColRow, model, player);
  }

  @Override
  protected HexagonButton[][] addHexButtonsToArray(Hexagon[][] hexList) {
    int boardSize = model.getBoardSize();
    HexagonButton[][] hexButtonList = new SquareButton[boardSize][boardSize];
    // add a button for every hexagon in the game
    for (int r = 0; r < boardSize; r++) {
      for (int q = 0; q < boardSize; q++) {
        if (hexList[r][q] == null) {
          hexButtonList[r][q] = null;
        } else {
          HexagonPlayer hexPlayer = hexList[r][q].getOccupancy();
          HexagonButton hexButton = new SquareButton(q, r, hexPlayer);
          hexButtonList[r][q] = hexButton;
        }
      }
    }
    return hexButtonList;
  }

  @Override
  protected void paintComponent(Graphics g) {
    int boardSize = model.getBoardSize();
    // Determine the size of each square
    int squareSize = Math.min(getWidth() / boardSize,
        getHeight() / boardSize);

    // Iterate through each row and column
    for (int r = 0; r < boardSize; r++) {
      for (int q = 0; q < boardSize; q++) {
        HexagonButton squareButton = buttons[r][q];
        if (squareButton != null) {
          // Calculate the x and y position of the square
          int x = q * squareSize;
          int y = r * squareSize;

          // Set the bounds of the square button
          squareButton.setBounds(x, y, squareSize, squareSize);

          // Add the button to the panel
          this.add(squareButton);
        }
      }
    }
    // Update the preferred size of the panel based on the squares
    setPreferredSize(new Dimension(boardSize * squareSize, boardSize * squareSize));
    // Revalidate and repaint the panel to apply changes
    revalidate();
    repaint();
  }




}
