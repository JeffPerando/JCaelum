
package com.elusivehawk.engine.core;

import java.util.Calendar;
import java.util.List;
import java.util.Random;
import com.elusivehawk.engine.util.FileHelper;
import com.elusivehawk.engine.util.StringHelper;

/**
 * 
 * Default logging implementation.
 * <p>
 * Note: Unlike {@link System}.out.println(), this is thread safe.
 * 
 * @author Elusivehawk
 */
public class GameLog implements ILog
{
	protected final List<String> crashDialog = StringHelper.read(FileHelper.createFile((CaelumEngine.DEBUG ? "src" : ".") + "/com/elusivehawk/engine/core/CrashReportDialog.txt"));
	protected final Random rng = new Random();
	
	private boolean enableVerbosity = true;
	
	@Override
	public void log(EnumLogType type, String msg)
	{
		if (!this.enableVerbosity && type == EnumLogType.VERBOSE)
		{
			return;
		}
		
		StringBuilder b = new StringBuilder();
		
		b.append("[");
		b.append(type.name());
		b.append("] ");
		b.append(StringHelper.parseDate(Calendar.getInstance(), "-", ":"));
		b.append(": ");
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
