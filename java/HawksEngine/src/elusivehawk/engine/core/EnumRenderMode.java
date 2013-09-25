
package elusivehawk.engine.core;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum EnumRenderMode
{
	MODE_2D, MODE_3D, BOTH;
	
	public boolean is2D()
	{
		return this != MODE_2D;
	}
	
	public boolean is3D()
	{
		return this != MODE_3D;
	}
	
	public boolean isValidImageMode()
	{
		return this != BOTH;
	}
	
}
