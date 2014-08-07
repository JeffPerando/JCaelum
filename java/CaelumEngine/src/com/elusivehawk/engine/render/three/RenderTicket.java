
package com.elusivehawk.engine.render.three;

import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.UUID;
import com.elusivehawk.engine.CaelumException;
import com.elusivehawk.engine.assets.Asset;
import com.elusivehawk.engine.assets.IAssetReceiver;
import com.elusivehawk.engine.assets.Material;
import com.elusivehawk.engine.assets.Shader;
import com.elusivehawk.engine.assets.Texture;
import com.elusivehawk.engine.render.IFilter;
import com.elusivehawk.engine.render.IFilterable;
import com.elusivehawk.engine.render.ILogicalRender;
import com.elusivehawk.engine.render.RenderConst;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.RenderHelper;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.VertexBuffer;
import com.elusivehawk.util.BufferHelper;
import com.elusivehawk.util.IDirty;
import com.elusivehawk.util.math.IQuaternionListener;
import com.elusivehawk.util.math.IVectorListener;
import com.elusivehawk.util.math.MathHelper;
import com.elusivehawk.util.math.Matrix;
import com.elusivehawk.util.math.MatrixHelper;
import com.elusivehawk.util.math.Quaternion;
import com.elusivehawk.util.math.Vector;

/**
 * 
 * Used to render static {@link Model}s with non-static information (i.e. rotation)
 * 
 * @author Elusivehawk
 * 
 * @see Model
 * @see IAssetReceiver
 * @see IDirty
 * @see IFilterable
 * @see ILogicalRender
 * @see IQuaternionListener
 * @see IVectorListener
 */
public class RenderTicket implements IAssetReceiver, IDirty, IFilterable, ILogicalRender, IQuaternionListener, IVectorListener
{
	protected final Vector offset = new Vector(),
			pos = new Vector(),
			scale = new Vector(1f, 1f, 1f);
	
	protected final Quaternion rotOff = new Quaternion(),
			rot = new Quaternion();
	
	protected final GLProgram p;
	protected final Material[] mats = RenderHelper.createMaterials();
	
	protected Model m;
	protected FloatBuffer buf = null;
	protected VertexBuffer vbo = null;
	
