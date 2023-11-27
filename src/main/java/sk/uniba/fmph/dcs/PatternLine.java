package sk.uniba.fmph.dcs;

import sk.uniba.fmph.dcs.interfaces.FloorInterface;
import sk.uniba.fmph.dcs.interfaces.PatternLineInterface;
import sk.uniba.fmph.dcs.interfaces.UsedTilesGiveInterface;
import sk.uniba.fmph.dcs.interfaces.WallLineInterface;

import java.util.ArrayList;
import java.util.List;

public class PatternLine implements PatternLineInterface {
    private int capacity;
    private List<Tile> tiles = new ArrayList<>();
    private Floor floor;
    private final UsedTilesGiveInterface usedTiles;
    private final WallLineInterface wallLine;
    public PatternLine(int capacity, Floor floor, UsedTilesGiveInterface usedTiles, WallLineInterface wallLine){
        this.capacity = capacity;
        this.floor = floor;
        this.usedTiles = usedTiles;
        this.wallLine = wallLine;
    }
    @Override
    public void put(List<Tile> tiles) {
        // If different color put to floor.
        if (!this.tiles.isEmpty() && this.tiles.get(0) != tiles.get(0)){
            floor.put(tiles);
            return;
        }

        if (tiles.size() + this.tiles.size() <= capacity){
            this.tiles.addAll(tiles);
        } else{
            int s = this.tiles.size();
            for (int i = 0; i < capacity - s; i++){
                this.tiles.add(tiles.get(i));
            }
            List<Tile> toFloor = new ArrayList<>();
            for (int i = capacity - s; i < tiles.size(); i++){
                toFloor.add(tiles.get(i));
            }
            floor.put(toFloor);
        }

    }

    @Override
    public Points finishRound() {
        if (this.tiles.size() == capacity){

            Tile result = tiles.get(0);
            if (wallLine.canPutTile(result)){

                usedTiles.give(tiles);
                this.tiles.clear();
                return wallLine.putTile(result);
            }
        }
        return new Points(0);
    }

    @Override
    public String state() {
        StringBuilder result = new StringBuilder();
        for (Tile tile : tiles) {
            result.append(tile);
        }
        for (int i = 0; i < capacity - tiles.size(); i++) {
            result.append(".");
        }
        return result.toString();
    }
}
