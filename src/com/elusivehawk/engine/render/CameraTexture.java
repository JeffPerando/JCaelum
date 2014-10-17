
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.engine.render.opengl.GLFramebuffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class CameraTexture implements IRenderableTexture
{
	private final ICamera cam;
	private final GLFramebuffer framebuf;
	
	private boolean rendered = false;
	
	public CameraTexture(ICamera camera)
	{
		this(camera, true);
		
	}
	
	public CameraTexture(ICamera camera, boolean depth)
	{
		this(camera, depth, CaelumEngine.display());
		
	}
	
	public CameraTexture(ICamera camera, IDisplay display)
	{
		this(camera, true, display);
		
	}
	
	public CameraTexture(ICamera camera, boolean depth, IDisplay display)
	{
		this(camera, depth, display.getWidth(), display.getHeight());
		
	}
	
	public CameraTexture(ICamera camera, int w, int h)
	{
		this(camera, true, w, h);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public CameraTexture(ICamera camera, boolean depth, int w, int h)
	{
		assert camera != null;
		
		cam = camera;
		framebuf = new GLFramebuffer(depth, w, h);
		
	}
	
	@Override
	public int getTexId()
	{
		return this.framebuf.getTexture();
	}
	
	@Override
	public boolean isAnimated()
	{
		return false;
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		this.framebuf.delete(rcon);
		
	}
	
	@Override
	public void render(RenderContext rcon)
	{
		if (this.rendered)
		{
			return;
		}
		
		if (this.framebuf.bind(rcon))
		{
			rcon.renderGame(this.cam);
			
			this.framebuf.unbind(rcon);
			
			this.rendered = true;
			
		}
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		this.cam.preRender(rcon, delta);
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		this.rendered = false;
		
	}
	
	public ICamera getCam()
	{
		return this.cam;
	}
	
}
