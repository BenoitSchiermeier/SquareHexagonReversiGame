package cs3500.reversi.textualview;

import cs3500.reversi.model.Hexagon;
import cs3500.reversi.model.Hexagon.HexagonPlayer;
import cs3500.reversi.model.ReversiReadOnlyModel;

/**
 * Represents the textual view of the Reversi game, presenting the game state in a string format.
 * This view can represent the game state in two formats: hexagonal and 2D array-like.
 */
public class ReversiModelTextView implements TextualView {
  private final ReversiReadOnlyModel model;

  public ReversiModelTextView(ReversiReadOnlyModel model) {
    this.model = model;
  }

  @Override
  public String arrayHexString() {
    StringBuilder sb = new StringBuilder();
    Hexagon[][] hexList = model.getHexList();
    for (Hexagon[] hexagons : hexList) {
      int countNulls = 0;

      // count the number of nulls in current row
      for (Hexagon hex : hexagons) {
        if (hex == null) {
          countNulls += 1;
        }
      }
      // append the spaces for each null:
      sb.append("  ".repeat(Math.max(0, countNulls)));
      // append the visual representation of the non-null hexagons
      for (Hexagon hexagon : hexagons) {
        if (hexagon != null) {
          sb.append(hexagon).append("   ");
        }
      }
      sb.append("\n");
    }
    sb.append("Black Score = ").append(model.getScore(HexagonPlayer.BLACK))
        .append("\n").append("White Score = ")
        .append(model.getScore(HexagonPlayer.WHITE)).append("\n");

    return sb.toString();
  }


  @Override
  public String arrayTo2dArrayString() {
    StringBuilder sb = new StringBuilder();
    Hexagon[][] hexList = model.getHexList();
    for (Hexagon[] hexagons : hexList) {
      for (Hexagon hexagon : hexagons) {
        if (hexagon == null) {
          sb.append("n").append("  ");
        }
        else {
          sb.append(hexagon).append("  ");

        }
      }
      sb.append("\n");
    }
    sb.append("Black Score = ").append(model.getScore(HexagonPlayer.BLACK))
        .append("\n").append("White Score = ")
        .append(model.getScore(HexagonPlayer.WHITE)).append("\n");

    return sb.toString();
  }

}
