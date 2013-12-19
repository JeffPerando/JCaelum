
package com.elusivehawk.engine.render;

/**
 * 
 * Used to ask the {@linkplain ICamera camera} for certain things.
 * 
 * @author Elusivehawk
 */
public enum EnumCameraPollType
{
	POS_X(false),
	POS_Y(false),
	POS_Z(true),
	ROT_X(true),
	ROT_Y(true),
	ROT_Z(true),
	FOV(false),
	ASPECT_RATIO(true),
	Z_FAR(true),
	Z_NEAR(true);
	
	public final boolean is3DOnly;
	
	@SuppressWarnings("unqualified-field-access")
	EnumCameraPollType(boolean is3d)
	{
		is3DOnly = is3d;
		
	}
	
}
