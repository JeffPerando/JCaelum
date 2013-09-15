
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
	public Vector2f pos = new Vector2f();
	public int width = 0, height = 0;
	public final ITexture tex;
	
	public ImageData(ITexture texture)
	{
		tex = texture;
		
	}
	
}
