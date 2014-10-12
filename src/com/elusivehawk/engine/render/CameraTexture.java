
package com.elusivehawk.engine.render;

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
	
	private volatile int tex = 0;
	private boolean rendered = false;
	
	@SuppressWarnings("unqualified-field-access")
	public CameraTexture(ICamera camera)
	{
		assert camera != null;
		
		cam = camera;
		
	}

	@Override
	public int getTexId()
	{
		return this.tex;
	}
	
	@Override
	public boolean isAnimated()
	{
		return false;
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		if (this.tex != 0)
		{
			rcon.getGL1().glDeleteTextures(this.tex);
			
		}
		
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
		
		// TODO Finish
		
		rcon.renderGame(this.cam);
		
		this.rendered = true;
		
	}
	
}
