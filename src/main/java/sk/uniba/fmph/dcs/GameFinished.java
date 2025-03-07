package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.GameFinishedInterface;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class GameFinished implements GameFinishedInterface {

    public FinishRoundResult gameFinished(List<List<Optional<Tile>>> wall){
        Horizontal horizontal = new Horizontal();
        if(horizontal.calculatePoints(wall) >= 2){
            return FinishRoundResult.GAME_FINISHED;
        }
        return FinishRoundResult.NORMAL;
    }
}