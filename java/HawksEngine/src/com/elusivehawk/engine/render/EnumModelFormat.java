
package com.elusivehawk.engine.render;

import java.io.File;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum EnumModelFormat
{
	OBJ(null),
	BLEND(null);
	
	private final IModelReader mr;
	
	EnumModelFormat(IModelReader reader)
	{
		mr = reader;
		
	}
	
	public boolean isValid(File file)
	{
		return file.getName().endsWith(this.name().toLowerCase());
	}
	
	public void load(Model m, File file)
	{
		this.mr.load(m, file);
		
	}
	
}
