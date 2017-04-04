package com.bamboo.tloll.physics;

import com.bamboo.tloll.constants.Constants;
import com.bamboo.tloll.graphics.Unit;
import com.bamboo.tloll.graphics.structure.Scene;
import com.bamboo.tloll.graphics.structure.Tile;
import com.bamboo.tloll.graphics.structure.WorldMap;

import com.bamboo.tloll.collision.CollisionDetector;

public class PhysicsEngine {


    private static PhysicsEngine _instance;

    // TODO(map) : Make me a singleton???
    private PhysicsEngine() {
    }

    public static PhysicsEngine getInstance() {
        if(_instance == null) {
            _instance = new PhysicsEngine();
        }
        return _instance;
    }

    public void movePlayer(Unit player) {

        float deltaX = player.getUnitVector().getXComponent();
        float deltaY = player.getUnitVector().getYComponent();
        Scene currentScene = WorldMap.getInstance().getCurrentScene();

        if (!isOutOfBoundsX(player, deltaX) && CollisionDetector.getInstance().isTilePassableX(player, deltaX)) {
            player.setPosX(player.getPosX() + deltaX);
	    player.setCenterX(player.getCenterX() + deltaX);
            if (moveInTileX(player, deltaX)) {
                player.setRelativeTileX(player.getRelativeTileX() + deltaX);
            } else {
                if (deltaX < 0.0) {
                    float newRelativeX = 64.0f - ((player.getRelativeTileX() + deltaX) * -1);
                    player.setRelativeTileX(newRelativeX);
                } else if (deltaX > 0.0) {
                    float newRelativeX = deltaX - (64.0f - player.getRelativeTileX());
                    player.setRelativeTileX(newRelativeX);
                }
            }
        }

        if (!isOutOfBoundsY(player, deltaY) && CollisionDetector.getInstance().isTilePassableY(player, deltaY)) {
            player.setPosY(player.getPosY() + deltaY);
	    player.setCenterY(player.getCenterY() + deltaY);
            if (moveInTileY(player, deltaY)) {
                player.setRelativeTileY(player.getRelativeTileY() + deltaY);
            } else {
                if (deltaY < 0.0) {
                    float newRelativeY = 64.0f - ((player.getRelativeTileY() + deltaY) * -1);
                    player.setRelativeTileY(newRelativeY);
                } else if (deltaY > 0.0) {
                    float newRelativeY = deltaY - (64.0f - player.getRelativeTileY());
                    player.setRelativeTileY(newRelativeY);
                }
            }

        }

    }

    // Bind the player to the X dimension of the board.
    public boolean isOutOfBoundsX(Unit player, float deltaX) {
        if (player.getCenterX() + player.getWidth() / 2 + deltaX > Constants.WIDTH) {
            player.setOutOfBoundsRight(true);
            return true;
        } else if (player.getCenterX() - player.getWidth() / 2 + deltaX < 0) {
            player.setOutOfBoundsLeft(true);
            return true;
        }
        player.setOutOfBoundsRight(false);
        player.setOutOfBoundsLeft(false);
        return false;
    }

    // Bind the player to the Y dimension of the board.
    public boolean isOutOfBoundsY(Unit player, float deltaY) {
        if (player.getCenterY() + player.getHeight() / 4 + deltaY > Constants.HEIGHT) {
            player.setOutOfBoundsUp(true);
            return true;
        } else if (player.getCenterY() - player.getHeight() / 2 + deltaY < 0) {
            player.setOutOfBoundsDown(true);
            return true;
        }
        player.setOutOfBoundsUp(false);
        player.setOutOfBoundsDown(false);
        return false;
    }

    public boolean moveInTileX(Unit player, float deltaX) {
        if (player.getRelativeTileX() + deltaX < Constants.TILE_WIDTH && player.getRelativeTileX() + deltaX > 0.0) {
            return true;
        }
        return false;
    }

    public boolean moveInTileY(Unit player, float deltaY) {
        if (player.getRelativeTileY() + deltaY < Constants.TILE_HEIGHT && player.getRelativeTileY() + deltaY > 0.0) {
            return true;
        }
        return false;
    }

}
