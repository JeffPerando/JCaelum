
package com.elusivehawk.util;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@MakeStruct
public class Token
{
	public final String str;
	public final int line, col;
	
	@SuppressWarnings("unqualified-field-access")
	public Token(String s, int l, int c)
	{
		str = s;
		line = l;
		col = c;
		
	}
	
	@Override
	public String toString()
	{
		return String.format("%s:%s:%s", this.str, this.line, this.col);
	}
	
}
