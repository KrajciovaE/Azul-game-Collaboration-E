package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.interfaces.WallLineInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PatternLineTest {
    private PatternLine patternLine;
    private Floor fakeFloor;

    @Before
    public void setUp(){
        // Initialize the fake objects
        FakeUsedTiles fakeUsedTiles = new FakeUsedTiles();
        ArrayList<Points> pointPattern = new ArrayList<Points>();
        pointPattern.add(new Points(1));
        pointPattern.add(new Points(2));
        pointPattern.add(new Points(2));

        int capacity = 5;
        this.fakeFloor = new Floor(fakeUsedTiles, pointPattern);
        WallLineInterface fakeWallLine = new FakeWallLine();

        patternLine = new PatternLine(capacity, fakeFloor, fakeUsedTiles, fakeWallLine);
    }

    @Test
    public void testPatternLine() {
        List<Tile> tiles1 = Arrays.asList(Tile.BLUE, Tile.BLUE);
        patternLine.put(tiles1);
        assertEquals("BB", patternLine.state());
        assertEquals(0, patternLine.finishRound().getValue());
        List<Tile> tiles2 = Arrays.asList(Tile.RED, Tile.RED);
        patternLine.put(tiles2);
        assertEquals("RR", fakeFloor.state());
        List<Tile> tiles3 = Arrays.asList(Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE);
        patternLine.put(tiles3);
        assertEquals("BBBBB", patternLine.state());
        assertEquals("RRB", fakeFloor.state());
        assertEquals(0, patternLine.finishRound().getValue());

    }

}
