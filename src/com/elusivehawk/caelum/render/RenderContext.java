
package com.elusivehawk.caelum.render;

import java.io.Closeable;
import java.util.List;
import com.elusivehawk.caelum.Display;
import com.elusivehawk.caelum.render.gl.GL1;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.glsl.GLSLEnumShaderType;
import com.elusivehawk.caelum.render.glsl.ShaderAsset;
import com.elusivehawk.caelum.render.glsl.Shaders;
import com.elusivehawk.caelum.render.tex.Color;
import com.elusivehawk.caelum.render.tex.ITexture;
import com.elusivehawk.caelum.render.tex.PixelGrid;
import com.elusivehawk.caelum.render.tex.TextureImage;
import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.math.MathHelper;
import com.elusivehawk.util.storage.DirtableStorage;
import com.elusivehawk.util.storage.Tuple;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class RenderContext implements Closeable, IUpdatable
{
	public static final int CLEAR_BITS = GLConst.GL_COLOR_BUFFER_BIT | GLConst.GL_DEPTH_BUFFER_BIT | GLConst.GL_STENCIL_BUFFER_BIT;
	
	private final Display display;
	private final IRenderable renderer;
	
	private final Shaders
				shaders = new Shaders(),
				shaders2d = new Shaders();
	
	private final DirtableStorage<Boolean> flipScreen = new DirtableStorage<Boolean>(false).setEnableNull(false);
	private final DirtableStorage<ICamera> cameraStorage = new DirtableStorage<ICamera>();
	
	private final List<IDeletable> deletables = Lists.newArrayList();
	private final List<IPreRenderer> preRenderers = Lists.newArrayList();
	private final List<IPostRenderer> postRenderers = Lists.newArrayList();
	
	private boolean initiated = false, rendering = false;
	
	private double delta = 0;
	
	private ITexture notex = null;
	
	private String glVersion, glVendor, glRenderer;
	private int maxTexCount = 0, renders = 0;
	
	private List<Tuple<ITexture, Integer>> texBinds = Lists.newArrayList();
	private int nextTex = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public RenderContext(Display d, IRenderable r)
	{
		assert d != null;
		assert r != null;
		
		display = d;
		renderer = r;
		
		preRenderers.add(r);
		postRenderers.add(r);
		
	}
	
	//XXX Internal methods
	
	@Override
	public void update(double delta) throws RenderException
	{
		if (!this.initiated)
		{
			throw new RenderException("Cannot render: Not initiated!");
		}
		
		if (this.rendering)
		{
			throw new RenderException("Already rendering! Use renderGame() you plebeian!");
		}
		
		this.rendering = true;
		
		this.delta = delta;
		
		try
		{
			GL1.glClear(CLEAR_BITS);
			
			this.preRenderers.forEach(((preR) -> {preR.preRender(this);}));
			
			this.renderGame();
			
			this.postRenderers.forEach(((postR) -> {postR.postRender(this);}));
			
		}
		catch (Throwable e)
		{
			throw e;
		}
		finally
		{
			if (this.flipScreen.isDirty())
			{
				this.flipScreen.setIsDirty(false);
				
			}
			
			if (this.cameraStorage.isDirty())
			{
				this.cameraStorage.setIsDirty(false);
				
			}
			
			this.rendering = false;
			
		}
		
	}
	
	@Override
	public void close()
	{
		this.deletables.forEach(((gl) -> {gl.delete(this);}));
		
		this.deletables.clear();
		
	}
	
	public boolean initContext()
	{
		if (this.initiated)
		{
			return false;
		}
		
		Logger.debug("OpenGL version: %s", this.glVersion = GL1.glGetString(GLConst.GL_VERSION));
		Logger.debug("OpenGL vendor: %s", this.glVendor = GL1.glGetString(GLConst.GL_VENDOR));
		Logger.debug("OpenGL renderer: %s", this.glRenderer = GL1.glGetString(GLConst.GL_RENDERER));
		Logger.debug("OpenGL texture count: %s", this.maxTexCount = GL1.glGetInteger(GLConst.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS));
		
		GL1.glViewport(this.display);
		GL1.glClearColor(Color.WHITE);
		
		for (GLSLEnumShaderType sh : GLSLEnumShaderType.values())
		{
			this.shaders.addShader(new ShaderAsset(String.format("/res/shaders/%s.glsl", sh.name().toLowerCase()), sh, true));
			this.shaders2d.addShader(new ShaderAsset(String.format("/res/shaders/%s2d.glsl", sh.name().toLowerCase()), sh, true));
			
		}
		
		this.notex = new TextureImage(new PixelGrid(16, 16, ((grid) ->
		{
			for (int x = 0; x < grid.getWidth(); x++)
			{
				for (int y = 0; y < grid.getHeight(); y++)
				{
					grid.setPixel(x, y, MathHelper.isOdd(x) && MathHelper.isOdd(y) ? Color.PINK : Color.BLACK);
					
				}
				
			}
			
		})).scale(2));
		
		this.initiated = true;
		
		return true;
	}
	
	//XXX End internal methods
	
	//XXX Hooks
	
	public void renderGame(ICamera cam) throws RenderException
	{
		assert cam != null;
		
		ICamera cam_tmp = this.cameraStorage.get();
		
		this.cameraStorage.set(cam);
		
		try
		{
			this.renderGame();
			
		}
		catch (RenderException e)
		{
			throw e;
		}
		finally
		{
			this.cameraStorage.set(cam_tmp);
			
		}
		
	}
	
	public void renderGame() throws RenderException
	{
		if (this.renders == RenderConst.RECURSIVE_LIMIT)
		{
			return;
		}
		
		this.renders++;
		
		try
		{
			this.renderer.render(this);
			
		}
		catch (RenderException e)
		{
			throw e;
		}
		finally
		{
			this.renders--;
			
		}
		
	}
	
	public synchronized void onScreenFlipped(boolean flip)
	{
		this.flipScreen.set(flip);
		
	}
	
	public int bindTexture(ITexture tex)
	{
		if (tex == null)
		{
			return -1;
		}
		
		for (Tuple<ITexture, Integer> boundTex : this.texBinds)
		{
			if (boundTex.one == tex)
			{
				return boundTex.two;
			}
			
		}
		
		if (this.nextTex == this.maxTexCount)
		{
			return -1;
		}
		
		int ret = this.nextTex;
		
		GL1.glActiveTexture(GLConst.GL_TEXTURE0 + this.nextTex);
		GL1.glBindTexture(tex);
		
		this.texBinds.add(Tuple.create(tex, this.nextTex));
		this.nextTex++;
		
		return ret;
	}
	
	public void releaseTextures()
	{
		this.texBinds.forEach(((tuple) ->
		{
			GL1.glActiveTexture(GLConst.GL_TEXTURE0 + tuple.two);
			GL1.glBindTexture(tuple.one.getType(), 0);
			
		}));
		
		this.texBinds.clear();
		this.nextTex = 0;
		
	}
	
	//XXX End hooks
	
	//XXX Getters
	
	public Display getDisplay()
	{
		return this.display;
	}
	
	public double getDelta()
	{
		return this.delta;
	}
	
	public Shaders getDefaultShaders()
	{
		return this.shaders;
	}
	
	public Shaders get2DShaders()
	{
		return this.shaders2d;
	}
	
	public ITexture getDefaultTexture()
	{
		return this.notex;
	}
	
	public int getMaxTextureCount()
	{
		return this.maxTexCount;
	}
	
	public String getGLVendor()
	{
		return this.glVendor;
	}
	
	public String getGLVersion()
	{
		return this.glVersion;
	}
	
	public String getGLRenderer()
	{
		return this.glRenderer;
	}
	
	public int getRenderCount()
	{
		return this.renders;
	}
	
	public boolean isScreenFlipped()
	{
		return this.flipScreen.get();
	}
	
	public boolean doUpdateScreenFlipUniform()
	{
		return this.flipScreen.isDirty();
	}
	
	public ICamera getCamera()
	{
		return this.cameraStorage.get();
	}
	
	public boolean doUpdateCamera()
	{
		return this.cameraStorage.isDirty();
	}
	
	//XXX End getters
	
	//XXX Setters and registries
	
	public void registerDeletable(IDeletable d)
	{
		if (!this.deletables.contains(d))
		{
			this.deletables.add(d);
			
		}
		
	}
	
	public void removeDeletable(IDeletable d)
	{
		this.deletables.remove(d);
		
	}
	
	public void registerRenderer(IRenderable r)
	{
		this.registerPreRenderer(r);
		this.registerPostRenderer(r);
		
	}
	
	public void registerPreRenderer(IPreRenderer preR)
	{
		if (!this.preRenderers.contains(preR))
		{
			this.preRenderers.add(preR);
			
		}
		
	}
	
	public void registerPostRenderer(IPostRenderer postR)
	{
		if (!this.postRenderers.contains(postR))
		{
			this.postRenderers.add(postR);
			
		}
		
	}
	
	public void setCamera(ICamera cam)
	{
		assert cam != null;
		
		this.cameraStorage.set(cam);
		
	}
	
	//XXX End setters and registries
	
}
