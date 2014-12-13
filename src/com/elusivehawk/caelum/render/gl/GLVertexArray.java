
package com.elusivehawk.caelum.render.gl;

import java.util.List;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.util.IPopulator;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class GLVertexArray implements IGLBindable
{
	private final List<GLBuffer> vbos = Lists.newArrayList();
	private final List<Integer> attribs = Lists.newArrayList();
	
	private int id = 0;
	private boolean initiated = false;
	
	public GLVertexArray(){}
	
	public GLVertexArray(IPopulator<GLVertexArray> pop)
	{
		pop.populate(this);
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		if (!this.initiated)
		{
			return;
		}
		
		if (this.isBound(rcon))
		{
			this.unbind(rcon);
			
		}
		
		GL3.glDeleteVertexArray(this.id);
		
	}
	
	@Override
	public boolean bind(RenderContext rcon)
	{
		int old = GL1.glGetInteger(GLConst.GL_VERTEX_ARRAY_BINDING);
		
		if (old != 0)
		{
			return false;
		}
		
		if (this.vbos.isEmpty())
		{
			return false;
		}
		
		if (this.id == 0)
		{
			this.id = GL3.glGenVertexArray();
			rcon.registerCleanable(this);
			
		}
		
		GL3.glBindVertexArray(this);
		
		this.vbos.forEach(((vbo) ->
		{
			vbo.bind(rcon);
			
			if (!this.initiated)
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
			
			if (vbo.needsUpdating())
			{
				vbo.reupload(rcon);
				
			}
			
			vbo.unbind(rcon);
			
		}));
		
		if (!this.initiated)
		{
			this.initiated = true;
			
		}
		
		GL2.glEnableVertexAttribArrays(this.attribs);
		
		return true;
	}
	
	@Override
	public void unbind(RenderContext rcon)
	{
		if (!this.isBound(rcon))
		{
			return;
		}
		
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
		this.vbos.add(vbo);
		
	}
	
}
