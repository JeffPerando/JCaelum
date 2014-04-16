
package com.elusivehawk.engine.render;

import java.util.Collection;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;
import com.elusivehawk.engine.core.GameState;
import com.elusivehawk.engine.core.IContext;
import com.elusivehawk.engine.core.IGameStateListener;
import com.elusivehawk.engine.core.IThreadContext;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.IGL1;
import com.elusivehawk.engine.util.ThreadTimed;
import com.elusivehawk.engine.util.storage.SemiFinalStorage;

/**
 * 
 * The primary thread for rendering the game's window.
 * 
 * @author Elusivehawk
 */
public class ThreadGameRender extends ThreadTimed implements IGameStateListener, IThreadContext
{
	protected final IRenderEnvironment renv;
	protected final RenderContext context;
	protected final SemiFinalStorage<IDisplay> display = (SemiFinalStorage<IDisplay>)new SemiFinalStorage<IDisplay>(null).setEnableNull(false);
	
	protected IRenderHUB hub;
	protected int fps;
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadGameRender(IRenderEnvironment renderEnv, IRenderHUB rhub)
	{
		hub = rhub;
		renv = renderEnv;
		context = new RenderContext(this);
		
		CaelumEngine.game().addGameStateListener(this);
		
	}
	
	@Override
	public boolean initiate()
	{
		if (!this.renv.initiate())
		{
			CaelumEngine.log().log(EnumLogType.ERROR, "Unable to load render environment.");
			
			return false;
		}
		
		this.context.initContext();
		
		this.hub.initiate();
		
		DisplaySettings settings = this.hub.getSettings();
		
		if (settings == null)
		{
			settings = new DisplaySettings();
			
		}
		
		this.fps = settings.targetFPS;
		
		return this.display.set(this.renv.createDisplay("default", settings));
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		if (this.isPaused() || this.hub == null)
		{
			return;
		}
		
		if (this.display.get().isCloseRequested())
		{
			System.exit(0);
			
			return;
		}
		
		this.hub.updateHUB(delta);
		
		this.context.setRenderStage(EnumRenderStage.PRERENDER);
		
		if (this.hub.updateDisplay())
		{
			DisplaySettings settings = this.hub.getSettings();
			IDisplay d = this.display.get();
			
			d.resize(settings.height, settings.width);
			d.setFullscreen(settings.fullscreen);
			d.setVSync(settings.vsync);
			d.setFPS(this.fps = settings.targetFPS);
			
		}
		
		Collection<IRenderEngine> engines = this.hub.getRenderEngines();
		IGL1 gl1 = this.context.getGL1();
		
		if (engines != null && !engines.isEmpty())
		{
			int priority = 0, priorityCount = Math.max(this.hub.getHighestPriority(), 1);
			int renderersUsed = 0;
			boolean flag = true;
			
			this.context.setRenderStage(EnumRenderStage.RENDER);
			
			gl1.glClear(GLConst.GL_COLOR_BUFFER_BIT | GLConst.GL_DEPTH_BUFFER_BIT);
			
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
						engine.render(this.context, this.hub);
						
					}
					catch (RenderException e)
					{
						this.handleException(e);
						
					}
					
					renderersUsed++;
					
					int tex = 0, texUnits = gl1.glGetInteger(GLConst.GL_MAX_TEXTURE_UNITS);
					
					for (int c = 0; c < texUnits; c++)
					{
						tex = gl1.glGetInteger(GLConst.GL_TEXTURE0 + c);
						
						if (tex != 0)
						{
							gl1.glBindTexture(GLConst.GL_TEXTURE0 + c, 0);
							
						}
						
					}
					
					RenderHelper.checkForGLError(gl1);
					
				}
				
			}
			
		}
		
		this.context.setRenderStage(EnumRenderStage.POSTEFFECTS);
		
		this.display.get().updateDisplay();
		
	}
	
	@Override
	public void onThreadStopped()
	{
		this.context.cleanup();
		
		try
		{
			this.display.get().close();
			
		}
		catch (Exception e){}
		
	}
	
	@Override
	public void handleException(Throwable e)
	{
		CaelumEngine.log().log(EnumLogType.ERROR, "Error caught during rendering: ", e);
		
	}
	
	@Override
	public int getTargetUpdateCount()
	{
		return this.fps;
	}
	
	@Override
	public double getMaxDelta()
	{
		return 0.5;
	}
	
	@Override
	public synchronized void onGameStateSwitch(GameState gs)
	{
		this.hub = gs.getRenderHUB();
		
	}
	
	@Override
	public IContext getContext()
	{
		return this.context;
	}
	
	public IRenderHUB getRenderHUB()
	{
		return this.hub;
	}
	
	public IRenderEnvironment getRenderEnv()
	{
		return this.renv;
	}
	
	public synchronized void flipScreen(boolean flip)
	{
		this.context.setScreenFlipped(flip);
		
	}
	
}
