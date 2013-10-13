
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.util.Tuple;

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
	
	public ImageData(int w, int h)
	{
		this(w, h, new BasicImageData());
		
	}
	
	public ImageData(Color color, int w, int h)
	{
		this(w, h, new BasicImageData(color));
		
	}
	
	public ImageData(int w, int h, IExtraImageData manager)
	{
		width = w;
		height = h;
		mgr = manager;
		
	}
	
}
