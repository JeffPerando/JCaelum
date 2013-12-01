
package com.elusivehawk.engine.script;

import com.elusivehawk.engine.core.Buffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ISymbol
{
	public String getSym();
	
	public EnumSymbolType getType();
	
	public IInvokable decode(ISymbol parent, Buffer<ISymbol> buf);
	
}
