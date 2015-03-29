
package com.elusivehawk.caelum.prefab;

import com.elusivehawk.util.math.VectorF;
import com.elusivehawk.util.parse.json.JsonArray;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Position extends VectorF implements IComponent
{
	public Position(){}
	
	public Position(int length)
	{
		super(length);
		
	}
	
	public Position(float... info)
	{
		super(info);
		
	}
	
	public Position(VectorF vec)
	{
		super(vec);
		
	}
	
	public Position(JsonArray json)
	{
		super(json);
		
	}
	
	@Override
	public void dispose(){}
	
	@Override
	public void initiate(Entity parent){}
	
}
