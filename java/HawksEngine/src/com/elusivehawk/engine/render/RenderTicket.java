
package com.elusivehawk.engine.render;

import java.util.HashMap;
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
public class RenderTicket
{
	protected final HashMap<EnumVectorType, Vector3f> vecs = new HashMap<EnumVectorType, Vector3f>();
	protected final Model m;
	protected final GLProgram p;
	protected final VertexBufferObject vbo = new VertexBufferObject(GL.GL_VERTEX_ARRAY);
	
	protected boolean dirty = false;
	public int frame = 0;
	protected IModelAnimation anim = null, lastAnim = null;
	
	public RenderTicket(Model model)
	{
		this(new GLProgram(), model);
		
	}
	
	public RenderTicket(GLProgram program, Model model)
	{
		p = program;
		m = model;
		
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
	
	public GLProgram getProgram()
	{
		return this.p;
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
	
	public void updateBeforeUse()
	{
		boolean usedBefore = this.anim == this.lastAnim;
		
		if (!usedBefore)
		{
			this.frame = 0;
			
		}
		
		this.anim.update(this.getExtraVBO(), this.frame++, usedBefore);
		
		if (this.frame == this.anim.getFrames())
		{
			this.frame = 0;
			
			this.anim.onCompletion(this);
			
		}
		
		if (this.dirty)
		{
			Matrix m = MatrixHelper.createHomogenousMatrix(this.vecs.get(EnumVectorType.ROTATION), this.vecs.get(EnumVectorType.SCALING), this.vecs.get(EnumVectorType.TRANSLATION));
			
			this.p.attachUniform("model", m.asBuffer(), GLProgram.EnumUniformType.M_FOUR);
			
			this.dirty = false;
			
		}
		
	}
	
	public static enum EnumVectorType
	{
		ROTATION, TRANSLATION, SCALING;
		
	}
	
}
