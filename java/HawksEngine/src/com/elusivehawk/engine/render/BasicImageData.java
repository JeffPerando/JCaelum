
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.math.Vector2f;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class BasicImageData implements IExtraImageData
{
	protected final Color col;
	protected final Vector2f[] texOffs = {new Vector2f(0f, 0f), new Vector2f(1f, 0f), new Vector2f(0f, 1f), new Vector2f(1f, 1f)};
	protected boolean delete = false;
	
	public BasicImageData()
	{
		this(new Color(EnumColorFormat.RGBA));
		
	}
	
	public BasicImageData(Color color)
	{
		col = color;
		
	}
	
	@Override
	public Color getColor(int corner)
	{
		return this.col;
	}
	
	@Override
	public Vector2f getTextureOffset(int corner)
	{
		return this.texOffs[corner];
	}
	
	@Override
	public boolean updateImagePosition(int index, ImageData info)
	{
		return false;
	}
	
	@Override
	public boolean flaggedForDeletion()
	{
		return this.delete;
	}
	
	public void flagForDeletion()
	{
		this.delete = true;
		
	}
	
}
