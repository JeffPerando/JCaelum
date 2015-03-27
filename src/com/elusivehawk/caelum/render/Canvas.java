
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.CaelumEngine;
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
	
	private final int width, height;
	
	private final Buffer<CanvasLayer> layers = new Buffer<CanvasLayer>();
	private CanvasLayer currentLayer = null;
	
	private boolean correctCoords = true;
	
	public Canvas()
	{
		this(CaelumEngine.defaultDisplay());
		
	}
	
	public Canvas(IPopulator<Canvas> pop)
	{
		this();
		
		pop.populate(this);
		
	}
	
	public Canvas(Display display)
	{
		this(display.getWidth(), display.getHeight());
		
	}
	
	public Canvas(Display display, IPopulator<Canvas> pop)
	{
		this(display);
		
		pop.populate(this);
		
	}
	
	public Canvas(int width, int height)
	{
		this(new GLProgram(), width, height);
		
	}
	
	public Canvas(int width, int height, IPopulator<Canvas> pop)
	{
		this(width, height);
		
		pop.populate(this);
		
	}
	
	public Canvas(Shaders shs)
	{
		this(shs, CaelumEngine.defaultDisplay());
		
	}
	
	public Canvas(Shaders shs, IPopulator<Canvas> pop)
	{
		this(shs);
		
		pop.populate(this);
		
	}
	
	public Canvas(Shaders shs, Display display)
	{
		this(shs, display.getWidth(), display.getHeight());
		
	}
	
	public Canvas(Shaders shs, Display display, IPopulator<Canvas> pop)
	{
		this(shs, display);
		
		pop.populate(this);
		
	}
	
	public Canvas(Shaders shs, int width, int height)
	{
		this(new GLProgram(shs), width, height);
		
	}
	
	public Canvas(Shaders shs, int width, int height, IPopulator<Canvas> pop)
	{
		this(shs, width, height);
		
		pop.populate(this);
		
	}
	
	public Canvas(GLProgram program)
	{
		this(program, CaelumEngine.defaultDisplay());
		
	}
	
	public Canvas(GLProgram program, IPopulator<Canvas> pop)
	{
		this(program);
		
		pop.populate(this);
		
	}
	
	public Canvas(GLProgram program, Display display)
	{
		this(program, display.getWidth(), display.getHeight());
		
	}
	
	public Canvas(GLProgram program, Display display, IPopulator<Canvas> pop)
	{
		this(program, display);
		
		pop.populate(this);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Canvas(GLProgram p, int w, int h)
	{
		super(p);
		
		width = w;
		height = h;
		
		zBuffer = false;
		
		createLayer();
		
		currentLayer = layers.get(0);
		
	}
	
	public Canvas(GLProgram program, int width, int height, IPopulator<Canvas> pop)
	{
		this(program, width, height);
		
		pop.populate(this);
		
	}
	
	@Override
	protected boolean initiate(RenderContext rcon)
	{
		if (this.program.shaderCount() == 0)
		{
			return this.program.attachShaders(RenderConst.SHADERS_2D) > 0;
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
	public void dispose()
	{
		this.layers.forEach(((layer) ->
		{
			layer.dispose();
			
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
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
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
	
	public void createSubCanvas(int xmin, int ymin, int xmax, int ymax)
	{
		this.createSubCanvas((float)xmin / this.width, (float)ymin / this.height, (float)xmax / this.width, (float)ymax / this.height);
		
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
	
	public void drawImage(int x, int y, int z, int w)
	{
		this.drawImage(x, y, z, w, Icon.BLANK_ICON);
		
	}
	
	public void drawImage(int x, int y, int z, int w, Icon icon)
	{
		this.drawImage((float)x / this.width, (float)y / this.height, (float)z / this.width, (float)w / this.height, icon);
		
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
