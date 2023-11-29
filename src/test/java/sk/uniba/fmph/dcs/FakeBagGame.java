package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.BagInterface;
import sk.uniba.fmph.dcs.interfaces.UsedTilesTakeInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeBagGame implements BagInterface {
    List<Tile> tiles;
    UsedTilesTakeInterface usedTiles;

    public FakeBagGame(UsedTilesTakeInterface usedTiles) {
        this.usedTiles = usedTiles;
        this.tiles = new ArrayList<>();

        // 1
        tiles.add(Tile.BLACK);
        tiles.add(Tile.BLUE);
        tiles.add(Tile.BLUE);
        tiles.add(Tile.BLUE);

        // 2
        tiles.add(Tile.RED);
        tiles.add(Tile.BLACK);
        tiles.add(Tile.BLACK);
        tiles.add(Tile.BLACK);

        // 3
        tiles.add(Tile.BLUE);
        tiles.add(Tile.BLUE);
        tiles.add(Tile.GREEN);
        tiles.add(Tile.GREEN);

        // 4
        tiles.add(Tile.GREEN);
        tiles.add(Tile.GREEN);
        tiles.add(Tile.BLUE);
        tiles.add(Tile.BLUE);

        // 5
        tiles.add(Tile.GREEN);
        tiles.add(Tile.GREEN);
        tiles.add(Tile.GREEN);
        tiles.add(Tile.BLUE);

        // 1
        tiles.add(Tile.BLUE);
        tiles.add(Tile.GREEN);
        tiles.add(Tile.GREEN);
        tiles.add(Tile.GREEN);

        // 2
        tiles.add(Tile.RED);
        tiles.add(Tile.BLACK);
        tiles.add(Tile.BLACK);
        tiles.add(Tile.BLACK);

        // 3
        tiles.add(Tile.BLUE);
        tiles.add(Tile.BLUE);
        tiles.add(Tile.GREEN);
        tiles.add(Tile.GREEN);

        // 4
        tiles.add(Tile.YELLOW);
        tiles.add(Tile.YELLOW);
        tiles.add(Tile.BLUE);
        tiles.add(Tile.BLUE);

        // 5
        tiles.add(Tile.GREEN);
        tiles.add(Tile.GREEN);
        tiles.add(Tile.GREEN);
        tiles.add(Tile.BLUE);

        // 1
        tiles.add(Tile.YELLOW);
        tiles.add(Tile.RED);
        tiles.add(Tile.YELLOW);
        tiles.add(Tile.BLUE);

        // 2
        tiles.add(Tile.RED);
        tiles.add(Tile.BLACK);
        tiles.add(Tile.BLACK);
        tiles.add(Tile.BLACK);

        // 3
        tiles.add(Tile.BLUE);
        tiles.add(Tile.BLUE);
        tiles.add(Tile.GREEN);
        tiles.add(Tile.GREEN);

        // 4
        tiles.add(Tile.GREEN);
        tiles.add(Tile.GREEN);
        tiles.add(Tile.BLUE);
        tiles.add(Tile.BLUE);

        // 5
        tiles.add(Tile.GREEN);
        tiles.add(Tile.GREEN);
        tiles.add(Tile.GREEN);
        tiles.add(Tile.BLUE);

        // 1
        tiles.add(Tile.BLUE);
        tiles.add(Tile.GREEN);
        tiles.add(Tile.GREEN);
        tiles.add(Tile.GREEN);

        // 2
        tiles.add(Tile.RED);
        tiles.add(Tile.BLACK);
        tiles.add(Tile.BLACK);
        tiles.add(Tile.BLACK);

        // 3
        tiles.add(Tile.BLUE);
        tiles.add(Tile.BLUE);
        tiles.add(Tile.GREEN);
        tiles.add(Tile.GREEN);

        // 4
        tiles.add(Tile.YELLOW);
        tiles.add(Tile.YELLOW);
        tiles.add(Tile.BLUE);
        tiles.add(Tile.BLUE);


    }
    @Override
    public List<Tile> take(int count) {
        if (this.isEmpty()) startNewRound();

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
