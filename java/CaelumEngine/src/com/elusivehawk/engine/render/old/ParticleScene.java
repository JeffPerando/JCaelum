
package com.elusivehawk.engine.render.old;

import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.List;
import com.elusivehawk.engine.render.Color;
import com.elusivehawk.engine.render.ColorFormat;
import com.elusivehawk.engine.render.EnumColorFilter;
import com.elusivehawk.engine.render.ILogicalRender;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.RenderHelper;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.VertexBuffer;
import com.elusivehawk.util.BufferHelper;
import com.elusivehawk.util.math.Vector;

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
	protected final List<IParticle> particles;
	protected final VertexBuffer vbo;
	protected final int particleCount;
	protected final GLProgram p;
	
	@SuppressWarnings("unqualified-field-access")
	public ParticleScene(int maxParticles)
	{
		particles = null;//FIXME new SimpleList<IParticle>(maxParticles, false);
		buf = BufferHelper.createFloatBuffer(maxParticles * PARTICLE_FLOAT_COUNT);
		particleCount = maxParticles;
		
		p = new GLProgram(); //TODO Create default particle shaders.
		vbo = new VertexBuffer(GLConst.GL_ARRAY_BUFFER, buf, GLConst.GL_STREAM_DRAW);
		
		RenderContext con = RenderHelper.renderContext();
		
		if (p.bind(con))
		{
			p.attachVBO(vbo, Arrays.asList(0, 1));
			
			p.unbind(con);
			
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
		Vector pos = (Vector)new Vector(3).set(p.getPosition());
		
		for (int c = 0; c < 3; c++)
		{
			this.buf.put(pos.get(c));
			
		}
		
		//EnumColorFormat.RGBA.convert(p.getColor()).store(this.buf);
		
		this.particles.add(p);
		
	}
	
	public int getParticleCount()
	{
		return this.particles.size();
	}
	
	@Override
	public boolean updateBeforeRender(RenderContext con, double delta)
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
				Vector vec = (Vector)new Vector(3).set(p.getPosition());
				Color col = p.getColor();
				
				this.buf.position(c * PARTICLE_FLOAT_COUNT);
				
				for (int count = 0; count < 3; count++)
				{
					this.buf.put(vec.get(count));
					
				}
				
				for (EnumColorFilter filter : ColorFormat.RGBA.filters)
				{
					this.buf.put(col.getColorFloat(filter));
					
				}
				
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
