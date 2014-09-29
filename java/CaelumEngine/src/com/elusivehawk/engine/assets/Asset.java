
package com.elusivehawk.engine.assets;

import java.io.InputStream;
import com.elusivehawk.engine.CaelumEngine;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Asset
{
	public final String filepath;
	public final EnumAssetType type;
	
	private boolean read = false;
	
	protected Asset(String path)
	{
		this(path, EnumAssetType.OTHER);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	protected Asset(String path, EnumAssetType aType)
	{
		assert path != null && !"".equals(path);
		assert aType != null;
		
		filepath = path;
		type = aType;
		
		CaelumEngine.tasks().scheduleTask(new TaskLoadAsset(this));
		
	}
	
	@Override
	public String toString()
	{
		return this.filepath;
	}
	
	public final boolean isRead()
	{
		return this.read;
	}
	
	public final boolean read(InputStream is) throws Throwable
	{
		return this.read ? true : (this.read = this.readAsset(is));
	}
	
	protected abstract boolean readAsset(InputStream is) throws Throwable;
	
}
