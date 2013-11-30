
package com.elusivehawk.engine.core;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class GameLog
{
	public static volatile boolean enableVerbosity = true;
	public static final List<String> CRASH_DIALOG = TextParser.read(FileHelper.createFile((CaelumEngine.DEBUG ? "src" : ".") + "/com/elusivehawk/engine/core/CrashReportDialog.txt"));
	
	private static Random rand = new Random();
	
	public static void info(String message)
	{
		log(message, EnumLogType.INFO);
		
	}
	
	public static void debug(String message)
	{
		log(message, EnumLogType.DEBUG);
		
	}
	
	public static void warn(String message)
	{
		log(message, EnumLogType.WARN);
		
	}
	
	public static void error(String message)
	{
		log(message, EnumLogType.ERROR);
		
	}
	
	public static void error(Throwable e)
	{
		error(null, e);
		
	}
	
	public static void error(String message, Throwable e)
	{
		error(message == null ? (CRASH_DIALOG.isEmpty() ? "Error found:" : CRASH_DIALOG.get(rand.nextInt(CRASH_DIALOG.size()))) : message);
		
		for (PrintStream ps : EnumLogType.ERROR.out)
		{
			e.printStackTrace(ps);
			
		}
		
	}
	
	public static synchronized void log(String message, EnumLogType type)
	{
		if (!enableVerbosity && type.verbose)
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
		b.append(message);
		
		String fin = b.toString();
		
		for (PrintStream ps : type.out)
		{
			ps.println(fin);
			
		}
		
	}
	
	public static enum EnumLogType
	{
		INFO(System.out, true),
		DEBUG(System.out, true),
		WARN(System.err, false),
		ERROR(System.err, false);
		
		List<PrintStream> out = new ArrayList<PrintStream>();
		final boolean verbose;
		
		EnumLogType(PrintStream ps, boolean isVerbose)
		{
			out.add(ps);
			verbose = isVerbose;
			
		}
		
		public static void addOutputToEnums(PrintStream stream)
		{
			for (EnumLogType type : values())
			{
				type.addOutput(stream);
				
			}
			
		}
		
		public synchronized void addOutput(PrintStream stream)
		{
			this.out.add(stream);
			
		}
		
	}
	
}
