
package com.elusivehawk.util;

import java.io.File;
import java.util.Calendar;
import java.util.List;

/**
 * 
 * Default logging implementation.
 * 
 * @author Elusivehawk
 */
public class DefaultLog implements ILog
{
	protected final List<String> crashDialog = CompInfo.BUILT ? StringHelper.read(FileHelper.getResourceStream("/res/CrashReportDialog.txt")) : StringHelper.read(new File(CompInfo.JAR_DIR.getParentFile(), "/res/CrashReportDialog.txt"));
	
	private boolean enableVerbosity = true;
	
	@Override
	public void log(EnumLogType type, String msg)
	{
		if (!this.enableVerbosity && type == EnumLogType.VERBOSE)
		{
			return;
		}
		
		if (!CompInfo.DEBUG && type == EnumLogType.DEBUG)
		{
			return;
		}
		
		String fin = String.format("[%s] [%s]: %s\n", type, StringHelper.parseDate(Calendar.getInstance(), "-", ":"), type.err && msg == null && !this.crashDialog.isEmpty() ? this.crashDialog.get(RNG.rng().nextInt(this.crashDialog.size())) : msg);
		
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
