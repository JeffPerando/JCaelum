
package com.elusivehawk.caelum.render.tex;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import com.elusivehawk.util.parse.json.IJsonSerializer;
import com.elusivehawk.util.parse.json.JsonObject;
import com.elusivehawk.util.parse.json.JsonParseException;
import com.elusivehawk.util.storage.Bitmask;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * The more flexible cousin to {@link java.awt.Color}.
 * 
 * @author Elusivehawk
 * 
 * @see ColorFilter
 * @see ColorFormat
 */
public class Color implements IJsonSerializer
{
	public static final Color BLACK = new Color(ColorFormat.RGB).setImmutable();
	public static final Color GREY = new Color(ColorFormat.RGB, 0.5f, 0.5f, 0.5f).setImmutable();
	public static final Color GRAY = GREY;
	public static final Color WHITE = new Color(ColorFormat.RGB, 1f, 1f, 1f).setImmutable();
	
	public static final Color RED = new Color(ColorFormat.RGB, 1f, 0f, 0f).setImmutable();
	public static final Color GREEN = new Color(ColorFormat.RGB, 0f, 1f, 0f).setImmutable();
	public static final Color BLUE = new Color(ColorFormat.RGB, 0f, 0f, 1f).setImmutable();
	
	public static final Color YELLOW = new Color(ColorFormat.RGB, 1f, 1f, 0f).setImmutable();
	public static final Color PINK = new Color(ColorFormat.RGB, 1f, 0f, 1f).setImmutable();
	public static final Color CYAN = new Color(ColorFormat.RGB, 0f, 1f, 1f).setImmutable();
	
	private final ColorFormat format;
	
	private int color = 0;
	private boolean immutable = false;
	
	@SuppressWarnings("unqualified-field-access")
	public Color(ColorFormat cf)
	{
		format = cf;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Color(ColorFormat cf, int col)
	{
		this(cf);
		
		color = col;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Color(java.awt.Color col)
	{
		this(ColorFormat.RGBA);
		
		color = col.getRGB();
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Color(Color col)
	{
		this(col.getFormat());
		
		color = col.getColor();
		
	}
	
	public Color(JsonObject json)
	{
		this(ColorFormat.valueOf(json.getValue("format").toString()), json);
		
	}
	
	public Color(ColorFormat cf, JsonObject json)
	{
		this(cf);
		
		for (ColorFilter filter : cf.filters)
		{
			Object color = json.getValue(filter.toString());
			
			if (color == null)
			{
				continue;
			}
			
			if (!(color instanceof Number))
			{
				throw new JsonParseException("Invalid value for JSON key \"%s\": %s", filter.toString(), color);
			}
			
			Number n = (Number)color;
			
			if (color instanceof Double)
			{
				setColor(filter, n.floatValue());
				
			}
			else if (color instanceof Long)
			{
				setColor(filter, n.intValue());
				
			}
			
		}
		
	}
	
	public Color(ColorFormat cf, float... fs)
	{
		this(cf);
		
		int c = 0;
		
		for (ColorFilter col : cf.filters)
		{
			setColor(col, fs[c++]);
			
			if (c == fs.length)
			{
				break;
			}
			
		}
		
	}
	
	public Color(ColorFormat cf, int[] bs)
	{
		this(cf);
		
		int c = 0;
		
		for (ColorFilter col : cf.filters)
		{
			setColor(col, bs[c++]);
			
			if (c == bs.length)
			{
				return;
			}
			
		}
		
	}
	
	@Override
	public String toJson(int tabs)
	{
		JsonObject ret = new JsonObject();
		
		for (ColorFilter filter : this.format.filters)
		{
			ret.add(filter.toString(), getColor(filter));
			
		}
		
		return ret.toJson(tabs);
	}
	
	@Override
	public Color clone()
	{
		return new Color(this);
	}
	
	@Override
	public String toString()
	{
		StringBuilder b = new StringBuilder();
		
		b.append(String.format("%s:[", this.format));
		
		boolean prev = false;
		
		for (ColorFilter cf : this.format.filters)
		{
			if (prev)
			{
				b.append(", ");
				
			}
			else prev = true;
			
			b.append(String.format("%s: %s", cf, this.getColor(cf)));
			
		}
		
		b.append("]");
		
		return b.toString();
	}
	
	@Override
	public int hashCode()
	{
		return this.getColor();
	}
	
	public int getColor()
	{
		return this.color;
	}
	
	public int getColor(ColorFilter cf)
	{
		Bitmask bitmask = this.format.getBitmask(cf);
		
		if (bitmask == null)
		{
			return 0;
		}
		
		return (int)bitmask.getValue(this.color);
	}
	
	public float getColorf(ColorFilter col)
	{
		return (float)this.getColor(col) / 255;
	}
	
	public ColorFormat getFormat()
	{
		return this.format;
	}
	
	public boolean isImmutable()
	{
		return this.immutable;
	}
	
	public synchronized Color setColor(int col)
	{
		if (!this.immutable)
		{
			this.color = col;
			
		}
		
		return this;
	}
	
	public Color setColor(Color col)
	{
		for (ColorFilter cf : col.format.filters)
		{
			this.setColor(cf, col.getColor(cf));
			
		}
		
		return this;
	}
	
	public Color setColor(ColorFilter cf, float col)
	{
		return this.setColor(cf, (int)(255 * col));
	}
	
	public Color setColor(ColorFilter cf, int col)
	{
		if (!this.isImmutable())
		{
			col = col & 0xFF;
			
			Bitmask bitmask = this.format.getBitmask(cf);
			
			if (bitmask != null)
			{
				this.setColor((int)bitmask.setValue(this.color, col));
				
				assert this.getColor(cf) == col : "Color did not set right! This is a bug!";
				
			}
			
		}
		
		return this;
	}
	
	public Color setImmutable()
	{
		this.immutable = true;
		
		return this;
	}
	
	public boolean supports(ColorFilter cf)
	{
		return this.format.supports(cf);
	}
	
	public float[] asFloats()
	{
		ColorFilter[] filters = this.format.filters;
		float[] ret = new float[filters.length];
		
		for (int c = 0; c < ret.length; c++)
		{
			ret[c] = this.getColorf(filters[c]);
			
		}
		
		return ret;
	}
	
	public FloatBuffer asBufferF()
	{
		return BufferHelper.makeFloatBuffer(this.asFloats());
	}
	
	public void writeToBuffer(ByteBuffer buf)
	{
		for (ColorFilter filter : this.format.filters)
		{
			buf.put((byte)this.getColor(filter));
			
		}
		
	}
	
	public void writeToBufferf(FloatBuffer buf)
	{
		for (ColorFilter filter : this.format.filters)
		{
			buf.put(this.getColorf(filter));
			
		}
		
	}
	
	public Color convertTo(ColorFormat nf)
	{
		if (this.format == nf)
		{
			return this;
		}
		
		Color ret = new Color(nf);
		
		for (ColorFilter cf : nf.filters)
		{
			ret.setColor(cf, this.getColor(cf));
			
		}
		
		if (this.isImmutable())
		{
			ret.setImmutable();
			
		}
		
		return ret;
	}
	
}
