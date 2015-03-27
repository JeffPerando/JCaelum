
package com.elusivehawk.caelum.render;

import java.util.List;
import com.elusivehawk.util.storage.SyncList;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Deletables
{
	private static final Deletables INSTANCE = new Deletables();
	
	private final List<IDeletable>
				deletables = Lists.newArrayList(),
				schDeletes = SyncList.newList();
	
	public void register(IDeletable d)
	{
		assert d != null;
		
		this.deletables.add(d);
		
	}
	
	public void scheduleDeletion(IDeletable d)
	{
		assert d != null;
		
		this.schDeletes.add(d);
		
	}
	
	public void cleanup()
	{
		if (this.schDeletes.isEmpty())
		{
			return;
		}
		
		this.schDeletes.forEach(((d) -> {d.delete();}));
		this.deletables.removeAll(this.schDeletes);
		this.schDeletes.clear();
		
	}
	
	public static Deletables instance()
	{
		return INSTANCE;
	}
	
}
