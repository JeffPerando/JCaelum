
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.render.glsl.Shaders;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class CanvasBufferer implements IRenderable
{
	private Canvas canvas, bufone, buftwo;
	private boolean swap = false;
	
	public CanvasBufferer()
	{
		this(new Shaders());
		
	}
	
	public CanvasBufferer(Shaders shs)
	{
		this(shs, 1);
		
	}
	
	public CanvasBufferer(int layers)
	{
		this(new Shaders(), layers);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public CanvasBufferer(Shaders shs, int layers)
	{
		canvas = new Canvas(shs, layers);
		bufone = new Canvas(shs, layers);
		buftwo = new Canvas(shs, layers);
		
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
	
	@Override
	public boolean render(RenderContext rcon) throws RenderException
	{
		return this.canvas.render(rcon);
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
	
}
