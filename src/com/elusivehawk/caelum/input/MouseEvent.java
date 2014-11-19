
package com.elusivehawk.caelum.input;

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
	public final EnumMouseClick[] status;
	
	public MouseEvent(Vector position, Vector positionOld)
	{
		this(position, positionOld, new EnumMouseClick[]{EnumMouseClick.UP, EnumMouseClick.UP, EnumMouseClick.UP});
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public MouseEvent(Vector position, Vector positionOld, EnumMouseClick[] clickStatuses)
	{
		super(EnumInputType.MOUSE);
		
		pos = position.setImmutable();
		posOld = positionOld.setImmutable();
		delta = position.sub(positionOld, false);
		status = clickStatuses;
		
	}
	
}
