package com.bamboo.tloll.debug;

import com.bamboo.tloll.Constants;
import com.bamboo.tloll.graphics.GraphicsUtil;
import com.bamboo.tloll.graphics.Renderer;
import com.bamboo.tloll.graphics.SpriteBuffer;
import com.bamboo.tloll.graphics.Unit;
import com.bamboo.tloll.graphics.structure.Scene;
import com.bamboo.tloll.graphics.structure.Tile;
import com.bamboo.tloll.graphics.structure.WorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Logger {

    String filePath = "";
    String level = "";

    public Logger() {
        this.level = "INFO";
    }

    public Logger(String level) {
        this.level = level;
    }

    public Logger(String level, String filePath) {
        this.level = level;
        this.filePath = filePath;
    }

    public void writeToFile() {
        // Method that will write out to a file if we ever needed it.
    }

    public void printDebugInfo(String message) {
        switch (this.level) {
            case "INFO":
                System.out.println("INFO: " + message);
                break;
            case "DEBUG":
                System.out.println("DEBUG: " + message);
                break;
            case "ERROR":
                System.out.println("ERROR: " + message);
                break;
        }
    }

    public void displayPlayerInfo(Unit player) {
        GraphicsUtil gu = GraphicsUtil.getInstance();


        printToWindow(gu, "Player Pos X " + player.getPosX(), 0.0f, 470.0f, false);
        printToWindow(gu, "Player Pos Y " + player.getPosY(), 0.0f, 450.0f, false);
        printToWindow(gu, "Player Center Coords", 0.0f, 430.0f, false);
        printToWindow(gu, "Player Center Coords", 0.0f, 430.0f, false);
        printToWindow(gu, "" + player.getCenterX() + " " + player.getCenterY(), 0.0f, 410.0f, false);
        printToWindow(gu, "Occupied TIle IDs", 0.0f, 390.0f, false);

    }

    public void displayOccupiedTiles(Unit player) {
        GraphicsUtil gu = GraphicsUtil.getInstance();
	
        float yPosForTileInfo = 370.0f;
        for (Tile tile : getOccupiedTiles(player)) {
            printToWindow(gu, "" + tile.getTileId(), 0.0f, yPosForTileInfo, false);
            yPosForTileInfo -= 20.0f;
        }

    }


    // Debug method for highlighting the current tile the player lives in.
    // NOTE(map) : This covers any tiles the player can reside in.
    public void highlightCurrentTile(Unit player) {
        GraphicsUtil gu = GraphicsUtil.getInstance();

	List<Tile> highlightList = getOccupiedTiles(player);

	for (Tile tile : highlightList)
	    {
		Tile highlightTile = new Tile(tile.getPosX(), tile.getPosY(), tile.getWidth(), tile.getHeight(), tile.isPassable(), tile.getBufferId(), tile.getTileId(), false);
		highlightTile.addBufferToMap(0, new SpriteBuffer(gu.loadTexture(Constants.USER_DIR + "/Assets/Images/highlight.png"), highlightTile.getHeight(), highlightTile.getWidth()));
		Renderer.drawSprite(highlightTile, 0);
	    }
    }

    private List<Tile> getOccupiedTiles(Unit player) {
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

    public void printToWindow(GraphicsUtil gu, String message, float posX, float posY, boolean leftToRight) {
        Renderer.drawString(gu, Constants.USER_DIR, posX, posY, message, leftToRight);
    }

}
