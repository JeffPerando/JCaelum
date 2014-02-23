
package com.elusivehawk.engine.render;

import java.util.List;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.IGL1;
import com.elusivehawk.engine.render.opengl.IGL2;
import com.elusivehawk.engine.render.opengl.IGL3;
import com.elusivehawk.engine.render.opengl.IGLCleanable;
import com.elusivehawk.engine.render.opengl.ITexture;
import com.elusivehawk.engine.util.FileHelper;
import com.elusivehawk.engine.util.SimpleList;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class RenderContext
{
	private final IRenderHUB hub;
	
	private final IGL1 gl1;
	private final IGL2 gl2;
	private final IGL3 gl3;
	
	private final int sVertex, sFrag, notex;
	
	private final List<ITexture> texturePool = new SimpleList<ITexture>(32);
	private final List<IGLCleanable> cleanables = new SimpleList<IGLCleanable>(32);
	
	private EnumRenderStage stage = null;
	
	@SuppressWarnings("unqualified-field-access")
	public RenderContext(IRenderHUB renderhub)
	{
		hub = renderhub;
		
		gl1 = (IGL1)CaelumEngine.renderEnvironment().getGL(IRenderEnvironment.GL_1);
		gl2 = (IGL2)CaelumEngine.renderEnvironment().getGL(IRenderEnvironment.GL_2);
		gl3 = (IGL3)CaelumEngine.renderEnvironment().getGL(IRenderEnvironment.GL_3);
		
		sVertex = RenderHelper.loadShader(FileHelper.createFile("/vertex.glsl"), GLConst.GL_VERTEX_SHADER, this);
		sFrag = RenderHelper.loadShader(FileHelper.createFile("/fragment.glsl"), GLConst.GL_FRAGMENT_SHADER, this);
		
		PixelGrid ntf = new PixelGrid(32, 32, EnumColorFormat.RGBA);
		
		for (int x = 0; x < ntf.getWidth(); x++)
		{
			for (int y = 0; y < ntf.getHeight(); y++)
			{
				ntf.setPixel(x, y, (x <= 16 && y >= 16) || (x >= 16 && y <= 16) ? 0xFF00FF : 0xFFFFFF);
				
			}
			
		}
		
		notex = RenderHelper.processImage(ntf, EnumRenderMode.MODE_2D, this);
		
	}
	
	public IRenderHUB getHub()
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
	
	public int getDefaultFragmentShader()
	{
		return this.sFrag;
	}
	
	public int getDefaultVertexShader()
	{
		return this.sVertex;
	}
	
	public int getDefaultTexture()
	{
		return this.notex;
	}
	
	public EnumRenderMode getRenderMode()
	{
		return this.hub.getRenderMode();
	}
	
	public void setRenderStage(EnumRenderStage rstage)
	{
		if (rstage == this.stage)
		{
			return;
		}
		
		this.stage = rstage;
		
		switch (this.stage)
		{
			case PRERENDER: this.updateTextures();
			
		}
		
	}
	
	public EnumRenderStage getCurrentRenderingStage()
	{
		return this.stage;
	}
	
	private void updateTextures()
	{
		if (this.texturePool.isEmpty())
		{
			return;
		}
		
		for (ITexture tex : this.texturePool)
		{
			tex.updateTexture(this);
			
		}
		
	}
	
	public void addTexture(ITexture tex)
	{
		assert tex != null;
		this.texturePool.add(tex);
		
		this.registerCleanable(tex);
		
	}
	
	public void removeTexture(int index)
	{
		this.texturePool.remove(index);
		
	}
	
	public void registerCleanable(IGLCleanable gl)
	{
		this.cleanables.add(gl);
		
	}
	
	public void cleanup()
	{
		if (this.cleanables.isEmpty())
		{
			return;
		}
		
		for (IGLCleanable gl : this.cleanables)
		{
			gl.glDelete(this);
			
		}
		
		this.cleanables.clear();
		
	}
	
}
