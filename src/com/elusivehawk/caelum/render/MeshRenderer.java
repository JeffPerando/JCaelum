
package com.elusivehawk.caelum.render;

import java.nio.FloatBuffer;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.render.gl.GL1;
import com.elusivehawk.caelum.render.gl.GL2;
import com.elusivehawk.caelum.render.gl.GLBuffer;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.gl.GLEnumBufferTarget;
import com.elusivehawk.caelum.render.gl.GLEnumDataUsage;
import com.elusivehawk.caelum.render.gl.GLProgram;
import com.elusivehawk.util.math.MatrixHelper;
import com.elusivehawk.util.math.Quaternion;
import com.elusivehawk.util.math.Vector;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * Used to render static {@link Mesh}es with non-static information (i.e. rotation)
 * 
 * @author Elusivehawk
 * 
 * @see Mesh
 * @see RenderableObj
 */
public class MeshRenderer extends RenderableObj implements Quaternion.Listener, Vector.Listener
{
	protected final Vector
			offset = new Vector(),
			pos = new Vector(),
			scale = new Vector(1f, 1f, 1f);
	
	protected final Quaternion
			rotOff = new Quaternion(),
			rot = new Quaternion();
	
	protected final Mesh mesh;
	
	protected GLBuffer vbo = null;
	protected FloatBuffer buf = null;
	
	//protected int frame = 0;
	//protected IModelAnimation anim = null, lastAnim = null;
	protected int texFrame = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public MeshRenderer(Mesh m)
	{
		assert m != null;
		
		mesh = m;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public MeshRenderer(Mesh m, GLProgram program)
	{
		super(program);
		
		assert m != null;
		
		mesh = m;
		
	}
	
	@Override
	protected boolean initiate(RenderContext rcon)
	{
		if (!this.mesh.isLoaded())
		{
			this.mesh.initiate(rcon);
			
			if (!this.mesh.isLoaded())
			{
				return false;
			}
			
		}
		
		this.buf = BufferHelper.createFloatBuffer(this.mesh.getIndiceCount() * 16);
		this.vbo = new GLBuffer(GLEnumBufferTarget.GL_ARRAY_BUFFER, GLEnumDataUsage.GL_DYNAMIC_DRAW, this.buf);
		
		this.mesh.populate(this.vao);
		this.vao.addVBO(this.vbo);
		
		return true;
	}
	
	@Override
	protected void doRender(RenderContext rcon) throws RenderException
	{
		GL1.glDrawElements(this.mesh.getDrawType(), this.mesh.getPolyCount(), GLConst.GL_UNSIGNED_INT, 0);
		
	}
	
	@Override
	public synchronized void onVecChanged(Vector vec)
	{
		this.offset.add(vec, this.pos);
		//this.setIsDirty(true);
		
	}
	
	@Override
	public synchronized void onQuatChanged(Quaternion q)
	{
		this.rotOff.add(q, this.rot);
		//this.setIsDirty(true);
		
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
		
	}
	
	@Override
	public String toString()
	{
		return String.format("%s:%s-%s-%s", this.mesh.filepath, this.pos.toString(), this.scale.toString(), this.rot.toString());
	}
	
	@Override
	public boolean isCulled(ICamera cam)
	{
		if (this.mesh == null)
		{
			return true;
		}
		
		//TODO Finish
		
		return false;
	}
	
	@Override
	protected void manipulateProgram(RenderContext rcon)
	{
		super.manipulateProgram(rcon);
		
		if (this.rot.isDirty() || this.scale.isDirty() || this.pos.isDirty())
		{
			GL2.glUniformMatrix4f("model", MatrixHelper.createHomogenousMatrix(this.rot, this.scale, this.pos));
			
			this.rot.setIsDirty(false);
			this.scale.setIsDirty(false);
			this.pos.setIsDirty(false);
			
		}
		
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
		
	}
	
	public synchronized void setIndice(int i, Matrix m)
	{
		this.buf.position(i * 16);
		
		m.save(this.buf);
		
		this.setIsDirty(true);
		
	}*/
	
	public Mesh getMesh()
	{
		return this.mesh;
	}
	
	/*public int getCurrentFrame()
	{
		return this.frame;
	}*/
	
	public int getCurrentTexFrame()
	{
		return this.texFrame;
	}
	
	public MeshRenderer setPosOffset(Vector off)
	{
		this.offset.set(off);
		this.pos.add(this.offset);
		
		return this;
	}
	
	public MeshRenderer setScale(Vector s)
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
	
	public MeshRenderer setRotOffset(Quaternion qoff)
	{
		this.rotOff.set(qoff);
		this.rot.add(this.rotOff);
		
		return this;
	}
	
}
