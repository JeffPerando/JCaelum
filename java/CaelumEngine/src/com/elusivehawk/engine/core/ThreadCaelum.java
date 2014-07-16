
package com.elusivehawk.engine.core;

import com.elusivehawk.util.concurrent.ThreadTimed;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
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
		CaelumEngine.log().log(EnumLogType.ERROR, null, e);
		
	}
	
}
