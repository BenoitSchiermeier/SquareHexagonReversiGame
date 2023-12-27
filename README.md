AI player vs AI player on Hexagonal Grid: 

![Screen Recording 2023-12-26 at 9 04 28 PM](https://github.com/BenoitSchiermeier/SquareHexagonReversiGame/assets/132936530/8bc0d89d-b456-478c-aebb-fc2fcae24dec)


AI player vs AI player on Square Grid: 

![Screen Recording 2023-12-26 at 9 13 24 PM](https://github.com/BenoitSchiermeier/SquareHexagonReversiGame/assets/132936530/2c9c9856-6ea2-4643-816a-c771dcdb967f)

Human player with Hints vs human player without hints: 

![Screen Recording 2023-12-26 at 9 22 14 PM 2](https://github.com/BenoitSchiermeier/SquareHexagonReversiGame/assets/132936530/e127a65c-ff34-4752-9b84-b3e290e1e109)


INVARIANTS:
1. The board cannot be started with a size less than 3 and its size remains
    constant after initialization.
2. The size of the board remain constant.


OVERVIEW:
This codebase is designed to implement the classic game of Reversi, with a twist: the game
board consists of hexagonal tiles instead of the traditional square grid. Players alternate
turns, placing their colored discs on the board to capture their opponent's discs. The game
continues until neither player can make a valid move. The goal is to have the majority of
the board occupied by one's own discs at the end of the game.



QUICK START:
// Initializes a 4x4 board (board can be any size except for less than size 3);
ReversiBoard board = new ReversiBoard(4);

// Black player makes a move at position (2,3) (black must move first)
board.play(4, 1, HexagonPlayer.BLACK);

// create a TextualView with the board created above:
TextualView textView = new ReversiModelTextView(board);

// prints the Hexagonal Grid as a string
System.out.println(textView.arrayHexString());



KEY COMPONENTS:
Model:
ReversiMutableModel & ReversiReadOnlyModel: Interfaces that define essential
functionalities for the Reversi game model. The mutable model allows modifications,
while the read-only model provides a safe view without modification abilities.

ReversiBoard: The central component representing the game board. It manages the game state,
validates moves, and keeps track of player turns.

View:
TextualView: Interface that ensures a class provides a textual representation of the game.
ReversiModelTextView: Concrete implementation of the textual view that presents the game
state in a string format.



KEY SUBCOMPONENTS:
Hexagon: Represents individual hexagonal cells on the board. Each hexagon has an
associated player occupancy state (BLACK, WHITE, or NONE).

HexagonPlayer: An enumeration indicating the possible states of a hexagon
cell: BLACK, WHITE, or NONE.

Pair: A utility class that captures a pair of integer values, usually used to represent
grid coordinates.



SOURCE ORGANIZATION:
cs3500.reversi.model: This package contains the primary game logic, including ReversiBoard,
Hexagon, Pair, ReversiMutableModel, ReversiReadOnlyModel.

cs3500.reversi.textualview: This package contains the views, including ReversiModelTextView,
and TextualView

ReversiBoard.java: Implements the game board logic.
Hexagon.java: Defines the hexagonal cells on the board.
ReversiMutableModel.java & ReversiReadOnlyModel.java: Interfaces defining model functionalities.
Pair.java: Utility class for coordinate representation.
TextualView.java: Interface for textual representation of the game.
ReversiModelTextView.java: Concrete class that generates the textual view of the game.


ReversiBoard Coordinate System:
The ReversiBoard class in the cs3500.reversi.model package uses a unique coordinate system to
manage the game board for Reversi, a strategy board game. This README provides an overview of
the coordinate system and its implementation.

**************************    CHANGES FOR PART 2    ***********************************************


Overview
Reversi is played on a hexagonal grid. The ReversiBoard class represents this grid using
a two-dimensional array (Hexagon[][] hexList). Each position on the board
is identified using two coordinates: q and r.

Coordinate System
Q Coordinate: Represents the horizontal axis of the hexagonal grid. It increases from left to right.
R Coordinate: Represents the vertical axis of the hexagonal grid. It increases from top to bottom.
The combination of these two coordinates uniquely identifies each hexagon on the board.

Board Representation
The board is represented as a 2D array of Hexagon objects, where each Hexagon can be at
a valid or invalid position. Invalid positions (where no hexagon exists) are
represented as null in the array.

Valid Position Check
The method validHexPosition(int q, int r) determines if a given (q, r) coordinate pair
corresponds to a valid position on the hexagonal grid.
The validity is based on the size of the board and the inherent structure of a hexagonal grid.

Initialization
During the initialization of the board in createBoard(), each position in the 2D array is
checked for validity. Valid positions are initialized with new Hexagon objects,
while invalid positions remain null. The starting positions of the players are set by
the addStartingPlayers() method, based on the middle of the board.

Utilization
The coordinate system is used throughout the class for various functionalities, including
making moves (play method), checking if a move is valid
(canMove methods), and determining the game state.



1. moved these methods from ReversiMutableModel to ReversiReadOnlyModel:
  isGameOver
  getCurrentPlayer
  canMove

 - ReversiModelFactory.java
To facilitate the creation of read-only models from mutable models, the ReversiModelFactory
class has been introduced. This factory class provides a method to convert a ReversiMutableModel
into a ReversiReadOnlyModel.

Usage
The ReversiModelFactory class contains a static method createReadOnlyModel, which takes a
ReversiMutableModel as input and returns a ReversiReadOnlyModel. This method checks if the input
model is an instance of ReversiBoard and, if so, creates a new ReversiBoard instance that acts as
a read-only model.



********* DOCUMENTING THE VIEW:  *********

COMPONENTS FOUND IN: package cs3500.reversi.view;

MAKING A MOVE:
To make a move in the game, select a cell using your mouse or keyboard navigation, and then press
the "Enter" key. This action will place your disc in the selected cell if the move is valid.
A cell that is already occupied with a player cannnot be selected.

PASSING A TURN:
To pass your turn, press the "P" key.

 - IReversiView.java
Description:
IReversiView is an interface that represents the view component in the Reversi game.
It defines the contract for all views within the game, ensuring a consistent structure
for various view implementations.

Key Functionalities:
As an interface, IReversiView may contain method declarations relevant to the display and
update of the game's graphical user interface.


 - GameBoardLayout.java
Description:
GameBoardLayout is a class that extends JFrame and implements IReversiView. It provides the
graphical layout for the Reversi game board, including the hexagonal cells and the score display.

Key Functionalities:
Hexagonal Grid Layout: Manages a grid of HexagonButton objects representing the game cells.
Score Display: Includes a JLabel to show the current scores of the players.
Dynamic Sizing: Calculates the dimensions and layout of the hexagonal grid based on the game
model's board size.
Usage:
Used to create and manage the main game window, displaying the Reversi board
and scores to the player.


 - HexagonPanel.java
Description:
HexagonPanel is a JPanel subclass responsible for arranging HexagonButton objects in a
hexagonal grid pattern. It handles user interactions like selecting and deselecting game cells.

Key Functionalities:
Grid Management: Arranges hexagonal buttons in a grid, handling their layout and dimensions.
User Interaction: Includes methods to handle key and mouse events for selecting and playing moves.
Cell Selection: Manages the logic for selecting and deselecting hexagonal cells on the game board.

Usage:
This panel is a crucial part of the game's UI, enabling players to interact with the game board
by selecting and playing moves.

 - HexagonButton.java
Description:
HexagonButton extends JButton and represents a single hexagonal cell on the Reversi board.
It is responsible for displaying the state of a cell and handling user interactions.

Key Functionalities:
Custom Shape: Renders a hexagon shape for the button, representing a game cell.
State Representation: Changes color based on the cell's occupancy (Black, White, or unoccupied).
User Interaction: Responds to click events for selection and deselection, visually indicating
the current state.

Usage:
These buttons form the individual cells of the Reversi game board, allowing players to
interact with and visualize the game state.


*********  DOCUMENTING THE STRATEGY:  ***********
COMPONENTS CAN BE FOUND IN: package cs3500.reversi.strategy;


 - ReversiStrategy.java
Description:
ReversiStrategy is an interface defining the contract for strategy implementations in the
Reversi game. It outlines the method through which a strategy can choose the next move based
on the current game state.

Key Functionalities:
chooseMove: Method that takes a ReversiReadOnlyModel and a HexagonPlayer as parameters,
returning a Move object. This method encapsulates the strategy's logic to select
the next move in the game.

 - AggressiveReversiStrategy.java
Description:
AggressiveReversiStrategy is a class that implements the ReversiStrategy interface.
It embodies an aggressive approach to playing Reversi, focusing on capturing the most
pieces possible with each move.

Key Functionalities:
Aggressive Move Selection: Prioritizes moves that result in the maximum number of pieces captured.
Tie-Breaking Mechanism: In cases where multiple moves capture the same number of pieces,
it selects the move with the uppermost-leftmost coordinate as a tie-breaker.
Dynamic Strategy: Adapts its move choice based on the current state of the board and the player
it's deciding for.
Usage:
This strategy can be used in a Reversi game to provide an AI player that aggressively captures
opponent pieces, making it challenging for human players or other AI strategies.


 - AvoidCornersStrategy.java
Description:
AvoidCornersStrategy is a class implementing the ReversiStrategy interface. This strategy aims to
avoid making moves adjacent to corners, focusing instead on controlling the center and other
areas of the board.

Key Functionalities:
Corner Avoidance: Actively avoids moves that are adjacent to the corners of the board.
Fallback Mechanism: In situations with no non-corner moves, it falls back to aggressive
strategies for move selection.

 - GoForCornersStrategy.java
Description:
GoForCornersStrategy, adhering to the ReversiStrategy interface, emphasizes capturing corner
positions, which are strategically advantageous in Reversi. Once captured, these positions
offer stability and cannot be flipped by the opponent.

Key Functionalities:
Corner Prioritization: Focuses on moves that capture corners whenever available.
Strategic Adaptability: When corner moves are not feasible, the strategy shifts to non-corner
aggressive moves or avoids corner-adjacent moves.

 - Move.java
Description:
Move is a class representing a single move in the Reversi game. It encapsulates the
coordinates of the move and the player who makes it.

Key Functionalities:
Coordinates Management: Stores the row (r) and column (q) of the move.
Player Association: Keeps track of which player (HexagonPlayer) is making the move.
Accessors: Provides methods to retrieve the coordinates and the player associated with the move.


QUICK START OF STRATEGY:
// create a model:
ReversiReadOnlyModel model = new ReversiBoard(4);
// create a strategy :
ReversiStrategy strategy = new AggressiveReversiStrategy();
// create a move with the chosen strategy:
Move newMove = strategy.chooseMove(model, model.getPlayer());
// access the Q coordinate of the move
int q = newMove.getQ();
// access the R coordinate of the move
int r = newMove.getR();
// access the player of the move
HexagonPlayer player = newMove.getPlayer();

// Example Usage for AvoidCornersStrategy
ReversiStrategy avoidCornersStrategy = new AvoidCornersStrategy();
Move avoidCornersMove = avoidCornersStrategy.chooseMove(model, model.getPlayer());

// Example Usage for GoForCornersStrategy
ReversiStrategy goForCornersStrategy = new GoForCornersStrategy();
Move goForCornersMove = goForCornersStrategy.chooseMove(model, model.getPlayer());


MinimaxStrategy for Reversi
- MinimaxStrategy.java :
Overview
The MinimaxStrategy class is an implementation of the Minimax algorithm specifically tailored
for the Reversi game. This strategy aims to optimize the player's moves by considering the
potential responses of the opponent.

Features
Minimax Algorithm: Utilizes the classic Minimax approach to predict the
outcome of moves and choose the best one.
Opponent Strategy Simulation: Assumes an opponent's strategy to better counteract their moves.
Disruptive Move Selection: Prioritizes moves that disrupt the opponent's best possible move.
Adaptive Play-style: Adjusts the strategy based on the opponent's predicted strategy.


QUICK START:
ReversiStrategy opponentStrategy = new AggressiveReversiStrategy(); // Or any other strategy
ReversiStrategy minimaxStrategy = new MinimaxStrategy(opponentStrategy);
// Move based on minimax strategy:
Move nextMove = minimaxStrategy.chooseMove(gameModel, currentPlayer);


TryTwo Strategy in Reversi:
The TryTwo strategy is a unique approach in our Reversi game, designed to provide flexibility
and adaptability in strategy selection during gameplay. This strategy encapsulates two different
strategies, attempting them in succession to determine the best possible move
under varying game conditions.

Overview:
Primary Strategy:
The TryTwo strategy first tries to use a primary strategy.
This is the preferred method for making a move, based on specific criteria or game
tactics defined in the strategy.

Secondary Strategy:
If the primary strategy fails (i.e., it finds no valid moves), the TryTwo strategy then resorts
to a secondary strategy. This fallback strategy offers an alternative method for choosing a move,
ensuring that the player has the best possible option available under the circumstances.

Usage
This strategy is particularly useful in scenarios where the primary strategy might be too
aggressive or specific, and there's a need for a more balanced or cautious approach as a backup.

Exception Handling
If both the primary and secondary strategies fail to find a valid move, the TryTwo strategy
throws an IllegalStateException. This exception indicates that no move is available,
and the player might need to pass their turn.

QUICK START OF TryTwo strategy:
ReversiStrategy aggressiveStrategy = new AggressiveReversiStrategy();
ReversiStrategy cornerStrategy = new GoForCornersStrategy();

ReversiStrategy tryTwoStrategy = new TryTwo(cornerStrategy, aggressiveStrategy);

// Usage in a game context
Move nextMove = tryTwoStrategy.chooseMove(model, currentPlayer);





**************************    CHANGES FOR PART 3    ***********************************************
Added documentation to "making a move":
A cell that is already occupied with a player cannot be selected.


Overview:
Part 3 of the Reversi game development introduces significant enhancements in terms of gameplay,
user interaction, and AI strategies. These changes aim to provide a more immersive and challenging
game experience.

Play the game from Jar:
The player whose turn it is signified by a yellow background and in the title the player's color
  whose turn it is is signified.
Player 1 will always be BLACK
Player 2 will always be WHITE
To play, the player must either pass by hitting the p key or play by selecting a cell
  and hitting the key ENTER


ReversiController:
A new controller class has been added to manage the interactions between the model and the view.
It incorporates logic for handling game events and player inputs.
The controller observes changes in the game board and updates the view accordingly.
It facilitates automatic moves by AI players and handles key events for human players.
Added a MockReversiCOntroller

BoardObserver Interface:
Introduced to enable the view to observe changes in the game model.
This interface allows the implementation of various views that react dynamically to changes in
the game state. The model notifies the observer in the methods:
- play(int, int, HexagonPlayer)
- pass()
- startObserverGame()


ViewFeatures Interface:
The ViewFeatures interface is an essential component in the Reversi game's architecture,
playing a pivotal role in managing interactions between the game's view and its controller.
It is designed specifically to handle key press events within the Reversi game view.

Model:
Included deep copying + a MockReversiBoard + a MockReversiModel for testing purposes and seeing
when called from the controller it correctly reflects the view and model

Model Interfaces:

Included deep copying in both MutuableModel and ReadOnlyModel

Key Features
Key Press Event Handling: The interface provides methods to react to specific key press events,
ensuring a seamless interaction between the user (through the view) and the game's
logic (managed by the controller).


ReversiMutableModel:
Added startGame method to start the model.

Enhanced Player Interaction: ---> KEYBOARD INPUTS:
Players can now interact with the game using keyboard inputs. For example, pressing "P" to pass
a turn and "Enter" to confirm a move.
Human players interact through keyboard events, while AI players make automatic moves
based on their strategies.

Reversi Main Class:
The main class for the game has been updated to initialize the game with various player
types and strategies. It sets up the game environment, including the board, players,
and the controller.

Quick start:
To start the game, one must
1. download the jar
2. run it
3. enter the desired board size (must be greater than 2),
   , enter the first player -> if human desired type "human"
   , enter the second player -> if ai player desired type the name of the strategy that player
   is going to use (documentation under):

   ex: ->    java -jar Reversi.jar 9 strategy1 strategy1


Configuring AI players in the command line:
AI player will use the desired strategy:
1. strategy1 - AggressiveReversiStrategy
2. strategy2 - AvoidCornersStrategy
3. strategy3 - GoForCornersStrategy
4. strategy4 - MinimaxStrategy (with AggressiveReversiStrategy as a base)
5. strategy12 - A combination of AvoidCornersStrategy and AggressiveReversiStrategy
6. strategy123 - A combination of GoForCornersStrategy, AvoidCornersStrategy,
                 and AggressiveReversiStrategy

the structure of the arguments must be as follows: "[board size (int)] [player1Type] [player2Type]"



***************************** CHANGES FOR EXTRA CREDIT ************************************

LEVEL 0 0F EXTRA CREDIT: HINTS...

Added new view Feature method to take care of hints.
Added new method in IReversiView.java:
addHints(HexagonPlayer) -> this method allows for hints to be shown onn the view's panel:

added:
HexHintButton : this class is a decorator class for HexagonButton, it changes the way the
    button is painted so that it displays a hint
HexHintPanel: this class extends the HexagonPanel and changes the method addHexButtonsToArray
    to add HexHintButton to an array instead of HexagonButton

in GameBoardLayout, changed the refresh and panelSetUp methods to use the decorator classes if the
    hints are turned on.
Changed the setUpKeyListener in GameBoardLayout to include these keybinds:
To use hints, the key binds are :
H -> turn on hints, this should be noticeable as the title changes to Hints: ON and the
  background of the player whose turn it is turns magenta instead of yellow.
J -> turn off hints, the title will say Hints: OFF and the background of the player whose turn it is
  will go back to yellow.

LEVEL 1 OF EXTRA CREDIT:

in (package cs3500.reversi.model):
added :
ReversiSquareBoard -> which extends ReversiBoard and changes the starting board, the starting
    players, the rules in which to search for valid moves.

LEVEL 2 OF EXTRA CREDIT:

in package (cs3500.reversi.view):
added:
ReversiSquareLayout -> which extends GameBoardLayout to create new Square panels
ReversiSquarePanel -> which extends HexagonPanel to override addHexButtonsToArray and
  paintComponent so that the square view can be shown
SquareButton -> which changes the bounds of HexagonButton so that it can be drawn as a square

optional additions to add hints to the decorated hints from level 0:
include classes:
SquareButtonHint -> which extends HexHintButton and recalculates the coordinates like in
    SquareButton
SquareHintPanel -> extends ReversiSquarePanel and changes the buttons that are added to the array
    to SquareButtonHint buttons
in ReversiSquareLayout overrode newPanel method to add hint panels or non-hint panels accordingly


LEVEL 3 OF EXTRA CREDIT:
only method that was added to the controller was makeHints:
    this method was necessary to give the view the player who needs the hint.
because of the MVC pattern, the view has no idea what player it has, for the hints to work, the
controller needed to give the view the player so that the view could calculate the hint properly.
this method also refreshes the view so that the new hint view can be seen (when hints are on,
the background of turns purple and the selection color does as well).


LEVEL 4 OF EXTRA CREDIT:
no changes were needed to be made to the strategies.










