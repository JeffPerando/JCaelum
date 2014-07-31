
package com.elusivehawk.engine.render;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import com.elusivehawk.engine.assets.Shader;
import com.elusivehawk.engine.assets.Texture;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;
import com.elusivehawk.engine.core.GameState;
import com.elusivehawk.engine.core.IContext;
import com.elusivehawk.engine.core.IGameStateListener;
import com.elusivehawk.engine.render.old.EnumRenderMode;
import com.elusivehawk.engine.render.old.IRenderEngine;
import com.elusivehawk.engine.render.old.IRenderHUB;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.GLEnumShader;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.IGL1;
import com.elusivehawk.engine.render.opengl.IGL2;
import com.elusivehawk.engine.render.opengl.IGL3;
import com.elusivehawk.engine.render.opengl.IGLBindable;
import com.elusivehawk.engine.render.opengl.IGLManipulator;
import com.elusivehawk.util.FileHelper;
import com.elusivehawk.util.IPausable;
import com.elusivehawk.util.storage.ImmutableArray;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class RenderContext implements IPausable, IGameStateListener, IContext
{
	private final IRenderEnvironment renv;
	
	private IDisplay display = null;
	private IRenderHUB hub = null;
	private int fps;
	private boolean paused = false;
	
	private IGL1 gl1;
	private IGL2 gl2;
	private IGL3 gl3;
	
	private int notex;
	
	private ImmutableArray<Shader> shaders = null;
	
	private final List<Texture> texturePool = Lists.newArrayList();
	private final List<IGLBindable> cleanables = Lists.newArrayList();
	private final List<RenderTask> rtasks = Lists.newArrayList();
	private final Map<EnumRenderMode, List<IGLManipulator>> manipulators = Maps.newHashMapWithExpectedSize(3);
	
	private DisplaySettings settings = new DisplaySettings();
	private boolean //Hey, I sorta like this...
			initiated = false,
			refreshScreen = false,
			flipScreen = false;
	
	@SuppressWarnings("unqualified-field-access")
	public RenderContext(IRenderEnvironment renderEnv)
	{
		renv = renderEnv;
		
	}
	
	@Deprecated
	@SuppressWarnings("unqualified-field-access")
	public RenderContext(IRenderEnvironment renderEnv, IRenderHUB rhub)
	{
		this(renderEnv);
		
		hub = rhub;
		CaelumEngine.game().addGameStateListener(this);
		
	}
	
	@Override
	public boolean initContext()
	{
		if (this.initiated)
		{
			return false;
		}
		
		if (!this.renv.initiate())
		{
			CaelumEngine.log().log(EnumLogType.ERROR, "Unable to load render environment.");
			
			return false;
		}
		
		this.gl1 = (IGL1)this.renv.getGL(IRenderEnvironment.GL_1);
		this.gl2 = (IGL2)this.renv.getGL(IRenderEnvironment.GL_2);
		this.gl3 = (IGL3)this.renv.getGL(IRenderEnvironment.GL_3);
		
		Shader[] shs = RenderHelper.createShaders();
		
		for (GLEnumShader sh : GLEnumShader.values())
		{
			File file = FileHelper.createFile(".", String.format("/%s.glsl", sh.name().toLowerCase()));
			
			if (FileHelper.canReadFile(file))
			{
				Shader s = new Shader(file, sh);
				
				s.finish();
				
				shs[sh.ordinal()] = s;
				
			}
			
		}
		
		this.shaders = new ImmutableArray<Shader>(shs);
		
		PixelGrid ntf = new PixelGrid(32, 32, EnumColorFormat.RGBA);
		
		for (int x = 0; x < ntf.getWidth(); x++)
		{
			for (int y = 0; y < ntf.getHeight(); y++)
			{
				ntf.setPixel(x, y, (x <= 16 && y >= 16) || (x >= 16 && y <= 16) ? 0xFF00FF : 0xFFFFFF);
				
			}
			
		}
		
		this.notex = RenderHelper.processImage(ntf);
		
		this.fps = this.settings.targetFPS;
		IDisplay d = this.renv.createDisplay("default", this.settings);
		
		if (d == null)
		{
			return false;
			
		}
		
		if (this.hub != null)
		{
			CaelumEngine.log().log(EnumLogType.WARN, "Rendering using render HUB system! Use Game.render()!");
			
			this.hub.initiate(d);
			
		}
		
		this.display = d;
		
		this.initiated = true;
		
		return true;
	}
	
	@Override
	public void cleanup()
	{
		for (IGLBindable gl : this.cleanables)
		{
			gl.glDelete(this);
			
		}
		
		this.gl1.glDeleteTextures(this.texturePool.toArray(new Texture[this.texturePool.size()]));
		
		this.cleanables.clear();
		this.texturePool.clear();
		
	}
	
	@Override
	public void onGameStateSwitch(GameState gs)
	{
		this.hub = gs.getRenderHUB();
		
	}
	
	@Override
	public boolean isPaused()
	{
		return this.paused;
	}
	
	@Override
	public void setPaused(boolean p)
	{
		this.paused = p;
		
	}
	
	public boolean drawScreen(double delta) throws RenderException
	{
		if (this.isPaused())
		{
			return false;
		}
		
		boolean useHub = (this.hub != null);
		
		if (useHub)
		{
			this.hub.updateHUB(delta);
			
		}
		
		this.preRender();
		
		if ((useHub && this.hub.updateDisplay()) || this.refreshScreen)
		{
			if (useHub)
			{
				this.settings = this.hub.getSettings();
				
			}
			
			this.display.resize(this.settings.height, this.settings.width);
			this.display.setFullscreen(this.settings.fullscreen);
			this.display.setVSync(this.settings.vsync);
			this.display.setFPS(this.fps = this.settings.targetFPS);
			
		}
		
		CaelumEngine.game().render(this, delta);
		
		if (useHub)
		{
			Collection<IRenderEngine> engines = this.hub.getRenderEngines();
			
			if (engines != null && !engines.isEmpty())
			{
				int priority = 0, priorityCount = Math.max(this.hub.getHighestPriority(), 1);
				int renderersUsed = 0;
				boolean flag = true;
				
				this.gl1.glClear(GLConst.GL_COLOR_BUFFER_BIT | GLConst.GL_DEPTH_BUFFER_BIT);
				
				for (int p = 0; p < priorityCount && flag; p++)
				{
					for (IRenderEngine engine : engines)
					{
						if (renderersUsed == engines.size())
						{
							flag = false;
							break;
						}
						
						priority = Math.min(engine.getPriority(this.hub), priorityCount - 1);
						
						if (priority == -1)
						{
							continue;
						}
						
						if (priority != p)
						{
							continue;
						}
						
						try
						{
							engine.render(this, this.hub);
							
						}
						catch (RenderException e)
						{
							CaelumEngine.log().err(null, e);
							
						}
						
						renderersUsed++;
						
						int tex = 0, texUnits = this.gl1.glGetInteger(GLConst.GL_MAX_TEXTURE_UNITS);
						
						for (int c = 0; c < texUnits; c++)
						{
							tex = this.gl1.glGetInteger(GLConst.GL_TEXTURE0 + c);
							
							if (tex != 0)
							{
								this.gl1.glBindTexture(GLConst.GL_TEXTURE0 + c, 0);
								
							}
							
						}
						
						RenderHelper.checkForGLError(this.gl1);
						
					}
					
				}
				
			}
			
		}
		
		this.postRender();
		
		return true;
	}
	
	/**
	 * 
	 * Experimental HUB-less render function.
	 * 
	 * @param delta
	 */
	@SuppressWarnings("unused")
	private void drawScreen0(double delta)//TODO Expand
	{
		this.preRender();
		
		CaelumEngine.game().render(this, delta);
		
		this.postRender();
		
	}
	
	public void preRender()
	{
		if (!this.texturePool.isEmpty())
		{
			for (Texture tex : this.texturePool)
			{
				if (!tex.isAnimated())
				{
					continue;
				}
				
				//tex.updateTexture();
				
			}
			
		}
		
		if (!this.manipulators.isEmpty())
		{
			List<IGLManipulator> mani;
			
			for (EnumRenderMode mode : EnumRenderMode.values())
			{
				mani = this.manipulators.get(mode);
				
				if (mani == null || mani.isEmpty())
				{
					continue;
				}
				
				for (IGLManipulator glm : mani)
				{
					glm.updateUniforms(this);
					
				}
				
			}
			
		}
		
	}
	
	public void postRender()
	{
		if (!this.manipulators.isEmpty())
		{
			List<IGLManipulator> mani;
			
			for (EnumRenderMode mode : EnumRenderMode.values())
			{
				mani = this.manipulators.get(mode);
				
				if (mani == null || mani.isEmpty())
				{
					continue;
				}
				
				for (IGLManipulator glm : mani)
				{
					glm.postRender();
					
				}
				
			}
			
		}
		
		if (!this.rtasks.isEmpty())
		{
			RenderTask t = this.rtasks.get(0);
			boolean rem = false;
			
			try
			{
				rem = t.completeTask();
				
			}
			catch (Throwable e)
			{
				CaelumEngine.log().err("Error caught whilst finishing render task:", e);
				
			}
			
			if (rem)
			{
				this.rtasks.remove(0);
				
			}
			
		}
		
	}
	
	@SuppressWarnings("unused")
	public void onDisplayResized(IDisplay d)
	{
		//TODO
		
	}
		
	@SuppressWarnings("unused")
	public void onDisplayClosed(IDisplay d)
	{
		this.cleanup();
		
	}
	
	public synchronized void onScreenFlipped(boolean flip)
	{
		this.flipScreen = flip;
		
	}
	
	public IRenderEnvironment getRenderEnv()
	{
		return this.renv;
	}
	
	@Deprecated
	public IRenderHUB getHUB()
	{
		return this.hub;
	}
	
	public IGL1 getGL1()
	{
		return this.gl1;
	}
	
	public IGL2 getGL2()
	{
		return this.gl2;
	}
	
	public IGL3 getGL3()
	{
		return this.gl3;
	}
	
	public IDisplay getDisplay()
	{
		return this.display;
	}
	
	public int getFPS()
	{
		return this.fps;
	}
	
	public ImmutableArray<Shader> getDefaultShaders()
	{
		return this.shaders;
	}
	
	public int getDefaultTexture()
	{
		return this.notex;
	}
	
	public boolean isScreenFlipped()
	{
		return this.flipScreen;
	}
	
	public void setSettings(DisplaySettings ds)
	{
		assert ds != null;
		
		this.settings = ds;
		this.refreshScreen = true;
		
	}
	
	public void addTexture(Texture tex)
	{
		assert tex != null;
		
		this.texturePool.add(tex);
		
	}
	
	public void removeTexture(Texture tex)
	{
		this.texturePool.remove(tex);
		
	}
	
	public void registerCleanable(IGLBindable gl)
	{
		this.cleanables.add(gl);
		
	}
	
	public void attachManipulator(EnumRenderMode mode, IGLManipulator glm)
	{
		List<IGLManipulator> mani = this.manipulators.get(mode);
		
		if (mani == null)
		{
			mani = Lists.newArrayList();
			
			this.manipulators.put(mode, mani);
			
		}
		
		mani.add(glm);
		
	}
	
	public void manipulateProgram(EnumRenderMode mode, GLProgram p)
	{
		List<IGLManipulator> mani = this.manipulators.get(mode);
		
		if (mani == null || mani.isEmpty())
		{
			return;
		}
		
		for (IGLManipulator glm : mani)
		{
			glm.manipulateUniforms(this, p);
			
		}
		
	}
	
	public synchronized void setScreenFlipped(boolean b)
	{
		this.flipScreen = b;
		
	}
	
	public synchronized void scheduleRTask(RenderTask rt)
	{
		this.rtasks.add(rt);
		
	}
	
}
