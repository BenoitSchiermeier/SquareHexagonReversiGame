package cs3500.reversi.view;


import cs3500.reversi.model.Hexagon;
import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiReadOnlyModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 * Represents a custom JPanel that arranges HexagonButtons in a hexagonal grid. This panel is used
 * to display the game board for Reversi.
 */
public class HexagonPanel extends JPanel {

  // represents all of the HexagonButton in the panel
  protected HexagonButton[][] buttons;
  // determines the maximum number of rows based on the model given
  protected int maxColRow;
  // keeps track of the selected button in the panel
  private HexagonButton selectedButton = null;
  protected HexagonPlayer player;
  protected ReversiReadOnlyModel model;

  /**
   * Constructs a HexagonPanel with a given array of HexagonButtons and the maximum number of rows.
   *
   * @param maxColRow The maximum number of rows in the game board.
   */
  public HexagonPanel(int maxColRow, ReversiReadOnlyModel model, HexagonPlayer player) {
    this.player = player;
    this.model = model;
    this.maxColRow = maxColRow;

    this.buttons = addHexButtonsToArray(model.getHexList());
    setBackground(Color.DARK_GRAY);

    // allows key events
    setFocusable(true);
    requestFocusInWindow();
    // selects and deselects buttons by clicking on them:
    addListeners(buttons);
    // makes it so that clicking outside the boundary of the board deselects the selected button
    addListenerDeselect();
  }

