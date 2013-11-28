
package com.elusivehawk.engine.render;

import java.io.File;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ModelFile extends Model
{
	public ModelFile(File file)
	{
		super(file);
		
	}
	
	@Override
	public void loadData(Object... objs)
	{
		File file = (File)objs[0];
		
		if (file == null)
		{
			return;
		}
		
		if (!file.exists())
		{
			return;
		}
		
		if (!file.isFile())
		{
			return;
		}
		
		if (!file.canRead())
		{
			return;
		}
		
		for (EnumModelFormat mf : EnumModelFormat.values())
		{
			if (mf.isValid(file))
			{
				mf.load(this, file);
				
			}
			
		}
		
	}
	
}
