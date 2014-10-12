
package com.elusivehawk.engine.render;

import java.nio.FloatBuffer;
import com.elusivehawk.engine.CaelumException;
import com.elusivehawk.engine.assets.IAssetReceiver;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.GLEnumBufferTarget;
import com.elusivehawk.engine.render.opengl.GLEnumDataUsage;
import com.elusivehawk.engine.render.opengl.GLEnumUType;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.VertexBuffer;
import com.elusivehawk.util.BufferHelper;
import com.elusivehawk.util.IDirty;
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
 * @see Filters
 * @see Model
 * @see IAssetReceiver
 * @see IDirty
 * @see ILogicalRender
 * @see IQuatListener
 * @see IVecListener
 */
public class RenderTicket extends RenderableObj implements Quaternion.Listener, Vector.Listener
{
	protected final Vector
			offset = new Vector(),
			pos = new Vector(),
			scale = new Vector(1f, 1f, 1f);
	
	protected final Quaternion
			rotOff = new Quaternion(),
			rot = new Quaternion();
	
	protected Model m = null;
	protected FloatBuffer buf = null;
	
	protected final VertexBuffer vbo = new VertexBuffer(GLEnumBufferTarget.GL_ARRAY_BUFFER, GLEnumDataUsage.GL_DYNAMIC_DRAW);
	
	//protected int frame = 0;
	//protected IModelAnimation anim = null, lastAnim = null;
	protected int texFrame = 0;
	
	public RenderTicket()
	{
		super();
		
	}
	
	public RenderTicket(GLProgram program)
	{
		super(program);
		
	}
	
	@Override
	protected boolean initiate(RenderContext rcon)
	{
		if (this.m == null)
		{
			return false;
		}
		
		if (!this.m.isModelFinished())
		{
			this.m.finishModel();
			
		}
		
		this.buf = BufferHelper.createFloatBuffer(this.m.getIndiceCount() * 16);
		
		this.vbo.uploadBuffer(this.buf);
		
		this.m.populate(this.vao);
		this.vao.attachVBO(this.vbo, 3);
		
		return true;
	}
	
	@Override
	protected void doRender(RenderContext rcon) throws RenderException
	{
		rcon.getGL1().glDrawElements(this.m.getPolygonType(), this.m.getPolyCount(), GLConst.GL_UNSIGNED_INT, 0);
		
	}
	
	@Override
	public synchronized void onVecChanged(Vector vec)
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
	public void preRender(RenderContext rcon, double delta)
	{
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
		
		Matrix m = MatrixHelper.createHomogenousMatrix(this.rot, this.scale, this.pos);
		
		this.p.attachUniform(rcon, "model", m.asBuffer(), GLEnumUType.M_FOUR);
		
		//TODO Load materials into program
		
		this.setIsDirty(false);
		
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
	
	@Override
	public synchronized RenderTicket setMaterials(MaterialSet ms)
	{
		assert ms != null;
		
		if (this.matSet != null)
		{
			this.dirts.remove(this.matSet);
			
		}
		
		super.setMaterials(ms);
		
		this.dirts.add(ms);
		
		return this;
	}
	
	@Override
	public synchronized void setFilters(Filters fs)
	{
		if (this.filters != null)
		{
			this.dirts.remove(this.filters);
			
		}
		
		super.setFilters(fs);
		
		if (fs != null)
		{
			this.dirts.add(fs);
			
		}
		
		this.setIsDirty(true);
		
	}
	
	public synchronized void setIndice(int i, Matrix m)
	{
		this.buf.position(i * 16);
		
		m.save(this.buf);
		
		this.setIsDirty(true);
		
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
	
	public RenderTicket setPosOffset(Vector off)
	{
		this.offset.set(off);
		this.pos.add(this.offset);
		
		this.setIsDirty(true);
		
		return this;
	}
	
	public RenderTicket setScale(Vector s)
	{
		for (int c = 0; c < s.size(); c++)
		{
			if (s.get(c) > 0f)
			{
				continue;
			}
			
			throw new CaelumException("[%s]: Vector %s has an invalid scaling float at position %s", this, s, c);
		}
		
		this.scale.set(s);
		
		return this;
	}
	
	public RenderTicket setRotOffset(Quaternion qoff)
	{
		this.rotOff.set(qoff);
		this.rot.add(this.rotOff);
		
		this.setIsDirty(true);
		
		return this;
	}
	
}
