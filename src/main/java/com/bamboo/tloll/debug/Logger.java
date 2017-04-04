package com.bamboo.tloll.debug;

import com.bamboo.tloll.constants.Constants;
import com.bamboo.tloll.graphics.GraphicsUtil;
import com.bamboo.tloll.graphics.Renderer;
import com.bamboo.tloll.graphics.SpriteBuffer;
import com.bamboo.tloll.graphics.Unit;
import com.bamboo.tloll.graphics.structure.Tile;
import com.bamboo.tloll.graphics.structure.WorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Logger {

    private static Logger _instance;
    private long fpsLastTime;

    public static Logger getInstance() {
        if (_instance == null) {
            _instance = new Logger();
        }
        return _instance;
    }

    private Logger() {
        fpsLastTime = 0l;
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

    public void calculateAndDisplayFps() {
        long newTime = System.currentTimeMillis();
        long delta = newTime- fpsLastTime;
        fpsLastTime = newTime;
        long fps = 1000/delta;
        displayFps(fps);
    }

    private void displayFps(long fps) {
        GraphicsUtil gu = GraphicsUtil.getInstance();
        printToWindow(gu, "FPS: " + fps, 0.0f, 320.0f, false);
    }


    // Debug method for highlighting the current tile the player lives in.
    // NOTE(map) : This covers any tiles the player can reside in.
    public void highlightCurrentTile(Unit player) {
        GraphicsUtil gu = GraphicsUtil.getInstance();

        List<Tile> highlightList = getOccupiedTiles(player);

        for (Tile tile : highlightList) {
            Tile highlightTile = new Tile(tile.getPosX(), tile.getPosY(), tile.getWidth(), tile.getHeight(), tile.isPassable(), tile.getBufferId(), tile.getTileId(), false);
            highlightTile.addBufferToMap(0, new SpriteBuffer(gu.loadTexture(Constants.USER_DIR + "/Assets/Images/highlight.png"), highlightTile.getHeight(), highlightTile.getWidth()));
            Renderer.drawSprite(highlightTile, 0);
        }
    }

    //TODO: refactor me out somewhere else. This is very important for the scene transition. It shouldn't live in the
    //TODO: logger. We have responsibilities intertwined. I think the unit is the lileky target
    public List<Tile> getOccupiedTiles(Unit player) {
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
