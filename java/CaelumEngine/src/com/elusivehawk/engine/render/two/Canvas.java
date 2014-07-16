
package com.elusivehawk.engine.render.two;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Canvas
{
	protected final CanvasLayer[] layers;
	
	@SuppressWarnings("unqualified-field-access")
	public Canvas(int layerCount)
	{
		layers = new CanvasLayer[layerCount];
		
	}
	
}
