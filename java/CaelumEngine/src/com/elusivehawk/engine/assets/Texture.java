
package com.elusivehawk.engine.assets;

import com.elusivehawk.engine.render.ILegibleImage;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Texture extends Asset
{
	protected int tex = 0;
	
	protected Texture(String filename)
	{
		super(filename);
		
	}
	
	public int getTexId()
	{
		return this.tex;
	}
	
	public void updateTexture(){}
	
	@SuppressWarnings("static-method")
	public int getFrameCount()
	{
		return 0;
	}
	
	@SuppressWarnings("static-method")
	public boolean isAnimated()
	{
		return false;
	}
	
	@SuppressWarnings({"static-method", "unused"})
	public ILegibleImage getSourceImg(int frame)
	{
		return null;
	}
	
}
