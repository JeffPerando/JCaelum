
package com.elusivehawk.engine.math;

import java.nio.IntBuffer;
import com.elusivehawk.engine.util.Buffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class VectorI extends Vector<Integer>
{
	public VectorI(int length, Buffer<Integer> buf)
	{
		super(length, buf);
		
	}
	
	public VectorI(Integer... info)
	{
		super(info);
		
	}
	
	public VectorI(int length, IntBuffer buf)
	{
		this(length);
		
		int l = Math.min(length, buf.remaining());
		
		for (int c = 0; c < l; c++)
		{
			set(c, buf.get());
			
		}
		
	}
	
	public VectorI(Vector<Integer> vec)
	{
		super(vec);
		
	}
	
	public VectorI(int length)
	{
		super(length);
		
	}
	
	@Override
	public VectorI add(IMathObject<Integer> obj, boolean local)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		VectorI ret = local ? this : new VectorI(this);
		
		for (int c = 0; c < l; c++)
		{
			ret.set(c, this.get(c) + obj.get(c));
			
		}
		
		ret.onChanged();
		
		return ret;
	}
	
	@Override
	public VectorI div(IMathObject<Integer> obj, boolean local)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		VectorI ret = local ? this : new VectorI(this);
		
		for (int c = 0; c < l; c++)
		{
			ret.set(c, this.get(c) / obj.get(c));
			
		}
		
		ret.onChanged();
		
		return ret;
	}
	
	@Override
	public VectorI sub(IMathObject<Integer> obj, boolean local)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		VectorI ret = local ? this : new VectorI(this);
		
		for (int c = 0; c < l; c++)
		{
			ret.set(c, this.get(c) - obj.get(c));
			
		}
		
		ret.onChanged();
		
		return ret;
	}
	
	@Override
	public VectorI mul(IMathObject<Integer> obj, boolean local)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		VectorI ret = local ? this : new VectorI(this);
		
		for (int c = 0; c < l; c++)
		{
			ret.set(c, this.get(c) * obj.get(c));
			
		}
		
		ret.onChanged();
		
		return ret;
	}
	
	@Override
	public void store(Buffer<Integer> buf)
	{
		for (int c = 0; c < this.getSize(); c++)
		{
			buf.add(this.get(c));
			
		}
		
	}
	
}
