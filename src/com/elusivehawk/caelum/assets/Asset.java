
package com.elusivehawk.caelum.assets;

import java.io.DataInputStream;
import com.elusivehawk.caelum.CaelumEngine;
import com.elusivehawk.util.Internal;
import com.elusivehawk.util.string.StringHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Asset
{
	public final String filepath, ext;
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
		ext = StringHelper.getSuffix(path, ".");
		
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
	
	@Internal
	public void onExistingAssetFound(Asset a)
	{
		this.read = a.read;
		
	}
	
	@Internal
	public final boolean read(DataInputStream in) throws Throwable
	{
		return this.read ? true : (this.read = this.readAsset(in));
	}
	
	protected abstract boolean readAsset(DataInputStream in) throws Throwable;
	
}
