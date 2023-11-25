package sk.uniba.fmph.dcs.interfaces;

import sk.uniba.fmph.dcs.Tile;

import java.util.List;

public interface TableCenterInterface extends TileSourceInterface {
    void add(List<Tile> tiles);
}
