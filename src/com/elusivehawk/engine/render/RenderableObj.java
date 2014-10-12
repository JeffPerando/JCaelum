
package com.elusivehawk.engine.render;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import com.elusivehawk.engine.assets.Asset;
import com.elusivehawk.engine.assets.IAssetReceiver;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.GLEnumUType;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.IGL1;
import com.elusivehawk.engine.render.opengl.VertexArray;
import com.elusivehawk.util.BufferHelper;
import com.elusivehawk.util.IDirty;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class RenderableObj implements IDirty, IFilterable, IRenderable, IAssetReceiver
{
	protected final GLProgram p;
	
	protected final VertexArray vao = new VertexArray();
	protected final List<IDirty> dirts = Lists.newArrayList();
	
	protected boolean dirty = true, initiated = false;
	protected volatile boolean zBuffer = true;
	protected Filters filters = null;
	protected MaterialSet matSet = null;
	
	protected RenderableObj()
	{
		this(new GLProgram());
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	protected RenderableObj(GLProgram program)
	{
		assert program != null;
		
		p = program;
		
		dirts.add(program);
		
	}
	
	@Override
	public void onAssetLoaded(Asset a)
	{
		if (a instanceof Shader)
		{
			this.p.attachShader((Shader)a);
			
		}
		
		if (a instanceof TextureAsset)
		{
			this.addMaterials(new Material().tex((TextureAsset)a));
			
		}
		
	}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
		if (!this.initiated)
		{
			if (!this.initiate(rcon))
			{
				return;
			}
			
			this.initiated = true;
			rcon.registerRenderer(this);
			
		}
		
		if (rcon.doUpdateCamera())
		{
			ICamera cam = rcon.getCamera();
			
			this.p.attachUniform(rcon, "view", cam.getView().asBuffer(), GLEnumUType.M_FOUR);
			this.p.attachUniform(rcon, "proj", cam.getProjection().asBuffer(), GLEnumUType.M_FOUR);
			
		}
		
		if (this.p.bind(rcon) && this.vao.bind(rcon))
		{
			boolean zBuffer = rcon.getGL2().glIsEnabled(GLConst.GL_DEPTH_TEST);
			
			if (zBuffer != this.zBuffer)
			{
				IGL1 gl1 = rcon.getGL1();
				
				if (this.zBuffer)
				{
					gl1.glEnable(GLConst.GL_DEPTH_TEST);
					
				}
				else
				{
					gl1.glDisable(GLConst.GL_DEPTH_TEST);
					
				}
				
			}
			
			this.doRender(rcon);
			
		}
		
		this.vao.unbind(rcon);
		this.p.unbind(rcon);
		
	}
	
	@Override
	public int addFilter(UUID type, IFilter f)
	{
		if (this.filters == null)
		{
			this.setFilters(new Filters());
			
		}
		
		return this.filters.addFilter(type, f);
	}
	
	@Override
	public void removeFilter(UUID type, IFilter f)
	{
		if (this.filters != null)
		{
			this.filters.removeFilter(type, f);
			
		}
		
	}
	
	@Override
	public void removeFilter(UUID type, int i)
	{
		if (this.filters != null)
		{
			this.filters.removeFilter(type, i);
			
		}
		
	}
	
	@Override
	public boolean isDirty()
	{
		if (!this.dirty)
		{
			for (IDirty d : this.dirts)
			{
				if (d.isDirty())
				{
					this.dirty = true;
					break;
				}
				
			}
			
		}
		
		return this.dirty;
	}
	
	@Override
	public synchronized void setIsDirty(boolean b)
	{
		if (!(this.dirty = b))
		{
			this.dirts.forEach(((d) -> {d.setIsDirty(false);}));
			
		}
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		this.forEveryMaterial(((mat) -> {mat.preRender(rcon, delta);}));
		
		if (this.isDirty())
		{
			this.p.attachUniform(rcon, "flip", BufferHelper.makeIntBuffer(rcon.isScreenFlipped() ? 1 : 0), GLEnumUType.ONE);
			
			this.forEveryMaterial(((mat) -> {mat.renderTexture(rcon);}));
			
			//TODO Load materials into program
			
			if (this.filters != null)
			{
				this.filters.filter(rcon, this.p);
				
			}
			
			this.setIsDirty(false);
			
		}
		
	}
	
	public synchronized void setFilters(Filters fs)
	{
		if (this.filters != null)
		{
			this.dirts.remove(this.filters);
			
		}
		
		this.filters = fs;
		
		if (fs != null)
		{
			this.dirts.add(fs);
			
		}
		
	}
	
	public synchronized RenderableObj setMaterials(MaterialSet ms)
	{
		if (this.matSet != null)
		{
			this.dirts.remove(this.matSet);
			
		}
		
		this.matSet = ms;
		
		if (ms != null)
		{
			this.dirts.add(ms);
			
		}
		
		return this;
	}
	
	public synchronized boolean addMaterials(Material... ms)
	{
		if (this.matSet == null)
		{
			this.setMaterials(new MaterialSet());
			
		}
		
		return this.matSet.addMaterials(ms);
	}
	
	public RenderableObj setEnableZBuffer(boolean z)
	{
		this.zBuffer = z;
		
		return this;
	}
	
	public int getMaterialCount()
	{
		return this.matSet == null ? 0 : this.matSet.matCount();
	}
	
	protected void forEveryMaterial(Consumer<Material> consumer)
	{
		int mCount = this.getMaterialCount();
		
		if (mCount > 0)
		{
			for (int c = 0; c < mCount; c++)
			{
				consumer.accept(this.matSet.getMat(c));
				
			}
			
		}
		
	}
	
	protected abstract boolean initiate(RenderContext rcon);
	
	protected abstract void doRender(RenderContext rcon) throws RenderException;
	
}
