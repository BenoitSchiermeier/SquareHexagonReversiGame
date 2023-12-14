package cs3500.reversi;

import cs3500.reversi.controller.ReversiController;
import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiBoard;
import cs3500.reversi.model.ReversiSquareBoard;
import cs3500.reversi.player.AIPlayer;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.Player;
import cs3500.reversi.strategy.AggressiveReversiStrategy;
import cs3500.reversi.strategy.AvoidCornersStrategy;
import cs3500.reversi.strategy.GoForCornersStrategy;
import cs3500.reversi.strategy.MinimaxStrategy;
import cs3500.reversi.strategy.ReversiStrategy;
import cs3500.reversi.strategy.TryTwo;
import cs3500.reversi.view.GameBoardLayout;
import cs3500.reversi.view.IReversiView;


import cs3500.reversi.view.ReversiSquareLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * Main class for the Reversi game, responsible for initializing the game and setting up players.
 */
public final class Reversi {

  // set of possible correct players user can input
  private static final Set<String> correctPlayer =
      Set.of("human", "strategy1", "strategy2", "strategy3",
          "strategy4", "strategy12", "strategy123");

  // represents the main board
  private static ReversiBoard board;

  // Dictionary mapping player types to their respective functions for creating players.
  private static final Map<String, Function<HexagonPlayer, Function<ReversiBoard, Player>>>
      playerDictionary = createPlayerMapForPlayers();


  /**
   * The main method for starting and running the Reversi game.
   *
   * @param args an array of command line arguments, specifying board size, player 1 type,
   *             and player 2 type in the format: [size] [player1] [player2]
   */

  public static void main(String[] args) {
//    ReversiBoard model = new ReversiSquareBoard(6);
//    IReversiView v1 = new ReversiSquareLayout(model);
//    IReversiView v2 = new ReversiSquareLayout(model);
//
//    Player p1 = new HumanPlayer(model, HexagonPlayer.BLACK);
////    Player p2 = new HumanPlayer(model, HexagonPlayer.WHITE);
//    Player p2 = new AIPlayer(model, HexagonPlayer.WHITE, new AggressiveReversiStrategy());
//
//    ReversiController c1 = new ReversiController(model, v1, p1);
//    ReversiController c2 = new ReversiController(model, v2, p2);
//
//    model.startGame();

    // placeholder for the boardSize
    int boardSize;
    String shape;

    // Check if  the command line arguments are right amount (needs 4)
    if (args.length >= 2) {
      try {

        shape = args[0].toLowerCase();
        //gets the board size amount
        boardSize = Integer.parseInt(args[1]);


        if (!isValidShape(shape)) {
          System.out.println("Invalid shape. Please put 'square' or 'hexagon'. ");
          return;
        }
        if(!isValidBoardSize(shape, boardSize)) {
          System.out.println("Invalid Board Size.");
          return;
        }

      } catch (NumberFormatException e) {
        //if the input wasn't a number
        System.out.println("Invalid Board Size.");
        return;
      }
    } else {
      System.out.println("Please input the same structure: [shape][ size] [player1] [player2]");
      return;
    }

    if(shape.equalsIgnoreCase("square")) {
      board = new ReversiSquareBoard(boardSize);
    } else if (shape.equalsIgnoreCase("hexagon")) {
      board = new ReversiBoard(boardSize);
    }

    //player1Type
    String player1Type = "human";
    if (args.length >= 3) {
      player1Type = args[2];
    }

    if (!isValidPlayerType(player1Type)) {
      System.out.println("Invalid player type for Player 1.");
      return;
    }

    //placeholder default case
    String player2Type = "strategy1";
    if (args.length >= 4) {
      player2Type = args[3];
    }

    if (!isValidPlayerType(player2Type)) {
      System.out.println("Invalid player type for Player 2.");
      return;
    }

    //sets it all up if two players were successfully inputted

    if (shape.equalsIgnoreCase("hexagon")) {
      setUpHexagonGame(board, player1Type, player2Type);
    } else if (shape.equalsIgnoreCase("square")) {
      setUpSquareGame(board, player1Type, player2Type);
    }

//    Player player2 = createPlayer(player2Type, HexagonPlayer.WHITE);
//    IReversiView view2 = new GameBoardLayout(board);
//    ReversiController controller2 = new ReversiController(board, view2, player2);
//
//
//    IReversiView view = new GameBoardLayout(board);
//    Player player1 = createPlayer(player1Type, HexagonPlayer.BLACK);
//    ReversiController controller = new ReversiController(board, view, player1);

    board.startGame();
  }

