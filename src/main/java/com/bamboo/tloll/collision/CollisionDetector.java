package com.bamboo.tloll.collision;

import com.bamboo.tloll.graphics.Unit;
import com.bamboo.tloll.graphics.structure.Tile;
import com.bamboo.tloll.graphics.structure.Scene;
import com.bamboo.tloll.graphics.structure.Obstacle;
import com.bamboo.tloll.graphics.structure.WorldMap;
import com.bamboo.tloll.constants.Constants;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class CollisionDetector
{

    private static CollisionDetector _instance;

    public static CollisionDetector getInstance() {
        if (_instance == null) {
            _instance = new CollisionDetector();
        }
        return _instance;
    }

    private CollisionDetector() {
    }
    
    public List<Tile> getOccupiedTiles(Unit player)
    {
	return getOccupiedTiles(player, 0.0f, 0.0f);
    }

    public List<Tile> getOccupiedTiles(Unit player, float deltaX, float deltaY)
    {
        WorldMap wm = WorldMap.getInstance();

        Map<Integer, Tile> occupiedTiles = new HashMap<>();

        int playerBottomLeftX = (int) ((player.getPosX() + deltaX) / Constants.TILE_WIDTH);
        int playerBottomLeftY = (int) ((player.getPosY() + deltaY) / Constants.TILE_HEIGHT);
        int tileId = playerBottomLeftX * 8 + playerBottomLeftY;
        occupiedTiles.put(tileId, wm.getCurrentScene().getTileList().get(tileId));

        int playerBottomRightX = (int) ((player.getPosX() + player.getWidth() + deltaX) / Constants.TILE_WIDTH);
        int playerBottomRightY = (int) ((player.getPosY() + deltaY) / Constants.TILE_HEIGHT);
        tileId = playerBottomRightX * 8 + playerBottomRightY;
        occupiedTiles.put(tileId, wm.getCurrentScene().getTileList().get(tileId));

        int playerUpperLeftX = (int) ((player.getPosX() + deltaX) / Constants.TILE_WIDTH);
        int playerUpperLeftY = (int) ((player.getPosY() + player.getHeight() + deltaY) / Constants.TILE_HEIGHT);
        tileId = playerUpperLeftX * 8 + playerUpperLeftY;
        occupiedTiles.put(tileId, wm.getCurrentScene().getTileList().get(tileId));

        int playerUpperRightX = (int) ((player.getPosX() + player.getWidth() + deltaX) / Constants.TILE_WIDTH);
        int playerUpperRightY = (int) ((player.getPosY() + player.getHeight() + deltaY) / Constants.TILE_HEIGHT);
        tileId = playerUpperRightX * 8 + playerUpperRightY;
        occupiedTiles.put(tileId, wm.getCurrentScene().getTileList().get(tileId));

        return new ArrayList<>(occupiedTiles.values());
    }
    
    // NOTE(map) : We are subtracting the Z position because it's a mo best way to hack the PhysicsEngine for now.
    // Could probably be a function instead that is more self documenting in the future and maybe less hacky if
    // we decide we want something like that on the overworld screen.
    public boolean canCrossObstacle(Unit player, float deltaX, float deltaY) {
	Scene currentScene = WorldMap.getInstance().getCurrentScene();
	for (Obstacle obstacle : currentScene.getObstacleList()) {
	    if ((player.getPosX() + deltaX) >= obstacle.getPosX() &&
		(player.getPosX() + deltaX) <= (obstacle.getPosX() + obstacle.getWidth()) &&
		(player.getPosY() + deltaY - player.getPosZ()) >= obstacle.getPosY() &&
		(player.getPosY() + deltaY - player.getPosZ()) <= (obstacle.getPosY() + obstacle.getHeight()) &&
		(player.getPosZ()) < obstacle.getProtrusionHeight()) {
		return false;
	    }
	}
	    
	return true;
    }
    
    public boolean isTilePassableX(Unit player, float deltaX)
    {
	List<Tile> futureOccupiedTiles = getOccupiedTiles(player, deltaX, 0.0f);
	for (Tile tile : futureOccupiedTiles)
	    {
		if (!tile.isPassable())
		    {
			return false;
		    }
	    }
	// TODO(map) : There may be an inherent risk of default returning true that needs to be discussed.
	return true;
    }

    public boolean isTilePassableY(Unit player, float deltaY)
    {
	List<Tile> futureOccupiedTiles = getOccupiedTiles(player, 0.0f, deltaY);
	for (Tile tile : futureOccupiedTiles)
	    {
		if (!tile.isPassable())
		    {
			return false;
		    }
	    }
	// TODO(map) : There may be an inherent risk of default returning true that needs to be discussed.
	return true;
    }

}
