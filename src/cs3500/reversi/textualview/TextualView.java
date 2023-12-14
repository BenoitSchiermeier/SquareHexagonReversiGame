package cs3500.reversi.textualview;

/**
 * Represents a textual view of a ReversiModel.
 */
public interface TextualView {

  /**
   * Returns a String representation of the 2D array of Hexagons in the ReversiModel,
   * where each Hexagon is represented by its toString() method and
   * null Hexagons are represented by spaces.
   *
   * @return a String representation of the 2D array of Hexagon objects
   */
  String arrayHexString();


  /**
   * Converts the 2D array of Hexagon objects to a String representation
   * where "n" stands for null.
   *
   * @return a String representation of the 2D array of Hexagon objects
   */
  String arrayTo2dArrayString();

}
