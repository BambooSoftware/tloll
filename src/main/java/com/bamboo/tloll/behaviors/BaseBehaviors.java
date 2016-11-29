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
		// TODO(map) : Hard coded value that needs to be passed eventually.
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
