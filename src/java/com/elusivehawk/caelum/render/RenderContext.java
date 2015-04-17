
package com.elusivehawk.caelum.render;

import java.util.List;
import com.elusivehawk.caelum.render.gl.GL1;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.storage.DirtableStorage;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class RenderContext implements IUpdatable
{
	public static final int CLEAR_BITS = GLConst.GL_COLOR_BUFFER_BIT | GLConst.GL_DEPTH_BUFFER_BIT | GLConst.GL_STENCIL_BUFFER_BIT;
	
	private final Window window;
	private final IRenderer renderer;
	
	private final DirtableStorage<Camera> cameraStorage = new DirtableStorage<Camera>();
	
	private final List<IPreRenderer> preRenderers = Lists.newArrayList();
	private final List<IPostRenderer> postRenderers = Lists.newArrayList();
	
	private boolean rendering = false;
	
	private double delta = 0;
	
	private int renders = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public RenderContext(Window w, IRenderer r)
	{
		assert w != null;
		assert r != null;
		
		window = w;
		renderer = r;
		
		preRenderers.add(r);
		postRenderers.add(r);
		
	}
	
	//XXX Internal methods
	
	@Override
	public void update(double delta) throws RenderException
	{
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
			if (this.cameraStorage.isDirty())
			{
				this.cameraStorage.setIsDirty(false);
				
			}
			
			this.rendering = false;
			
		}
		
	}
	
	//XXX End internal methods
	
	//XXX Hooks
	
	public void renderGame(Camera cam) throws RenderException
	{
		assert cam != null;
		
		Camera cam_tmp = this.cameraStorage.get();
		
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
	
	//XXX Getters
	
	public Window getWindow()
	{
		return this.window;
	}
	
	public double getDelta()
	{
		return this.delta;
	}
	
	public int getRenderCount()
	{
		return this.renders;
	}
	
	public Camera getCamera()
	{
		return this.cameraStorage.get();
	}
	
	public boolean doUpdateCamera()
	{
		return this.cameraStorage.isDirty();
	}
	
	//XXX End getters
	
	//XXX Setters and registries
	
	public void registerRenderer(Renderable r)
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
	
	public void setCamera(Camera cam)
	{
		assert cam != null;
		
		this.cameraStorage.set(cam);
		
	}
	
	//XXX End setters and registries
	
}
