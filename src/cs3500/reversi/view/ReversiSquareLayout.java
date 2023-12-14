package cs3500.reversi.view;

import cs3500.reversi.model.ReversiReadOnlyModel;

public class ReversiSquareLayout extends GameBoardLayout {
  public ReversiSquareLayout(ReversiReadOnlyModel model) {
    super(model);
  }

  @Override
  protected void newPanel() {
    if (hints) {
      buttonPanel = new SquareHintPanel(model.getBoardSize(), model, player);
    }
    else {
      buttonPanel = new ReversiSquarePanel(model.getBoardSize(), model, player);
    }
  }


}
