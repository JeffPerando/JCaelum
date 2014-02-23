
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
	protected final List<Light> lights = new SimpleList<Light>(256, false);
	
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
