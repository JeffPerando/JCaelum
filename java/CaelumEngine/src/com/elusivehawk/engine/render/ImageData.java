
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.math.VectorF;
import com.elusivehawk.engine.render2.Color;
import com.elusivehawk.engine.render2.EnumColorFormat;
import com.elusivehawk.engine.util.Tuple;

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
	public final VectorF[] texOffs = {new VectorF(2, 0f, 0f), new VectorF(2, 1f, 0f), new VectorF(2, 0f, 1f), new VectorF(2, 1f, 1f)};
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
