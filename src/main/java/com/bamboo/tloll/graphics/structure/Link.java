package com.bamboo.tloll.graphics.structure;

/**
 * This class represents the linking between scenes.
 *
 * @author ablackbu
 */
public class Link {

    private int sceneId;
    private int exitId;
    
    public Link(int sceneId, int exitId) {
        this.sceneId = sceneId;
        this.exitId = exitId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setExitId(int exitId) {
        this.exitId = exitId;
    }

    public int getExitId() {
        return exitId;
    }

    @Override
    public String toString() {
        return "Link [sceneId: " + sceneId + ";  exitId: " + exitId + "]";
    }

}