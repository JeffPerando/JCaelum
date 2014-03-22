
package com.elusivehawk.engine.math;

import java.nio.FloatBuffer;
import com.elusivehawk.engine.util.Buffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class VectorF extends Vector<Float>
{
	public VectorF(int length, Buffer<Float> buf)
	{
		super(length, buf);
		
	}
	
	public VectorF(Float... info)
	{
		super(info);
		
	}
	
	public VectorF(int length, FloatBuffer buf)
	{
		super(length);
		
		int l = Math.min(length, buf.remaining());
		
		for (int c = 0; c < l; c++)
		{
			set(c, buf.get());
			
		}
		
	}
	
	public VectorF(Vector<Float> vec)
	{
		super(vec);
		
	}
	
	public VectorF(int size)
	{
		super(size);
		
	}
	
	@Override
	public VectorF add(IMathObject<Float> obj, boolean local)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		VectorF ret = local ? this : new VectorF(this);
		
		for (int c = 0; c < l; c++)
		{
			ret.set(c, this.get(c) + obj.get(c));
			
		}
		
		ret.onChanged();
		
		return ret;
	}
	
	@Override
	public VectorF div(IMathObject<Float> obj, boolean local)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		VectorF ret = local ? this : new VectorF(this);
		
		for (int c = 0; c < l; c++)
		{
			ret.set(c, this.get(c) / obj.get(c));
			
		}
		
		ret.onChanged();
		
		return ret;
	}
	
	@Override
	public VectorF sub(IMathObject<Float> obj, boolean local)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		VectorF ret = local ? this : new VectorF(this);
		
		for (int c = 0; c < l; c++)
		{
			ret.set(c, this.get(c) - obj.get(c));
			
		}
		
		ret.onChanged();
		
		return ret;
	}
	
	@Override
	public VectorF mul(IMathObject<Float> obj, boolean local)
	{
		int l = Math.min(this.getSize(), obj.getSize());
		VectorF ret = local ? this : new VectorF(this);
		
		for (int c = 0; c < l; c++)
		{
			ret.set(c, this.get(c) * obj.get(c));
			
		}
		
		ret.onChanged();
		
		return ret;
	}
	
	@Override
	public void store(Buffer<Float> buf)
	{
		for (int c = 0; c < this.getSize(); c++)
		{
			buf.add(this.get(c));
			
		}
		
	}
	
}
