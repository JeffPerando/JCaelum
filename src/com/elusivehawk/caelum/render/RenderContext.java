
package com.elusivehawk.caelum.render;

import java.io.Closeable;
import java.util.List;
import com.elusivehawk.caelum.Display;
import com.elusivehawk.caelum.render.gl.GL1;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.gl.GLProgram;
import com.elusivehawk.caelum.render.gl.IGLDeletable;
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
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class RenderContext implements Closeable, IUpdatable
{
	private final Display display;
	private final IRenderable renderer;
	
	private final Shaders
				shaders = new Shaders(),
				shaders2d = new Shaders();
	private final GLProgram p = new GLProgram(this.shaders);
	private final DirtableStorage<Boolean> flipScreen = new DirtableStorage<Boolean>(false).setEnableNull(false);
	
	private final List<IGLDeletable> cleanables = Lists.newArrayList();
	private final List<IPreRenderer> preRenderers = Lists.newArrayList();
	private final List<IPostRenderer> postRenderers = Lists.newArrayList();
	
	private int maxTexCount = 0, renders = 0;
	
	private ITexture notex = null;
	
	private boolean
			initiated = false,
			updateCameraUniforms = true;
	
	private ICamera camera = null;
	
	@SuppressWarnings("unqualified-field-access")
	public RenderContext(Display d, IRenderable r)
	{
		assert d != null;
		assert r != null;
		
		display = d;
		renderer = r;
		
	}
	
	@Override
	public void close()
	{
		this.cleanables.forEach(((gl) -> {gl.delete(this);}));
		
		this.cleanables.clear();
		
	}
	
	@Override
	public void update(double delta)
	{
		try
		{
			GL1.glClear(GLConst.GL_COLOR_BUFFER_BIT | GLConst.GL_DEPTH_BUFFER_BIT | GLConst.GL_STENCIL_BUFFER_BIT);
			
			this.renderer.preRender(this, delta);
			this.preRenderers.forEach(((preR) -> {preR.preRender(this, delta);}));
			
			this.renderGame();
			
			this.renderer.postRender(this);
			this.postRenderers.forEach(((postR) -> {postR.postRender(this);}));
			
		}
		catch (Throwable e)
		{
			Logger.err(e);
			
		}
		
		if (this.flipScreen.isDirty())
		{
			this.flipScreen.setIsDirty(false);
			
		}
		
	}
	
	public boolean initContext()
	{
		if (this.initiated)
		{
			return false;
		}
		
		Logger.verbose("OpenGL version: %s", GL1.glGetString(GLConst.GL_VERSION));
		Logger.verbose("OpenGL vendor: %s", GL1.glGetString(GLConst.GL_VENDOR));
		Logger.verbose("OpenGL renderer: %s", GL1.glGetString(GLConst.GL_RENDERER));
		
		GL1.glViewport(this.display);
		GL1.glClearColor(Color.WHITE);
		
		//Begin glEnable calls
		
		GL1.glEnable(GLConst.GL_BLEND);
		//GL1.glBlendFunc(GLConst.GL_SRC_ALPHA, GLConst.GL_ONE_MINUS_SRC_ALPHA);
		
		//End glEnable calls
		
		for (GLSLEnumShaderType sh : GLSLEnumShaderType.values())
		{
			this.shaders.addShader(new ShaderAsset(String.format("/res/shaders/%s.glsl", sh.name().toLowerCase()), sh));
			this.shaders2d.addShader(new ShaderAsset(String.format("/res/shaders/%s2d.glsl", sh.name().toLowerCase()), sh));
			
		}
		
		PixelGrid ntf = new PixelGrid(16, 16);
		
		for (int x = 0; x < ntf.getWidth(); x++)
		{
			for (int y = 0; y < ntf.getHeight(); y++)
			{
				ntf.setPixel(x, y, MathHelper.isOdd(x) && MathHelper.isOdd(y) ? Color.PINK : Color.BLACK);
				
			}
			
		}
		
		this.notex = new TextureImage(ntf.scale(2));
		
		this.maxTexCount = GL1.glGetInteger(GLConst.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS);
		
		this.initiated = true;
		
		return true;
	}
	
	public void renderGame(ICamera cam)
	{
		assert cam != null;
		
		ICamera cam_tmp = this.camera;
		
		this.camera = cam;
		this.updateCameraUniforms = true;
		
		this.renderGame();
		
		this.camera = cam_tmp;
		this.updateCameraUniforms = false;
		
	}
	
	public void renderGame()
	{
		if (this.renders == RenderConst.RECURSIVE_LIMIT)
		{
			return;
		}
		
		this.renders++;
		
		this.renderer.render(this);
		
		this.renders--;
		
	}
	
	public synchronized void onScreenFlipped(boolean flip)
	{
		this.flipScreen.set(flip);
		
	}
	
	public Display getDisplay()
	{
		return this.display;
	}
	
	public Shaders getDefaultShaders()
	{
		return this.shaders;
	}
	
	public Shaders get2DShaders()
	{
		return this.shaders2d;
	}
	
	public GLProgram getDefaultProgram()
	{
		return this.p;
	}
	
	public ITexture getDefaultTexture()
	{
		return this.notex;
	}
	
	public int getMaxTextureCount()
	{
		return this.maxTexCount;
	}
	
	public int getRenderCount()
	{
		return this.renders;
	}
	
	public boolean isScreenFlipped()
	{
		return this.flipScreen.get();
	}
	
	public boolean updateScreenFlipUniform()
	{
		return this.flipScreen.isDirty();
	}
	
	public ICamera getCamera()
	{
		return this.camera;
	}
	
	public boolean doUpdateCamera()
	{
		return this.camera != null && this.updateCameraUniforms;
	}
	
	public void registerCleanable(IGLDeletable gl)
	{
		if (!this.cleanables.contains(gl))
		{
			this.cleanables.add(gl);
			
		}
		
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
		
		this.camera = cam;
		
	}
	
}
