package com.bamboo.tloll;

public final class Constants
{

    private Constants()
    {
	// Restrict initiation.
    }

    // NOTE(map) : Arbitrary size.  Right now we have extra space because we may want to put debug info
    // in the right side of the screen for now.
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    public static final String TITLE = "The Legend of Ling Ling";

    // NOTE(map) : The tile width is assuming 1 pixel is equal to 1 meter in game space.
    public static final int TILE_WIDTH = 80;
    public static final int TILE_HEIGHT = 80;
    

}
