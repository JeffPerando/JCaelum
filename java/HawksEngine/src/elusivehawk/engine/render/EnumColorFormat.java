
package elusivehawk.engine.render;

import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum EnumColorFormat
{
	RGBA(EnumColor.RED, EnumColor.GREEN, EnumColor.BLUE, EnumColor.ALPHA),
	ARGB(EnumColor.ALPHA, EnumColor.RED, EnumColor.GREEN, EnumColor.BLUE),
	ABGR(EnumColor.ALPHA, EnumColor.BLUE, EnumColor.GREEN, EnumColor.RED),
	BGRA(EnumColor.BLUE, EnumColor.GREEN, EnumColor.RED, EnumColor.ALPHA);
	
	EnumColorFormat(EnumColor... f)
	{
		colors = f;
		
		offsets = new int[f.length];
		
		boolean flag = false;
		
		for (int c = 0; c < f.length; c++)
		{
			offsets[colors[c].ordinal()] = 24 - (c * 8);
			
			if (colors[c] == EnumColor.ALPHA)
			{
				flag = true;
				
			}
			
		}
		
		alpha = flag;
		
	}
	
	public final EnumColor[] colors;
	private int[] offsets;
	private final boolean alpha;
	
	public int getColorOffset(EnumColor col)
	{
		return this.offsets[col.ordinal()];
	}
	
	public Color convert(Color old)
	{
		if (old.getFormat().ordinal() == this.ordinal())
		{
			return old;
		}
		
		ByteBuffer buf = BufferUtils.createByteBuffer(this.colors.length);
		
		for (EnumColor col : this.colors)
		{
			buf.put(old.getColor(col));
			
		}
		
		return new Color(this, buf);
	}
	
	public boolean supportsAlpha()
	{
		return this.alpha;
	}
	
}
