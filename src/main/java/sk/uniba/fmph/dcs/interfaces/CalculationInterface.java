package sk.uniba.fmph.dcs.interfaces;

import sk.uniba.fmph.dcs.Tile;

import java.util.List;
import java.util.Optional;

public interface CalculationInterface {
    int calculate(List<List<Optional<Tile>>> wall);
}