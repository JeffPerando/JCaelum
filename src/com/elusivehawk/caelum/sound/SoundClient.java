
package com.elusivehawk.caelum.sound;

import java.util.List;
import org.lwjgl.openal.ALContext;
import com.elusivehawk.caelum.IDisposable;
import com.elusivehawk.util.storage.SyncList;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class SoundClient implements IDisposable
{
	private final List<SoundBuffer> buffers = SyncList.newList(), bufDeleteBuf = Lists.newArrayList();
	private final List<SoundSource> sources = SyncList.newList(), srcDeleteBuf = Lists.newArrayList();
	
	private ALContext context = null;
	
	@Override
	public void dispose()
	{
		if (this.context == null)
		{
			return;
		}
		
		this.buffers.forEach(((buffer) -> {buffer.destroy();}));
		this.buffers.clear();
		
		this.sources.forEach(((sound) -> {sound.destroy();}));
		this.sources.clear();
		
		this.context.destroy();
		
	}
	
	public void initiate()
	{
		this.context = ALContext.create();
		
	}
	
	public void cleanup()
	{
		this.srcDeleteBuf.forEach(((source) -> {source.destroy();}));
		this.sources.removeAll(this.srcDeleteBuf);
		this.srcDeleteBuf.clear();
		
		this.bufDeleteBuf.forEach(((buffer) -> {buffer.destroy();}));
		this.buffers.removeAll(this.bufDeleteBuf);
		this.bufDeleteBuf.clear();
		
	}
	
	public void registerSource(SoundSource source)
	{
		assert source != null;
		
		this.sources.add(source);
		
	}
	
	public void registerBuffer(SoundBuffer buffer)
	{
		assert buffer != null;
		
		this.buffers.add(buffer);
		
	}
	
	public void scheduleSourceForDeletion(SoundSource source)
	{
		assert source != null;
		
		this.srcDeleteBuf.add(source);
		
	}
	
	public void scheduleBufferForDeletion(SoundBuffer buffer)
	{
		assert buffer != null;
		
		this.bufDeleteBuf.add(buffer);
		
	}
	
}
