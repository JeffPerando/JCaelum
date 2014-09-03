
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
	private FloatBuffer buf, prev;
	private IntBuffer indices, indPrev;
	private final List<Pair<Integer>> floatDiff = Lists.newArrayList(),
			intDiff = Lists.newArrayList();
	
	private boolean dirty = false;
	private int indicecount = 0;
	private Pair<Integer> fDiff = null;
	
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
	
	public FloatBufferer(int floatsPerIndice, int indiceCount, IPopulator<FloatBufferer> pop)
	{
		this(floatsPerIndice, indiceCount);
		
		pop.populate(this);
		
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
			if (this.buf.remaining() == 0)
			{
				this.buf = BufferHelper.expand(this.buf, this.fpi * 12);
				
			}
			
			in = this.indicecount;
			this.indicecount++;
			
			this.buf.put(fs);
			
			this.buf.position(this.buf.position() - this.fpi);
			this.prev.position(this.buf.position());
			
			int pos = this.buf.position();
			
			for (int c = 0; c < this.fpi; c++)
			{
				if (this.buf.get() == this.prev.get())
				{
					if (this.fDiff != null)
					{
						this.floatDiff.add(this.fDiff);
						this.fDiff = null;
						
					}
					
				}
				else
				{
					if (this.fDiff == null)
					{
						this.fDiff = new Pair<Integer>(pos + c, 0);
						
					}
					
					this.fDiff.two++;
					
				}
				
			}
			
		}
		
		this.buf.rewind();
		this.prev.rewind();
		
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
		
		this.prev.clear();
		this.indPrev.clear();
		
		if (this.fDiff != null)
		{
			this.floatDiff.add(this.fDiff);
			this.fDiff = null;
			
		}
		
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
