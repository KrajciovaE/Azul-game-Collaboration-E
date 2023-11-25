package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.interfaces.BagInterface;
import sk.uniba.fmph.dcs.interfaces.TableCenterInterface;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class FakeTableCenter implements TableCenterInterface {
    public List<Tile> tiles = new ArrayList<>();
    @Override
    public void add(List<Tile> tiles) {
        this.tiles.addAll(tiles);
    }

    @Override
    public List<Tile> take(int idx) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void startNewRound() {
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

class FakeBag implements BagInterface {

    List<Tile> tiles = new ArrayList<>();

    public FakeBag() {
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

    @Override
    public String state() {
        StringBuilder result = new StringBuilder();
        for (Tile tile : tiles) {
            result.append(tile.toString());
        }
        return result.toString();
    }
}

public class FactoryTest {
    private Factory factory;
    private BagInterface fakeBag;
    private TableCenterInterface fakeTableCenter;

    @Before
    public void setUp() {
        fakeTableCenter = new FakeTableCenter();
        fakeBag = new FakeBag();

        factory = new Factory(fakeBag, fakeTableCenter);
    }

    @Test
    public void testFactory() {
        factory.startNewRound();
        assertEquals(4, factory.tiles.size());
        System.out.println("Tiles in factory before take: " + factory.state());
        System.out.println("Tiles taken from factory: " + factory.take(0));
        System.out.println("Tiles on table center: " + fakeTableCenter.state());
        assertTrue(factory.isEmpty());
        factory.startNewRound();
        assertEquals(4, factory.tiles.size());
        System.out.println("Tiles in factory before take: " + factory.state());
        System.out.println("Tiles taken from factory: " + factory.take(1));
        System.out.println("Tiles table center: " + fakeTableCenter.state());
    }
}
