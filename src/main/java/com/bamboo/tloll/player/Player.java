package com.bamboo.tloll.player;

import com.bamboo.tloll.graphics.Unit;
import com.bamboo.tloll.input.KeyboardHandler;
import com.bamboo.tloll.physics.Vector3;

/**
 * Created by ablackbu on 3/28/17.
 */
public class Player {

    //A player has a control set and a unit they are current controlling.
    //this will be loaded from a config file.

    Unit unit;
    Controls controls;

    public Player(Controls controls) {
        this.controls = controls;
    }

    public Player(Unit unit, Controls controls) {
        this.unit = unit;
        this.controls = controls;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Controls getControls() {
        return controls;
    }

    public void setControls(Controls controls) {
        this.controls = controls;
    }

    public void processMovement() {
        //Since we have the controls and the player we can go ahead and trigger movement here.
        processUp();
        processDown();
        processLeft();
        processRight();
        processPrimary();
        processSecondary();
    }

    private void processUp() {
        if (KeyboardHandler.isKeyDown(controls.getUp())) {
            unit.moveUpStart(new Vector3(0.0f, 1.0f, 0.0f));
        } else {
            unit.moveUpStop(new Vector3(0.0f, -1.0f, 0.0f));
        }
    }

    private void processDown() {
        if (KeyboardHandler.isKeyDown(controls.getDown())) {
            unit.moveDownStart(new Vector3(0.0f, -1.0f, 0.0f));
        } else {
            unit.moveDownStop(new Vector3(0.0f, 1.0f, 0.0f));
        }
    }

    private void processLeft() {
        if (KeyboardHandler.isKeyDown(controls.getLeft())) {
            unit.moveLeftStart(new Vector3(-1.0f, 0.0f, 0.0f));
        } else {
            unit.moveLeftStop(new Vector3(1.0f, 0.0f, 0.0f));
        }
    }

    private void processRight() {
        if (KeyboardHandler.isKeyDown(controls.getRight())) {
            unit.moveRightStart(new Vector3(1.0f, 0.0f, 0.0f));
        } else {
            unit.moveRightStop(new Vector3(-1.0f, 0.0f, 0.0f));
        }
    }

    private void processPrimary() {
    }

    private void processSecondary() {
    }

}
