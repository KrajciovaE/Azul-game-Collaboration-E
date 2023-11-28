package sk.uniba.fmph.dcs;

import org.junit.Before;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.interfaces.*;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;

public class GameIntegration {
    private Game game;
    private Bag bag;
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
        bag = new Bag(usedTiles);

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
        // I am trying to simulate game, because i have a real bag, i have no idea what is

        int c = 1;
        boolean emptyBag = false;
        while (!game.isGameOver) {
            if (emptyBag) break;

            System.out.println( c + ". round: \n" + tableArea.state() );


            // Player 1 is trying to fill the first wall line.
            if (game.getCurrentPlayerId() == 1) {
                // Red tile is not in first wall line and factory 1 has it.
                if (!board1.getWall().get(0).state().contains("R") && factory1.tiles.contains(Tile.RED)) {
                    for (int i = 0; i < 4; i++) {
                        if (factory1.tiles.get(i) == Tile.RED) {
                            game.take(game.getCurrentPlayerId(), 1, i, 0);
                        }
                    }
                }
                // Blue tile is not in first wall line and factory 1 has it.
                else if (!board1.getWall().get(0).state().contains("B") && factory1.tiles.contains(Tile.BLUE)) {
                    for (int i = 0; i < 4; i++) {
                        if (factory1.tiles.get(i) == Tile.BLUE) {
                            game.take(game.getCurrentPlayerId(), 1, i, 0);
                        }
                    }
                }
                // Black tile is not in first wall line and factory 1 has it.
                else if (!board1.getWall().get(0).state().contains("L") && factory1.tiles.contains(Tile.BLACK)) {
                    for (int i = 0; i < 4; i++) {
                        if (factory1.tiles.get(i) == Tile.BLACK) {
                            game.take(game.getCurrentPlayerId(), 1, i, 0);
                        }
                    }
                }
                // Green tile is not in first wall line and factory 1 has it.
                else if (!board1.getWall().get(0).state().contains("G") && factory1.tiles.contains(Tile.GREEN)) {
                    for (int i = 0; i < 4; i++) {
                        if (factory1.tiles.get(i) == Tile.GREEN) {
                            game.take(game.getCurrentPlayerId(), 1, i, 0);
                        }
                    }
                }
                // Yellow tile is not in first wall line and factory 1 has it.
                else if (!board1.getWall().get(0).state().contains("I") && factory1.tiles.contains(Tile.YELLOW)) {
                    for (int i = 0; i < 4; i++) {
                        if (factory1.tiles.get(i) == Tile.YELLOW) {
                            game.take(game.getCurrentPlayerId(), 1, i, 0);
                        }
                    }
                }
                else game.take(game.getCurrentPlayerId(), 1, 0, 0);
            }


            // Player 2 is trying to do the same.
            if (game.getCurrentPlayerId() == 2) {
                // Red tile is not in first wall line and factory 2 has it.
                if (!board2.getWall().get(0).state().contains("R") && factory2.tiles.contains(Tile.RED)) {
                    for (int i = 0; i < 4; i++) {
                        if (factory2.tiles.get(i) == Tile.RED) {
                            game.take(game.getCurrentPlayerId(), 2, i, 0);
                        }
                    }
                }
                // Blue tile is not in first wall line and factory 2 has it.
                else if (!board2.getWall().get(0).state().contains("B") && factory2.tiles.contains(Tile.BLUE)) {
                    for (int i = 0; i < 4; i++) {
                        if (factory2.tiles.get(i) == Tile.BLUE) {
                            game.take(game.getCurrentPlayerId(), 2, i, 0);
                        }
                    }
                }
                // Black tile is not in first wall line and factory 2 has it.
                else if (!board2.getWall().get(0).state().contains("L") && factory2.tiles.contains(Tile.BLACK)) {
                    for (int i = 0; i < 4; i++) {
                        if (factory2.tiles.get(i) == Tile.BLACK) {
                            game.take(game.getCurrentPlayerId(), 2, i, 0);
                        }
                    }
                }
                // Green tile is not in first wall line and factory 2 has it.
                else if (!board2.getWall().get(0).state().contains("G") && factory2.tiles.contains(Tile.GREEN)) {
                    for (int i = 0; i < 4; i++) {
                        if (factory2.tiles.get(i) == Tile.GREEN) {
                            game.take(game.getCurrentPlayerId(), 2, i, 0);
                        }
                    }
                }
                // Yellow tile is not in first wall line and factory 2 has it.
                else if (!board2.getWall().get(0).state().contains("I") && factory2.tiles.contains(Tile.YELLOW)) {
                    for (int i = 0; i < 4; i++) {
                        if (factory2.tiles.get(i) == Tile.YELLOW) {
                            game.take(game.getCurrentPlayerId(), 2, i, 0);
                        }
                    }
                }
                else game.take(game.getCurrentPlayerId(), 2, 0, 0);
            }
            
            for ( BoardInterface board : boards ) {
                System.out.println(board.state());
            }
            c++;

            assertTrue(factory1.isEmpty());
            assertTrue(factory2.isEmpty());

            System.out.println( c + ". round: \n" + tableArea.state() );

            game.take(game.getCurrentPlayerId(), 3, 0, 1);
            game.take(game.getCurrentPlayerId(), 4, 0, 1);
            for ( BoardInterface board : boards ) {
                System.out.println(board.state());
            }
            c++;

            assertTrue(factory3.isEmpty());
            assertTrue(factory4.isEmpty());

            System.out.println( c + ". round: \n" + tableArea.state() );
            c++;

            game.take(game.getCurrentPlayerId(), 5, 0, 2);
            if (tileSources.get(0).state().contains("S")) {
                game.take(game.getCurrentPlayerId(), 0, 1, 2);
            } else game.take(game.getCurrentPlayerId(), 0, 0, 2);

            for ( BoardInterface board : boards ) {
                System.out.println(board.state());
            }

            assertTrue(factory5.isEmpty());

            System.out.println( c + ". round: \n" + tableArea.state() );
            c++;

            int index = 2;
            int both = 0;
            while (!tileSources.get(0).isEmpty() && !emptyBag) {
                if (bag.isEmpty()) {
                    emptyBag = true;
                    break;
                }

                if (tileSources.get(0).state().length() == 1 && tileSources.get(0).state().contains("S")) break;

                if (tileSources.get(0).state().contains("S")) {
                    game.take(game.getCurrentPlayerId(), 0, 1, index);
                }
                else game.take(game.getCurrentPlayerId(), 0, 0, index);
                both ++;
                if (both % 2 == 0) {
                    for ( BoardInterface board : boards ) {
                        System.out.println(board.state());
                    }
                    System.out.println( c + ". round: \n" + tableArea.state() );
                    c++;
                    index ++;
                    if (index == 5) index = 0;

                }
            }
            if (emptyBag) {
                System.out.println("Players are too dump, let's call it truce.");
            }

        }



    }

    public void fillFirstWall(int currentPlayer, Board board, Factory factory, int source) {
        // Red tile is not in first wall line and factory has it.
        if (!board.getWall().get(0).state().contains("R") && factory.tiles.contains(Tile.RED)) {
            for (int i = 0; i < 4; i++) {
                if (factory.tiles.get(i) == Tile.RED) {
                    game.take(currentPlayer, source, i, 0);
                }
            }
        }
        // Blue tile is not in first wall line and factory has it.
        else if (!board.getWall().get(0).state().contains("B") && factory.tiles.contains(Tile.BLUE)) {
            for (int i = 0; i < 4; i++) {
                if (factory.tiles.get(i) == Tile.BLUE) {
                    game.take(currentPlayer, source, i, 0);
                }
            }
        }
        // Black tile is not in first wall line and factory has it.
        else if (!board.getWall().get(0).state().contains("L") && factory.tiles.contains(Tile.BLACK)) {
            for (int i = 0; i < 4; i++) {
                if (factory.tiles.get(i) == Tile.BLACK) {
                    game.take(currentPlayer, source, i, 0);
                }
            }
        }
        // Green tile is not in first wall line and factory has it.
        else if (!board.getWall().get(0).state().contains("G") && factory.tiles.contains(Tile.GREEN)) {
            for (int i = 0; i < 4; i++) {
                if (factory.tiles.get(i) == Tile.GREEN) {
                    game.take(currentPlayer, source, i, 0);
                }
            }
        }
        // Yellow tile is not in first wall line and factory 1 has it.
        else if (!board1.getWall().get(0).state().contains("I") && factory.tiles.contains(Tile.YELLOW)) {
            for (int i = 0; i < 4; i++) {
                if (factory.tiles.get(i) == Tile.YELLOW) {
                    game.take(currentPlayer, source, i, 0);
                }
            }
        }
        else game.take(currentPlayer, source, 0, 0);
    }

}
