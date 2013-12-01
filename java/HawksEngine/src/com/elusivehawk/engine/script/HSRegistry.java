
package com.elusivehawk.engine.script;

import java.util.HashMap;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class HSRegistry
{
	private static final HSRegistry INSTANCE = new HSRegistry();
	
	private HashMap<String, ISymbol> syms = new HashMap<String, ISymbol>();
	
	private HSRegistry()
	{
		
	}
	
	public static HSRegistry instance()
	{
		return INSTANCE;
	}
	
	public void registerSymbol(ISymbol s)
	{
		this.syms.put(s.getSym(), s);
		
	}
	
	public ISymbol getSymbol(String str)
	{
		return this.syms.get(str);
	}
	
}
