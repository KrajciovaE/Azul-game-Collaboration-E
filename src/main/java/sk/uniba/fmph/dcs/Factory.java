package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.BagInterface;
import sk.uniba.fmph.dcs.interfaces.TableCenterInterface;
import sk.uniba.fmph.dcs.interfaces.TileSourceInterface;

import java.util.ArrayList;
import java.util.List;

public class Factory implements TileSourceInterface {
    private final BagInterface bag;

    public List<Tile> tiles;        // For testing purposes, must be public
    private final TableCenterInterface tableCenter;

    public Factory(BagInterface bag, TableCenterInterface tableCenter){
        this.bag = bag;
        this.tableCenter = tableCenter;
    }

    @Override
    public List<Tile> take(int idx) {
        List<Tile> result = new ArrayList<>();
        List<Tile> toTable = new ArrayList<>();

        for (Tile tile : tiles) {
            if (tile == tiles.get(idx)) {
                result.add(tile);
            } else toTable.add(tile);
        }

        tableCenter.add(toTable);
        this.tiles.clear();
        return result;
    }

    @Override
    public boolean isEmpty() {
        return tiles.size() == 0;
    }

    @Override
    public void startNewRound() {
        int count = 4;
        tiles = bag.take(count);
    }

    @Override
    public String state() {
        StringBuilder result = new StringBuilder();
        for (Tile tile : tiles) {
            result.append(tile.toString());
        }
        return result.toString();
    }
}
