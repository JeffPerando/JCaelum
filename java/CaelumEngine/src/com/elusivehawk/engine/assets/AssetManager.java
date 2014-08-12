
package com.elusivehawk.engine.assets;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import com.elusivehawk.engine.CaelumException;
import com.elusivehawk.util.FileHelper;
import com.elusivehawk.util.task.ITaskListener;
import com.elusivehawk.util.task.Task;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class AssetManager implements ITaskListener
{
	protected final List<IAssetReceiver> receivers = Lists.newArrayList();
	
	protected final List<Asset> assets = Lists.newArrayList();
	
	protected final List<File>
			resLocs = Lists.newArrayList(),
			filesToScan = Lists.newArrayList();
	
	protected boolean loaded = false;
	
	public AssetManager(){}
	
	@Override
	public void onTaskComplete(Task task)
	{
		if (!(task instanceof TaskLoadAsset))
		{
			return;
		}
		
		TaskLoadAsset t = (TaskLoadAsset)task;
		
		Asset a = t.getCompleteAsset();
		
		if (a == null)
		{
			throw new NullPointerException("Asset is null!");
		}
		
		if (this.assets.contains(a))
		{
			throw new CaelumException("Duplicate asset entry %s!", a);
		}
		
		synchronized (this)
		{
			this.assets.add(a);
			
		}
		
		Iterator<IAssetReceiver> itr = this.receivers.iterator();
		
		while (itr.hasNext())
		{
			itr.next().onAssetLoaded(a);
			
		}
		
	}
	
	public synchronized void addSearchDirectory(File dir)
	{
		if (!dir.exists())
		{
			return;
		}
		
		if (!dir.isDirectory())
		{
			return;
		}
		
		this.filesToScan.add(dir);
		
	}
	
	public synchronized void addAssetReceiver(IAssetReceiver r)
	{
		this.receivers.add(r);
		
	}
	
	public synchronized void removeAssetReceiver(IAssetReceiver r)
	{
		this.receivers.remove(r);
		
	}
	
	public synchronized void dropAsset(Asset a)
	{
		if (a == null)
		{
			throw new NullPointerException();
		}
		
		this.assets.remove(a);
	}
	
	public void initiate()
	{
		if (this.loaded)
		{
			return;
		}
		
		for (File file : this.filesToScan)
		{
			this.resLocs.addAll(FileHelper.getFiles(file));
			
		}
		
		this.loaded = true;
		
	}
	
	protected Asset getExistingAsset(String filename)
	{
		if (!this.assets.isEmpty())
		{
			for (Asset a : this.assets)
			{
				if (filename.endsWith(a.filepath))
				{
					return a;
				}
				
			}
			
		}
		
		return null;
	}
	
	protected File findFile(String loc)
	{
		for (File file : this.resLocs)
		{
			if (file.getPath().endsWith(loc))
			{
				return file;
			}
			
		}
		
		return null;
	}
	
}
