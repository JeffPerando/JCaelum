
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.assets.Asset;
import com.elusivehawk.engine.assets.EnumAssetType;
import com.elusivehawk.engine.util.IDirty;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class NonStaticTexture extends Asset implements IDirty
{
	protected boolean dirty = false;
	
	protected NonStaticTexture(String filename)
	{
		super(filename, EnumAssetType.TEXTURE);
		
	}
	
	@Override
	public boolean isDirty()
	{
		return this.dirty;
	}
	
	@Override
	public void setIsDirty(boolean b)
	{
		this.dirty = b;
		
	}
	
	/**
	 * Called once a frame.
	 * 
	 * Note: Should not be called by user code.
	 */
	public abstract void updateTexture();
	
}
