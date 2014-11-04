
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
public class CameraTexture extends RenderableTexture
{
	private final ICamera cam;
	
	@SuppressWarnings("unqualified-field-access")
	public CameraTexture(ICamera camera)
	{
		cam = camera;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public CameraTexture(ICamera camera, boolean depth)
	{
		super(depth);
		
		cam = camera;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public CameraTexture(ICamera camera, IDisplay display)
	{
		super(display);
		
		cam = camera;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public CameraTexture(ICamera camera, IDisplay display, boolean depth)
	{
		super(display, depth);
		
		cam = camera;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public CameraTexture(ICamera camera, int width, int height, boolean depth)
	{
		super(width, height, depth);
		
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
