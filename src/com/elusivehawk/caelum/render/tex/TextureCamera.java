
package com.elusivehawk.caelum.render.tex;

import com.elusivehawk.caelum.render.ICamera;
import com.elusivehawk.caelum.render.IDisplay;
import com.elusivehawk.caelum.render.RenderContext;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TextureCamera extends RenderableTexture
{
	private final ICamera cam;
	
	@SuppressWarnings("unqualified-field-access")
	public TextureCamera(ICamera camera)
	{
		assert camera != null;
		
		cam = camera;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TextureCamera(ICamera camera, boolean depth)
	{
		super(depth);
		
		assert camera != null;
		
		cam = camera;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TextureCamera(ICamera camera, IDisplay display)
	{
		super(display);
		
		assert camera != null;
		
		cam = camera;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TextureCamera(ICamera camera, IDisplay display, boolean depth)
	{
		super(display, depth);
		
		assert camera != null;
		
		cam = camera;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TextureCamera(ICamera camera, int width, int height, boolean depth)
	{
		super(width, height, depth);
		
		assert camera != null;
		
		cam = camera;
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		this.cam.preRender(rcon, delta);
		
	}
	
	@Override
	public void renderTexture(RenderContext rcon)
	{
		rcon.renderGame(this.cam);
		
	}
	
}
