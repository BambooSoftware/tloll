package com.bamboo.tloll.debug;

import com.bamboo.tloll.constants.Constants;
import com.bamboo.tloll.graphics.GraphicsUtil;
import com.bamboo.tloll.graphics.Renderer;
import com.bamboo.tloll.graphics.SpriteBuffer;
import com.bamboo.tloll.graphics.Unit;
import com.bamboo.tloll.graphics.structure.Tile;
import com.bamboo.tloll.graphics.structure.WorldMap;
import com.bamboo.tloll.graphics.Sprite;

import com.bamboo.tloll.collision.CollisionDetector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Logger {

    private static Logger _instance;
    private long fpsLastTime;

    // NOTE(map) : This will get moved out eventually once we decide a good place for text rendering.
    private Map<Character, Sprite> alphabetSprites;

    public static Logger getInstance() {
        if (_instance == null) {
            _instance = new Logger();
        }
        return _instance;
    }

    private Logger() {
        fpsLastTime = 0l;
	alphabetSprites = new HashMap<Character, Sprite>();
    }

    public void displayPlayerInfo(Unit player) {
        printToWindow("Player Pos X " + player.getPosX(), 0.0f, 470.0f);
        printToWindow("Player Pos Y " + player.getPosY(), 0.0f, 450.0f);
	printToWindow("Player Pos Z " + player.getPosZ(), 0.0f, 430.0f);
	printToWindow("Player V3 X " + player.getUnitVector().getXComponent(), 0.0f, 410.0f);
	printToWindow("Player V3 Y " + player.getUnitVector().getYComponent(), 0.0f, 390.0f);
	printToWindow("Player V3 Z " + player.getUnitVector().getZComponent(), 0.0f, 370.0f);
	printToWindow("Player Center Coords", 0.0f, 350.0f);
        printToWindow("" + player.getCenterX() + " " + player.getCenterY(), 0.0f, 330.0f);
        printToWindow("Occupied TIle IDs", 0.0f, 290.0f);
	
    }

    public void displayOccupiedTiles(Unit player) {
        GraphicsUtil gu = GraphicsUtil.getInstance();

        float yPosForTileInfo = 270.0f;
        for (Tile tile : CollisionDetector.getInstance().getOccupiedTiles(player)) {
            printToWindow("" + tile.getTileId(), 0.0f, yPosForTileInfo);
            yPosForTileInfo -= 20.0f;
        }

    }

    public void calculateAndDisplayFps() {
        long newTime = System.currentTimeMillis();
        long delta = newTime- fpsLastTime;
        fpsLastTime = newTime;
        long fps = 1000/delta;
        displayFps(fps);
    }

    private void displayFps(long fps) {
        GraphicsUtil gu = GraphicsUtil.getInstance();
        printToWindow("FPS: " + fps, 0.0f, 310.0f);
    }


    // Debug method for highlighting the current tile the player lives in.
    // NOTE(map) : This covers any tiles the player can reside in.
    public void highlightCurrentTile(Unit player) {
        GraphicsUtil gu = GraphicsUtil.getInstance();

        List<Tile> highlightList = CollisionDetector.getInstance().getOccupiedTiles(player);

        for (Tile tile : highlightList) {
            Tile highlightTile = new Tile(tile.getPosX(), tile.getPosY(), tile.getWidth(), tile.getHeight(), tile.isPassable(), tile.getBufferId(), tile.getTileId(), false);
            highlightTile.addBufferToMap(0, new SpriteBuffer(gu.loadTexture(Constants.USER_DIR + "/Assets/Images/highlight.png"), highlightTile.getHeight(), highlightTile.getWidth()));
            Renderer.drawSprite(highlightTile, 0);
        }
    }

    
    public void printToWindow(String message, float posX, float posY) {
        Renderer.drawString(posX, posY, message);
    }

    public void setAlphabetSprites(Map<Character, Sprite> alphabetSprites)
    {
	this.alphabetSprites = alphabetSprites;
    }
    public Map<Character, Sprite> getAlphabetSprites()
    {
        return alphabetSprites;
    }
}
