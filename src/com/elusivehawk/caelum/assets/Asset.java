
package com.elusivehawk.caelum.assets;

import java.io.DataInputStream;
import com.elusivehawk.caelum.CaelumEngine;
import com.elusivehawk.util.Internal;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.parse.ParseHelper;
import com.elusivehawk.util.parse.json.IJsonSerializer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Asset implements IJsonSerializer
{
	public final String filepath, ext;
	public final EnumAssetType type;
	
	private boolean read = false;
	
	protected Asset(String path)
	{
		this(path, EnumAssetType.OTHER);
		
	}
	
	protected Asset(String path, EnumAssetType aType)
	{
		this(path, aType, false);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	protected Asset(String path, EnumAssetType aType, boolean readNow)
	{
		assert path != null && !path.equals("");
		assert aType != null;
		
		filepath = path;
		type = aType;
		ext = ParseHelper.getSuffix(path, ".");
		
		if (readNow)
		{
			try
			{
				CaelumEngine.assets().readAsset(this);
				
			}
			catch (Throwable e)
			{
				Logger.err("Failed to read asset %s", e, this);
				
			}
			
		}
		else
		{
			CaelumEngine.tasks().scheduleTask(new TaskLoadAsset(this));
			
		}
		
	}
	
	@Override
	public String toJson(int tabs)
	{
		return this.toString();
	}
	
	@Override
	public String toString()
	{
		return String.format("\"%s\"", this.filepath);
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
