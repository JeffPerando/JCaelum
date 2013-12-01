
package com.elusivehawk.engine.script;

import java.util.List;
import com.elusivehawk.engine.core.Buffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class SymbolBuffer extends Buffer<ISymbol>
{
	public void init(List<String> strs) throws HawkScriptException
	{
		this.l.clear();
		this.pos = 0;
		
		String currSym = "";
		
		for (int line = 0; line < strs.size(); line++)
		{
			String str = strs.get(line);
			
			if ("".equals(str))
			{
				continue;
			}
			
			int c = 0;
			char ch = ' ';
			ISymbol tmpSym = null;
			
			while (c != str.length())
			{
				while ((ch = str.charAt(c++)) != ' ' && (tmpSym = HSRegistry.instance().getSymbol("" + ch)) == null)
				{
					currSym += ch;
					
				}
				
				if (currSym.length() != 0)
				{
					ISymbol sym = HSRegistry.instance().getSymbol(currSym);
					
					if (sym == null)
					{
						throw new HSParseException("Invalid symbol on line " + line + ": " + currSym);
						
					}
					
					this.l.add(sym);
					currSym = "";
					
				}
				
				if (tmpSym != null)
				{
					this.l.add(tmpSym);
					
				}
				
			}
			
		}
		
	}
	
}
