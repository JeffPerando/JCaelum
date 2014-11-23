
package com.elusivehawk.caelum.input;

import com.elusivehawk.caelum.Display;
import com.elusivehawk.util.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class MouseEvent extends InputEvent
{
	public final Vector pos, posOld, delta;
	public final EnumMouseClick[] status, statusOld;
	
	@SuppressWarnings("unqualified-field-access")
	public MouseEvent(Display displayUsed, Vector position, Vector positionOld, EnumMouseClick[] clicks, EnumMouseClick[] clicksOld)
	{
		super(EnumInputType.MOUSE, displayUsed);
		
		pos = position.clone().setImmutable();
		posOld = positionOld.clone().setImmutable();
		delta = position.sub(positionOld, false);
		status = clicks;
		statusOld = clicksOld;
		
	}
	
}
