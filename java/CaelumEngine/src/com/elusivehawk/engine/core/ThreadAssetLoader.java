
package com.elusivehawk.engine.core;

import com.elusivehawk.engine.util.ThreadStoppable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ThreadAssetLoader extends ThreadStoppable
{
	private final AssetManager assetMgr;
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadAssetLoader(AssetManager mgr)
	{
		assetMgr = mgr;
		
	}
	
	@Override
	public boolean initiate()
	{
		this.assetMgr.initiate();
		
		return this.assetMgr.canLoadAssets();
	}
	
	@Override
	public void rawUpdate() throws Throwable
	{
		this.assetMgr.continueLoadingAssets();
		
	}
	
	@Override
	protected boolean canRun()
	{
		return !this.assetMgr.isFinishedLoading();
	}
	
}
