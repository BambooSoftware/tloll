package com.bamboo.tloll.behaviors;

import com.bamboo.tloll.graphics.Unit;

public final class BaseBehaviors
{

    public BaseBehaviors()
    {
	// Empty constructor.
    }

    public static void patrolUnitLeftRight(Unit unit, float deltaX)
    {
	if (unit.getRight())
	    {
		// TODO(map) : Hard coded width.  Need to pass in the size of the window.
		if (unit.getCenterX() + deltaX > 512)
		    {
			unit.setRight(false);
		    }
		else
		    {
			unit.setPosX(unit.getPosX() + deltaX);
			unit.setCenterX(unit.getCenterX() + deltaX);
		    }
	    }
	else
	    {
		deltaX = deltaX * -1;
		if (unit.getCenterX() + deltaX < 0)
		    {
			unit.setRight(true);
		    }
		else
		    {
			unit.setPosX(unit.getPosX() + deltaX);
			unit.setCenterX(unit.getCenterX() + deltaX);
		    }
	    }
    }
    
}
