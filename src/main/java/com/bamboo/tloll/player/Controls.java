package com.bamboo.tloll.player;

/**
 * Created by ablackbu on 3/28/17.
 */
public class Controls {


    private int up;
    private int down;
    private int left;
    private int right;
    private int primary;
    private int secondary;

    public Controls(int up, int down, int left, int right, int primary, int secondary ) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.primary = primary;
        this.secondary = secondary;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getPrimary() {
        return primary;
    }

    public void setPrimary(int primary) {
        this.primary = primary;
    }

    public int getSecondary() {
        return secondary;
    }

    public void setSecondary(int secondary) {
        this.secondary = secondary;
    }

    public int[] getDirectionalInputs() {
        return new int[] {getUp(), getDown(),getLeft(), getRight()};
    }

}
