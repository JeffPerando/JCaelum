
package elusivehawk.engine.render;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import elusivehawk.engine.util.IStoreable;

/**
 * 
 * A simpler way to do color.
 * 
 * @author Elusivehawk
 */
public abstract class Color implements IStoreable
{
	@Override
	public boolean store(ByteBuffer buf)
	{
		this.loadIntoBuffer(buf, true);
		
		return true;
	}
	
	@Override
	public boolean store(FloatBuffer buf)
	{
		this.loadIntoBuffer(buf, true);
		
		return true;
	}
	
	@Override
	public int hashCode()
	{
		return this.getColor();
	}
	
	public abstract int getColor();
	
	public abstract byte getColor(EnumColor id);
	
	public abstract float getColorFloat(EnumColor id);
	
	public abstract boolean supportsAlpha();
	
	public abstract void loadIntoBuffer(ByteBuffer buf, boolean alpha);
	
	public abstract void loadIntoBuffer(FloatBuffer buf, boolean alpha);
	
}
