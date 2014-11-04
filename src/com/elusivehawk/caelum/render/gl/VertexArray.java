
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
	private boolean bound = false, initiated = false;
	
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
		
		if (this.bound)
		{
			this.unbind(rcon);
			
		}
		
		for (Entry<VertexBuffer, List<Integer>> entry : this.vbos.entrySet())
		{
			entry.getKey().unbind(rcon);
			
		}
		
		rcon.getGL3().glDeleteVertexArray(this.id);
		
	}
	
	@Override
	public boolean bind(RenderContext rcon)
	{
		if (this.bound)
		{
			return true;
		}
		
		if (this.vbos.isEmpty())
		{
			return false;
		}
		
		if (this.id == 0)
		{
			this.id = rcon.getGL3().glGenVertexArray();
			rcon.registerCleanable(this);
			
		}
		
		rcon.getGL3().glBindVertexArray(this.id);
		
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
					rcon.getGL2().glEnableVertexAttribArray(attrib);
					
				}
				
			}
			
		}
		
		this.bound = true;
		this.initiated = true;
		
		return true;
	}
	
	@Override
	public void unbind(RenderContext rcon)
	{
		if  (!this.bound)
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
						rcon.getGL2().glDisableVertexAttribArray(a);
						
					}
					
				}
				
			}
			
		}
		
		rcon.getGL3().glBindVertexArray(0);
		
		this.bound = false;
		
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
	
}
