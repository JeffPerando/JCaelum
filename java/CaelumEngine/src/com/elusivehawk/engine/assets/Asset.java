
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
	
	private boolean finished = false;
	
	@SuppressWarnings("unqualified-field-access")
	protected Asset(String filename)
	{
		name = filename;
		
	}
	
	public final boolean isFinished()
	{
		return this.finished;
	}
	
	public final boolean finish()
	{
		return (this.finished = this.finishAsset());
	}
	
	protected abstract boolean finishAsset();
	
}
