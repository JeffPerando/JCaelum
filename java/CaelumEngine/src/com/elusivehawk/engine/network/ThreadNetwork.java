
package com.elusivehawk.engine.network;

import java.net.Socket;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;
import com.elusivehawk.engine.util.ThreadTimed;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
abstract class ThreadNetwork extends ThreadTimed
{
	protected final int updateCount;
	protected final Socket skt;
	protected final IHost host;
	
	@SuppressWarnings("unqualified-field-access")
	ThreadNetwork(IHost h, Socket s, int ups)
	{
		assert h != null;
		assert s != null;
		
		host = h;
		skt = s;
		updateCount = ups;
		
	}
	
	@Override
	public void handleException(Throwable e)
	{
		CaelumEngine.instance().getLog().log(EnumLogType.ERROR, null, e);
		
	}
	
	@Override
	public int getTargetUpdateCount()
	{
		return this.updateCount;
	}
	
	@Override
	public double getMaxDelta()
	{
		return 0.5;
	}
	
}
