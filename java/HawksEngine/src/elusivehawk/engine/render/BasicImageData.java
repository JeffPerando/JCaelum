
package elusivehawk.engine.render;

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
	public boolean updateImagePosition(int index, ImageData info)
	{
		return false;
	}
	
}
