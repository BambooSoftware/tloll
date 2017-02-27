package com.bamboo.tloll.graphics.structure;

import com.bamboo.tloll.graphics.Position;
import com.bamboo.tloll.util.Utilities;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bamboo.tloll.graphics.Unit;
import com.bamboo.tloll.graphics.MapCreator;

/**
 * This class represents pieces of the world map. The part of the screen the player will interact with. It consists of a
 * set of tiles which are individual squares which may or may not provide free movement. Building a map piece is rather
 * painful but using a built one should be quite fast.
 *
 * @author ablackbu
 */
public class Scene {

    int sceneId;

    Map<Integer, Link> links;
    ImmutableList<Tile> tiles;
    ImmutableMap<Position, Boolean> blockIndex;

    // TODO(map) : Temp list of tiles so we can add dynamically.
    List<Tile> tilesTemp = new ArrayList<Tile>();

    private List<Unit> enemyList;

    public Scene() {
        buildBlockIndex();
    }

    // 1 = Lower Left
    // 2 = Lower Right
    // 3 = Upper Left
    // 4 = Upper Right
    // 5 = Straight Left/Right
    // 6 = Straight Up/Down
    /*public Scene(int sceneId, int typeOfScene) {
        this.sceneId = sceneId;

        switch (typeOfScene) {
        case 1:
            tiles = ImmutableList.copyOf(MapCreator.createSampleMapLowerLeft());
            break;
        case 2:
            tiles = ImmutableList.copyOf(MapCreator.createSampleMapLowerRight());
            break;
        case 3:
            tiles = ImmutableList.copyOf(MapCreator.createSampleMapUpperLeft());
            break;
        case 4:
            tiles = ImmutableList.copyOf(MapCreator.createSampleMapUpperRight());
            break;
        case 5:
            tiles = ImmutableList.copyOf(MapCreator.createSampleMapLeftRight());
            break;
        case 6:
            tiles = ImmutableList.copyOf(MapCreator.createSampleMapTopDown());
            break;
        }
    }*/

    // TODO(map) : Maybe we need a class to take in a list of tiles now instead of some arbitrary
    // number???
    public Scene(int sceneId, List<Tile> tiles, Map<Integer, Link> links) {
        this.sceneId = sceneId;
        this.tiles = ImmutableList.copyOf(tiles);
        this.links = ImmutableMap.copyOf(links);
    }

    /**
     * Builds a blocker map index. This allows us to provide a destination position and quickly accept or reject the
     * movement.
     */
    private void buildBlockIndex() {
        Map<Position, Boolean> tempBlockIndex = new HashMap<>();

        for (Tile tile : tiles) {
            if (!tile.isPassable()) {

                for (int x = Utilities.truncateToInt(tile.getPosX()); x <= (tile.getPosX() + tile.getWidth()); ++x) {
                    for (int y = Utilities.truncateToInt(tile.getPosY()); y <= (tile.getPosY()
                            + tile.getHeight()); ++y) {
                        tempBlockIndex.put(new Position(x, y), Boolean.TRUE);
                    }
                }

            }
        }

        blockIndex = ImmutableMap.copyOf(tempBlockIndex);
    }

    public boolean isMovementAllowed(Position source, Position destination) {
        return blockIndex.get(destination) == null;
    }

    public List<Tile> getTileList() {
        return tiles;
    }

    public List<Unit> getEnemyList() {
        return enemyList;
    }

    public void setEnemyList(List<Unit> enemyList) {
        this.enemyList = enemyList;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public Map<Integer, Link> getLinks() {
        return links;
    }

}
