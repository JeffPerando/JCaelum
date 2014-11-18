
package com.elusivehawk.caelum.render;

import java.util.List;
import com.elusivehawk.caelum.Display;
import com.elusivehawk.caelum.IContext;
import com.elusivehawk.caelum.IDisplayImpl;
import com.elusivehawk.caelum.render.gl.GL1;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.gl.GLEnumShader;
import com.elusivehawk.caelum.render.gl.GLProgram;
import com.elusivehawk.caelum.render.gl.IGLDeletable;
import com.elusivehawk.caelum.render.gl.Shader;
import com.elusivehawk.caelum.render.gl.Shaders;
import com.elusivehawk.caelum.render.tex.Color;
import com.elusivehawk.caelum.render.tex.ITexture;
import com.elusivehawk.caelum.render.tex.PixelGrid;
import com.elusivehawk.caelum.render.tex.TextureImage;
import com.elusivehawk.util.EnumLogType;
import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.math.MathHelper;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class RenderContext implements IUpdatable, IContext
{
	private final Display display;
	private final IRenderable renderer;
	
	private int
			maxTexCount = 0,
			renders = 0;
	
	private ITexture notex = null;
	
	private final Shaders shaders = new Shaders();
	private final GLProgram p = new GLProgram(this.shaders);
	
	private final List<IGLDeletable> cleanables = Lists.newArrayList();
	private final List<IPreRenderer> preRenderers = Lists.newArrayList();
	private final List<IPostRenderer> postRenderers = Lists.newArrayList();
	
	private boolean
			initiated = false,
			flipScreen = false,
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
	public boolean initContext()
	{
		if (this.initiated)
		{
			return false;
		}
		
		GL1.glViewport(0, 0, this.display.getWidth(), this.display.getHeight());
		
		Logger.log().log(EnumLogType.VERBOSE, "OpenGL version: %s", GL1.glGetString(GLConst.GL_VERSION));
		Logger.log().log(EnumLogType.VERBOSE, "OpenGL vendor: %s", GL1.glGetString(GLConst.GL_VENDOR));
		Logger.log().log(EnumLogType.VERBOSE, "OpenGL renderer: %s", GL1.glGetString(GLConst.GL_RENDERER));
		
		for (GLEnumShader sh : GLEnumShader.values())
		{
			this.shaders.addShader(new Shader(String.format("/res/shaders/%s.glsl", sh.name().toLowerCase()), sh));
			
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
		
		this.maxTexCount = GL1.glGetInteger(GLConst.GL_MAX_TEXTURE_UNITS);
		
		this.initiated = true;
		
		return true;
	}
	
	@Override
	public void cleanup()
	{
		this.cleanables.forEach(((gl) -> {gl.delete(this);}));
		
		this.cleanables.clear();
		
	}
	
	@Override
	public void update(double delta)
	{
		try
		{
			GL1.glViewport(0, 0, this.display.getWidth(), this.display.getHeight());
			
			//Clears the OpenGL buffer
			
			GL1.glClear(0b0100010100000000);
			
			//Pre-rendering 
			
			this.renderer.preRender(this, delta);
			
			this.preRenderers.forEach(((preR) -> {preR.preRender(this, delta);}));
			
			//Rendering the game itself
			
			this.renderGame();
			
			//Post rendering.
			
			this.renderer.postRender(this);
			
			this.postRenderers.forEach(((postR) -> {postR.postRender(this);}));
			
		}
		catch (Throwable e)
		{
			Logger.log().err(e);
			
		}
		
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
	
	private void renderGame()
	{
		if (this.renders == RenderConst.RECURSIVE_LIMIT)
		{
			return;
		}
		
		this.renders++;
		
		this.renderer.render(this);
		
		this.renders--;
		
	}
	
	public void onDisplayResized(IDisplayImpl d)
	{
		//TODO
		
	}
	
	public void onDisplayClosed(IDisplayImpl d)
	{
		this.cleanup();
		
	}
	
	public synchronized void onScreenFlipped(boolean flip)
	{
		this.flipScreen = flip;
		
	}
	
	public Display getDisplay()
	{
		return this.display;
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
		return this.flipScreen;
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
	
	public synchronized void setScreenFlipped(boolean b)
	{
		this.flipScreen = b;
		
	}
	
}
