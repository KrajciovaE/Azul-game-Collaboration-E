package sk.uniba.fmph.dcs.interfaces;

import sk.uniba.fmph.dcs.Tile;

import java.util.List;

public interface TableAreaInterface {
    List<Tile> take(int sourceIdx, int idx);
    boolean isRoundEnd();
    void startNewRound();
    String state();
}
