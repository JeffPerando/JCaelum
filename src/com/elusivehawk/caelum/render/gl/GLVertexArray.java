
package com.elusivehawk.caelum.render.gl;

import java.util.List;
import com.elusivehawk.caelum.render.IBindable;
import com.elusivehawk.caelum.render.IDeletable;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.util.IPopulator;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class GLVertexArray implements IBindable, IDeletable
{
	private final List<GLBuffer> buffers = Lists.newArrayList();
	private final List<Integer> attribs = Lists.newArrayList();
	
	private int id = 0;
	private boolean initiated = false, deleted = false;
	
	public GLVertexArray(){}
	
	public GLVertexArray(IPopulator<GLVertexArray> pop)
	{
		this();
		
		pop.populate(this);
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		if (this.deleted)
		{
			return;
		}
		
		if (!this.initiated)
		{
			return;
		}
		
		if (this.isBound(rcon))
		{
			this.unbind(rcon);
			
		}
		
		GL3.glDeleteVertexArray(this.id);
		
		this.deleted = true;
		
	}
	
	@Override
	public boolean bind(RenderContext rcon)
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
		
		if (!this.initiated)
		{
			this.id = GL3.glGenVertexArray();
			
			rcon.registerDeletable(this);
			
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
			
			this.initiated = true;
			
		}
		
		GL3.glBindVertexArray(this);
		
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
	public void unbind(RenderContext rcon)
	{
		if (!this.isBound(rcon))
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
	public boolean isBound(RenderContext rcon)
	{
		return this.id != 0 && GL1.glGetInteger(GLConst.GL_VERTEX_ARRAY_BINDING) == this.id;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void addVBO(GLBuffer vbo)
	{
		this.buffers.add(vbo);
		
	}
	
}
