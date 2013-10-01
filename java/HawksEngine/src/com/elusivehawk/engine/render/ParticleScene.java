
package com.elusivehawk.engine.render;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;
import com.elusivehawk.engine.math.Vector4f;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ParticleScene
{
	protected FloatBuffer buf;
	protected final List<IParticle> particles = new ArrayList<IParticle>();
	protected final VertexBufferObject vbo;
	public final int particleCount;
	public final GLProgram p;
	
	public ParticleScene(int maxParticles)
	{
		this(maxParticles, null);
		
	}
	
	public ParticleScene(int maxParticles, List<IParticle> initParticles)
	{
		buf = BufferUtils.createFloatBuffer(maxParticles * 8);
		particleCount = maxParticles;
		
		if (initParticles != null)
		{
			for (IParticle p : initParticles)
			{
				this.spawnParticle(p);
				
			}
			
		}
		
		p = new GLProgram(); //TODO Create default particle shaders.
		vbo = new VertexBufferObject(GL.GL_ARRAY_BUFFER, buf, GL.GL_STREAM_DRAW);
		
		p.attachVBOs(vbo);
		
		p.attachVertexAttribs(new String[]{"in_pos", "in_col"}, new int[]{0, 1}, false);
		
		GL.glVertexAttribPointer(0, 4, false, 0, buf);
		GL.glVertexAttribPointer(1, 4, false, 4, buf);
		
	}
	
	public void spawnParticle(IParticle p)
	{
		if (this.particles.size() >= this.particleCount)
		{
			return;
		}
		
		this.buf.position(this.particles.size() * 8);
		new Vector4f(p.getPosition(), 1f).store(this.buf);
		EnumColorFormat.RGBA.convert(p.getColor()).store(this.buf);
		
		this.particles.add(p);
		
	}
	
	public boolean updateBeforeRendering()
	{
		for (int c = 0; c < this.particles.size(); c++)
		{
			IParticle p = this.particles.get(c);
			
			p.updateParticle();
			
			if (p.flaggedForRemoval())
			{
				this.particles.remove(c);
				
				//TODO Fix
				
				continue;
			}
			
			if (p.updatePositionOrColor())
			{
				Vector4f vec = new Vector4f(p.getPosition(), 1f);
				Color col = EnumColorFormat.RGBA.convert(p.getColor());
				
				this.buf.position(c * 8);
				
				vec.store(this.buf);
				col.store(this.buf);
				
			}
			
		}
		
		if (this.getParticleCount() == 0)
		{
			return false;
		}
		
		this.buf.position(0);
		
		return true;
	}
	
	public int getParticleCount()
	{
		return this.particles.size();
	}
	
}
