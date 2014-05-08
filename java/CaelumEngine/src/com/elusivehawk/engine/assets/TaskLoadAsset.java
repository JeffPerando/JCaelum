
package com.elusivehawk.engine.assets;

import java.io.File;
import com.elusivehawk.engine.util.FileHelper;
import com.elusivehawk.engine.util.task.ITaskListener;
import com.elusivehawk.engine.util.task.Task;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TaskLoadAsset extends Task
{
	protected final AssetManager mgr;
	protected final String assetLoc;
	
	protected Asset fin = null;
	
	@SuppressWarnings("unqualified-field-access")
	public TaskLoadAsset(IAssetReceiver r, AssetManager assetMgr, String loc)
	{
		super(new AssetTaskListener(r));
		
		assert assetMgr != null;
		assert loc != null && !loc.isEmpty();
		
		mgr = assetMgr;
		assetLoc = loc;
		
	}
	
	@Override
	protected boolean finishTask() throws Throwable
	{
		Asset a = this.mgr.getExistingAsset(this.assetLoc);
		
		if (a != null)
		{
			this.fin = a;
			
			return true;
		}
		
		File file = this.mgr.findFile(this.assetLoc);
		
		if (!FileHelper.canReadFile(file))
		{
			return false;
		}
		
		IAssetReader r = this.mgr.getReader(file);
		
		if (r == null)
		{
			return false;
		}
		
		a = r.readAsset(this.mgr, file);
		
		if (a == null)
		{
			return false;
		}
		
		this.fin = a;
		this.mgr.registerAsset(a);
		
		return true;
	}
	
	public Asset getCompleteAsset()
	{
		return this.fin;
	}
	
	public static class AssetTaskListener implements ITaskListener
	{
		private final IAssetReceiver r;
		
		@SuppressWarnings("unqualified-field-access")
		public AssetTaskListener(IAssetReceiver ar)
		{
			assert ar != null;
			
			r = ar;
			
		}
		
		@Override
		public void onTaskComplete(Task task)
		{
			this.r.onAssetLoaded(((TaskLoadAsset)task).getCompleteAsset());
			
		}
		
	}
	
}
