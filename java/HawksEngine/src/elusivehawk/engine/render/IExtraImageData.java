
package elusivehawk.engine.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IExtraImageData
{
	public ITexture getTexture();
	
	public Color getColor(int corner);
	
	public boolean updateImagePosition(int index, ImageData info);
	
}
