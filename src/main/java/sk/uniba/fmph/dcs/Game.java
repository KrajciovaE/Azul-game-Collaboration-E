package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.*;

import java.util.List;
import java.util.Random;

public class Game implements GameInterface {
    private final TableAreaInterface tableArea;
    private final List<BoardInterface> allBoards;
    private final ObserverInterface gameObserver;
    private final int playerCount;
    private int currentPlayerId;
    private int startingPlayerId;
    boolean isGameOver;

    public Game(TableAreaInterface tableArea, List<BoardInterface> allBoards, ObserverInterface gameObserver, int playerCount) {

        this.tableArea = tableArea;
        this.allBoards = allBoards;
        this.gameObserver = gameObserver;
        this.playerCount = playerCount;
        this.isGameOver = false;
        //Random random = new Random();
        //startingPlayerId = random.nextInt(this.playerCount);
        startingPlayerId = 0;       // For testing purposes.
        currentPlayerId = startingPlayerId;
        this.tableArea.startNewRound();
        gameObserver.notify("Game started");
        gameObserver.notify("Player " + startingPlayerId + " starts");
    }

    @Override
    public boolean take(int playerId, int sourceId, int idx, int destinationIdx) {

        if ( isGameOver ) {
            gameObserver.notify("Game is over, no more taking.");
            return false;
        }
        if ( playerId != currentPlayerId ) return false;
        List<Tile> tiles = tableArea.take(sourceId, idx);
        if ( tiles.isEmpty() ) return false;
        if(tiles.contains(Tile.STARTING_PLAYER)) startingPlayerId = currentPlayerId;

        allBoards.get(playerId).put(destinationIdx, tiles);
        gameObserver.notify(allBoards.get(playerId).state());
        if ( tableArea.isRoundEnd() ) {
            handleRoundEnd();
            if ( isGameOver ) return finish();
            gameObserver.notify("Player " + startingPlayerId + " starts");
        } else {
            currentPlayerId = ( currentPlayerId + 1 ) % playerCount;
            gameObserver.notify("Player " + currentPlayerId + " has a turn.");
        }
        return true;
    }

    private void handleRoundEnd() {

        FinishRoundResult result = FinishRoundResult.NORMAL;

        for ( BoardInterface board : allBoards) {
            if (board.finishRound() == FinishRoundResult.GAME_FINISHED ) {
                result = FinishRoundResult.GAME_FINISHED;
            }
        }

        if ( result == FinishRoundResult.GAME_FINISHED ) {
            for ( BoardInterface board : allBoards) {
                board.endGame();
            }
            isGameOver = true;
            gameObserver.notify("Game is finished.");
        } else {
            gameObserver.notify("Round is finished.");
            tableArea.startNewRound();
        }
    }

    private boolean finish() {
        int maxPoints = 0;
        int winnerPlayerId = 0;
        for (int i = 0; i < playerCount; i++)
        {
            int points = allBoards.get(i).getPoints().getValue();
            gameObserver.notify("Player " + i  + " finished with " + points + " points.");
            if ( points > maxPoints ) {
                maxPoints = points;
                winnerPlayerId = i;
            }
        }
        gameObserver.notify("Player " + winnerPlayerId + " won with " + maxPoints + " points.");
        return true;
    }

    public int getCurrentPlayerId() {
        return this.currentPlayerId;
    }
}
