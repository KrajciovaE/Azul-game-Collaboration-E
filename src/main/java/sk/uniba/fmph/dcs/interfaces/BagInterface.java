package sk.uniba.fmph.dcs.interfaces;

import sk.uniba.fmph.dcs.Tile;

import java.util.List;

public interface BagInterface {
    List<Tile> take(int count);
    String state();
}
