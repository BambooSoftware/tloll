package com.bamboo.tloll.graphics.structure;

import com.bamboo.tloll.graphics.Position;
import com.bamboo.tloll.util.Utilities;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents pieces of the world map. The part of the screen the player will interact with. It consists of a
 * set of tiles which are individual squares which may or may not provide free movement. Building a map piece is rather
 * painful but using a built one should be quite fast.
 *
 * @author ablackbu
 */
public class MapPiece {

    ImmutableList<Tile> tiles;
    ImmutableMap<Position, Boolean> blockIndex;

    public MapPiece() {
        buildTiles();
        buildBlockIndex();
    }

    private void buildTiles() {
        List<Tile> tempTiles = new ArrayList<>();
        //TODO:BUILD TILES How will we be building these ?
        tiles = ImmutableList.copyOf(tempTiles);
    }

    /**
     * Builds a blocker map index. This allows us to provide a destination position and quickly accept or reject the
     * movement.
     */
    private void buildBlockIndex() {
        Map<Position, Boolean> tempBlockIndex = new HashMap<>();

        for(Tile tile: tiles) {
            if(!tile.isPassable()) {

                for(int x = Utilities.truncateToInt(tile.getPosX()); x <= (tile.getPosX()+tile.getWidth()); ++x) {
                    for(int y = Utilities.truncateToInt(tile.getPosY()); y <= (tile.getPosY()+tile.getHeight()); ++y) {
                        tempBlockIndex.put(new Position(x, y) ,Boolean.TRUE);
                    }
                }

            }
        }

        blockIndex = ImmutableMap.copyOf(tempBlockIndex);
    }

    public boolean isMovementAllowed(Position source, Position destination) {
        return blockIndex.get(destination) == null;
    }

}