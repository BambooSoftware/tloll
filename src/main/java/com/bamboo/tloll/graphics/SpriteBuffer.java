package com.bamboo.tloll.graphics;

import java.nio.ByteBuffer;

public class SpriteBuffer {

    private ByteBuffer byteBuffer;
    private int imageHeight; 
    private int imageWidth;
    private int spriteSheetRows;
    private int spriteSheetCols;

    /**
     * Use for static images that do not animate. 
     */
    public SpriteBuffer(ByteBuffer byteBuffer, int imageHeight, int imageWidth) {
        this(byteBuffer, imageHeight, imageWidth, 1, 1);
    }

    /**
     * When you're lazy and don't want to cast errytime
     */
    public SpriteBuffer(ByteBuffer byteBuffer, float imageHeight, float imageWidth) {
        this(byteBuffer, (int) imageHeight, (int) imageWidth, 1, 1);
    }
    /**
     * When you're lazy and don't want to cast errytime
     */
    public SpriteBuffer(ByteBuffer byteBuffer, float imageHeight, float imageWidth, int spriteSheetRows, int spriteSheetCols) {
        this(byteBuffer, (int) imageHeight, (int) imageWidth, spriteSheetRows, spriteSheetCols);
    }

    public SpriteBuffer(ByteBuffer byteBuffer, int imageHeight, int imageWidth, int spriteSheetRows, int spriteSheetCols) {
        this.byteBuffer = byteBuffer;
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
        this.spriteSheetRows = spriteSheetRows;
        this.spriteSheetCols = spriteSheetCols;
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;    
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getSpriteSheetRows() {
        return spriteSheetRows;
    }

    public int getSpriteSheetCols() {
        return spriteSheetCols;
    }

    public int getSpriteSheetHeight() {
        return spriteSheetRows*imageHeight;
    }

    public int getSpirteSheetWidth() {
        return spriteSheetCols*imageWidth;
    }

}