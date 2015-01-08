
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.assets.Asset;
import com.elusivehawk.caelum.assets.EnumAssetType;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class GraphicAsset extends Asset implements IDeletable
{
	protected boolean loaded = false, registered = false;
	
	public GraphicAsset(String path, EnumAssetType aType)
	{
		super(path, aType);
		
	}
	
	public GraphicAsset(String path, EnumAssetType aType, boolean readNow)
	{
		super(path, aType, readNow);
		
	}
	
	@Override
	public void onExistingAssetFound(Asset a)
	{
		super.onExistingAssetFound(a);
		
		if (a instanceof GraphicAsset)
		{
			GraphicAsset g = (GraphicAsset)a;
			
			this.registered = g.registered;
			this.loaded = g.loaded;
			
		}
		
	}
	
	public boolean isLoaded()
	{
		return this.loaded && this.isRead();
	}
	
}
