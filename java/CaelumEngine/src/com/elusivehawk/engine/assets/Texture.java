
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
	protected final ILegibleImage[] frames;
	
	protected Texture(String filename)
	{
		this(filename, 1);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	protected Texture(String filename, ILegibleImage... imgs)
	{
		this(filename, imgs.length);
		
		System.arraycopy(imgs, 0, frames, 0, imgs.length);
		
	}
	
	protected Texture(String filename, int frameCount)
	{
		super(filename);
		this.frames = new ILegibleImage[frameCount];
		
	}
	
	@SuppressWarnings("unused")
	public int getTexId(int frame)
	{
		return this.tex;
	}
	
	public int getFrameCount()
	{
		return this.frames.length;
	}
	
	public boolean isAnimated()
	{
		return this.getFrameCount() > 1;
	}
	
	public ILegibleImage getSourceImg(int frame)
	{
		return this.frames[frame];
	}
	
}
