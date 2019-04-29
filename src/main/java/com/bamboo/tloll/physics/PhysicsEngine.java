package com.bamboo.tloll.physics;

import com.bamboo.tloll.collision.CollisionDetector;
import com.bamboo.tloll.constants.Constants;
import com.bamboo.tloll.graphics.Unit;

public class PhysicsEngine {

    private static PhysicsEngine _instance;

    private PhysicsEngine() {
    }

    public static PhysicsEngine getInstance() {
        if(_instance == null) {
            _instance = new PhysicsEngine();
        }
        return _instance;
    }

    public void movePlayer(Unit player) {
	Vector3 movement = normalizeMovement(player);

        float deltaX = movement.getXComponent();
        float deltaY = movement.getYComponent();
	float deltaZ = movement.getZComponent();

	if (!isOutOfBoundsX(player, deltaX) &&
	    CollisionDetector.getInstance().isTilePassableX(player, deltaX) &&
	    CollisionDetector.getInstance().canCrossObstacle(player, deltaX, deltaY)) {
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

        if (!isOutOfBoundsY(player, deltaY) &&
	    CollisionDetector.getInstance().isTilePassableY(player, deltaY) &&
	    CollisionDetector.getInstance().canCrossObstacle(player, deltaX, deltaY)) {
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

    public void jumpPlayer(Unit player)
    {
	float deltaX = player.getUnitVector().getXComponent();
        float deltaY = player.getUnitVector().getYComponent();
	float deltaZ = player.getUnitVector().getZComponent();

	// TODO(map) : At least for now we don't care about out of bounds with Z.
	// This should however work without glitching the player movement like it currently does.
	if (player.isJumping())
	    {
		player.setPosY(player.getPosY() + deltaY);
		player.setPosZ(player.getPosZ() + deltaZ);
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

    private Vector3 normalizeMovement(Unit unit) {
        long currentTime = System.currentTimeMillis();
        long deltaTime  =  currentTime - unit.getLastMoved();
        unit.setLastMoved(currentTime);

        float secondsPerFrame = (float) deltaTime/1000.0f;
        float movementValue = unit.getMaxSpeed() * secondsPerFrame;

        float deltaX = unit.getUnitVector().getXComponent() * movementValue;
        float deltaY = unit.getUnitVector().getYComponent() * movementValue;
        float deltaZ = unit.getUnitVector().getZComponent() * movementValue;

        return new Vector3(deltaX, deltaY, deltaZ);
    }

}
