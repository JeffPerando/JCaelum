
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.prefab.Rectangle;
import com.elusivehawk.caelum.render.gl.GLProgram;
import com.elusivehawk.caelum.render.glsl.Shaders;
import com.elusivehawk.caelum.render.tex.Material;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.storage.Buffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Canvas extends RenderableObj
{
	public static final int INDICES_PER_IMG = 6;
	public static final int FLOATS_PER_INDEX = 4;
	public static final int FLOATS_PER_IMG = FLOATS_PER_INDEX * INDICES_PER_IMG;
	
	private final Buffer<CanvasLayer> layers = new Buffer<CanvasLayer>();
	private CanvasLayer currentLayer = null;
	
	private boolean correctCoords = true;
	
	public Canvas()
	{
		this(1);
		
	}
	
	public Canvas(IPopulator<Canvas> pop)
	{
		this();
		
		pop.populate(this);
		
	}
	
	public Canvas(int layerCount)
	{
		this(new GLProgram(), layerCount);
		
	}
	
	public Canvas(int layerCount, IPopulator<Canvas> pop)
	{
		this(layerCount);
		
		pop.populate(this);
		
	}
	
	public Canvas(Shaders shs)
	{
		this(shs, 1);
		
	}
	
	public Canvas(Shaders shs, IPopulator<Canvas> pop)
	{
		this(shs);
		
		pop.populate(this);
		
	}
	
	public Canvas(Shaders shs, int layerCount)
	{
		this(new GLProgram(shs), layerCount);
		
	}
	
	public Canvas(Shaders shs, int layerCount, IPopulator<Canvas> pop)
	{
		this(shs, layerCount);
		
		pop.populate(this);
		
	}
	
	public Canvas(GLProgram program)
	{
		this(program, 1);
		
	}
	
	public Canvas(GLProgram program, IPopulator<Canvas> pop)
	{
		this(program);
		
		pop.populate(this);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Canvas(GLProgram p, int layerCount)
	{
		super(p);
		
		zBuffer = false;
		
		do
		{
			createLayer();
			
		}
		while (layers.size() < layerCount);
		
	}
	
	public Canvas(GLProgram program, int layers, IPopulator<Canvas> pop)
	{
		this(program, layers);
		
		pop.populate(this);
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		this.layers.forEach(((layer) -> {layer.postRender(rcon);}));
		
	}
	
	@Override
	protected boolean initiate(RenderContext rcon)
	{
		if (this.program.shaderCount() == 0)
		{
			return this.program.attachShaders(rcon.get2DShaders()) > 0;
		}
		
		return true;
	}
	
	@Override
	protected boolean doRender(RenderContext rcon) throws RenderException
	{
		boolean ret = false;
		
		for (int c = 0; c < this.layers.size(); c++)
		{
			if (this.layers.get(c).render(rcon) && !ret)
			{
				ret = true;
				
			}
			
		}
		
		return ret;
	}
	
	@Override
	public void setMaterial(Material mat)
	{
		this.currentLayer.setMaterial(mat);
		
	}
	
	@Override
	public void preRender(RenderContext rcon)
	{
		this.layers.forEach(((layer) -> {layer.preRender(rcon);}));
		
	}
	
	@SuppressWarnings("sync-override")
	@Override
	public RenderableObj setEnableZBuffer(boolean z)
	{
		throw new UnsupportedOperationException("Canvases cannot change their Z buffering status, you nincompoop.");
	}
	
	public int getImageCount()
	{
		return this.currentLayer.getImageCount();
	}
	
	public int getLayerCount()
	{
		return this.layers.size();
	}
	
	public boolean doCoordCorrection()
	{
		return this.correctCoords;
	}
	
	public void setSubCanvas(float xmin, float ymin, float xmax, float ymax)
	{
		this.setSubCanvas(new Rectangle(xmin, ymin, xmax, ymax));
		
	}
	
	public void setSubCanvas(Rectangle r)
	{
		this.currentLayer.setSubCanvas(r);
		
	}
	
	public Canvas setEnableCoordCorrection(boolean b)
	{
		this.correctCoords = b;
		
		return this;
	}
	
	public void createLayer()
	{
		this.currentLayer = new CanvasLayer(this);
		
		this.layers.add(this.currentLayer);
		
	}
	
	public boolean nextLayer()
	{
		if (this.layers.hasNext())
		{
			this.currentLayer = this.layers.next();
			
			return true;
		}
		
		return false;
	}
	
	public void setLayer(int layer)
	{
		this.currentLayer = this.layers.get(layer);
		
	}
	
	public void clear()
	{
		this.currentLayer = this.layers.get(0);
		this.layers.position(0);
		
		this.layers.forEach(((layer) ->
		{
			layer.clear();
			
		}));
		
	}
	
	public void clearCurrentLayer()
	{
		this.currentLayer.clear();
		
	}
	
	public void drawImage(float x, float y, float z, float w)
	{
		this.drawImage(new Rectangle(x, y, z, w));
		
	}
	
	public void drawImage(float x, float y, float z, float w, Icon icon)
	{
		this.drawImage(new Rectangle(x, y, z, w), icon);
		
	}
	
	public void drawImage(Rectangle r)
	{
		this.drawImage(r, Icon.BLANK_ICON);
		
	}
	
	public void drawImage(Rectangle r, Icon icon)
	{
		this.currentLayer.drawImage(r, icon);
		
	}
	
}
