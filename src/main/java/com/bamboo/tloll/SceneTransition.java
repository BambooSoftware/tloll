package com.bamboo.tloll;

import com.bamboo.tloll.graphics.Unit;
import com.bamboo.tloll.graphics.structure.Tile;
import com.bamboo.tloll.graphics.structure.WorldMap;
import com.bamboo.tloll.graphics.structure.Scene;
import com.bamboo.tloll.graphics.structure.Link;

import com.bamboo.tloll.debug.Logger;
import com.bamboo.tloll.collision.CollisionDetector;

import java.util.List;

public final class SceneTransition {

    public static void checkForTransition(Unit player) {
        // We will check the list of occupied tiles for any stairs.
        List<Tile> occupiedTiles = CollisionDetector.getInstance().getOccupiedTiles(player);
        for (Tile tile : occupiedTiles) {
            if (tile.getBufferId().equals("stairs")) {
                System.out.println("We are going to transition");
                handleTransition(player, tile);
            }
        }
    }

    public static void handleTransition(Unit player, Tile tile) {
        // In the event stairs exist we will transition and set the current scene to the new scene.
        Link exitLink = WorldMap.getInstance().getCurrentScene().getLinks().get(tile.getTileId());
        Scene transitionedScene = WorldMap.getInstance().getSceneMap().get(exitLink.getSceneId());
        WorldMap.getInstance().setCurrentScene(transitionedScene);

        // Set the player's new position.
        player.setPosX(transitionedScene.getTileList().get(exitLink.getExitId()).getPosX());
        player.setPosY(transitionedScene.getTileList().get(exitLink.getExitId()).getPosY());
        player.setRelativeTileX(0.0f);
        player.setRelativeTileY(0.0f);
        player.setCenterX(player.getPosX() + (player.getWidth() / 2));
        player.setCenterY(player.getPosY() + (player.getHeight() / 2));
    }
}
