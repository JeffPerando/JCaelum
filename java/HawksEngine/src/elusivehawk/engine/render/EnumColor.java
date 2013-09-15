
package elusivehawk.engine.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum EnumColor
{
	RED(16, 8, 24, 0),
	GREEN(8, 0, 0, 8),
	BLUE(0, 24, 8, 16),
	ALPHA(24, 16, 16, 24);
	
	public final int rgba, argb, abgr, bgra;
	
	EnumColor(int rgb, int arg, int abg, int bgr)
	{
		rgba = rgb;
		argb = arg;
		abgr = abg;
		bgra = bgr;
		
	}
	
}
