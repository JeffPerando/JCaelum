
package com.elusivehawk.caelum.sound;

import java.nio.ByteBuffer;
import org.lwjgl.openal.AL10;
import com.elusivehawk.caelum.CaelumEngine;
import com.elusivehawk.caelum.IDisposable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class SoundBuffer implements IDisposable
{
	protected final SoundClient client;
	
	protected ByteBuffer source = null;
	protected int format = 0, sampleRate = 0;
	
	private boolean initiated = false;
	private TaskLoadSound loadTask = null;
	private int id = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public SoundBuffer(SoundClient sClient)
	{
		assert sClient != null;
		
		client = sClient;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public SoundBuffer(SoundClient sClient, ByteBuffer data, int fmt, int samples)
	{
		this(sClient);
		
		assert data != null;
		
		source = data;
		format = fmt;
		sampleRate = samples;
		
	}
	
	@Override
	public void dispose()
	{
		if (this.initiated)
		{
			this.client.scheduleBufferForDeletion(this);
			
		}
		
	}
	
	public int getId()
	{
		if (!this.initiated)
		{
			if (this.loadTask == null)
			{
				this.loadTask = new TaskLoadSound(this.source, this.format, this.sampleRate);
				
				CaelumEngine.tasks().scheduleTask(this.loadTask);
				
			}
			else if (this.loadTask.isFinished())
			{
				synchronized (this)
				{
					this.id = this.loadTask.getBufferId();
					
					this.initiated = true;
					
					this.loadTask = null;
					
				}
				
				this.client.registerBuffer(this);
				
			}
			
		}
		
		return this.id;
	}
	
	public void destroy()
	{
		if (this.initiated)
		{
			AL10.alDeleteBuffers(this.loadTask == null ? this.id : this.loadTask.getBufferId());
			
			this.id = 0;
			
		}
		
	}
	
}
