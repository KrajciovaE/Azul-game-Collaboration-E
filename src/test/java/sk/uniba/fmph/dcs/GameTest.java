package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.interfaces.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

class FakeTableCenterGame implements TableCenterInterface {
    private ArrayList<Tile> tiles;
    private boolean isFirst;

    public FakeTableCenterGame() {
        tiles = new ArrayList<>();
        isFirst = true;
    }

    @Override
    public void add(List<Tile> tiles) {
        this.tiles.addAll(tiles);
    }

    @Override
    public List<Tile> take(int idx) {
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
        return this.tiles.isEmpty();
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
class FakeTableArea implements TableAreaInterface {
    List<TileSourceInterface> tileSources;
    public FakeTableArea(List<TileSourceInterface> tileSources) {
        this.tileSources = tileSources;
    }
    @Override
    public List<Tile> take(int sourceIdx, int idx) {
        return tileSources.get(sourceIdx).take(idx);
    }

    @Override
    public boolean isRoundEnd() {
        for (TileSourceInterface tileSource : tileSources) {
            if (!tileSource.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void startNewRound() {
        for (TileSourceInterface tileSource : tileSources) {
            tileSource.startNewRound();
        }
    }

    @Override
    public String state() {
        StringBuilder toReturn = new StringBuilder();
        for (final TileSourceInterface tileSource : tileSources) {
            toReturn.append(tileSource.toString());
        }
        return toReturn.toString();
    }
}
class FakeBoard implements BoardInterface {

    @Override
    public void put(int destinationIdx, List<Tile> tiles) {

    }

    @Override
    public FinishRoundResult finishRound() {
        return null;
    }

    @Override
    public void endGame() {

    }

    @Override
    public String state() {
        return null;
    }

    @Override
    public Points getPoints() {
        return null;
    }
}
class FakeObserver implements ObserverInterface {

    @Override
    public void notify(String newState) {

    }
}

public class GameTest {

    Game game;
    TableAreaInterface tableArea;
    List<BoardInterface> allBoards;
    List<TileSourceInterface> tileSources;
    ObserverInterface observer;
    int playerCount;
    int startingPlayerId;
    int currentPlayerId;
    boolean isGameOver;

    @Before
    public void setUp() {
        FakeTableCenterGame tableCenter = new FakeTableCenterGame();
        UsedTilesTakeInterface usedTiles = new FakeUsedTilesTake();
        Bag bag = new Bag(usedTiles);
        Factory factory = new Factory(bag, tableCenter);
        tileSources = new ArrayList<>();
        tileSources.add(factory);
        tileSources.add(tableCenter);

        tableArea = new FakeTableArea(tileSources);
        allBoards = new ArrayList<>();

        BoardInterface board1 = new FakeBoard();
        BoardInterface board2 = new FakeBoard();
        allBoards.add(board1);
        allBoards.add(board2);

        observer = new FakeObserver();

        playerCount = 2;
        startingPlayerId = 1;
        currentPlayerId = 1;

        isGameOver = false;

        game = new Game(tableArea, allBoards, observer, playerCount);
    }

    @Test
    public void testGame() {
        String factoryState = tileSources.get(0).state();
        assertFalse("Factory has taken tiles.", factoryState.isEmpty());
        System.out.println("Factory before taking: " + factoryState);
        int startP = game.getCurrentPlayerId();
        assertTrue("Starting player took from factory.", game.take(startP, 0, 0, 0));
        assertNotEquals("Starting player is not on turn.", startP, game.getCurrentPlayerId());
        assertFalse("Starting player is not on turn, so he cant take.", game.take(startP, 0, 0, 0));
        assertEquals("Factory is now empty.", "", tileSources.get(0).state());
        assertThrows( IllegalArgumentException.class, () -> game.take(game.getCurrentPlayerId(), 0, 0, 0));         //"Player can not take from empty factory."
        String tableCenter = tileSources.get(1).state();
        assertTrue("Table center contains StartingPlayerTile.", tableCenter.contains("S"));
        System.out.println("Table center after taking from factory: " + tableCenter);

        int nextP = game.getCurrentPlayerId();
        assertTrue("Next player took from table center.", game.take(nextP, 1, 1, 0));

        tableCenter = tileSources.get(1).state();
        assertFalse("Table center does not contains StartingPlayerTile anymore.", tableCenter.contains("S"));
        System.out.println("Table center after taking from it: " + tableCenter);
    }
}
