
package com.elusivehawk.engine.assets;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.elusivehawk.engine.CaelumException;
import com.elusivehawk.util.FileHelper;
import com.elusivehawk.util.storage.SyncList;
import com.elusivehawk.util.task.ITaskListener;
import com.elusivehawk.util.task.Task;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class AssetManager implements ITaskListener
{
	protected final List<IAssetReceiver> receivers = new SyncList<IAssetReceiver>();
	
	protected final Map<EnumAssetType, List<Asset>> assets = Maps.newHashMap();
	
	protected final List<File>
			resLocs = Lists.newArrayList(),
			filesToScan = new SyncList<File>();
	
	protected boolean loaded = false;
	protected String parent = ".";
	
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
		
		List<Asset> assetList = this.assets.get(a.type);
		
		if (assetList == null)
		{
			assetList = SyncList.newList();
			
			this.assets.put(a.type, assetList);
			
		}
		
		if (assetList.contains(a))
		{
			throw new CaelumException("Duplicate asset entry %s!", a);
		}
		
		assetList.add(a);
		
		Iterator<IAssetReceiver> itr = this.receivers.iterator();
		
		while (itr.hasNext())
		{
			itr.next().onAssetLoaded(a);
			
		}
		
	}
	
	public void addSearchDirectory(File dir)
	{
		if (!dir.exists())
		{
			return;
		}
		
		if (!dir.isDirectory())
		{
			return;
		}
		
		if (this.loaded)
		{
			this.resLocs.addAll(FileHelper.getFiles(dir));
			
		}
		else
		{
			this.filesToScan.add(dir);
			
		}
		
	}
	
	public void setDefaultResourceParent(String path)
	{
		assert path != null && !"".equals(path);
		
		this.parent = path;
		
	}
	
	public void addAssetReceiver(IAssetReceiver r)
	{
		this.receivers.add(r);
		
	}
	
	public void removeAssetReceiver(IAssetReceiver r)
	{
		this.receivers.remove(r);
		
	}
	
	public void dropAsset(Asset a)
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
	
	protected Asset getExistingAsset(String filename, EnumAssetType type)
	{
		if (!this.assets.isEmpty())
		{
			List<Asset> assetList = this.assets.get(type);
			
			if (assetList == null)
			{
				return null;
			}
			
			for (Asset a : assetList)
			{
				if (filename.endsWith(a.filepath))
				{
					return a;
				}
				
			}
			
		}
		
		return null;
	}
	
	protected File getFile(String loc)
	{
		for (File file : this.resLocs)
		{
			if (file.getPath().endsWith(loc))
			{
				return file;
			}
			
		}
		
		return new File(this.parent, loc);
	}
	
}
