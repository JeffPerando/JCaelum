
package com.elusivehawk.engine.render;

import java.util.List;
import java.util.Map;
import com.elusivehawk.engine.assets.AbstractTexture;
import com.elusivehawk.engine.assets.Asset;
import com.elusivehawk.engine.core.IContext;
import com.elusivehawk.engine.render.opengl.GLEnumShader;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.IGL1;
import com.elusivehawk.engine.render.opengl.IGL2;
import com.elusivehawk.engine.render.opengl.IGL3;
import com.elusivehawk.engine.render.opengl.IGLBindable;
import com.elusivehawk.engine.render.opengl.IGLManipulator;
import com.elusivehawk.engine.util.FileHelper;
import com.elusivehawk.engine.util.SimpleList;
import com.google.common.collect.Maps;

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
	
	private final List<AbstractTexture> texturePool = SimpleList.newList(32);
	private final List<IGLBindable> cleanables = SimpleList.newList(32);
	private final Map<EnumRenderMode, List<IGLManipulator>> manipulators = Maps.newHashMapWithExpectedSize(3);
	
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
		
		this.sVertex = RenderHelper.loadShader(FileHelper.createFile("/vertex.glsl"), GLEnumShader.VERTEX);
		this.sFrag = RenderHelper.loadShader(FileHelper.createFile("/fragment.glsl"), GLEnumShader.FRAG);
		
		PixelGrid ntf = new PixelGrid(32, 32, EnumColorFormat.RGBA);
		
		for (int x = 0; x < ntf.getWidth(); x++)
		{
			for (int y = 0; y < ntf.getHeight(); y++)
			{
				ntf.setPixel(x, y, (x <= 16 && y >= 16) || (x >= 16 && y <= 16) ? 0xFF00FF : 0xFFFFFF);
				
			}
			
		}
		
		this.notex = RenderHelper.processImage(ntf);
		
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
			case PRERENDER: this.updateEverything(); break;
			case POSTEFFECTS: this.onPostRender(); break;
			
		}
		
	}
	
	private void updateEverything()
	{
		if (!this.texturePool.isEmpty())
		{
			for (AbstractTexture tex : this.texturePool)
			{
				if (!tex.isAnimated())
				{
					continue;
				}
				
				tex.updateTexture();
				
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
	
	public void addTexture(AbstractTexture tex)
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
			gl.glDelete(this);
			
		}
		
		this.gl1.glDeleteTextures(this.texturePool.toArray(new Asset[this.texturePool.size()]));
		
		this.cleanables.clear();
		
	}
	
	private void onPostRender()
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
	
	public void attachManipulator(EnumRenderMode mode, IGLManipulator glm)
	{
		if (!glm.isModeValid(mode))
		{
			throw new RuntimeException(String.format("Invalid rendering mode: %s", mode.name()));
		}
		
		List<IGLManipulator> mani = this.manipulators.get(mode);
		
		if (mani == null)
		{
			mani = SimpleList.newList(32);
			
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
	
	public void setScreenFlipped(boolean b)
	{
		this.flipScreen = b;
		
	}
	
}
