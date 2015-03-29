
package com.elusivehawk.caelum.render.gl;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.util.IPopulator;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class GLVertexArray extends GLObject
{
	private final List<GLBuffer> buffers = Lists.newArrayList();
	private final List<Integer> attribs = Lists.newArrayList();
	private final AtomicBoolean reset = new AtomicBoolean(true);
	
	private int id = 0;
	
	public GLVertexArray(){}
	
	public GLVertexArray(IPopulator<GLVertexArray> pop)
	{
		this();
		
		pop.populate(this);
		
	}
	
	@Override
	public void unbind(RenderContext rcon)
	{
		if (!this.isBound())
		{
			return;
		}
		
		this.buffers.forEach(((vbo) ->
		{
			if (vbo.getTarget() != GLEnumBufferTarget.GL_ARRAY_BUFFER)
			{
				vbo.unbind(rcon);
				
			}
			
		}));
		
		GL2.glDisableVertexAttribArrays(this.attribs);
		
		GL3.glBindVertexArray(null);
		
	}
	
	@Override
	public boolean isBound()
	{
		return this.id != 0 && GL1.glGetInteger(GLConst.GL_VERTEX_ARRAY_BINDING) == this.id;
	}
	
	@Override
	public boolean bindImpl(RenderContext rcon)
	{
		int old = GL1.glGetInteger(GLConst.GL_VERTEX_ARRAY_BINDING);
		
		if (old != 0)
		{
			return false;
		}
		
		if (this.buffers.isEmpty())
		{
			return false;
		}
		
		GL3.glBindVertexArray(this);
		
		if (this.reset.get())
		{
			this.attribs.clear();
			
			this.buffers.forEach(((vbo) ->
			{
				if (vbo.getTarget() == GLEnumBufferTarget.GL_ARRAY_BUFFER)
				{
					if (vbo.bind(rcon))
					{
						vbo.getAttribs().forEach(((attrib) ->
						{
							if (!this.attribs.contains(attrib.index))
							{
								GL2.glVertexAttribPointer(attrib);
								
								this.attribs.add(attrib.index);
								
							}
							
						}));
						
					}
					
					vbo.unbind(rcon);
					
				}
				
			}));
			
			this.reset.set(false);
			
		}
		
		GL2.glEnableVertexAttribArrays(this.attribs);
		
		this.buffers.forEach(((vbo) ->
		{
			if (vbo.getTarget() != GLEnumBufferTarget.GL_ARRAY_BUFFER)
			{
				vbo.bind(rcon);
				
			}
			
		}));
		
		return true;
	}
	
	@Override
	protected void initiate(RenderContext rcon)
	{
		this.id = GL3.glGenVertexArray();
		
	}
	
	@Override
	protected void deleteImpl()
	{
		GL3.glDeleteVertexArray(this.id);
		
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void addVBO(GLBuffer vbo)
	{
		if (vbo != null)
		{
			this.buffers.add(vbo);
			
		}
		
	}
	
	public void clearVBOs()
	{
		this.buffers.clear();
		
		this.reset.set(true);
		
	}
	
}
