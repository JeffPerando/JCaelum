
package com.elusivehawk.engine.render;

import java.util.List;
import com.elusivehawk.engine.util.SimpleList;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class LightMap
{
	public static final int LIGHT_CAP = 1024;
	
	protected final List<Light> lights = SimpleList.newList(LIGHT_CAP, false);
	
	public LightMap()
	{
		
	}
	
	public Light attachLight(Light l)
	{
		if (!this.lights.add(l))
		{
			return null;
		}
		
		return l;
	}
	
}
