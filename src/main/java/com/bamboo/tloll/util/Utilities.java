package com.bamboo.tloll.util;

/**
 * Created by ablackbu on 12/6/16.
 */
public final class Utilities {

    public static int truncateToInt(float src) {
        return Math.round(src);
    }

    public static int asInt(boolean b) {
        return b ? 1 : 0;
    }

}
