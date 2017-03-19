package com.bamboo.tloll.graphics;

import com.bamboo.tloll.Constants;
import com.bamboo.tloll.graphics.structure.Scene;
import com.bamboo.tloll.graphics.structure.Tile;
import com.bamboo.tloll.graphics.structure.WorldMap;
import com.bamboo.tloll.util.BufferMap;

import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

public final class Renderer {

    public Renderer() {
        // Empty constructor.
    }

    public static void drawSprite(Sprite unit, int bufferId) {
        glBindTexture(GL_TEXTURE_2D, bufferId);
        glPixelStoref(GL_UNPACK_ALIGNMENT, 1);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, (int) unit.getWidth(), (int) unit.getHeight(), 0, GL_RGBA,
                GL_UNSIGNED_BYTE, unit.getBufferMap().get(bufferId).getByteBuffer());
        glBegin(GL_QUADS);
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(unit.getPosX(), unit.getPosY(), 0.0f);
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(unit.getPosX() + unit.getWidth(), unit.getPosY(), 0.0f);
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(unit.getPosX() + unit.getWidth(), unit.getPosY() + unit.getHeight(), 0.0f);
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(unit.getPosX(), unit.getPosY() + unit.getHeight(), 0.0f);
        glEnd();
    }

    public static void drawSpriteAnimation(Sprite unit, int bufferId, int maxFrames, float width, float minHeight,
                                           float maxHeight, int imageWidth, int imageHeight) {

        if (unit.getAnimatedSpriteNumber() > maxFrames) {
            unit.setAnimatedSpriteNumber(1);
        }
        float xValMin = width * (unit.getAnimatedSpriteNumber() - 1);
        float xValMax = width * unit.getAnimatedSpriteNumber();
        glBindTexture(GL_TEXTURE_2D, bufferId);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        // NOTE(map) : The animated guy randomly disappears if we use GL_CLAMP_TO_EDGE
        // and I'm not sure why right now.
        //glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE );
        //glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE );
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, imageWidth, imageHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE,
                unit.getBufferMap().get(bufferId).getByteBuffer());
        glBegin(GL_QUADS);
        glTexCoord2f(xValMin, maxHeight);
        glVertex3f(unit.getPosX(), unit.getPosY(), 0.0f);
        glTexCoord2f(xValMax, maxHeight);
        glVertex3f(unit.getPosX() + unit.getWidth(), unit.getPosY(), 0.0f);
        glTexCoord2f(xValMax, minHeight);
        glVertex3f(unit.getPosX() + unit.getWidth(), unit.getPosY() + unit.getHeight(), 0.0f);
        glTexCoord2f(xValMin, minHeight);
        glVertex3f(unit.getPosX(), unit.getPosY() + unit.getHeight(), 0.0f);
        glEnd();
    }

    public static void drawAnimatedUnit(Unit unit, int bufferId, int colNumber) {
        SpriteBuffer spriteBuffer = unit.getBufferMap().get(bufferId);
        int sheetHeight = spriteBuffer.getSpriteSheetHeight();
        int sheetWidth = spriteBuffer.getSpirteSheetWidth();

        float xMin = ((float) colNumber - 1) / spriteBuffer.getSpriteSheetCols();
        float xMax = ((float) colNumber) / spriteBuffer.getSpriteSheetCols();
        float yMin = ((float) unit.getDirection().getValue() - 1) / spriteBuffer.getSpriteSheetRows();
        float yMax = ((float) unit.getDirection().getValue()) / spriteBuffer.getSpriteSheetRows();

        glBindTexture(GL_TEXTURE_2D, bufferId);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, sheetWidth, sheetHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, unit.getBufferMap().get(bufferId).getByteBuffer());
        glBegin(GL_QUADS);
        glTexCoord2f(xMin, yMax);
        glVertex3f(unit.getPosX(), unit.getPosY(), 0.0f);
        glTexCoord2f(xMax, yMax);
        glVertex3f(unit.getPosX() + unit.getWidth(), unit.getPosY(), 0.0f);
        glTexCoord2f(xMax, yMin);
        glVertex3f(unit.getPosX() + unit.getWidth(), unit.getPosY() + unit.getHeight(), 0.0f);
        glTexCoord2f(xMin, yMin);
        glVertex3f(unit.getPosX(), unit.getPosY() + unit.getHeight(), 0.0f);
        glEnd();

    }

    // Method for drawing a string onto the screen.  Only draws left to right curently.
    // NOTE(map) : There are hard coded values currently because they match with the png file and have
    // no need to be changed at the moment.  They could be changed later if desired.
    public static void drawString(GraphicsUtil gu, String currentDir, float posX, float posY, String message,
                                  boolean leftToRight) {
        int imageWidth = 468;
        int imageHeight = 25;

        message = message.toUpperCase();
        char[] characters = message.toCharArray();

        if (leftToRight) {
            Sprite sprite = new Sprite(posX, posY, 13.0f, 25.0f);

            int bufferId = 0;
            SpriteBuffer spriteBuffer = new SpriteBuffer(gu.loadTexture(currentDir + "/Assets/Images/alphabet_white.png"), imageHeight, imageWidth, 1, 36);
            sprite.addBufferToMap(bufferId, spriteBuffer);


            for (char character : characters) {
                // Setting up the parameters for getting the correct letter from the alphabet.
                int numRepOfChar = character - 'A';
                float xMin = (1.0f / 36.0f) * (numRepOfChar * 1);
                float xMax = (1.0f / 36.0f) * ((numRepOfChar + 1) * 1);
                float yMin = 0.0f;
                float yMax = 1.0f;

                // Doing the actual rendering here.
                glBindTexture(GL_TEXTURE_2D, bufferId);
                glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
                glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
                glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
                glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, imageWidth, imageHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE,
                        sprite.getBufferMap().get(bufferId).getByteBuffer());
                glBegin(GL_QUADS);
                glTexCoord2f(xMin, yMax);
                glVertex3f(sprite.getPosX(), sprite.getPosY(), 0.0f);
                glTexCoord2f(xMax, yMax);
                glVertex3f(sprite.getPosX() + sprite.getWidth(), sprite.getPosY(), 0.0f);
                glTexCoord2f(xMax, yMin);
                glVertex3f(sprite.getPosX() + sprite.getWidth(), sprite.getPosY() + sprite.getHeight(), 0.0f);
                glTexCoord2f(xMin, yMin);
                glVertex3f(sprite.getPosX(), sprite.getPosY() + sprite.getHeight(), 0.0f);
                glEnd();
                sprite.setPosX(sprite.getPosX() + sprite.getWidth());
                ;
            }
        } else {
            // NOTE(map) : This need - 13 px because that's the width of single square we
            // need to go back for the first box to be drawn.
            float leftJustifiedPosX = Constants.WIDTH - 13;
            Sprite sprite = new Sprite(leftJustifiedPosX, posY, 13.0f, 25.0f);

            int bufferId = 0;
            SpriteBuffer spriteBuffer = new SpriteBuffer(gu.loadTexture(currentDir + "/Assets/Images/alphabet_white.png"), imageHeight, imageWidth, 1, 36);
            sprite.addBufferToMap(bufferId, spriteBuffer);


            for (int i = characters.length - 1; i > -1; i--) {
                // Setting up the parameters for getting the correct letter from the alphabet.
                if (characters[i] == '.') {
                    characters[i] = ' ';
                }
                if (characters[i] == 'f') {
                    characters[i] = 'F';
                }

                int numRepOfChar = characters[i] - 'A';
                if (numRepOfChar < -7 && numRepOfChar > -19) {
                    int number = Character.getNumericValue(characters[i]);
                    if (number == 0)
                        number = 10;
                    numRepOfChar = 'Z' - 'A' + number;
                }
                float xMin = (1.0f / 36.0f) * (numRepOfChar * 1);
                float xMax = (1.0f / 36.0f) * ((numRepOfChar + 1) * 1);
                float yMin = 0.0f;
                float yMax = 1.0f;

                // Doing the actual rendering here.
                glBindTexture(GL_TEXTURE_2D, bufferId);
                glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
                glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
                glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
                glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, imageWidth, imageHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE,
                        sprite.getBufferMap().get(bufferId).getByteBuffer());
                glBegin(GL_QUADS);
                glTexCoord2f(xMin, yMax);
                glVertex3f(sprite.getPosX(), sprite.getPosY(), 0.0f);
                glTexCoord2f(xMax, yMax);
                glVertex3f(sprite.getPosX() + sprite.getWidth(), sprite.getPosY(), 0.0f);
                glTexCoord2f(xMax, yMin);
                glVertex3f(sprite.getPosX() + sprite.getWidth(), sprite.getPosY() + sprite.getHeight(), 0.0f);
                glTexCoord2f(xMin, yMin);
                glVertex3f(sprite.getPosX(), sprite.getPosY() + sprite.getHeight(), 0.0f);
                glEnd();
                sprite.setPosX(sprite.getPosX() - sprite.getWidth());

            }
        }
    }

    public static void drawScene(Scene scene) {
        for (Tile tile : scene.getTileList()) {
            drawSprite(tile, 0);
        }
    }

    public static Scene loadTileBuffers() {

        Map<Integer, Scene> loadedScenes = WorldMap.getInstance().getSceneMap();
        for (int i = 0; i < loadedScenes.size(); ++i) {
            loadTileBuffers(loadedScenes.get(i));
        }

        return loadedScenes.get(0);
    }

    public static void loadTileBuffers(Scene scene) {
        //TODO: change the method for the buffers to a singleton pattern buffer map.
        for (Tile tile : scene.getTileList()) {
            SpriteBuffer sBuffer = BufferMap.getInstance().getSpriteBuffer(tile.getBufferId());
            tile.getBufferMap().put(0, sBuffer);
        }
    }


}
