
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.IDisposable;
import com.elusivehawk.caelum.render.gl.GLProgram;
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
		this(shs, 1);
		
	}
	
	public CanvasBufferer(Shaders shs, IPopulator<CanvasBufferer> pop)
	{
		this(shs);
		
		pop.populate(this);
		
	}
	
	public CanvasBufferer(int layers)
	{
		this(new Shaders(), layers);
		
	}
	
	public CanvasBufferer(int layers, IPopulator<CanvasBufferer> pop)
	{
		this(layers);
		
		pop.populate(this);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public CanvasBufferer(Shaders shs, int layers)
	{
		GLProgram p = new GLProgram(shs);
		
		canvas = new Canvas(p, layers);
		bufone = new Canvas(p, layers);
		buftwo = new Canvas(p, layers);
		
	}
	
	public CanvasBufferer(Shaders shs, int layers, IPopulator<CanvasBufferer> pop)
	{
		this(shs, layers);
		
		pop.populate(this);
		
	}
	
	@Override
	public void dispose(Object... args)
	{
		this.canvas.dispose(args);
		this.bufone.dispose(args);
		this.buftwo.dispose(args);
		
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
