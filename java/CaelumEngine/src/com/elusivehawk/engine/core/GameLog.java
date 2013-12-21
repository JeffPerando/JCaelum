
package com.elusivehawk.engine.core;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * 
 * Static class for sending things to the console.
 * <p>
 * Note: Unlike {@link System}.out.println(), this is thread safe.<br>
 * 
 * @author Elusivehawk
 */
public final class GameLog implements ILog
{
	private final List<String> crashDialog = TextParser.read(FileHelper.createFile((CaelumEngine.DEBUG ? "src" : ".") + "/com/elusivehawk/engine/core/CrashReportDialog.txt"));
	private final Random rng = new Random();
	
	public boolean enableVerbosity = true;
	
	@Override
	public void log(EnumLogType type, String msg)
	{
		if (!this.enableVerbosity && type == EnumLogType.VERBOSE)
		{
			return;
		}
		
		StringBuilder b = new StringBuilder();
		Calendar cal = Calendar.getInstance();
		
		b.append("[" + type.name() + "] ");
		b.append(cal.get(Calendar.DATE) + "-");
		b.append(cal.get(Calendar.MONTH) + 1 + "-");
		b.append(cal.get(Calendar.YEAR) + " ");
		int minute = cal.get(Calendar.MINUTE);
		boolean amOrPm = cal.get(Calendar.AM_PM) == Calendar.PM;
		b.append(cal.get(Calendar.HOUR) + ":" + (minute < 10 ? "0" : "") + minute + ":" + cal.get(Calendar.SECOND) + ":" + cal.get(Calendar.MILLISECOND) + " " + (amOrPm ? "PM" : "AM") + ": ");
		b.append(type.err && msg == null ? this.crashDialog.get(this.rng.nextInt(this.crashDialog.size())) : msg);
		
		String fin = b.toString();
		
		if (type.err)
		{
			System.err.println(fin);
			
		}
		else
		{
			System.out.println(fin);
			
		}
		
	}
	
	@Override
	public void log(EnumLogType type, String msg, Throwable e)
	{
		this.log(type, msg);
		e.printStackTrace();
		
	}
	
}
