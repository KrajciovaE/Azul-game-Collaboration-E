package sk.uniba.fmph.dcs.interfaces;

import sk.uniba.fmph.dcs.Points;
import sk.uniba.fmph.dcs.Tile;
import sk.uniba.fmph.dcs.WallLine;

import java.util.List;
import java.util.Optional;

public interface WallLineInterface extends WallLinePutTileInterface{
    boolean canPutTile(Tile tile);
    List<Optional<Tile>> getTiles();
    Points putTile(Tile tile);
    String state();
    void setLineUp(WallLineInterface lineUp);
    void setLineDown(WallLineInterface lineDown);
}
