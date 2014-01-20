
package com.elusivehawk.engine.core;

import java.util.Calendar;
import java.util.List;
import java.util.Random;
import com.elusivehawk.engine.util.FileHelper;
import com.elusivehawk.engine.util.TextParser;

/**
 * 
 * Default logging implementation.
 * <p>
 * Note: Unlike {@link System}.out.println(), this is thread safe.
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
		
		b.append("[" + type.name() + "] ");
		b.append(TextParser.parseDate(Calendar.getInstance()) + ": ");
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
