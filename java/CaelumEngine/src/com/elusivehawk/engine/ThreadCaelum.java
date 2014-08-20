
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
	public ThreadCaelum(){}
	
	public ThreadCaelum(String name)
	{
		super(name);
		
	}
	
	@Override
	public void handleException(Throwable e)
	{
		CaelumEngine.log().err(e);
		
	}
	
}
