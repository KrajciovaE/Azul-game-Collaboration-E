package sk.uniba.fmph.dcs.interfaces;

import sk.uniba.fmph.dcs.Points;
import sk.uniba.fmph.dcs.Tile;

import java.util.List;
import java.util.Optional;

public interface FinalPointsCalculationInterface {
    Points getPoints(List<List<Optional<Tile>>> wall);
}
