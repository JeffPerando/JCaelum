
package com.elusivehawk.engine.render;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.lwjgl.BufferUtils;
import com.elusivehawk.engine.math.Vector;
import com.elusivehawk.engine.math.VectorF;

/**
 * 
 * @deprecated To be replaced with a less memory-hogging particle system.
 * 
 * @author Elusivehawk
 */
@Deprecated
public class ParticleScene implements ILogicalRender
{
	public static final int PARTICLE_FLOAT_COUNT = 7;
	
	protected final FloatBuffer buf;
	protected final List<IParticle> particles = new ArrayList<IParticle>();
	protected final VertexBufferObject vbo;
	protected final int particleCount;
	protected final GLProgram p;
	
	@SuppressWarnings("unqualified-field-access")
	public ParticleScene(int maxParticles)
	{
		buf = BufferUtils.createFloatBuffer(maxParticles * PARTICLE_FLOAT_COUNT);
		particleCount = maxParticles;
		
		p = GLProgram.create(null); //TODO Create default particle shaders.
		vbo = new VertexBufferObject(GL.GL_ARRAY_BUFFER, buf, GL.GL_STREAM_DRAW);
		
		if (p.bind())
		{
			p.attachVBO(vbo, Arrays.asList(0, 1));
			
			p.unbind();
			
		}
		else
		{
			throw new RuntimeException("Could not create particle scene, due to a program binding failure.");
			
		}
		
	}
	
	public void spawnParticle(IParticle p)
	{
		if (this.particles.size() >= this.particleCount)
		{
			return;
		}
		
		this.buf.position(this.particles.size() * PARTICLE_FLOAT_COUNT);
		Vector<Float> pos = new VectorF(3, p.getPosition());
		
		for (int c = 0; c < 3; c++)
		{
			this.buf.put(pos.get(c));
			
		}
		
		EnumColorFormat.RGBA.convert(p.getColor()).store(this.buf);
		
		this.particles.add(p);
		
	}
	
	public int getParticleCount()
	{
		return this.particles.size();
	}
	
	@Override
	public boolean updateBeforeUse(IRenderHUB hub)
	{
		if (this.getParticleCount() == 0)
		{
			return false;
		}
		
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
				Vector<Float> vec = new VectorF(3, p.getPosition());
				Color col = EnumColorFormat.RGBA.convert(p.getColor());
				
				this.buf.position(c * PARTICLE_FLOAT_COUNT);
				
				for (int count = 0; count < 3; count++)
				{
					this.buf.put(vec.get(count));
					
				}
				
				col.store(this.buf);
				
			}
			
		}
		
		this.buf.position(0);
		
		return this.getParticleCount() != 0;
	}
	
	@Override
	public GLProgram getProgram()
	{
		return this.p;
	}
	
}
