
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
public class Canvas extends ProgramRenderable
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
		
		currentLayer = layers.get(0);
		
	}
	
	public Canvas(GLProgram program, int layers, IPopulator<Canvas> pop)
	{
		this(program, layers);
		
		pop.populate(this);
		
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
	protected void doRender(RenderContext rcon) throws RenderException
	{
		this.layers.forEach(((layer) ->
		{
			layer.render(rcon);
			
		}));
		
	}
	
	@Override
	public void setMaterial(Material mat)
	{
		this.currentLayer.setMaterial(mat);
		
	}
	
	@Override
	public void preRenderImpl(RenderContext rcon)
	{
		this.layers.forEach(((layer) ->
		{
			layer.preRender(rcon);
			
		}));
		
	}
	
	@Override
	public void postRenderImpl(RenderContext rcon)
	{
		this.layers.forEach(((layer) ->
		{
			layer.postRender(rcon);
			
		}));
		
	}
	
	@Override
	public void dispose(Object... args)
	{
		this.layers.forEach(((layer) ->
		{
			layer.dispose(args);
			
		}));
		
	}
	
	@SuppressWarnings("sync-override")
	@Override
	public ProgramRenderable setEnableZBuffer(boolean z)
	{
		throw new UnsupportedOperationException("Canvases cannot change their Z buffering status, you nincompoop.");
	}
	
	public Material getMaterial()
	{
		return this.currentLayer.getMaterial();
	}
	
	public Material getMaterial(int layer)
	{
		return this.layers.get(layer).getMaterial();
	}
	
	public int getImageCount()
	{
		return this.currentLayer.getImageCount();
	}
	
	public int getImageCount(int layer)
	{
		return this.layers.get(layer).getImageCount();
	}
	
	public int getLayerCount()
	{
		return this.layers.size();
	}
	
	public boolean doCoordCorrection()
	{
		return this.correctCoords;
	}
	
	public void createSubCanvas(Rectangle r)
	{
		this.createSubCanvas(r.x, r.y, r.z, r.w);
		
	}
	
	public void createSubCanvas(float xmin, float ymin, float xmax, float ymax)
	{
		this.currentLayer.createSubCanvas(xmin, ymin, xmax, ymax);
		
	}
	
	public void setEnableCoordCorrection(boolean b)
	{
		this.correctCoords = b;
		
	}
	
	public void setMaterial(int layer, Material m)
	{
		this.layers.get(layer).setMaterial(m);
		
	}
	
	public void createLayer()
	{
		this.layers.add(new CanvasLayer(this));
		
	}
	
	public void nextLayer()
	{
		this.currentLayer = this.layers.next();
		
	}
	
	public boolean hasNextLayer()
	{
		return this.layers.hasNext();
	}
	
	public void setLayer(int layer)
	{
		this.currentLayer = this.layers.get(layer);
		
	}
	
	public void clear()
	{
		this.currentLayer = this.layers.get(0);
		this.layers.position(0);
		
		for (int c = 0; c < this.layers.size(); c++)
		{
			this.layers.get(c).clear();
			
		}
		
	}
	
	public void clearLayer()
	{
		this.currentLayer.clear();
		
	}
	
	public void clearLayer(int layer)
	{
		this.layers.get(layer).clear();
		
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
