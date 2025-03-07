package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.TableCenterInterface;
import sk.uniba.fmph.dcs.interfaces.TileSourceInterface;

import java.util.ArrayList;
import java.util.List;

public class TableCenter implements TableCenterInterface {
    public ArrayList<Tile> tiles;           // For testing purposes.
    private boolean isFirst;

    public TableCenter() {
        tiles = new ArrayList();
        isFirst = true;
    }

    @Override
    public void add(List<Tile> tiles) {
        this.tiles.addAll(tiles);
    }

    @Override
    public ArrayList<Tile> take(int idx) {
        if(idx > tiles.size()) throw new IndexOutOfBoundsException("Index out of bounds");
        Tile pickedTile = tiles.get(idx);
        ArrayList<Tile> pickedTiles = new ArrayList<Tile>();
        if(isFirst) pickedTiles.add(tiles.remove(0));
        isFirst = false;
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i) == pickedTile) {
                pickedTiles.add(tiles.remove(i));
                i--;
            }
        }
        return pickedTiles;
    }

    @Override
    public boolean isEmpty() {
        return tiles.size() == 0;
    }

    @Override
    public void startNewRound() {
        tiles = new ArrayList<>();
        isFirst = true;
        tiles.add(Tile.STARTING_PLAYER);
    }

    @Override
    public String state() {
        StringBuilder toReturn = new StringBuilder();
        for (final Tile tile : tiles) {
            toReturn.append(tile.toString());
        }
        return toReturn.toString();
    }
}
