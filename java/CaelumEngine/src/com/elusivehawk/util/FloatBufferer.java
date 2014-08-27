
package com.elusivehawk.util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import com.elusivehawk.util.math.MathHelper;
import com.elusivehawk.util.storage.Pair;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class FloatBufferer implements IDirty
{
	private final int fpi;
	private final FloatBuffer buf, prev;
	private final IntBuffer indices, indPrev;
	private final List<Pair<Integer>> floatDiff = Lists.newArrayList(),
			intDiff = Lists.newArrayList();
	
	private boolean dirty = false;
	private int indicecount = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public FloatBufferer(int floatsPerIndice, int indiceCount)
	{
		assert floatsPerIndice > 0;
		
		fpi = floatsPerIndice;
		
		buf = BufferHelper.createFloatBuffer(fpi * indiceCount);
		prev = BufferHelper.createFloatBuffer(fpi * indiceCount);
		
		indices = BufferHelper.createIntBuffer(indiceCount);
		indPrev = BufferHelper.createIntBuffer(indiceCount);
		
	}
	
	@Override
	public boolean isDirty()
	{
		return this.dirty;
	}
	
	@Override
	public void setIsDirty(boolean b)
	{
		this.dirty = b;
		
	}
	
	public void add(float... fs)
	{
		this.addIndex(this.getOrCreateIndex(fs));
		
	}
	
	public int getIndex(float... fs)
	{
		assert fs.length == this.fpi;
		
		int ret = -1;
		
		floatitr: for (int c = 0; c < this.indicecount; c++)
		{
			for (int i = 0; i < this.fpi; i++)
			{
				if (fs[i] != this.buf.get(c * this.fpi + i))
				{
					continue floatitr;
				}
				
			}
			
			ret = c;
			break;
		}
		
		return ret;
	}
	
	public int getOrCreateIndex(float... fs)
	{
		int in = this.getIndex(fs);
		
		if (in == -1)
		{
			in = this.indicecount;
			this.indicecount++;
			
			this.buf.put(fs);
			
		}
		
		return in;
	}
	
	public int[] getOrCreateIndices(float[]... fss)
	{
		int[] ret = new int[fss.length];
		int i = 0;
		
		for (float[] fs : fss)
		{
			ret[i++] = this.getOrCreateIndex(fs);
			
		}
		
		return ret;
	}
	
	public void addIndex(int i)
	{
		assert this.indicecount > 0;
		assert MathHelper.bounds(i, 0, this.indicecount);
		
		this.indices.put(i);
		
	}
	
	public void rewind()
	{
		this.buf.rewind();
		this.indices.rewind();
		
	}
	
	public void reset()
	{
		this.buf.clear();
		this.indices.clear();
		
		this.prev.rewind();
		this.indPrev.rewind();
		
		while (this.buf.remaining() > 0)
		{
			this.prev.put(this.buf.get());
			
		}
		
		this.buf.rewind();
		
		while (this.buf.remaining() > 0)
		{
			this.buf.put(0);
			
		}
		
		while (this.indices.remaining() > 0)
		{
			this.indPrev.put(this.indices.get());
			
		}
		
		this.indices.rewind();
		
		while (this.indices.remaining() > 0)
		{
			this.indices.put(0);
			
		}
		
	}
	
	public void genDiffs()
	{
		Pair<Integer> diff = null;
		
		for (int c = 0; c < this.buf.capacity(); c++)
		{
			if (this.buf.get(c) != this.prev.get(c))
			{
				if (diff == null)
				{
					diff = new Pair<Integer>(c, 0);
					
				}
				
				diff.two++;
				
			}
			else
			{
				this.floatDiff.add(diff);
				diff = null;
				
			}
			
		}
		
		for (int c = 0; c < this.indices.capacity(); c++)
		{
			if (this.indices.get(c) != this.indPrev.get(c))
			{
				if (diff == null)
				{
					diff = new Pair<Integer>(c, 0);
					
				}
				
				diff.two++;
				
			}
			else
			{
				this.intDiff.add(diff);
				diff = null;
				
			}
			
		}
		
	}
	
	public FloatBuffer getBuffer()
	{
		return this.buf;
	}
	
	public IntBuffer getIndices()
	{
		return this.indices;
	}
	
	public List<Pair<Integer>> getFloatDiffs()
	{
		return this.floatDiff;
	}
	
	public List<Pair<Integer>> getIntDiffs()
	{
		return this.intDiff;
	}
	
}
