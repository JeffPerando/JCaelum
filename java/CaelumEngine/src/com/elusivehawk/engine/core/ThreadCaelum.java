
package com.elusivehawk.engine.core;

import com.elusivehawk.engine.util.ThreadTimed;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class ThreadCaelum extends ThreadTimed
{
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.util.ThreadTimed#getMaxDelta()
	 */
	@Override
	public double getMaxDelta()
	{
		return 0.5;
	}
	
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.util.ThreadStoppable#handleException(java.lang.Throwable)
	 */
	@Override
	public void handleException(Throwable e)
	{
		CaelumEngine.log().log(EnumLogType.ERROR, null, e);
		
	}
	
}