  /**
   * Adds a MouseListener to the panel to handle mouse click events for deselecting a selected
   * button.
   */
  private void addListenerDeselect() {
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (selectedButton != null && !isClickOnButton(e.getX(), e.getY())) {
          selectedButton.deselect();
          selectedButton = null;
        }
      }
    });
  }

  /**
   * Determines if a mouse click occurred on any HexagonButton.
   *
   * @param x The x-coordinate of the mouse click.
   * @param y The y-coordinate of the mouse click.
   * @return True if the click is on a HexagonButton, false otherwise.
   */
  private boolean isClickOnButton(int x, int y) {
    for (HexagonButton[] button : buttons) {
      for (int q = 0; q < button.length; q++) {
        if (button[q] != null && button[q].wasClickedOn(x, y)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Adds ActionListeners to the HexagonButtons for handling button click events.
   *
   * @param buttons The array of HexagonButtons to which listeners are added.
   */
  private void addListeners(HexagonButton[][] buttons) {
    ActionListener buttonListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        HexagonButton clickedButton = (HexagonButton) e.getSource();
        if (selectedButton == clickedButton) {
          // The clicked button is already selected, so deselect it
          clickedButton.deselect();
          selectedButton = null; // No button is now selected
        } else {
          // A different button is clicked or no button was previously selected
          if (selectedButton != null) {
            // Deselect the previously selected button
            selectedButton.deselect();
          }
          int q = clickedButton.getQ();
          int r = clickedButton.getR();
          // Select the clicked button
          clickedButton.select();
          selectedButton = clickedButton; // Update the currently selected button
        }
      }
    };
    for (int r = 0; r < buttons.length; r++) {
      for (int q = 0; q < buttons[r].length; q++) {
        if (buttons[r][q] != null) {
          buttons[r][q].addActionListener(buttonListener);
        }
      }
    }
  }

  /**
   * Finds the optimal dimensions for the panel based on the arrangement of HexagonButtons.
   *
   * @return The calculated Dimension for the panel.
   */
  protected Dimension findDimention() {
    double boardHeight = maxColRow * 70;
    double verticalDistance = boardHeight / (0.875 * maxColRow);
    double hexagonRadius = verticalDistance / 2;
    double horizontalDistance = hexagonRadius * Math.sqrt(3);

    double furthestRight = 0;
    double furthestBottom = 0;

    for (int r = 0; r < maxColRow; r++) {
      int offset = 0;
      int numButtonPerList = findNumberOfButtonPerList(r);

      for (int q = 0; q < maxColRow; q++) {
        HexagonButton hexButton = buttons[r][q];
        double x = (maxColRow - numButtonPerList) * horizontalDistance / 2 + offset;
        double y = r * (.75 * verticalDistance);

        furthestRight = Math.max(furthestRight, x + horizontalDistance);
        furthestBottom = Math.max(furthestBottom, y + verticalDistance + 50);

        if (hexButton != null) {
          numButtonPerList -= 1;
          offset += (int) (horizontalDistance / 2);
        }
      }
    }
    return new Dimension((int) furthestRight, (int) furthestBottom);
  }

  /**
   * Custom paint component for arranging HexagonButtons in a hexagonal grid layout.
   *
   * @param g The Graphics object used to paint the component.
   */

  protected void paintComponent(Graphics g) {
    double boardHeight;
    int marginSafety = 10;
    if (getHeight() < getWidth() * .875 + marginSafety) {
      boardHeight = getHeight() * 1.13 - marginSafety;
    } else {
      boardHeight = getWidth();
    }
    // finds vertical distance between hexagons (.875 use for overlap of hexagons)
    double verticalDistance = boardHeight / (0.875 * maxColRow);
    // finds the "size" of hexagon also known as radius
    double hexagonRadius = verticalDistance / 2;
    // finds the horizontal distance between hexagons
    double horizontalDistance = hexagonRadius * Math.sqrt(3);
    // height equals vertical distance but height is cast to int
    int height = (int) verticalDistance;

    double furthestRight = 0;
    double furthestBottom = 0;

    // search through the 2d array: buttons
    for (int r = 0; r < maxColRow; r++) {
      // keeps track of the offset needed (resets every row)
      int offset = 0;
      // finds the number of hexagons in the current row
      int numButtonPerList = findNumberOfButtonPerList(r);

      for (int q = 0; q < maxColRow; q++) {
        HexagonButton hexButton = buttons[r][q];
        // starting x position
        double x = (maxColRow - numButtonPerList) * horizontalDistance / 2 + offset;
        // starting y position (.75 because hexagons overlap):
        double y = r * (.75 * verticalDistance);

        furthestRight = Math.max(furthestRight, x + horizontalDistance);
        furthestBottom = Math.max(furthestBottom, y + verticalDistance);

        // set the bounds of the hexagonButton
        // (height used twice because min is taken in setBounds and larger dimension is needed):
        if (hexButton != null) {
          hexButton.setBounds((int) x, (int) y, height, height);
          // add the button to the panel
          this.add(hexButton);
          // decrease the counter of buttons in the row by one
          numButtonPerList -= 1;
          // add half the width/horizontal distance of a hexagon to the offset
          offset += (int) (horizontalDistance / 2);
        }
      }
    }
    repaint();
    revalidate();
    setPreferredSize(new Dimension((int) furthestRight, (int) furthestBottom));
  }

  /**
   * Finds the number of HexagonButtons in a specific row of the panel.
   *
   * @param r The row index for which the number of buttons is to be found.
   * @return The number of HexagonButtons in the specified row.
   */
  protected int findNumberOfButtonPerList(int r) {
    int numButtonPerList = 0;

    for (int q = 0; q < maxColRow; q++) {
      if (model.getHexList()[r][q] != null) {
        numButtonPerList += 1;
      }
    }
    return numButtonPerList;
  }


  protected HexagonButton getSelectedButton() {
    return this.selectedButton;
  }

  /**
   * Creates and adds HexagonButtons to a two-dimensional array based on the Hexagon objects
   * in the provided hexList.
   *
   * @param hexList A two-dimensional array of Hexagon objects representing the game board.
   * @return A two-dimensional array of HexagonButton objects representing the buttons on the board.
   */
  protected HexagonButton[][] addHexButtonsToArray(Hexagon[][] hexList) {
    HexagonButton[][] hexButtonList = new HexagonButton[maxColRow][maxColRow];
    // add a button for every hexagon in the game
    for (int r = 0; r < maxColRow; r++) {
      for (int q = 0; q < maxColRow; q++) {
        if (hexList[r][q] == null) {
          hexButtonList[r][q] = null;
        } else {
          HexagonPlayer hexPlayer = hexList[r][q].getOccupancy();
          HexagonButton hexButton = new HexagonButton(q, r, hexPlayer);
          hexButtonList[r][q] = hexButton;
        }
      }
    }
    return hexButtonList;
  }

}

