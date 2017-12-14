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
	processJump();
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

    private void processJump()
    {
	// TODO(map) : Pretty simple logic here.  If the downward speed is currently 0 then do nothing.
	// If the space is pressed move upward until a certain point and then proceed to fall.
	if (!unit.isJumping())
	    {
		if (KeyboardHandler.isKeyDown(controls.getJump()))
		    {
			unit.setIsJumping(true);
			unit.setIsRising(true);
		    }
	    }
	else
	    {
		if (unit.isRising())
		    {
			if (unit.getPosZ() >= unit.getMaxHeight())
			    {
				unit.setIsRising(false);
				unit.setPosZ(unit.getMaxHeight());
			    }
			else
			    {
				unit.setPosZ(unit.getPosZ() + 1.0f);
				unit.setPosY(unit.getPosY() + 1.0f);
			    }
		    }
		else
		    {
			if (unit.getPosZ() <= 0.0f)
			    {
				unit.setIsJumping(false);
				unit.setPosZ(0.0f);
			    }
			else
			    {
				unit.setPosZ(unit.getPosZ() - 1.0f);
				unit.setPosY(unit.getPosY() - 1.0f);
			    }
		    }
	    }
    }
    
}
