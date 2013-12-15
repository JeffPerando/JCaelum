
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.core.Tuple;
import com.elusivehawk.engine.math.Vector2f;

/**
 * 
 * @deprecated To be replaced with a simpler imaging system.
 * 
 * @author Elusivehawk
 */
@Deprecated
public class ImageData
{
	public final Tuple<Integer, Integer> pos = new Tuple<Integer, Integer>(0, 0);
	public final int width, height;
	public final IExtraImageData mgr;
	public final Vector2f[] texOffs = {new Vector2f(0, 0), new Vector2f(1, 0), new Vector2f(0, 1), new Vector2f(1, 1)};
	public final Color[] colors = {new Color(EnumColorFormat.RGBA), new Color(EnumColorFormat.RGBA), new Color(EnumColorFormat.RGBA), new Color(EnumColorFormat.RGBA)};
	
	public ImageData(int w, int h)
	{
		this(w, h, null);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public ImageData(int w, int h, IExtraImageData manager)
	{
		width = w;
		height = h;
		mgr = manager;
		
	}
	
}
