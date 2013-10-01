
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.math.Vector2f;

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
	
	public Vector2f getTextureOffset(int corner);
	
	public boolean updateImagePosition(int index, ImageData info);
	
}
