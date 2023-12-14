package cs3500.reversi.model;

/**
 * Represents a single cell (hexagon) in the Reversi board game.
 * A hexagon can be occupied by a player (BLACK or WHITE) or remain unoccupied (NONE).
 */
public class Hexagon {
  // status of occupancy:
  private HexagonPlayer occupancy;

  /**
   * Initializes a hexagon cell as unoccupied.
   */
  public Hexagon() {
    // initializes the hexagon as not being occupied by player
    this.occupancy = HexagonPlayer.NONE;
  }

  /**
   * Constructs a new Hexagon as a copy of the specified hexagon. This constructor
   * allows for the duplication of a Hexagon's state, particularly its occupancy status.
   * It is useful for scenarios where the individual hex state needs to be preserved
   * during operations that may alter the hex's state.
   *
   * @param hexagon The Hexagon to be copied. Must not be null.
   * @throws NullPointerException if the provided hexagon is null, to prevent
   *                              creating a copy of a non-existent hexagon.
   */
  public Hexagon(Hexagon hexagon) {
    if (hexagon == null) {
      throw new IllegalArgumentException("Hexagon to be copied cannot be null.");
    }
    this.occupancy = hexagon.getOccupancy();
  }

  /**
   * The HexagonPlayer enum represents the three possible states for a player:
   * BLACK, WHITE, or NONE.
   */
  public enum HexagonPlayer {
    BLACK, WHITE, NONE
  }


  /**
   * Changes the occupancy status of the hexagon.
   *
   * @param occupancy the new occupancy status of the hexagon
   */
  public void changeHexOccupancy(HexagonPlayer occupancy) {
    this.occupancy = occupancy;
  }

  /**
   * Provides a string representation of the hexagon's occupancy.
   * <p>
   * Returns a single character string representing the occupancy of the hexagon:
   * "B" for BLACK, "W" for WHITE, and "_" for NONE.
   * </p>
   *
   * @return A string representing the hexagon's occupancy.
   */
  @Override
  public String toString() {
    switch (occupancy) {
      case BLACK:
        return "B";
      case WHITE:
        return "W";
      case NONE:
      default:
        return "_";
    }
  }

  /**
   * Retrieves the occupancy state of the hexagon.
   *
   * @return The current occupancy state of the hexagon (BLACK, WHITE, or NONE).
   */
  public HexagonPlayer getOccupancy() {
    return this.occupancy;
  }

}
