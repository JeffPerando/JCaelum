
package com.elusivehawk.engine.render;

/**
 * 
 * @deprecated To be replaced with a simpler imaging system.
 * 
 * @author Elusivehawk
 */
@Deprecated
public interface IExtraImageData
{
	public boolean updateImagePosition(int index, ImageData info);
	
	public boolean flaggedForDeletion();
	
}
