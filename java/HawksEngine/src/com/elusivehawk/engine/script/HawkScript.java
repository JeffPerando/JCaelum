
package com.elusivehawk.engine.script;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.elusivehawk.engine.core.FileHelper;
import com.elusivehawk.engine.core.TextParser;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class HawkScript
{
	protected final File file;
	protected boolean initiated = false;
	protected SymbolBuffer buf = new SymbolBuffer();
	protected List<IInvokable> invokables = new ArrayList<IInvokable>();
	protected int pos = 0;
	
	public HawkScript(String src)
	{
		this(FileHelper.createFile(src));
		
	}
	
	public HawkScript(File script)
	{
		file = script;
		
	}
	
	public void initiateOrRefresh() throws HawkScriptException
	{
		List<String> strs = TextParser.read(this.file);
		
		this.buf.init(strs);
		
		ISymbol lastSym = null, sym = null;
		
		while (this.buf.hasNext())
		{
			sym = this.buf.next();
			
			IInvokable inv = sym.decode(lastSym, this.buf);
			
			if (inv != null)
			{
				this.invokables.add(inv);
				
			}
			
			lastSym = sym;
			
		}
		
		this.initiated = true;
		
	}
	
	public void update()
	{
		if (!this.initiated)
		{
			return;
		}
		
		IInvokable i = this.invokables.get(this.pos).next();
		
		while (i == null)
		{
			i = this.invokables.get(++this.pos).next();
			
		}
		
		i.invoke();
		
	}
	
}
