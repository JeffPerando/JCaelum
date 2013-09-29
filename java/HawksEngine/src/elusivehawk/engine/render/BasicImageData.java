
package elusivehawk.engine.render;

import elusivehawk.engine.math.Vector2f;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class BasicImageData implements IExtraImageData
{
	protected final ITexture tex;
	protected final Color col;
	protected final Vector2f[] texOffs = {new Vector2f(0f, 0f), new Vector2f(1f, 0f), new Vector2f(0f, 1f), new Vector2f(1f, 1f)};
	
	public BasicImageData(ITexture texture)
	{
		this(texture, new Color());
		
	}
	
	public BasicImageData(ITexture texture, Color color)
	{
		tex = texture;
		col = color;
		
	}
	
	@Override
	public ITexture getTexture()
	{
		return this.tex;
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
	
}