	protected boolean dirty = true, zBuffer = true, initiated = false;//, animPause = false;
	//protected int frame = 0;
	//protected IModelAnimation anim = null, lastAnim = null;
	protected int matCount = 0, texFrame = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public RenderTicket(Vector off, Quaternion roff)
	{
		this();
		
		offset.set(off);
		rotOff.set(roff);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public RenderTicket(Quaternion roff)
	{
		this();
		
		rotOff.set(roff);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public RenderTicket(Vector off)
	{
		this();
		
		offset.set(off);
		
	}
	
	public RenderTicket()
	{
		this(new GLProgram());
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public RenderTicket(GLProgram program)
	{
		p = program;
		
	}
	
	@Override
	public synchronized void onVectorChanged(Vector vec)
	{
		this.offset.add(vec, this.pos);
		this.setIsDirty(true);
		
	}
	
	@Override
	public synchronized void onQuatChanged(Quaternion q)
	{
		this.rotOff.add(q, this.rot);
		this.setIsDirty(true);
		
	}
	
	@Override
	public boolean updateBeforeRender(RenderContext con, double delta)
	{
		if (!this.initiated)
		{
			if (this.m == null)
			{
				return false;
			}
			
			if (!this.m.isFinished() && !this.m.finish())
			{
				return false;
			}
			
			for (Material mat : this.mats)
			{
				if (mat == null)
				{
					continue;
				}
				
				if (!mat.isFinished())
				{
					mat.finish();
					
				}
				
			}
			
			this.buf = BufferHelper.createFloatBuffer(this.m.getIndiceCount() * 16);
			this.vbo = new VertexBuffer(GLConst.GL_ARRAY_BUFFER, this.buf, GLConst.GL_STREAM_DRAW);
			
			this.p.attachModel(this.m);
			this.p.attachVBO(this.vbo, Arrays.asList(3));
			
			this.initiated = true;
			
		}
		
		/*if (this.anim != null && !this.isAnimationPaused())
		{
			boolean usedBefore = this.anim == this.lastAnim;
			
			if (!usedBefore)
			{
				this.frame = 0;
				
			}
			
			boolean fin = (this.frame == this.anim.getFrameCount());
			
			if (this.anim.update(this, usedBefore, fin))
			{
				this.buf.rewind();
				
				this.vbo.updateEntireVBO(this.buf, context);
				
			}
			
			this.frame = (fin ? 0 : this.frame + 1);
			
		}*/
		
		if (this.isDirty())
		{
			Matrix m = MatrixHelper.createHomogenousMatrix(this.rot, this.scale, this.pos);
			
			this.p.attachUniform("model", m.asBuffer(), GLProgram.EnumUniformType.M_FOUR);
			
			//TODO Load materials into program
			
			this.setIsDirty(false);
			
		}
		
		return true;
	}
	
	@Override
	public GLProgram getProgram()
	{
		return this.p;
	}
	
	@Override
	public void addFilter(UUID id, IFilter f)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean removeFilter(UUID id)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean isDirty()
	{
		return this.dirty;
	}
	
	@Override
	public synchronized void setIsDirty(boolean b)
	{
		this.dirty = b;
		
	}
	
	@Override
	public boolean onAssetLoaded(Asset a)
	{
		if (this.m == null && a instanceof Model)
		{
			this.m = (Model)a;
			
		}
		if (a instanceof Shader)
		{
			this.p.attachShader((Shader)a);
			
		}
		else if (a instanceof Texture)
		{
			if (this.matCount == RenderConst.MATERIAL_CAP)
			{
				return false;
			}
			
			return this.addMaterials(new Material(String.format("mat-%s", this.matCount + 1), (Texture)a));
			
		}
		else if (a instanceof Material)
		{
			return this.addMaterials((Material)a);
		}
		
		return true;
	}
	
	@Override
	public String toString()
	{
		return String.format("%s:%s-%s-%s", this.m.getName(), this.pos.toString(), this.scale.toString(), this.rot.toString());
	}
	
	/*public synchronized void setVector(EnumVectorType type, Vector vec)
	{
		this.vecs.get(type).set(vec);
		
		this.setIsDirty(true);
		
	}
	
	public synchronized void setModelAnimation(IModelAnimation a)
	{
		this.lastAnim = this.anim;
		
		this.anim = a;
		
	}
	
	public synchronized void setFrame(int f)
	{
		this.frame = f;
		
	}*/
	
	public synchronized void setEnableZBuffer(boolean b)
	{
		this.zBuffer = b;
		
	}
	
	public synchronized boolean addMaterials(Material... materials)
	{
		if (this.matCount == RenderConst.MATERIAL_CAP)
		{
			return false;
		}
		
		for (Material mat : materials)
		{
			if (this.matCount == RenderConst.MATERIAL_CAP)
			{
				break;
			}
			
			this.mats[this.matCount++] = mat;
			this.setIsDirty(true);
			
		}
		
		return true;
	}
	
	public synchronized void setMaterial(int i, Material m)
	{
		assert MathHelper.bounds(i, 0, 15);
		
		if (this.mats[i] == null)
		{
			this.matCount++;
			
		}
		
		this.mats[i] = m;
		
	}
	
	/**
	 * 
	 * 
	 * 
	 * @param pos
	 * @param m
	 */
	public synchronized void setIndice(int pos, Matrix m)
	{
		this.buf.position(this.m.getIndice(pos) * 16);
		
		m.save(this.buf);
		
		this.setIsDirty(true);
		
	}
	
	public FloatBuffer getBuffer()
	{
		return this.buf;
	}
	
	public Model getModel()
	{
		return this.m;
	}
	
	/*public int getCurrentFrame()
	{
		return this.frame;
	}*/
	
	public int getCurrentTexFrame()
	{
		return this.texFrame;
	}
	
	public boolean enableZBuffering()
	{
		return this.zBuffer;
	}
	
	public synchronized void setPosOffset(Vector off)
	{
		this.offset.set(off);
		this.pos.add(this.offset);
		
		this.setIsDirty(true);
		
	}
	
	public void setScale(Vector s)
	{
		for (int c = 0; c < s.getSize(); c++)
		{
			if (s.get(c) > 0f)
			{
				continue;
			}
			
			throw new CaelumException("[%s]: Vector %s has an invalid scaling float at position %s", this, s, c);
		}
		
		synchronized (this)
		{
			this.scale.set(s);
			
		}
		
	}
	
	public synchronized void setRotOffset(Quaternion qoff)
	{
		this.rotOff.set(qoff);
		this.rot.add(this.rotOff);
		
		this.setIsDirty(true);
		
	}
	
}
