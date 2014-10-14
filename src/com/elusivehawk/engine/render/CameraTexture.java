
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.opengl.GLFramebuffer;

/**
 * 
 * Renders the game scene, and copies what it renders to a texture.
 * <p>
 * NOTICE: NOT STATIC!
 * 
 * @author Elusivehawk
 */
public class CameraTexture implements IFramebufferTexture
{
	private final ICamera cam;
	private final GLFramebuffer framebuf;
	
	private boolean rendered = false;
	
	public CameraTexture(ICamera camera)
	{
		this(camera, true);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public CameraTexture(ICamera camera, boolean depth)
	{
		assert camera != null;
		
		cam = camera;
		framebuf = new GLFramebuffer(depth);
		
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
	public void postRender(RenderContext rcon)
	{
		this.rendered = false;
		
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
	
}
