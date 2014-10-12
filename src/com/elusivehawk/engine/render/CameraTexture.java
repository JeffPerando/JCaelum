
package com.elusivehawk.engine.render;

/**
 * 
 * Renders the game scene, and copies what it renders to a texture.
 * <p>
 * NOTICE: NOT STATIC!
 * 
 * @author Elusivehawk
 */
public class CameraTexture implements ITexture
{
	private final ICamera cam;
	
	private volatile int tex = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public CameraTexture(ICamera camera)
	{
		assert camera != null;
		
		cam = camera;
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		//TODO Finish
		
		rcon.renderGame(this.cam, delta);
		
	}

	@Override
	public void delete(RenderContext rcon)
	{
		rcon.getGL1().glDeleteTextures(this.tex);
		
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
	
}
