package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.*;

import java.util.List;
import java.util.Random;

public class Game implements GameInterface {
    private List<BoardInterface> allBoards;
    private TableAreaInterface tableArea;
    private List<BoardInterface> playerBoards;
    private int currentPlayerId;
    private int playerCount;
    private ObserverInterface gameObserver;
    boolean isGameOver;

    public Game(List<BoardInterface> allBoards,  TableAreaInterface tableArea, int playerCount, ObserverInterface gameObserver) {

        this.allBoards = allBoards;
        this.tableArea = tableArea;
        this.playerCount = playerCount;
        this.gameObserver = gameObserver;
        this.isGameOver = false;
        Random random = new Random();
        currentPlayerId = random.nextInt(this.playerCount);
        gameObserver.notify("Game started");
        gameObserver.notify("Player " + currentPlayerId + " starts");
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

        allBoards.get(playerId).put(destinationIdx, tiles);
        if ( tableArea.isRoundEnd() ) {
            handleRoundEnd();
            if ( isGameOver ) return finish();
        }
        currentPlayerId = ( currentPlayerId + 1 ) % playerCount;
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
            gameObserver.notify("Player " + i + " finished with " + points + " points.");
            if ( points > maxPoints ) {
                maxPoints = points;
                winnerPlayerId = i;
            }
        }
        gameObserver.notify("Player " + winnerPlayerId + " won with " + maxPoints + " points.");
        return true;
    }
}
