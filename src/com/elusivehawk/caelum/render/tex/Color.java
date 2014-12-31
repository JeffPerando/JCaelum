
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
	public static final Color BLACK = new Color(ColorFormat.RGB);
	public static final Color GREY = new Color(ColorFormat.RGB, 0.5f, 0.5f, 0.5f);
	public static final Color GRAY = GREY;
	public static final Color WHITE = new Color(ColorFormat.RGB, 1.0f, 1.0f, 1.0f);
	
	public static final Color RED = new Color(ColorFormat.RGB, 1.0f, 0.0f, 0.0f);
	public static final Color GREEN = new Color(ColorFormat.RGB, 0.0f, 1.0f, 0.0f);
	public static final Color BLUE = new Color(ColorFormat.RGB, 0.0f, 0.0f, 1.0f);
	
	public static final Color YELLOW = new Color(ColorFormat.RGB, 1.0f, 1.0f, 0.0f);
	public static final Color PINK = new Color(ColorFormat.RGB, 1.0f, 0.0f, 1.0f);
	public static final Color CYAN = new Color(ColorFormat.RGB, 0.0f, 1.0f, 1.0f);
	
	public final ColorFormat format;
	public int color = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public Color(ColorFormat cf)
	{
		format = cf;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Color(java.awt.Color col)
	{
		this(ColorFormat.RGBA);
		
		color = col.getRGB();
		
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
	
	public Color(ColorFormat cf, int... bs)
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
	public int hashCode()
	{
		return this.getColor();
	}
	
	public int getColor()
	{
		return this.color;
	}
	
	public Color setColor(int col)
	{
		this.color = col;
		
		return this;
	}
	
	public Color setColor(ColorFilter cf, float col)
	{
		return this.setColor(cf, (int)(255 * col));
	}
	
	public Color setColor(ColorFilter cf, int col)
	{
		col = col & 0xFF;
		
		Bitmask bitmask = this.format.getBitmask(cf);
		
		if (bitmask != null)
		{
			this.color = (int)bitmask.setValue(this.color, col);
			
			assert this.getColor(cf) == col : "Color did not set right! This is a bug!";
			
		}
		
		return this;
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
	
	public Color convert(ColorFormat nf)
	{
		return nf.convert(this);
	}
	
}
