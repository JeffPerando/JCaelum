
package com.elusivehawk.caelum.sound;

import java.io.DataInputStream;
import java.nio.ByteBuffer;
import org.lwjgl.openal.AL10;
import com.elusivehawk.caelum.CaelumEngine;
import com.elusivehawk.caelum.assets.Asset;
import com.elusivehawk.caelum.assets.EnumAssetType;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class SoundAsset extends Asset implements ISoundBuffer
{
	private final SoundClient client;
	
	private boolean initiated = false;
	private TaskLoadSound loadTask = null;
	
	private ByteBuffer source = null;
	private int format = 0, sampleRate = 0, id = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public SoundAsset(SoundClient sClient, String path)
	{
		super(path, EnumAssetType.SOUND);
		
		assert sClient != null;
		
		client = sClient;
		
	}
	
	@Override
	public int getId()
	{
		if (!this.isRead())
		{
			return 0;
		}
		
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
	
	@Override
	public void destroy()
	{
		if (this.initiated)
		{
			AL10.alDeleteBuffers(this.loadTask == null ? this.id : this.loadTask.getBufferId());
			
			this.id = 0;
			
		}
		
	}
	
	@Override
	protected boolean readAsset(DataInputStream in) throws Throwable
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected boolean disposeImpl(Object... args)
	{
		if (!this.initiated)
		{
			return false;
		}
		
		this.client.scheduleBufferForDeletion(this);
		
		return true;
	}
	
}
