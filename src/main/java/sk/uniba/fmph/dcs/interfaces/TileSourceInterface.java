package sk.uniba.fmph.dcs.interfaces;

import sk.uniba.fmph.dcs.Tile;

import java.util.ArrayList;
import java.util.List;

public interface TileSourceInterface {
    List<Tile> take (int idx);
    boolean isEmpty();
    void startNewRound();
    String state();
}
