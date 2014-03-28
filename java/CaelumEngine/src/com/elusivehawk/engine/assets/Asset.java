
package com.elusivehawk.engine.assets;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Asset
{
	public final EnumAssetType type;
	
	protected final String name;
	protected boolean finished = false;
	
	@SuppressWarnings("unqualified-field-access")
	protected Asset(String filename, EnumAssetType atype)
	{
		name = filename;
		type = atype;
		
	}
	
	public final boolean isFinished()
	{
		return this.finished;
	}
	
	public final void finish()
	{
		this.finished = this.finishAsset();
		
	}
	
	public String getName()
	{
		return this.name;
	}
	
	@SuppressWarnings("static-method")
	public Object getAttachment()
	{
		return null;
	}
	
	public abstract int[] getIds();
	
	protected abstract boolean finishAsset();
	
}