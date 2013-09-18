
package elusivehawk.engine.render;

import elusivehawk.engine.math.Vector2f;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ImageData
{
	public boolean requiresUpdating = false;
	public final Vector2f pos = new Vector2f();
	public final int width, height;
	public final ITexture tex;
	
	public ImageData(ITexture texture, int w, int h)
	{
		tex = texture;
		width = w;
		height = h;
		
	}
	
}
