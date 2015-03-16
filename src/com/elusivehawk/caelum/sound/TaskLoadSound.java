
package com.elusivehawk.caelum.sound;

import java.nio.ByteBuffer;
import org.lwjgl.openal.AL10;
import com.elusivehawk.util.task.Task;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TaskLoadSound extends Task
{
	private final ByteBuffer src;
	private final int format, sampleRate;
	
	private int buf = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public TaskLoadSound(ByteBuffer buf, int fmt, int samples)
	{
		assert buf != null;
		
		src = buf;
		format = fmt;
		sampleRate = samples;
		
	}
	
	@Override
	protected boolean finishTask() throws Throwable
	{
		this.buf = AL10.alGenBuffers();
		
		AL10.alBufferData(this.buf, this.format, this.src, this.sampleRate);
		
		return true;
	}
	
	public int getBufferId()
	{
		return this.buf;
	}
	
}
