
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
	private static final String ID = "CaelumDroid";
	
	@Override
	public void log(EnumLogType type, String msg)
	{
		switch (type)
		{
			case INFO: Log.i(ID, msg);
			case DEBUG: Log.d(ID, msg);
			case ERROR: Log.e(ID, msg);
			case VERBOSE: Log.v(ID, msg);
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
			case VERBOSE: Log.v(ID, msg, e);
			case WARN: Log.w(ID, msg, e);
			case WTF: Log.wtf(ID, msg, e);
			
		}
		
	}
	
}
