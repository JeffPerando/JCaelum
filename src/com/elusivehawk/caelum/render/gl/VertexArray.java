
package com.elusivehawk.caelum.render.gl;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.util.IPopulator;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class VertexArray implements IGLBindable
{
	private final HashMap<VertexBuffer, List<Integer>> vbos = new HashMap<VertexBuffer, List<Integer>>();
	
	private int id = 0;
	private boolean initiated = false;
	
	public VertexArray(){}
	
	public VertexArray(IPopulator<VertexArray> pop)
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
		
		for (Entry<VertexBuffer, List<Integer>> entry : this.vbos.entrySet())
		{
			entry.getKey().unbind(rcon);
			
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
		
		GL3.glBindVertexArray(this.id);
		
		for (Entry<VertexBuffer, List<Integer>> entry : this.vbos.entrySet())
		{
			if (!this.initiated)
			{
				entry.getKey().bind(rcon);
				
			}
			
			if (!entry.getValue().isEmpty())
			{
				for (int attrib : entry.getValue())
				{
					GL2.glEnableVertexAttribArray(attrib);
					
				}
				
			}
			
		}
		
		this.initiated = true;
		
		return true;
	}
	
	@Override
	public void unbind(RenderContext rcon)
	{
		if (!this.isBound(rcon))
		{
			return;
		}
		
		if (!this.vbos.isEmpty())
		{
			for (Entry<VertexBuffer, List<Integer>> entry : this.vbos.entrySet())
			{
				if (!entry.getValue().isEmpty())
				{
					for (int a : entry.getValue())
					{
						GL2.glDisableVertexAttribArray(a);
						
					}
					
				}
				
			}
			
		}
		
		GL3.glBindVertexArray(0);
		
	}
	
	public void attachVBO(VertexBuffer vbo, int... attribs)
	{
		List<Integer> valid = Lists.newArrayList();
		
		if (attribs != null && attribs.length > 0)
		{
			for (int a : attribs)
			{
				boolean found = false;
				
				for (List<Integer> l : this.vbos.values())
				{
					if (l.contains(a))
					{
						found = true;
						
					}
					
				}
				
				if (!found)
				{
					valid.add(a);
					
				}
				
			}
			
		}
		
		this.vbos.put(vbo, valid);
		
	}
	
	public boolean isBound(RenderContext rcon)
	{
		int i = GL1.glGetInteger(GLConst.GL_VERTEX_ARRAY_BINDING);
		
		return i != 0 && i == this.id;
	}
	
}
