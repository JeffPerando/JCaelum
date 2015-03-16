
package com.elusivehawk.caelum.sound;

import org.lwjgl.openal.AL10;
import com.elusivehawk.caelum.IDisposable;
import com.elusivehawk.util.math.VectorF;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class SoundSource implements IDisposable
{
	private final SoundClient client;
	
	private boolean initiated = false, loop = false, locked = false, destroyed = false;
	private int id = 0, lastBuf = 0;
	
	private float pitch = 0f, gain = 0f;
	private VectorF pos = new VectorF(), vel = new VectorF();
	
	@SuppressWarnings("unqualified-field-access")
	public SoundSource(SoundClient sClient, ISoundBuffer buf)
	{
		assert sClient != null;
		
		client = sClient;
		
	}
	
	@Override
	public void dispose(Object... args)
	{
		this.client.scheduleSourceForDeletion(this);
		
	}
	
	public SoundSource pitch(float f)
	{
		if (this.destroyed)
		{
			return this;
		}
		
		if (this.locked)
		{
			return this;
		}
		
		this.pitch = f;
		
		return this;
	}
	
	public SoundSource gain(float f)
	{
		if (this.destroyed)
		{
			return this;
		}
		
		if (this.locked)
		{
			return this;
		}
		
		this.gain = f;
		
		return this;
	}
	
	public SoundSource position(VectorF vec)
	{
		if (this.destroyed)
		{
			return this;
		}
		
		if (this.locked)
		{
			return this;
		}
		
		this.pos = vec;
		
		return this;
	}
	
	public SoundSource velocity(VectorF vec)
	{
		if (this.destroyed)
		{
			return this;
		}
		
		if (this.locked)
		{
			return this;
		}
		
		this.vel = vec;
		
		return this;
	}
	
	public SoundSource loop(boolean b)
	{
		if (this.destroyed)
		{
			return this;
		}
		
		if (this.locked)
		{
			return this;
		}
		
		this.loop = b;
		
		return this;
	}
	
	public SoundSource lock()
	{
		this.locked = true;
		
		return this;
	}
	
	public VectorF getPosition()
	{
		return this.pos;
	}
	
	public VectorF getVelocity()
	{
		return this.vel;
	}
	
	public boolean play(ISoundBuffer buffer)
	{
		if (buffer == null)
		{
			throw new NullPointerException();
		}
		
		if (this.destroyed)
		{
			return false;
		}
		
		if (!this.initiated)
		{
			this.id = AL10.alGenSources();
			
			this.client.registerSource(this);
			
			AL10.alSourcef(this.id, AL10.AL_PITCH, this.pitch);
			AL10.alSourcef(this.id, AL10.AL_GAIN, this.gain);
			AL10.alSource(this.id, AL10.AL_POSITION, this.pos.asBuffer());
			AL10.alSource(this.id, AL10.AL_VELOCITY, this.vel.asBuffer());
			AL10.alSourcei(this.id, AL10.AL_LOOPING, this.loop ? 1 : 0);
			
			this.initiated = true;
			
		}
		
		int buf = buffer.getId();
		
		if (this.lastBuf != buf)
		{
			AL10.alSourceStop(this.id);
			AL10.alSourcei(this.id, AL10.AL_BUFFER, buf);
			
			this.lastBuf = buf;
			
		}
		
		if (this.pos.isDirty())
		{
			AL10.alSource(this.id, AL10.AL_POSITION, this.pos.asBuffer());
			
		}
		
		if (this.vel.isDirty())
		{
			AL10.alSource(this.id, AL10.AL_VELOCITY, this.vel.asBuffer());
			
		}
		
		int status = this.getState();
		
		if (status == AL10.AL_PLAYING)
		{
			return true;
		}
		
		AL10.alSourcePlay(this.id);
		
		return true;
	}
	
	public void pause()
	{
		if (this.destroyed)
		{
			return;
		}
		
		if (this.getState() == AL10.AL_PLAYING)
		{
			AL10.alSourcePause(this.id);
			
		}
		
	}
	
	public void stop()
	{
		if (this.destroyed)
		{
			return;
		}
		
		if (this.getState() == AL10.AL_PLAYING)
		{
			AL10.alSourceStop(this.id);
			
		}
		
	}
	
	public void destroy()
	{
		if (!this.destroyed)
		{
			AL10.alDeleteSources(this.id);
			
			this.destroyed = true;
			
		}
		
	}
	
	public int getState()
	{
		if (this.destroyed)
		{
			return AL10.AL_NONE;
		}
		
		return AL10.alGetSourcei(this.id, AL10.AL_SOURCE_STATE);
	}
	
}
