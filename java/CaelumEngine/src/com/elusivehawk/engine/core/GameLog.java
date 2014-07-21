
package com.elusivehawk.engine.core;

import java.util.Calendar;
import java.util.List;
import com.elusivehawk.util.FileHelper;
import com.elusivehawk.util.RNG;
import com.elusivehawk.util.StringHelper;

/**
 * 
 * Default logging implementation.
 * 
 * @author Elusivehawk
 */
public class GameLog implements ILog
{
	protected final List<String> crashDialog = StringHelper.read(FileHelper.createFile("CrashReportDialog.txt"));
	
	private boolean enableVerbosity = true;
	
	@Override
	public void log(EnumLogType type, String msg)
	{
		if (!this.enableVerbosity && type == EnumLogType.VERBOSE)
		{
			return;
		}
		
		String fin = String.format("[%s] [%s]: %s\n", type, StringHelper.parseDate(Calendar.getInstance(), "-", ":"), type.err && msg == null ? this.crashDialog.get(RNG.rng().nextInt(this.crashDialog.size())) : msg);
		
		if (type.err)
		{
			System.err.print(fin);
			
		}
		else
		{
			System.out.print(fin);
			
		}
		
	}
	
	@Override
	public void log(EnumLogType type, String msg, Throwable e)
	{
		this.log(type, msg);
		e.printStackTrace();
		
	}
	
	@Override
	public boolean enableVerbosity()
	{
		return this.enableVerbosity;
	}
	
	@Override
	public void setEnableVerbosity(boolean v)
	{
		this.enableVerbosity = v;
		
	}
	
}
