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

    public static final String USER_DIR = System.getProperty("user.dir");


    // NOTE(map) : These speeds are based on average human running speed in m/s.  This also assumes
    // that 1 pixel is equivalent to 1 meter game space.
    //TODO: maybe these should be heroUnit attributes?
    public static final float MAX_SPEED_X = 7.0f;
    public static final float MIN_SPEED_X = -7.0f;
    public static final float MAX_SPEED_Y = 7.0f;
    public static final float MIN_SPEED_Y = -7.0f;


}
