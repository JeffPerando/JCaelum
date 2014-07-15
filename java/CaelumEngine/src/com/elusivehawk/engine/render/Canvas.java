
package com.elusivehawk.engine.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Canvas
{
	protected final Object[] layers;
	
	@SuppressWarnings("unqualified-field-access")
	public Canvas(int layerCount)
	{
		layers = new Object[layerCount];
		
	}
	
}
