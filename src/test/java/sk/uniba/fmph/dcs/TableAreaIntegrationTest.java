package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.interfaces.BagInterface;
import sk.uniba.fmph.dcs.interfaces.TableCenterInterface;
import sk.uniba.fmph.dcs.interfaces.TileSourceInterface;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TableAreaIntegrationTest {
    private TableArea tableArea;
    private BagInterface bag;
    private TableCenterInterface tableCenter;
    private ArrayList<TileSourceInterface> tileSources;
    private Factory factory1;
    private Factory factory2;

    @Before
    public void setUp() {
        UsedTiles usedTiles = new UsedTiles();
        bag = new Bag(usedTiles);
        tableCenter = new TableCenter();
        tileSources = new ArrayList<>();
        tileSources.add(tableCenter);
        factory1 = new Factory(bag, tableCenter);
        factory2 = new Factory(bag, tableCenter);
        tileSources.add(factory1);
        tileSources.add(factory2);

        tableArea = new TableArea(tileSources);
    }

    @Test
    public void testTableArea() {
        tableArea.startNewRound();

        assertFalse("Factory 1 took tiles from bag.", factory1.isEmpty());
        assertFalse("Factory 2 took tiles from bag.", factory2.isEmpty());

        System.out.println("Start of first round: \n" + tableArea.state());

        assertFalse("Table area took from factory 1.", tableArea.take(1,0).isEmpty());
        assertTrue("Factory 1 is now empty.", factory1.isEmpty());

        System.out.println("After first taking: \n" + tableArea.state());

        assertFalse("Table area took from factory 2.", tableArea.take(2,0).isEmpty());
        assertTrue("Factory 2 is now empty.", factory2.isEmpty());

        assertTrue("Table center contains starting player.", this.tableCenter.state().contains("S"));

        System.out.println("After second taking: \n" + tableArea.state());

        // Trying to take non-existing tile.
        assertThrows(IndexOutOfBoundsException.class, () -> tableArea.take(0, 10));
        // Trying to take from empty factory.
        assertThrows(IllegalArgumentException.class, () -> tableArea.take(1, 0));

        assertFalse("Rounds did not end yet.", tableArea.isRoundEnd());

        while ( !tableCenter.isEmpty() ) {
            assertFalse("We take from table center till its not empty.", tableArea.take(0,0).isEmpty());
        }

        assertTrue("Table center is now empty.", tableCenter.isEmpty());
        assertTrue("Both factories and table center is empty, so it is end of the round.", tableArea.isRoundEnd());

        System.out.println("End of the round: \n" + tableArea.state());

        tableArea.startNewRound();
        System.out.println("Start of new round: \n" + tableArea.state());
    }

}
