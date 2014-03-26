
package com.elusivehawk.engine.render;

import java.util.List;
import com.elusivehawk.engine.assets.Asset;
import com.elusivehawk.engine.core.IContext;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.IGL1;
import com.elusivehawk.engine.render.opengl.IGL2;
import com.elusivehawk.engine.render.opengl.IGL3;
import com.elusivehawk.engine.render.opengl.IGLBindable;
import com.elusivehawk.engine.render.opengl.IGLManipulator;
import com.elusivehawk.engine.util.FileHelper;
import com.elusivehawk.engine.util.SimpleList;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class RenderContext implements IContext
{
	private final ThreadGameRender thr;
	
	private IGL1 gl1;
	private IGL2 gl2;
	private IGL3 gl3;
	
	private int sVertex, sFrag, notex;
	
	private final List<INonStaticTexture> texturePool = new SimpleList<INonStaticTexture>(32);
	private final List<IPostRenderer> postRenderers = new SimpleList<IPostRenderer>(32);
	private final List<IGLBindable> cleanables = new SimpleList<IGLBindable>(32);
	private final List<IGLManipulator> manipulators = new SimpleList<IGLManipulator>(32);
	
	private EnumRenderStage stage = null;
	private boolean initiated = false, flipScreen = false;
	
	@SuppressWarnings("unqualified-field-access")
	public RenderContext(ThreadGameRender renderThr)
	{
		thr = renderThr;
		
	}
	
	@Override
	public void initContext()
	{
		if (this.initiated)
		{
			return;
		}
		
		this.gl1 = (IGL1)this.thr.getRenderEnv().getGL(IRenderEnvironment.GL_1);
		this.gl2 = (IGL2)this.thr.getRenderEnv().getGL(IRenderEnvironment.GL_2);
		this.gl3 = (IGL3)this.thr.getRenderEnv().getGL(IRenderEnvironment.GL_3);
		
		this.sVertex = RenderHelper.loadShader(FileHelper.createFile("/vertex.glsl"), GLConst.GL_VERTEX_SHADER);
		this.sFrag = RenderHelper.loadShader(FileHelper.createFile("/fragment.glsl"), GLConst.GL_FRAGMENT_SHADER);
		
		PixelGrid ntf = new PixelGrid(32, 32, EnumColorFormat.RGBA);
		
		for (int x = 0; x < ntf.getWidth(); x++)
		{
			for (int y = 0; y < ntf.getHeight(); y++)
			{
				ntf.setPixel(x, y, (x <= 16 && y >= 16) || (x >= 16 && y <= 16) ? 0xFF00FF : 0xFFFFFF);
				
			}
			
		}
		
		this.notex = RenderHelper.processImage(ntf, EnumRenderMode.MODE_2D);
		
		this.initiated = true;
		
	}
	
	public IRenderHUB getHub()
	{
		return this.thr.getRenderHUB();
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
		return this.getHub().getRenderMode();
	}
	
	public EnumRenderStage getCurrentRenderingStage()
	{
		return this.stage;
	}
	
	public boolean isScreenFlipped()
	{
		return this.flipScreen;
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
			case PRERENDER: this.updateTextures(); break;
			case POSTEFFECTS: this.onPostRender(); break;
			
		}
		
	}
	
	private void updateTextures()
	{
		if (this.texturePool.isEmpty())
		{
			return;
		}
		
		for (INonStaticTexture tex : this.texturePool)
		{
			tex.updateTexture();
			
		}
		
	}
	
	public void addTexture(INonStaticTexture tex)
	{
		assert tex != null;
		this.texturePool.add(tex);
		
	}
	
	public void removeTexture(int index)
	{
		this.texturePool.remove(index);
		
	}
	
	public void registerCleanable(IGLBindable gl)
	{
		this.cleanables.add(gl);
		
	}
	
	@Override
	public void cleanup()
	{
		if (this.cleanables.isEmpty())
		{
			return;
		}
		
		for (IGLBindable gl : this.cleanables)
		{
			gl.glDelete();
			
		}
		
		this.gl1.glDeleteTextures(this.texturePool.toArray(new Asset[this.texturePool.size()]));
		
		this.cleanables.clear();
		
	}
	
	public void addPostRenderer(IPostRenderer pr)
	{
		this.postRenderers.add(pr);
		
	}
	
	private void onPostRender()
	{
		if (this.postRenderers.isEmpty())
		{
			return;
		}
		
		for (IPostRenderer pr : this.postRenderers)
		{
			pr.postRender();
			
		}
		
		for (INonStaticTexture tex : this.texturePool)
		{
			tex.setIsDirty(false);
			
		}
		
	}
	
	public void addProgramManipulator(IGLManipulator glm)
	{
		this.manipulators.add(glm);
		
	}
	
	public void manipulateProgram(GLProgram p)
	{
		for (IGLManipulator glm : this.manipulators)
		{
			glm.manipulateUniforms(p);
			
		}
		
	}
	
	public void setScreenFlipped(boolean b)
	{
		this.flipScreen = b;
		
	}
	
}
