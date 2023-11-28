package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.TableAreaInterface;
import sk.uniba.fmph.dcs.interfaces.TileSourceInterface;

import java.util.ArrayList;
import java.util.List;

public class TableArea implements TableAreaInterface {
    List<TileSourceInterface> tileSources;

    public TableArea(ArrayList<TileSourceInterface> tileSources) {
        this.tileSources = tileSources;
    }

    @Override
    public List<Tile> take(int sourceIdx, int idx) {
        return tileSources.get(sourceIdx).take(idx);
    }

    @Override
    public boolean isRoundEnd() {
        for (TileSourceInterface tileSource : tileSources) {
            if (!tileSource.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void startNewRound() {
        for (TileSourceInterface tileSource : tileSources) {
            tileSource.startNewRound();
        }

    }

    @Override
    public String state() {
        String toReturn = "";
        int counter = 0;
        for (final TileSourceInterface tileSource : tileSources) {
            if (counter == 0) toReturn += "Table center: ";
            else toReturn += "Factory " + counter + ": ";
            toReturn += tileSource.state() + "\n";
            counter++;
        }
        return toReturn;
    }
}
