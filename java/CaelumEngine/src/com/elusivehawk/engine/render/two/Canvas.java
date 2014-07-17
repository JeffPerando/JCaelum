
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
	protected CanvasLayer currLayer = null;
	
	@SuppressWarnings("unqualified-field-access")
	public Canvas(int layerCount)
	{
		layers = new CanvasLayer[layerCount];
		
	}
	
	public Canvas(int layerCount, int imgs)
	{
		this(layerCount);
		setLayers(imgs);
		
	}
	
	public void setLayer(int layer, int imgs)
	{
		if (this.layers[layer] == null)
		{
			this.layers[layer] = new CanvasLayer(imgs);
			
		}
		
	}
	
	public void setLayers(int imgs)
	{
		for (int c = 0; c < this.layers.length; c++)
		{
			this.setLayer(c, imgs);
			
		}
		
	}
	
	public void setActiveLayer(int layer)
	{
		this.currLayer = this.layers[layer];
		
	}
	
	public int getLayerCount()
	{
		return this.layers.length;
	}
	
}
