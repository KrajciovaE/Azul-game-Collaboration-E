package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.interfaces.UsedTilesTakeInterface;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

class FakeUsedTilesTake implements UsedTilesTakeInterface{
    public List<Tile> tiles;

    public FakeUsedTilesTake() {
        tiles = new ArrayList<>();
    }

    public List<Tile> takeAll(){
        List<Tile> result = new ArrayList<>(tiles);
        tiles.clear();
        return result;
    }

}

public class BagTest {
    Bag bag;
    UsedTilesTakeInterface fakeUsedTiles;
    List<Tile> fakeFactory;

    @Before
    public void setUp() {
        fakeUsedTiles = new FakeUsedTilesTake();
        fakeFactory = new ArrayList<>();

        bag = new Bag(fakeUsedTiles);
    }

    @Test
    public void testBag(){
        assertEquals(100, bag.tiles.size());
        assertFalse(bag.isEmpty());
        fakeFactory = bag.take(4);
        assertEquals(96, bag.tiles.size());
        fakeFactory = bag.take(96);
        assertTrue(bag.isEmpty());
    }
}