  private static void setUpHexagonGame(ReversiBoard board, String player1Type, String player2Type) {
    // Player 1 setup
    Player player1 = createPlayer(player1Type, HexagonPlayer.BLACK);
    IReversiView view1 = new GameBoardLayout(board);
    ReversiController controller1 = new ReversiController(board, view1, player1);

    // Player 2 setup
    Player player2 = createPlayer(player2Type, HexagonPlayer.WHITE);
    IReversiView view2 = new GameBoardLayout(board);
    ReversiController controller2 = new ReversiController(board, view2, player2);
  }

  private static void setUpSquareGame(ReversiBoard board, String player1Type, String player2Type) {

    // Player 1 setup
    Player player1 = createPlayer(player1Type, HexagonPlayer.BLACK);
    IReversiView view1 = new ReversiSquareLayout(board);
    ReversiController controller1 = new ReversiController(board, view1, player1);

    // Player 2 setup
    Player player2 = createPlayer(player2Type, HexagonPlayer.WHITE);
    IReversiView view2 = new ReversiSquareLayout(board);
    ReversiController controller2 = new ReversiController(board, view2, player2);
  }


  private static boolean isValidBoardSize(String shape, int boardSize) {
    if(shape.equalsIgnoreCase("square")) {
      return (boardSize > 4) && (boardSize % 2 == 0);
    } else if (shape.equalsIgnoreCase("hexagon")) {
      return boardSize > 3;
    }
    return false;
  }

  private static boolean isValidShape(String shape) {
    return shape.equals("square") || shape.equalsIgnoreCase("hexagon");
  }

  /**
   * Creates a mapping of player types to functions that create player instances.
   * @return A map of player type strings to corresponding player creation functions.
   */
  private static Map<String, Function<HexagonPlayer, Function<ReversiBoard, Player>>>
      createPlayerMapForPlayers() {
    Map<String, Function<HexagonPlayer, Function<ReversiBoard, Player>>> playerCommands =
        new HashMap<>();
    ReversiStrategy strat1 = new AggressiveReversiStrategy();
    ReversiStrategy strat2 = new AvoidCornersStrategy();
    ReversiStrategy strat3 = new GoForCornersStrategy();

    createPlayerLookup(playerCommands, strat1, strat2, strat3);
    return playerCommands;
  }

  /**
   * Initializes the playerCommands map with different player types and strategies.
   * This method populates the given map with various player creation
   * functions, each associated with a key. The keys are strings representing different
   * player types or strategies, and the values are functions that create players based
   * on the provided ReversiBoard and HexagonPlayer instances.
   *
   * @param playerCommands A map where the key is a string representing the player type or strategy,
   *                       and the value is a function that takes a HexagonPlayer and returns
   *                       another function.
   *                       This returned function takes a ReversiBoard and returns a
   *                       Player instance.
   * @param strat1         The first ReversiStrategy to be used for creating AI players.
   * @param strat2         The second ReversiStrategy to be used for creating AI players.
   * @param strat3         The third ReversiStrategy to be used for creating AI players.
   */
  private static void createPlayerLookup(
      Map<String, Function<HexagonPlayer, Function<ReversiBoard, Player>>> playerCommands,
      ReversiStrategy strat1, ReversiStrategy strat2, ReversiStrategy strat3) {
    playerCommands.put("human", hexagonPlayer -> board -> new HumanPlayer(board, hexagonPlayer));
    playerCommands.put("strategy1",
        hexagonPlayer -> board -> new AIPlayer(board, hexagonPlayer, strat1));
    playerCommands.put("strategy2",
        hexagonPlayer -> board -> new AIPlayer(board, hexagonPlayer, strat2));
    playerCommands.put("strategy3",
        hexagonPlayer -> board -> new AIPlayer(board, hexagonPlayer, strat3));
    playerCommands.put("strategy12",
        hexagonPlayer -> board -> new AIPlayer(board, hexagonPlayer, new TryTwo(strat2, strat1)));
    playerCommands.put("strategy123", hexagonPlayer -> board -> new AIPlayer(board, hexagonPlayer,
        new TryTwo(strat3, new TryTwo(strat2, strat1))));
    playerCommands.put("strategy4",
        hexagonPlayer -> board -> new AIPlayer(board, hexagonPlayer, new MinimaxStrategy(strat1)));
  }

  /**
   * Validates if the given player type is correct.
   * @param player The player type to validate.
   * @return True if the player type is valid, False otherwise.
   */
  private static boolean isValidPlayerType(String player) {
    return correctPlayer.contains(player.toLowerCase());
  }

  /**
   * Creates a player based on the given player type and color.
   * @param player The type of the player (human, AI, strategy1, etc.).
   * @param playerColor The color of the player (HexagonPlayer.BLACK or HexagonPlayer.WHITE).
   * @return The created Player instance.
   */
  private static Player createPlayer(String player, HexagonPlayer playerColor) {
    return playerDictionary.get(player.toLowerCase()).apply(playerColor).apply(board);
  }
}