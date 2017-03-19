package com.bamboo.tloll.debug;

import com.bamboo.tloll.Constants;
import com.bamboo.tloll.graphics.GraphicsUtil;
import com.bamboo.tloll.graphics.Renderer;
import com.bamboo.tloll.graphics.SpriteBuffer;
import com.bamboo.tloll.graphics.Unit;
import com.bamboo.tloll.graphics.structure.Scene;
import com.bamboo.tloll.graphics.structure.Tile;

import java.util.ArrayList;
import java.util.List;

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

    public void displayOccupiedTiles(Unit player, Scene currentScene) {
        GraphicsUtil gu = GraphicsUtil.getInstance();

        float yPosForTileInfo = 370.0f;
        for (Tile tile : getOccupiedTiles(player, currentScene)) {
            printToWindow(gu, "" + tile.getTileId(), 0.0f, yPosForTileInfo, false);
            yPosForTileInfo -= 20.0f;
        }

    }


    // Debug method for highlighting the current tile the player lives in.
    // NOTE(map) : This covers any tiles the player can reside in.
    public static void highlightCurrentTile(Scene currentScene, Unit player) {
        GraphicsUtil gu = GraphicsUtil.getInstance();

        for (Tile tile : currentScene.getTileList()) {
            if (player.getPosX() <= (tile.getPosX() + tile.getWidth()) &&
                    player.getPosX() >= tile.getPosX() &&
                    player.getPosY() <= (tile.getPosY() + tile.getHeight()) &&
                    player.getPosY() >= tile.getPosY()) {
                Tile highlightTile = new Tile(tile.getPosX(), tile.getPosY(), tile.getWidth(), tile.getHeight(), tile.isPassable(), tile.getBufferId(), tile.getTileId(), false);

                highlightTile.addBufferToMap(0, new SpriteBuffer(gu.loadTexture(Constants.USER_DIR + "/Assets/Images/highlight.png"), highlightTile.getHeight(), highlightTile.getWidth()));
                Renderer.drawSprite(highlightTile, 0);
            }
            if ((player.getPosX() + player.getWidth()) <= (tile.getPosX() + tile.getWidth()) &&
                    (player.getPosX() + player.getWidth()) >= tile.getPosX() &&
                    player.getPosY() <= (tile.getPosY() + tile.getHeight()) &&
                    player.getPosY() >= tile.getPosY()) {
                Tile highlightTile = new Tile(tile.getPosX(), tile.getPosY(), tile.getWidth(), tile.getHeight(), tile.isPassable(), tile.getBufferId(), tile.getTileId(), false);

                highlightTile.addBufferToMap(0, new SpriteBuffer(gu.loadTexture(Constants.USER_DIR + "/Assets/Images/highlight.png"), highlightTile.getHeight(), highlightTile.getWidth()));
                Renderer.drawSprite(highlightTile, 0);
            }
            if (player.getPosX() <= (tile.getPosX() + tile.getWidth()) &&
                    player.getPosX() >= tile.getPosX() &&
                    (player.getPosY() + player.getHeight()) <= (tile.getPosY() + tile.getHeight()) &&
                    (player.getPosY() + player.getHeight()) >= tile.getPosY()) {
                Tile highlightTile = new Tile(tile.getPosX(), tile.getPosY(), tile.getWidth(), tile.getHeight(), tile.isPassable(), tile.getBufferId(), tile.getTileId(), false);

                highlightTile.addBufferToMap(0, new SpriteBuffer(gu.loadTexture(Constants.USER_DIR + "/Assets/Images/highlight.png"), highlightTile.getHeight(), highlightTile.getWidth()));
                Renderer.drawSprite(highlightTile, 0);
            }
            if ((player.getPosX() + player.getWidth()) <= (tile.getPosX() + tile.getWidth()) &&
                    (player.getPosX() + player.getWidth()) >= tile.getPosX() &&
                    (player.getPosY() + player.getHeight()) <= (tile.getPosY() + tile.getHeight()) &&
                    (player.getPosY() + player.getHeight()) >= tile.getPosY()) {
                Tile highlightTile = new Tile(tile.getPosX(), tile.getPosY(), tile.getWidth(), tile.getHeight(), tile.isPassable(), tile.getBufferId(), tile.getTileId(), false);

                highlightTile.addBufferToMap(0, new SpriteBuffer(gu.loadTexture(Constants.USER_DIR + "/Assets/Images/highlight.png"), highlightTile.getHeight(), highlightTile.getWidth()));
                Renderer.drawSprite(highlightTile, 0);
            }
        }
    }



    //TODO: optimize me - this is not a good way but is all we have atm. This logic is used in multiple places
    //Need to consolidate.
    private List<Tile> getOccupiedTiles(Unit player, Scene currentScene) {
        List<Tile> occupiedTiles = new ArrayList<Tile>();
        for (Tile tile : currentScene.getTileList()) {
            if (player.getPosX() <= (tile.getPosX() + tile.getWidth()) &&
                    player.getPosX() >= tile.getPosX() &&
                    player.getPosY() <= (tile.getPosY() + tile.getHeight()) &&
                    player.getPosY() >= tile.getPosY()) {
                occupiedTiles.add(tile);
            }

            if ((player.getPosX() + player.getWidth()) <= (tile.getPosX() + tile.getWidth()) &&
                    (player.getPosX() + player.getWidth()) >= tile.getPosX() &&
                    player.getPosY() <= (tile.getPosY() + tile.getHeight()) &&
                    player.getPosY() >= tile.getPosY()) {
                if (!occupiedTiles.contains(tile)) {
                    occupiedTiles.add(tile);
                }
            }

            if (player.getPosX() <= (tile.getPosX() + tile.getWidth()) &&
                    player.getPosX() >= tile.getPosX() &&
                    (player.getPosY() + player.getHeight()) <= (tile.getPosY() + tile.getHeight()) &&
                    (player.getPosY() + player.getHeight()) >= tile.getPosY()) {
                if (!occupiedTiles.contains(tile)) {
                    occupiedTiles.add(tile);
                }
            }

            if ((player.getPosX() + player.getWidth()) <= (tile.getPosX() + tile.getWidth()) &&
                    (player.getPosX() + player.getWidth()) >= tile.getPosX() &&
                    (player.getPosY() + player.getHeight()) <= (tile.getPosY() + tile.getHeight()) &&
                    (player.getPosY() + player.getHeight()) >= tile.getPosY()) {
                if (!occupiedTiles.contains(tile)) {
                    occupiedTiles.add(tile);
                }
            }

        }

        return occupiedTiles;
    }

    public void printToWindow(GraphicsUtil gu, String message, float posX, float posY, boolean leftToRight) {
        Renderer.drawString(gu, Constants.USER_DIR, posX, posY, message, leftToRight);
    }

}
