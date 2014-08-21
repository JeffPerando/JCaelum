
package com.elusivehawk.engine.render;

import java.io.File;
import java.util.List;
import java.util.Map;
import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.engine.EnumLogType;
import com.elusivehawk.engine.GameState;
import com.elusivehawk.engine.IContext;
import com.elusivehawk.engine.IGameStateListener;
import com.elusivehawk.engine.render.old.EnumRenderMode;
import com.elusivehawk.engine.render.old.IRenderHUB;
import com.elusivehawk.engine.render.old.RenderTask;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.GLEnumShader;
import com.elusivehawk.engine.render.opengl.GLException;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.IGL1;
import com.elusivehawk.engine.render.opengl.IGL2;
import com.elusivehawk.engine.render.opengl.IGL3;
import com.elusivehawk.engine.render.opengl.IGLDeletable;
import com.elusivehawk.engine.render.opengl.IGLManipulator;
import com.elusivehawk.util.IPausable;
import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.storage.ImmutableArray;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class RenderContext implements IUpdatable, IPausable, IGameStateListener, IContext
{
	private final IRenderEnvironment renv;
	private final IDisplay display;
	
	private IRenderHUB hub = null;
	private int fps;
	private boolean paused = false;
	
	private IGL1 gl1;
	private IGL2 gl2;
	private IGL3 gl3;
	
	private int notex, maxTexCount;
	
	private ImmutableArray<Shader> shaders = null;
	
	private final List<IGLDeletable> cleanables = Lists.newArrayList();
	@Deprecated
	private final List<RenderTask> rtasks = Lists.newArrayList();
	@Deprecated
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
		display = CaelumEngine.display();
		
	}
	
	@Override
	public void preInit()
	{
		if (!this.renv.initiate())
		{
			CaelumEngine.log().log(EnumLogType.ERROR, "Unable to load render environment.");
			
			return;
		}
		
		this.gl1 = (IGL1)this.renv.getGL(IRenderEnvironment.GL_1);
		this.gl2 = (IGL2)this.renv.getGL(IRenderEnvironment.GL_2);
		this.gl3 = (IGL3)this.renv.getGL(IRenderEnvironment.GL_3);
		
		Shader[] shs = RenderHelper.createShaders();
		
		for (GLEnumShader sh : GLEnumShader.values())
		{
			String loc = String.format("/%s.glsl", sh.name().toLowerCase());
			
			if (new File(".", loc).exists())
			{
				Shader s = new Shader(loc, sh);
				
				shs[sh.ordinal()] = s;
				
			}
			
		}
		
		this.shaders = new ImmutableArray<Shader>(shs);
		
	}
	
	@Override
	public boolean initContext()
	{
		if (this.initiated)
		{
			return false;
		}
		
		PixelGrid ntf = new PixelGrid(32, 32, ColorFormat.RGBA);
		
		for (int x = 0; x < ntf.getWidth(); x++)
		{
			for (int y = 0; y < ntf.getHeight(); y++)
			{
				ntf.setPixel(x, y, (x <= 16 && y >= 16) || (x >= 16 && y <= 16) ? 0xFF00FF : 0xFFFFFF);
				
			}
			
		}
		
		try
		{
			this.notex = RenderHelper.processImage(ntf);
			
		}
		catch (GLException e)
		{
			CaelumEngine.log().err(e);
			
		}
		
		this.maxTexCount = this.gl1.glGetInteger(GLConst.GL_MAX_TEXTURE_UNITS);
		
		this.fps = this.settings.targetFPS;
		
		if (this.hub != null)
		{
			CaelumEngine.log().log(EnumLogType.WARN, "Rendering using render HUB system! Override Game.render() instead!");
			
			this.hub.initiate(this.display);
			
		}
		
		this.initiated = true;
		
		return true;
	}
	
	@Override
	public void cleanup()
	{
		this.gl1.glDeleteTextures(this.notex);
		
		for (IGLDeletable gl : this.cleanables)
		{
			gl.delete(this);
			
		}
		
		this.cleanables.clear();
		
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
	
	@Override
	public void update(double delta) throws RenderException
	{
		boolean useHub = (this.hub != null);
		
		if (useHub)
		{
			this.hub.updateHUB(delta);
			
		}
		
		if ((useHub && this.hub.updateDisplay()) || this.refreshScreen)
		{
			if (useHub)
			{
				this.settings = this.hub.getSettings();
				
			}
			
			this.display.updateSettings(this.settings);
			
		}
		
		this.renderGame(delta);
		this.display.updateDisplay();
		
	}
	
	private void renderGame(double delta)
	{
		this.preRender();
		
		CaelumEngine.game().render(this, delta);
		
		this.postRender();
		
	}
	
	public void setRenderHUB(IRenderHUB rhub)
	{
		if (this.hub != null)
		{
			return;
		}
		
		this.hub = rhub;
		
		CaelumEngine.game().addGameStateListener(this);
		
	}
	
	private void preRender()
	{
		this.preRenderDepr();
		
	}
	
	private void postRender()
	{
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
		
		this.postRenderDepr();
		
	}
	
	@Deprecated
	private void preRenderDepr()
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
					glm.updateUniforms(this);
					
				}
				
			}
			
		}
		
	}
	
	@Deprecated
	private void postRenderDepr()
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
	
	public int getMaxTextureCount()
	{
		return this.maxTexCount;
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
	
	public void registerCleanable(IGLDeletable gl)
	{
		if (this.cleanables.contains(gl))
		{
			return;
		}
		
		this.cleanables.add(gl);
		
	}
	
	@Deprecated
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
	
	@Deprecated
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
	
	@Deprecated
	public synchronized void scheduleRTask(RenderTask rt)
	{
		this.rtasks.add(rt);
		
	}
	
}
