
package com.elusivehawk.engine.android;

import android.util.Log;
import com.elusivehawk.engine.core.EnumLogType;
import com.elusivehawk.engine.core.ILog;

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
	public void log(EnumLogType type, String msg)
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
	public void log(EnumLogType type, String msg, Throwable e)
	{
		switch (type)
		{
			case INFO: Log.i(ID, msg, e);
			case DEBUG: Log.d(ID, msg, e);
			case ERROR: Log.e(ID, msg, e);
			case VERBOSE: if (this.verbosity) Log.v(ID, msg, e);
			case WARN: Log.w(ID, msg, e);
			case WTF: Log.wtf(ID, msg, e);
			
		}
		
	}
	
	@Deprecated
	@Override
	public void log(EnumLogType type, Iterable<String> str)
	{
		for (String line : str)
		{
			this.log(type, line);
			
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
