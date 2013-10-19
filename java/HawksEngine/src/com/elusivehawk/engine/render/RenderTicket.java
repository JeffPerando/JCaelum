
package com.elusivehawk.engine.render;

import java.nio.FloatBuffer;
import java.util.HashMap;
import org.lwjgl.BufferUtils;
import com.elusivehawk.engine.core.IDirty;
import com.elusivehawk.engine.math.Matrix;
import com.elusivehawk.engine.math.MatrixHelper;
import com.elusivehawk.engine.math.Vector3f;
import com.elusivehawk.engine.math.Vector4f;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class RenderTicket implements IDirty
{
	protected final HashMap<EnumVectorType, Vector3f> vecs = new HashMap<EnumVectorType, Vector3f>();
	protected final Model m;
	protected final GLProgram p;
	protected final VertexBufferObject vbo = new VertexBufferObject(GL.GL_VERTEX_ARRAY);
	protected final FloatBuffer buf;
	
	protected boolean dirty = false;
	protected int frame = 0;
	protected IModelAnimation anim = null, lastAnim = null;
	
	public RenderTicket(Model model, int indiceCount)
	{
		this(new GLProgram(), model, indiceCount);
		
	}
	
	public RenderTicket(GLProgram program, Model model, int indiceCount)
	{
		p = program;
		m = model;
		buf = BufferUtils.createFloatBuffer(indiceCount * 9);
		
		vbo.loadData(buf, GL.GL_STREAM_DRAW);
		
		p.attachModel(m);
		
		for (EnumVectorType type : EnumVectorType.values())
		{
			vecs.put(type, new Vector3f());
			
		}
		
	}
	
	public synchronized void setVector(EnumVectorType type, Vector4f vec)
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
	
	public synchronized void setIndice(int pos, Vector3f rot, Vector3f trans, Vector3f scale)
	{
		this.buf.position(pos * 9);
		
		rot.store(this.getBuffer());
		trans.store(this.getBuffer());
		scale.store(this.getBuffer());
		
		this.dirty = true;
		
	}
	
	public FloatBuffer getBuffer()
	{
		return this.buf;
	}
	
	public Model getModel()
	{
		return this.m;
	}
	
	public GLProgram getProgram()
	{
		return this.p;
	}
	
	public VertexBufferObject getExtraVBO()
	{
		return this.vbo;
	}
	
	public int getCurrentFrame()
	{
		return this.frame;
	}
	
	public void updateBeforeUse(IRenderHUB hub)
	{
		boolean usedBefore = this.anim == this.lastAnim;
		
		if (!usedBefore)
		{
			this.frame = 0;
			
		}
		
		boolean finished = (this.frame == this.anim.getFrameCount());
		
		this.anim.update(this, usedBefore, finished);
		
		this.frame = (finished ? 0 : this.frame + 1);
		
		if (this.dirty)
		{
			this.buf.rewind();
			
			this.vbo.updateEntireVBO(this.buf);
			
			Matrix m = MatrixHelper.createHomogenousMatrix(this.vecs.get(EnumVectorType.ROTATION), this.vecs.get(EnumVectorType.SCALING), this.vecs.get(EnumVectorType.TRANSLATION));
			
			this.p.attachUniform("model", m.asBuffer(), GLProgram.EnumUniformType.M_FOUR);
			
			ICamera cam = hub.getCamera();
			
			if (cam.getRenderMode().is3D() && cam.isDirty())
			{
				Matrix camM = MatrixHelper.createHomogenousMatrix(cam.getCamRot(), new Vector3f(1.0f, 1.0f, 1.0f), null);
				
				this.p.attachUniform("cam", camM.asBuffer(), GLProgram.EnumUniformType.M_FOUR);
				
			}
			
			this.dirty = false;
			
		}
		
	}
	
	@Override
	public boolean isDirty()
	{
		return this.dirty;
	}
	
	@Override
	public void setIsDirty(boolean dirty){}
	
	public static enum EnumVectorType
	{
		ROTATION, TRANSLATION, SCALING;
		
	}
	
}
