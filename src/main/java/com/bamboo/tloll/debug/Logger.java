package com.bamboo.tloll.debug;

import com.bamboo.tloll.constants.Constants;
import com.bamboo.tloll.graphics.GraphicsUtil;
import com.bamboo.tloll.graphics.Renderer;
import com.bamboo.tloll.graphics.SpriteBuffer;
import com.bamboo.tloll.graphics.Unit;
import com.bamboo.tloll.graphics.structure.Tile;
import com.bamboo.tloll.graphics.structure.WorldMap;

import com.bamboo.tloll.collision.CollisionDetector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Logger {

    private static Logger _instance;

    public static Logger getInstance() {
        if (_instance == null) {
            _instance = new Logger();
        }
        return _instance;
    }

    private Logger() {
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
        for (Tile tile : CollisionDetector.getInstance().getOccupiedTiles(player)) {
            printToWindow(gu, "" + tile.getTileId(), 0.0f, yPosForTileInfo, false);
            yPosForTileInfo -= 20.0f;
        }

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

    
    public void printToWindow(GraphicsUtil gu, String message, float posX, float posY, boolean leftToRight) {
        Renderer.drawString(gu, Constants.USER_DIR, posX, posY, message, leftToRight);
    }

}
