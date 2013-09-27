
package elusivehawk.engine.render;

import elusivehawk.engine.util.Tuple;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ImageData
{
	public final Tuple<Integer, Integer> pos = new Tuple<Integer, Integer>(0, 0);
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
