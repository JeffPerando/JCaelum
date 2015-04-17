
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.CaelumEngine;
import com.elusivehawk.caelum.IDisposable;
import com.elusivehawk.caelum.render.glsl.Shaders;
import com.elusivehawk.caelum.render.tex.Material;
import com.elusivehawk.util.IPopulator;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class CanvasBufferer implements IRenderer, IDisposable
{
	private Canvas canvas, bufone, buftwo;
	private boolean swap = false;
	
	public CanvasBufferer()
	{
		this(new Shaders());
		
	}
	
	public CanvasBufferer(IPopulator<CanvasBufferer> pop)
	{
		this();
		
		pop.populate(this);
		
	}
	
	public CanvasBufferer(Shaders shs)
	{
		this(shs, CaelumEngine.defaultWindow());
		
	}
	
	public CanvasBufferer(Shaders shs, IPopulator<CanvasBufferer> pop)
	{
		this(shs);
		
		pop.populate(this);
		
	}
	
	public CanvasBufferer(int width, int height)
	{
		this(new Shaders(), width, height);
		
	}
	
	public CanvasBufferer(int width, int height, IPopulator<CanvasBufferer> pop)
	{
		this(width, height);
		
		pop.populate(this);
		
	}
	
	public CanvasBufferer(Shaders shs, Window window)
	{
		this(shs, window.getWidth(), window.getHeight());
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public CanvasBufferer(Shaders shs, int width, int height)
	{
		canvas = new Canvas(shs, width, height);
		bufone = new Canvas(shs, width, height);
		buftwo = new Canvas(shs, width, height);
		
	}
	
	public CanvasBufferer(Shaders shs, int width, int height, IPopulator<CanvasBufferer> pop)
	{
		this(shs, width, height);
		
		pop.populate(this);
		
	}
	
	@Override
	public void dispose()
	{
		this.canvas.dispose();
		this.bufone.dispose();
		this.buftwo.dispose();
		
	}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
		this.canvas.render(rcon);
		
	}
	
	@Override
	public void preRender(RenderContext rcon)
	{
		if (this.swap)
		{
			Canvas cvs = this.canvas;
			
			synchronized (this)
			{
				this.canvas = this.buftwo;
				this.buftwo = cvs;
				
			}
			
			this.swap = false;
			
		}
		
		this.canvas.preRender(rcon);
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		this.canvas.postRender(rcon);
		
	}
	
	public Canvas getCanvas()
	{
		return this.bufone;
	}
	
	public synchronized void swapBuffers()
	{
		Canvas buf_tmp = this.bufone;
		
		this.bufone = this.buftwo;
		this.buftwo = buf_tmp;
		
		this.swap = true;
		
	}
	
	public void setMaterial(Material m)
	{
		this.canvas.setMaterial(m);
		this.bufone.setMaterial(m);
		this.buftwo.setMaterial(m);
		
	}
	
	public void setMaterial(int layer, Material m)
	{
		this.canvas.setMaterial(layer, m);
		this.bufone.setMaterial(layer, m);
		this.buftwo.setMaterial(layer, m);
		
	}
	
}
