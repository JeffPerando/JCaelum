
package com.elusivehawk.engine.sound;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import com.elusivehawk.engine.core.GameLog;
import com.elusivehawk.engine.core.ThreadStoppable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ThreadSoundPlayer extends ThreadStoppable
{
	protected final List<ISound> sounds = new ArrayList<ISound>();
	
	@Override
	public void rawUpdate()
	{
		for (ISound s : this.sounds)
		{
			if (AL10.alGetSourcei(s.getId(), AL10.AL_SOURCE_STATE) == AL10.AL_STOPPED)
			{
				if (s.isMusic())
				{
					
				}
				
			}
			
		}
		
	}
	
	@Override
	public boolean initiate()
	{
		boolean run = true;
		
		try
		{
			AL.create();
			
			SoundUtil.checkForALError();
			
		}
		catch (Exception e)
		{
			GameLog.error(e);
			
			run = false;
			
		}
		
		return run;
	}
	
	public synchronized void playSound(ISound s)
	{
		if (AL10.alIsSource(s.getId()))
		{
			this.sounds.add(s);
			
		}
		
	}
	
}
