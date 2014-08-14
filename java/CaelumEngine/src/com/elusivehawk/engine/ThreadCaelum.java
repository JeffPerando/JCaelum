
package com.elusivehawk.engine;

import com.elusivehawk.util.Internal;
import com.elusivehawk.util.concurrent.ThreadTimed;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Internal
public abstract class ThreadCaelum extends ThreadTimed
{
	@Override
	public double getMaxDelta()
	{
		return 0.5;
	}
	
	@Override
	public void handleException(Throwable e)
	{
		CaelumEngine.log().err(e);
		
	}
	
}
