package com.bamboo.tloll.player;

import com.bamboo.tloll.WorldManager;
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
    boolean overworldToCombatTransition = false;
    
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

    public void processInput() {
        //Since we have the controls and the player we can go ahead and trigger movement here.
        processUp();
        processDown();
        processLeft();
        processRight();
        processPrimary();
        processSecondary();
	processJump();
	processToggle(); // TODO(map) : This will need to be removed when prod build
	
	// NOTE(map) : This may not be the best solution at the end of the day.  It is a temporary fix and should
	// be investigated further about how to ensure every unit on screen has its lastMoved value updated
	// every frame.
	unit.setLastMoved(System.currentTimeMillis());
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

    private void processToggle() {
	if (KeyboardHandler.isKeyDown(controls.getToggle()) && !overworldToCombatTransition) {
	    WorldManager.getInstance().setInCombat(!WorldManager.getInstance().getInCombat());
	}
    }

    private void processJump()
    {
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
				unit.jump(new Vector3(0.0f, 1.0f, 1.0f));
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
				unit.jump(new Vector3(0.0f, -1.0f, -1.0f));
			    }
		    }
		unit.getUnitVector().setYComponent(0.0f);
		unit.getUnitVector().setZComponent(0.0f);
	    }
    }
    
}
