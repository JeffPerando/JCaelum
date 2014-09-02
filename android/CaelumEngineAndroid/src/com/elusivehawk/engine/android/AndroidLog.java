
package com.elusivehawk.engine.android;

import android.util.Log;
import com.elusivehawk.util.EnumLogType;
import com.elusivehawk.util.ILog;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class AndroidLog implements ILog
{
	private static final String ID = "CaelumAndroid";
	
	private boolean verbosity = true;
	
	@Override
	public void log(EnumLogType type, String msg, Object... info)
	{
		switch (type)
		{
			case INFO: Log.i(ID, msg);
			case DEBUG: Log.d(ID, msg);
			case ERROR: Log.e(ID, msg);
			case VERBOSE: if (this.verbosity) Log.v(ID, msg);
			case WARN: Log.w(ID, msg);
			case WTF: Log.wtf(ID, msg);
			
		}
		
	}
	
	@Override
	public boolean enableVerbosity()
	{
		return this.verbosity;
	}

	@Override
	public void setEnableVerbosity(boolean v)
	{
		this.verbosity = v;
		
	}
	
}
