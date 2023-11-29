package sk.uniba.fmph.dcs;

import org.junit.Before;

import java.util.*;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import sk.uniba.fmph.dcs.interfaces.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;


public class GameIntegration {
    private Game game;
    private FakeBagGame bag;
    private TableArea tableArea;
    private ArrayList<TileSourceInterface> tileSources;
    private GameObserver gameObserver = new GameObserver();
    private TableCenter tableCenter;
    ArrayList<BoardInterface> boards = new ArrayList<>();
    private Board board1;
    private Board board2;
    private NotifyMe notifyMe;

    @Before
    public void setUp() {
        int playerCount = 2;

        // Bag.
        List<PatternLineInterface> patterLines;
        ArrayList<Points> pointPattern = new ArrayList<>(Arrays.asList(new Points(-1), new Points(-1), new Points(-2), new Points(-2), new Points(-2), new Points(-3), new Points(-3)));
        UsedTiles usedTiles = new UsedTiles();
        bag = new FakeBagGame(usedTiles);

        // Boards, points, wall lines, pattern lines
        for (int i = 0; i < playerCount; i++) {

            Floor floor = new Floor(usedTiles, pointPattern);

            ArrayList<Points> points = new ArrayList<>();
            List<WallLineInterface> wallLines = new ArrayList<>();

            // Tiles for wall lines.
            ArrayList<LinkedList<Tile>> tileTypesList = new ArrayList<>();
            tileTypesList.add(new LinkedList<>(Arrays.asList(Tile.BLUE, Tile.YELLOW, Tile.RED, Tile.GREEN, Tile.BLACK)));
            tileTypesList.add(new LinkedList<>(Arrays.asList(Tile.BLACK, Tile.BLUE, Tile.YELLOW, Tile.RED, Tile.GREEN)));
            tileTypesList.add(new LinkedList<>(Arrays.asList(Tile.GREEN, Tile.BLACK, Tile.BLUE, Tile.YELLOW, Tile.RED)));
            tileTypesList.add(new LinkedList<>(Arrays.asList(Tile.RED, Tile.GREEN, Tile.BLACK, Tile.BLUE, Tile.YELLOW)));
            tileTypesList.add(new LinkedList<>(Arrays.asList(Tile.YELLOW, Tile.RED, Tile.GREEN, Tile.BLACK, Tile.BLUE)));

            // Wall lines.
            for (int j = 0; j < 5; j++) {
                wallLines.add(new WallLine(tileTypesList.get(j), null, null));
            }
            // Wall lines above.
            for (int j = 0; j < 4; j++) {
                wallLines.get(j).setLineUp(wallLines.get(j + 1));
            }
            // Wall lines under.
            for (int j = 1; j < 5; j++) {
                wallLines.get(j).setLineDown(wallLines.get(j - 1));
            }

            // Patter lines.
            List<PatternLineInterface> patternLines = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                patternLines.add(new PatternLine(j + 1, floor, usedTiles, wallLines.get(j)));
            }

            // Final points calculation and game finished.
            FinalPointsCalculation finalPointsCalculation = new FinalPointsCalculation();
            GameFinished gameFinished = new GameFinished();

            boards.add(new Board(floor, points, patternLines, wallLines, finalPointsCalculation, gameFinished));
        }

        // Table area.
        tableCenter = new TableCenter();
        tileSources = new ArrayList<>();
        tileSources.add(tableCenter);
        for (int j = 0; j < 5; j++) {
            tileSources.add(new Factory(bag, tableCenter));
        }

        tableArea = new TableArea(tileSources);

        notifyMe = new NotifyMe();
        gameObserver.registerObserver(notifyMe);

        // Set up the game with 2 players.
        game = new Game(tableArea, boards, gameObserver, playerCount);

    }

    @Test
    public void testGamePlay() {

        game.take(game.getCurrentPlayerId(), 1, 0, 0);
        game.take(game.getCurrentPlayerId(), 2, 0, 0);

        game.take(game.getCurrentPlayerId(), 0, 1, 2);
        game.take(game.getCurrentPlayerId(), 0, 0, 2);

        game.take(game.getCurrentPlayerId(), 3, 0, 1);
        game.take(game.getCurrentPlayerId(), 4, 0, 1);

        game.take(game.getCurrentPlayerId(), 0, 0, 3);
        game.take(game.getCurrentPlayerId(), 0, 0, 3);

        game.take(game.getCurrentPlayerId(), 5, 0, 3);
        game.take(game.getCurrentPlayerId(), 0, 0, 3);

        game.take(game.getCurrentPlayerId(), 1, 0, 3);
        game.take(game.getCurrentPlayerId(), 2, 0, 0);

        game.take(game.getCurrentPlayerId(), 0, 1, 2);
        game.take(game.getCurrentPlayerId(), 0, 0, 2);

        game.take(game.getCurrentPlayerId(), 3, 0, 1);
        game.take(game.getCurrentPlayerId(), 4, 0, 1);

        game.take(game.getCurrentPlayerId(), 5, 3, 0);
        game.take(game.getCurrentPlayerId(), 0, 0, 4);

        game.take(game.getCurrentPlayerId(), 0, 0, 3);

        game.take(game.getCurrentPlayerId(), 2, 3, 0);
        game.take(game.getCurrentPlayerId(), 3, 3, 0);

        game.take(game.getCurrentPlayerId(), 5, 1, 3);
        game.take(game.getCurrentPlayerId(), 4, 0, 3);

        game.take(game.getCurrentPlayerId(), 0, 5, 2);
        game.take(game.getCurrentPlayerId(), 1, 3, 4);

        game.take(game.getCurrentPlayerId(), 0, 1, 1);
        game.take(game.getCurrentPlayerId(), 0, 1, 1);

        game.take(game.getCurrentPlayerId(), 4, 0, 0);
        game.take(game.getCurrentPlayerId(), 3, 3, 0);

        game.take(game.getCurrentPlayerId(), 0, 1, 2);
        game.take(game.getCurrentPlayerId(), 2, 3, 2);

        game.take(game.getCurrentPlayerId(), 1, 1, 1);
        game.take(game.getCurrentPlayerId(), 5, 0, -1);

        game.take(game.getCurrentPlayerId(), 0, 1, 4);
        game.take(game.getCurrentPlayerId(), 0, 0, 4);

        game.take(game.getCurrentPlayerId(), 0, 0, 1);

        game.take(game.getCurrentPlayerId(), 1, 0, 0);
        game.take(game.getCurrentPlayerId(), 5, 3, 3);

        game.take(game.getCurrentPlayerId(), 3, 2, 1);
        game.take(game.getCurrentPlayerId(), 4, 3, 0);

        game.take(game.getCurrentPlayerId(), 0, 2, 4);
        game.take(game.getCurrentPlayerId(), 2, 0, 1);

        game.take(game.getCurrentPlayerId(), 0, 0, 3);
        game.take(game.getCurrentPlayerId(), 0, 0, 4);

        assertTrue(game.isGameOver);
    }
}

