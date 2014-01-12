
package com.elusivehawk.engine.render;

import java.nio.FloatBuffer;
import java.util.HashMap;
import com.elusivehawk.engine.math.Matrix;
import com.elusivehawk.engine.math.MatrixHelper;
import com.elusivehawk.engine.math.Vector;
import com.elusivehawk.engine.math.VectorF;
import com.elusivehawk.engine.render.opengl.GL;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.ITexture;
import com.elusivehawk.engine.render.opengl.VertexBufferObject;
import com.elusivehawk.engine.util.BufferHelper;
import com.elusivehawk.engine.util.IDirty;

/**
 * 
 * Used to render static {@link Model}s with non-static information (i.e. rotation, {@link IModelAnimation})
 * 
 * @author Elusivehawk
 */
public class RenderTicket implements IDirty, ILogicalRender
{
	protected final HashMap<EnumVectorType, Vector<Float>> vecs = new HashMap<EnumVectorType, Vector<Float>>();
	protected final Model m;
	protected final GLProgram p;
	protected final VertexBufferObject vbo;
	protected final FloatBuffer buf;
	
	protected boolean dirty = false, animPause = false;
	protected int frame = 0;
	protected IModelAnimation anim = null, lastAnim = null;
	protected ITexture tex;
	
	public RenderTicket(Model model)
	{
		this(GLProgram.create(RenderHelper.VERTEX_SHADER_3D, RenderHelper.FRAGMENT_SHADER_3D), model);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public RenderTicket(GLProgram program, Model model)
	{
		p = program;
		m = model;
		buf = BufferHelper.createFloatBuffer(m.indiceCount.get() * 3);
		vbo = new VertexBufferObject(GL.GL_VERTEX_ARRAY, this.buf, GL.GL_STREAM_DRAW);
		
		p.attachRenderTicket(this);
		
		for (EnumVectorType type : EnumVectorType.values())
		{
			vecs.put(type, type.getDefault());
			
		}
		
	}
	
	public synchronized void setVector(EnumVectorType type, Vector<Float> vec)
	{
		this.vecs.get(type).set(vec);
		
		this.dirty = true;
		
	}
	
	public synchronized void setModelAnimation(IModelAnimation a)
	{
		this.lastAnim = this.anim;
		
		this.anim = a;
		
	}
	
	public synchronized void setFrame(int f)
	{
		this.frame = f;
		
	}
	
	public synchronized void setTexture(ITexture texture)
	{
		this.tex = texture;
		
	}
	
	public synchronized void setAnimationPaused(boolean b)
	{
		this.animPause = b;
		
	}
	
	/**
	 * 
	 * NOTICE: Do *NOT* call this outside of the designated model animation, since it's not synchronized.
	 * 
	 * @param pos
	 * @param rot
	 * @param trans
	 * @param scale
	 */
	public void setIndice(int pos, Vector<Float> rot, Vector<Float> trans, Vector<Float> scale)
	{
		this.buf.position(pos * 9);
		
		for (int c = 0; c < rot.getSize(); c++)
		{
			this.buf.put(rot.get(c));
			
		}
		
		for (int c = 0; c < trans.getSize(); c++)
		{
			this.buf.put(trans.get(c));
			
		}
		
		for (int c = 0; c < scale.getSize(); c++)
		{
			this.buf.put(scale.get(c));
			
		}
		
	}
	
	public FloatBuffer getBuffer()
	{
		return this.buf;
	}
	
	public Model getModel()
	{
		return this.m;
	}
	
	public VertexBufferObject getExtraVBO()
	{
		return this.vbo;
	}
	
	public int getCurrentFrame()
	{
		return this.frame;
	}
	
	public ITexture getTexture()
	{
		return this.tex;
	}
	
	public boolean isAnimationPaused()
	{
		return this.animPause;
	}
	
	@Override
	public GLProgram getProgram()
	{
		return this.p;
	}
	
	@Override
	public boolean updateBeforeUse(IRenderHUB hub)
	{
		if (!this.m.isFinished())
		{
			return false;
		}
		
		if (this.anim != null && !this.isAnimationPaused())
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
				
				this.vbo.updateEntireVBO(this.buf);
				
			}
			
			this.frame = (fin ? 0 : this.frame + 1);
			
		}
		
		if (this.isDirty())
		{
			Matrix m = MatrixHelper.createHomogenousMatrix(this.vecs.get(EnumVectorType.ROTATION), this.vecs.get(EnumVectorType.SCALING), this.vecs.get(EnumVectorType.TRANSLATION));
			
			this.p.attachUniform("model", m.asBuffer(), GLProgram.EnumUniformType.M_FOUR);
			
			hub.getCamera().updateUniform(this.getProgram(), EnumRenderMode.MODE_3D);
			
			this.setIsDirty(false);
			
		}
		
		return true;
	}
	
	@Override
	public boolean isDirty()
	{
		return this.dirty;
	}
	
	@Override
	public void setIsDirty(boolean b)
	{
		this.dirty = b;
		
	}
	
	public static enum EnumVectorType
	{
		ROTATION(new VectorF(3)),
		TRANSLATION(new VectorF(3)),
		SCALING(new VectorF(3, 1.0f, 1.0f, 1.0f));
		
		private final VectorF vec;
		
		@SuppressWarnings("unqualified-field-access")
		EnumVectorType(VectorF d)
		{
			vec = d;
			
		}
		
		protected VectorF getDefault()
		{
			return new VectorF(this.vec);
		}
		
	}
	
}
