
package com.elusivehawk.engine.assets;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Asset
{
	public final String name;
	
	protected boolean finished = false;
	
	@SuppressWarnings("unqualified-field-access")
	protected Asset(String filename)
	{
		name = filename;
		
	}
	
	public final boolean isFinished()
	{
		return this.finished;
	}
	
	public final void finish()
	{
		this.finished = this.finishAsset();
		
	}
	
	public abstract int[] getIds();
	
	protected abstract boolean finishAsset();
	
}
