package sk.uniba.fmph.dcs.interfaces;

import sk.uniba.fmph.dcs.Points;
import sk.uniba.fmph.dcs.Tile;

import java.util.List;

public interface PatternLineInterface {
     void put(List<Tile> tiles);
     Points finishRound();
     String state();
}
