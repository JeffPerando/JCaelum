
package com.elusivehawk.caelum.render.tex;

import com.elusivehawk.caelum.render.ICamera;
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
	
	public TextureCamera(ICamera camera)
	{
		this(camera, true);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TextureCamera(ICamera camera, boolean depth)
	{
		super(depth);
		
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
