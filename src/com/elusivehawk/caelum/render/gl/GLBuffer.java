
package com.elusivehawk.caelum.render.gl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.List;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.storage.SyncList;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class GLBuffer extends GLObject
{
	private final GLEnumBufferTarget t;
	
	private final List<VertexAttrib> attribs = SyncList.newList();
	
	private int id = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public GLBuffer(GLEnumBufferTarget target)
	{
		assert target != null;
		
		t = target;
		
	}
	
	public GLBuffer(GLEnumBufferTarget target, IPopulator<GLBuffer> pop)
	{
		this(target);
		
		pop.populate(this);
		
	}
	
	public GLBuffer(GLEnumBufferTarget target, ByteBuffer buf, GLEnumDataUsage usage, RenderContext rcon)
	{
		this(target);
		
		init(rcon, buf, usage);
		
	}
	
	public GLBuffer(GLEnumBufferTarget target, ByteBuffer buf, GLEnumDataUsage usage, RenderContext rcon, IPopulator<GLBuffer> pop)
	{
		this(target, buf, usage, rcon);
		
		pop.populate(this);
		
	}

	public GLBuffer(GLEnumBufferTarget target, DoubleBuffer buf, GLEnumDataUsage usage, RenderContext rcon)
	{
		this(target);
		
		init(rcon, buf, usage);
		
	}
	
	public GLBuffer(GLEnumBufferTarget target, DoubleBuffer buf, GLEnumDataUsage usage, RenderContext rcon, IPopulator<GLBuffer> pop)
	{
		this(target, buf, usage, rcon);
		
		pop.populate(this);
		
	}

	public GLBuffer(GLEnumBufferTarget target, FloatBuffer buf, GLEnumDataUsage usage, RenderContext rcon)
	{
		this(target);
		
		init(rcon, buf, usage);
		
	}
	
	public GLBuffer(GLEnumBufferTarget target, FloatBuffer buf, GLEnumDataUsage usage, RenderContext rcon, IPopulator<GLBuffer> pop)
	{
		this(target, buf, usage, rcon);
		
		pop.populate(this);
		
	}

	public GLBuffer(GLEnumBufferTarget target, IntBuffer buf, GLEnumDataUsage usage, RenderContext rcon)
	{
		this(target);
		
		init(rcon, buf, usage);
		
	}
	
	public GLBuffer(GLEnumBufferTarget target, IntBuffer buf, GLEnumDataUsage usage, RenderContext rcon, IPopulator<GLBuffer> pop)
	{
		this(target, buf, usage, rcon);
		
		pop.populate(this);
		
	}
	
	public GLBuffer(GLEnumBufferTarget target, ShortBuffer buf, GLEnumDataUsage usage, RenderContext rcon)
	{
		this(target);
		
		init(rcon, buf, usage);
		
	}
	
	public GLBuffer(GLEnumBufferTarget target, ShortBuffer buf, GLEnumDataUsage usage, RenderContext rcon, IPopulator<GLBuffer> pop)
	{
		this(target, buf, usage, rcon);
		
		pop.populate(this);
		
	}
	
	@Override
	public void unbind(RenderContext rcon)
	{
		GL1.glBindBuffer(this.t, 0);
		
	}
	
	@Override
	public boolean isBound()
	{
		return GL1.glGetInteger(this.t.bind) == this.id;
	}
	
	@Override
	public boolean bindImpl(RenderContext rcon)
	{
		if (this.isBound())
		{
			return true;
		}
		
		GL1.glBindBuffer(this);
		
		return true;
	}
	
	@Override
	protected void initiate(RenderContext rcon)
	{
		this.id = GL1.glGenBuffer();
		
	}
	
	@Override
	protected void deleteImpl()
	{
		GL1.glDeleteBuffers(this);
		
	}
	
	public GLEnumBufferTarget getTarget()
	{
		return this.t;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public List<VertexAttrib> getAttribs()
	{
		return this.attribs;
	}
	
	public void addAttrib(int index, int size, int type, int stride, long first)
	{
		this.addAttrib(index, size, type, false, stride, first);
		
	}
	
	public void addAttrib(int index, int size, int type, boolean normalized, int stride, long first)
	{
		this.addAttrib(new VertexAttrib(index, size, type, normalized, stride, first));
		
	}
	
	public void addAttrib(VertexAttrib attrib)
	{
		assert attrib != null;
		
		if (!this.attribs.isEmpty())
		{
			for (VertexAttrib a : this.attribs)
			{
				if (a.index == attrib.index)
				{
					return;
				}
				
			}
			
		}
		
		this.attribs.add(attrib);
		
	}
	
	public void init(RenderContext rcon, ByteBuffer buf, GLEnumDataUsage usage)
	{
		if (this.bind(rcon))
		{
			GL1.glBufferData(this.t, buf, usage);
			
			this.unbind(rcon);
			
		}
		
	}
	
	public void init(RenderContext rcon, DoubleBuffer buf, GLEnumDataUsage usage)
	{
		if (this.bind(rcon))
		{
			GL1.glBufferData(this.t, buf, usage);
			
			this.unbind(rcon);
			
		}
		
	}
	
	public void init(RenderContext rcon, FloatBuffer buf, GLEnumDataUsage usage)
	{
		if (this.bind(rcon))
		{
			GL1.glBufferData(this.t, buf, usage);
			
			this.unbind(rcon);
			
		}
		
	}
	
	public void init(RenderContext rcon, IntBuffer buf, GLEnumDataUsage usage)
	{
		if (this.bind(rcon))
		{
			GL1.glBufferData(this.t, buf, usage);
			
			this.unbind(rcon);
			
		}
		
	}
	
	public void init(RenderContext rcon, ShortBuffer buf, GLEnumDataUsage usage)
	{
		if (this.bind(rcon))
		{
			GL1.glBufferData(this.t, buf, usage);
			
			this.unbind(rcon);
			
		}
		
	}
	
	public void update(RenderContext rcon, ByteBuffer buf, long offset)
	{
		if (this.bind(rcon))
		{
			GL1.glBufferSubData(this.t, offset, buf);
			
			this.unbind(rcon);
			
		}
		
	}
	
	public void update(RenderContext rcon, DoubleBuffer buf, long offset)
	{
		if (this.bind(rcon))
		{
			GL1.glBufferSubData(this.t, offset, buf);
			
			this.unbind(rcon);
			
		}
		
	}
	
	public void update(RenderContext rcon, FloatBuffer buf, long offset)
	{
		if (this.bind(rcon))
		{
			GL1.glBufferSubData(this.t, offset, buf);
			
			this.unbind(rcon);
			
		}
		
	}
	
	public void update(RenderContext rcon, IntBuffer buf, long offset)
	{
		if (this.bind(rcon))
		{
			GL1.glBufferSubData(this.t, offset, buf);
			
			this.unbind(rcon);
			
		}
		
	}
	
	public void update(RenderContext rcon, ShortBuffer buf, long offset)
	{
		if (this.bind(rcon))
		{
			GL1.glBufferSubData(this.t, offset, buf);
			
			this.unbind(rcon);
			
		}
		
	}
	
}
