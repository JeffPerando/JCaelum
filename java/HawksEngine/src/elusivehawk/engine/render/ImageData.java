
package elusivehawk.engine.render;

import elusivehawk.engine.math.Vector2i;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ImageData
{
	public final Vector2i pos = new Vector2i();
	public final int width, height;
	public final IExtraImageData mgr;
	
	public ImageData(ITexture texture, int w, int h)
	{
		this(w, h, new BasicImageData(texture));
		
	}
	
	public ImageData(ITexture texture, Color color, int w, int h)
	{
		this(w, h, new BasicImageData(texture, color));
		
	}
	
	public ImageData(int w, int h, IExtraImageData manager)
	{
		width = w;
		height = h;
		mgr = manager;
		
	}
	
}
