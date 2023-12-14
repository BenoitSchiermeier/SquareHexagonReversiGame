package cs3500.reversi.view;

import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiReadOnlyModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Represents the graphical layout of the game board for the Reversi game.
 * This class extends JFrame and implements the IReversiView interface to provide
 * a graphical representation of the Reversi game state.
 *
 * <p>The class manages the visual components of the game board, including hexagon buttons
 * for each cell, a panel for the buttons, and a label for the score. It uses information
 * from the provided game model to set up and update the board's appearance.</p>
 */
public class GameBoardLayout extends JFrame implements IReversiView, BoardObserver {
  // represents the pannel with the buttons on it, protected so that it
  // can be changed by ReversiSquareLayout
  protected HexagonPanel buttonPanel;
  // determines the maximum number of rows based on the model given
  protected final int maxColRow;
  protected ReversiReadOnlyModel model;
  // keeps track of how many times the gameOver error has been thrown
  private int gameOverError = 0;
  // represents a feature listener
  protected ViewFeatures featuresListener;
  protected boolean hints;
  protected HexagonPlayer player;

  /**
   * Constructs a GameBoardLayout instance with the given game model.
   * Initializes the frame with the appropriate board size, button panel, and score label.
   *
   * @param model The read-only model of the Reversi game, providing the necessary game data.
   */
  public GameBoardLayout(ReversiReadOnlyModel model) {
    this.hints = false;
    this.model = model;
    // adds an observer to the model
    model.addObserver(this);
    // find the board size using the model
    int boardSize = model.getBoardSize();
    // calculate the number of possible rows and columns
    this.maxColRow = boardSize * 2 - 1;
    // add the buttons to the frame
    buttonPanel = panelSetUp();
    // set up key listener:
    setUpKeyListener();
    // initialize the frame to be viewed
    initializeFrame();
  }


  /**
   * Sets the ViewFeatures listener and initializes the key listener for the view.
   * This method registers a component (typically the controller) to handle specific view events
   * and sets up the key listener to trigger these events.
   *
   * @param listener the ViewFeatures listener to handle view events.
   */
  @Override
  public void setFeaturesListener(ViewFeatures listener) {
    this.featuresListener = listener;
    setUpKeyListener();
  }

  /**
   * Sets up the key listener for the view. It detects key presses and notifies
   * the ViewFeatures listener, if set, to handle specific key events.
   * Currently, it listens for 'pass' and 'play' key presses.
   */
  protected void setUpKeyListener() {
    KeyListener kbd = new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (featuresListener != null) {
          if (e.getKeyCode() == KeyEvent.VK_P) {
            featuresListener.onPassKeyPressed();
          } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            featuresListener.onPlayKeyPressed();
          }
          else if (e.getKeyCode() == KeyEvent.VK_H) {
            hints = true;
            featuresListener.makeHints();
          }
          else if (e.getKeyCode() == KeyEvent.VK_J) {
            hints = false;
            featuresListener.makeHints();
          }
        }
      }
    };
    buttonPanel.addKeyListener(kbd);
  }

  @Override
  public void addHints(HexagonPlayer player) {
    this.player = player;
  }

  /**
   * Responds to changes in the game board. Refreshes the view to reflect
   * the current game state and displays the game's end message if the game
   * has concluded.
   */
  @Override
  public void onBoardChanged() {
    refresh();
    if (model.isGameOver() && this.gameOverError < 1) {
      setBackgroundColor(Color.RED);
      String winner;
      if (model.getScore(HexagonPlayer.BLACK) == model.getScore(HexagonPlayer.WHITE)) {
        winner = "Its a Tie!";
      }
      else if (model.getScore(HexagonPlayer.BLACK) > model.getScore(HexagonPlayer.WHITE)) {
        winner = HexagonPlayer.BLACK + " is the winner!";
      }
      else {
        winner = HexagonPlayer.WHITE + " is the winner!";
      }
      this.gameOverError++;
      showError("The game has been ended \n " + winner);
    }
  }

  @Override
  public void startObserverGame() {
    // do nothing for the view
  }

  /**
   * Displays an error message in a dialog box.
   *
   * @param e The error message string to be displayed.
   */
  public void showError(String e) {
    JOptionPane.showMessageDialog(this, e, "Notification", JOptionPane.ERROR_MESSAGE);
    if (e.contains("The game has been ended")) {
      this.dispose();
    }
  }

  /**
   * Sets the background color of the panel containing the hexagon buttons.
   *
   * @param color The color to set as the background.
   */
  @Override
  public void setBackgroundColor(Color color) {
    this.buttonPanel.setBackground(color);
    if (color == Color.YELLOW && hints) {
      this.buttonPanel.setBackground(Color.magenta);
    }
  }

  @Override
  public void setFocusable() {
    buttonPanel.requestFocusInWindow();
  }

  /**
   * Retrieves the currently selected hexagon button on the game board.
   *
   * @return The selected HexagonButton, or null if no button is selected.
   */
  @Override
  public HexagonButton getSelectedButton() {
    return buttonPanel.getSelectedButton();
  }

  /**
   * Refreshes the view to reflect any changes in the game state.
   */
  @Override
  public void refresh() {
    this.updateScore();
    this.remove(buttonPanel);
    newPanel();
    // Re-add the key listener to the new button panel
    if (featuresListener != null) {
      setUpKeyListener();
    }
    this.add(buttonPanel, BorderLayout.CENTER);
    revalidate();
    repaint();
  }

  /**
   * Makes the game view visible to the user. Typically called at the start
   * of the game to display the game window or interface.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  /**
   * Initializes the JFrame with appropriate settings for the game board.
   * Sets the default close operation, preferred size, and adds the score panel.
   */
  private void initializeFrame() {
    updateScore();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Dimension d = buttonPanel.findDimention();
    setPreferredSize(d);
    buttonPanel.setPreferredSize(d);
    pack();
  }

  /**
   * Updates the title of the JFrame to reflect the current scores of the
   * players and whose turn it is.
   */
  protected void updateScore() {
    // find black's score
    String scoreBlack = Integer.toString(model.getScore(HexagonPlayer.BLACK));
    // find white's score
    String scoreWhite = Integer.toString(model.getScore(HexagonPlayer.WHITE));
    String hintsOnOff;
    if (hints) {
      hintsOnOff = "ON";
    }
    else {
      hintsOnOff = "OFF";
    }
    setTitle("Score: Black = " + scoreBlack + ",  White = " + scoreWhite + "  |  "
        + model.getCurrentPlayer().toString() + "'s turn | " + "Hints: " + hintsOnOff);
  }

  /**
   * Sets up the main panel of the frame that will hold the hexagon buttons.
   * Arranges the buttons in a hexagonal grid pattern.
   *
   * @return The prepared HexagonPanel with the buttons arranged.
   */
  protected HexagonPanel panelSetUp() {
    newPanel();
    this.setLayout(new BorderLayout());
    this.add(buttonPanel, BorderLayout.CENTER);
    return buttonPanel;
  }

  protected void newPanel() {
    if (hints) {
      buttonPanel = new HexHintPanel(maxColRow, model, player);
    }
    else {
      buttonPanel = new HexagonPanel(maxColRow, model, player);
    }
  }


}
