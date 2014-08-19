
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
	public void handleException(Throwable e)
	{
		CaelumEngine.log().err(e);
		
	}
	
}
