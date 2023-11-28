package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.WallLineInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WallLine implements WallLineInterface {
    private final ArrayList<Tile> tileTypes;
    public WallLineInterface lineUp;
    public WallLineInterface lineDown;

    private final boolean[] occupiedTiles;


    public WallLine(List<Tile> tileTypes, WallLineInterface lineUp, WallLineInterface lineDown) {
        this.tileTypes = new ArrayList<>(tileTypes);
        this.lineUp = lineUp;
        this.lineDown = lineDown;

        this.occupiedTiles = new boolean[tileTypes.size()];
    }

    @Override
    public void setLineUp(WallLineInterface lineUp) {
        this.lineUp = lineUp;
    }

    @Override
    public void setLineDown(WallLineInterface lineDown) {
        this.lineDown = lineDown;
    }

    @Override
    public boolean canPutTile(Tile tile) {
        return tileTypes.contains(tile) && !occupiedTiles[tileTypes.indexOf(tile)];
    }

    @Override
    public List<Optional<Tile>> getTiles() {
        ArrayList<Optional<Tile>> tiles = new ArrayList<>();
        for (int i = 0; i < tileTypes.size(); i++) {
            if (occupiedTiles[i]) {
                tiles.add(Optional.ofNullable(this.tileTypes.get(i)));
            } else {
                Tile t = null;
                tiles.add(Optional.ofNullable(t));
            }
        }
        return tiles;
    }

    @Override
    public Points putTile(Tile tile) {
        if (canPutTile(tile)) {
            int idx = tileTypes.indexOf(tile);
            this.occupiedTiles[idx] = true;

            int points = 1;

            int offset = 1;
            while (offset + idx < tileTypes.size()) {
                if (occupiedTiles[offset + idx]) {
                    points++;
                    offset++;
                } else {
                    break;
                }
            }

            offset = 1;
            while (idx - offset >= 0) {
                if (occupiedTiles[idx - offset]) {
                    points++;
                    offset++;
                } else {
                    break;
                }
            }
            WallLineInterface current = this;
            while (((WallLine) current).lineUp != null) {
                if (((WallLine) current).lineUp.getTiles().get(idx).isPresent()) {
                    points++;
                    current = ((WallLine) current).lineUp;
                } else {
                    break;
                }
            }

            current = this;
            while (((WallLine) current).lineDown != null) {
                if (((WallLine) current).lineDown.getTiles().get(idx).isPresent()) {
                    points++;
                    current = ((WallLine) current).lineDown;
                } else {
                    break;
                }
            }

            return new Points(points);

        }
        return new Points(0);
    }

    @Override
    public String state() {
        String toReturn = "";
        for (int i = 0; i < tileTypes.size(); i++) {
            if (occupiedTiles[i]) {
                toReturn += tileTypes.get(i).toString();
            }else{
                toReturn += ".";
            }
        }
        return toReturn;
    }
}