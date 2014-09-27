
package com.elusivehawk.util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import com.elusivehawk.util.math.MathHelper;
import com.elusivehawk.util.storage.Tuple;
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
	private final List<Tuple<Integer, FloatBuffer>> floatDiff = Lists.newArrayList();
	private final List<Tuple<Integer, IntBuffer>> intDiff = Lists.newArrayList();
	
	private volatile boolean dirty = false;
	private boolean resized = false;
	private int indicecount = 0;
	private Tuple<Integer, List<Float>> fDiff = null;
	
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
				this.indices = BufferHelper.expand(this.indices, 12);
				
				this.resized = true;
				
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
						this.addFloatEntry();
						
					}
					
				}
				else
				{
					if (this.fDiff == null)
					{
						this.fDiff = Tuple.create(pos + c, Lists.newArrayList());
						
					}
					
					this.fDiff.two.add(this.buf.get(pos));
					
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
		this.resized = false;
		
		this.buf.rewind();
		this.indices.rewind();
		
	}
	
	public void reset()
	{
		this.buf.clear();
		this.indices.clear();
		
		this.prev.clear();
		this.indPrev.clear();
		
		if (this.resized)
		{
			this.prev = BufferHelper.expand(this.prev, 12 * this.fpi);
			this.indPrev = BufferHelper.expand(this.indPrev, 12);
			
		}
		
		if (this.fDiff != null)
		{
			this.addFloatEntry();
			
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
		
		this.resized = false;
		
	}
	
	private void addFloatEntry()
	{
		this.floatDiff.add(Tuple.create(this.fDiff.one, BufferHelper.makeFloatBuffer(this.fDiff.two)));
		this.fDiff = null;
		
	}
	
	public FloatBuffer getBuffer()
	{
		return this.buf;
	}
	
	public IntBuffer getIndices()
	{
		return this.indices;
	}
	
	public List<Tuple<Integer, FloatBuffer>> getFloatDiffs()
	{
		return this.floatDiff;
	}
	
	public List<Tuple<Integer, IntBuffer>> getIntDiffs()
	{
		return this.intDiff;
	}
	
	public boolean resizedRecently()
	{
		return this.resized;
	}
	
}
