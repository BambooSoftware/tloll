package com.bamboo.tloll.collision;

import com.bamboo.tloll.graphics.Unit;
import com.bamboo.tloll.graphics.structure.Tile;
import com.bamboo.tloll.graphics.structure.WorldMap;

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
        WorldMap wm = WorldMap.getInstance();

        Map<Integer, Tile> occupiedTiles = new HashMap<>();

        int playerBottomLeftX = (int) (player.getPosX() / 80);
        int playerBottomLeftY = (int) (player.getPosY() / 80);
        int tileId = playerBottomLeftX * 8 + playerBottomLeftY;
        occupiedTiles.put(tileId, wm.getCurrentScene().getTileList().get(tileId));

        int playerBottomRightX = (int) ((player.getPosX() + player.getWidth()) / 80);
        int playerBottomRightY = (int) (player.getPosY() / 80);
        tileId = playerBottomRightX * 8 + playerBottomRightY;
        occupiedTiles.put(tileId, wm.getCurrentScene().getTileList().get(tileId));

        int playerUpperLeftX = (int) (player.getPosX() / 80);
        int playerUpperLeftY = (int) ((player.getPosY() + player.getHeight()) / 80);
        tileId = playerUpperLeftX * 8 + playerUpperLeftY;
        occupiedTiles.put(tileId, wm.getCurrentScene().getTileList().get(tileId));

        int playerUpperRightX = (int) ((player.getPosX() + player.getWidth()) / 80);
        int playerUpperRightY = (int) ((player.getPosY() + player.getHeight()) / 80);
        tileId = playerUpperRightX * 8 + playerUpperRightY;
        occupiedTiles.put(tileId, wm.getCurrentScene().getTileList().get(tileId));

        return new ArrayList<>(occupiedTiles.values());
    }

    public List<Tile> getOccupiedTiles(Unit player, float deltaX, float deltaY)
    {
        WorldMap wm = WorldMap.getInstance();

        Map<Integer, Tile> occupiedTiles = new HashMap<>();

        int playerBottomLeftX = (int) ((player.getPosX() + deltaX) / 80);
        int playerBottomLeftY = (int) ((player.getPosY() + deltaY) / 80);
        int tileId = playerBottomLeftX * 8 + playerBottomLeftY;
        occupiedTiles.put(tileId, wm.getCurrentScene().getTileList().get(tileId));

        int playerBottomRightX = (int) ((player.getPosX() + player.getWidth() + deltaX) / 80);
        int playerBottomRightY = (int) ((player.getPosY() + deltaY) / 80);
        tileId = playerBottomRightX * 8 + playerBottomRightY;
        occupiedTiles.put(tileId, wm.getCurrentScene().getTileList().get(tileId));

        int playerUpperLeftX = (int) ((player.getPosX() + deltaX) / 80);
        int playerUpperLeftY = (int) ((player.getPosY() + player.getHeight() + deltaY) / 80);
        tileId = playerUpperLeftX * 8 + playerUpperLeftY;
        occupiedTiles.put(tileId, wm.getCurrentScene().getTileList().get(tileId));

        int playerUpperRightX = (int) ((player.getPosX() + player.getWidth() + deltaX) / 80);
        int playerUpperRightY = (int) ((player.getPosY() + player.getHeight() + deltaY) / 80);
        tileId = playerUpperRightX * 8 + playerUpperRightY;
        occupiedTiles.put(tileId, wm.getCurrentScene().getTileList().get(tileId));

        return new ArrayList<>(occupiedTiles.values());
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
