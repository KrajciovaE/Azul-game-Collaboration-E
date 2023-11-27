package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.BagInterface;
import sk.uniba.fmph.dcs.interfaces.TileSourceInterface;
import sk.uniba.fmph.dcs.interfaces.UsedTilesTakeInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bag implements BagInterface {
    List<Tile> tiles = new ArrayList<>();
    UsedTilesTakeInterface usedTiles;
    public Bag(UsedTilesTakeInterface usedTiles){

        this.usedTiles = usedTiles;

        for (int i = 0; i < 100; i++){
            if (i < 20){
                tiles.add(Tile.RED);
            } else if (i < 40) {
                tiles.add(Tile.BLUE);
            } else if (i < 60) {
                tiles.add(Tile.GREEN);
            } else if (i < 80) {
                tiles.add(Tile.BLACK);
            } else tiles.add(Tile.YELLOW);
        }

        Collections.shuffle(tiles);
    }
    @Override
    public List<Tile> take(int count) {

        List<Tile> result = new ArrayList<>();
        for (int i = 0; i < count; i++){

            result.add(tiles.get(0));
            tiles.remove(0);
        }

        return result;
    }

    public boolean isEmpty() {
        return tiles.size() == 0;
    }

    public void startNewRound() {
        tiles.addAll(usedTiles.takeAll());
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
