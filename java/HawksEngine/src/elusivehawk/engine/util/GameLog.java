
package elusivehawk.engine.util;

import java.io.PrintStream;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import elusivehawk.engine.core.Game;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class GameLog
{
	public static volatile boolean enableVerbosity = true;
	public static final List<String> CRASH_DIALOG = TextParser.read(TextParser.createFile((Game.DEBUG ? "src" : ".") + "/elusivehawk/engine/core/CrashReportDialog.txt"));
	
	private static Random rand = new Random();
	
	public static void info(String message)
	{
		log(message, LogType.INFO);
		
	}
	
	public static void debug(String message)
	{
		log(message, LogType.DEBUG);
		
	}
	
	public static void warn(String message)
	{
		log(message, LogType.WARN);
		
	}
	
	public static void error(String message)
	{
		log(message, LogType.ERROR);
		
	}
	
	public static void error(Throwable e)
	{
		error(null, e);
		
	}
	
	public static void error(String message, Throwable e)
	{
		error(message == null ? CRASH_DIALOG.get(rand.nextInt(CRASH_DIALOG.size())) : message);
		e.printStackTrace(LogType.ERROR.getOutput());
		
	}
	
	public static synchronized void log(String message, LogType type)
	{
		if (!enableVerbosity && type.isVerbose)
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
		
		type.getOutput().println(b.toString());
		
	}
	
	public static enum LogType
	{
		INFO(System.out, true),
		DEBUG(System.out, true),
		WARN(System.err, false),
		ERROR(System.err, false);
		
		private PrintStream out;
		public final boolean isVerbose;
		
		LogType(PrintStream ps, boolean verbose)
		{
			out = ps;
			isVerbose = verbose;
			
		}
		
		public synchronized void setOutput(PrintStream stream)
		{
			this.out = stream;
			
		}
		
		public PrintStream getOutput()
		{
			return this.out;
		}
		
	}
	
}
