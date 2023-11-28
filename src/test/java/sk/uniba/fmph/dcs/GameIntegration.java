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
    private Factory factory1;
    private Factory factory2;
    private Factory factory3;
    private Factory factory4;
    private Factory factory5;

    private Board board1;
    private Board board2;

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

            if (i == 0) {
                board1 = new Board(floor, points, patternLines, wallLines, finalPointsCalculation, gameFinished);
                boards.add(board1);
            } else {
                board2 = new Board(floor, points, patternLines, wallLines, finalPointsCalculation, gameFinished);
                boards.add(board2);
            }

        }

        // Table area.
        tableCenter = new TableCenter();
        tileSources = new ArrayList<>();
        tileSources.add(tableCenter);
        this.factory1 = new Factory(bag, tableCenter);
        this.factory2 = new Factory(bag, tableCenter);
        this.factory3 = new Factory(bag, tableCenter);
        this.factory4 = new Factory(bag, tableCenter);
        this.factory5 = new Factory(bag, tableCenter);
        tileSources.add(factory1);
        tileSources.add(factory2);
        tileSources.add(factory3);
        tileSources.add(factory4);
        tileSources.add(factory5);

        tableArea = new TableArea(tileSources);

        ObserverInterface observer = new GameObserver();
        NotifyMe notifyMe = new NotifyMe();
        gameObserver.registerObserver(notifyMe);

        // Set up the game with 2 players.
        game = new Game(tableArea, boards, observer, playerCount);
    }

    @Test
    public void testGamePlay() {

        int c = 1;

        System.out.println( c + ". round: \n" + tableArea.state() );

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

        for ( BoardInterface board : boards ) {
            System.out.println(board.state());
        }
        System.out.println(tableArea.state());
        c++;

        System.out.println( c + ". round: \n" + tableArea.state() );

        game.take(game.getCurrentPlayerId(), 1, 0, 3);
        game.take(game.getCurrentPlayerId(), 2, 0, 0);

        game.take(game.getCurrentPlayerId(), 0, 1, 2);
        game.take(game.getCurrentPlayerId(), 0, 0, 2);

        game.take(game.getCurrentPlayerId(), 3, 0, 1);
        game.take(game.getCurrentPlayerId(), 4, 0, 1);

        game.take(game.getCurrentPlayerId(), 5, 3, 0);
        game.take(game.getCurrentPlayerId(), 0, 0, 4);

        game.take(game.getCurrentPlayerId(), 0, 0, 3);

        for ( BoardInterface board : boards ) {
            System.out.println(board.state());
        }
        System.out.println(tableArea.state());
        c++;

        System.out.println( c + ". round: \n" + tableArea.state() );

        game.take(game.getCurrentPlayerId(), 2, 3, 0);
        game.take(game.getCurrentPlayerId(), 3, 3, 0);

        game.take(game.getCurrentPlayerId(), 5, 1, 3);
        game.take(game.getCurrentPlayerId(), 4, 0, 3);

        game.take(game.getCurrentPlayerId(), 0, 5, 2);
        game.take(game.getCurrentPlayerId(), 0, 1, 1);

        game.take(game.getCurrentPlayerId(), 1, 0, 1);
        game.take(game.getCurrentPlayerId(), 0, 1, 4);

        game.take(game.getCurrentPlayerId(), 0, 0, 4);

        for ( BoardInterface board : boards ) {
            System.out.println(board.state());
        }
        System.out.println(tableArea.state());
        c++;


        System.out.println( c + ". round: \n" + tableArea.state() );

        game.take(game.getCurrentPlayerId(), 1, 0, 0);
        game.take(game.getCurrentPlayerId(), 5, 3, 0);

        game.take(game.getCurrentPlayerId(), 0, 4, 1);
        game.take(game.getCurrentPlayerId(), 4, 3, 3);

        for ( BoardInterface board : boards ) {
            System.out.println(board.state());
        }
        System.out.println(tableArea.state());
        c++;

    }


}

