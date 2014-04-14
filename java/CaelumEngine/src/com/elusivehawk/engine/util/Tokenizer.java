
package com.elusivehawk.engine.util;

import java.util.List;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Tokenizer
{
	protected List<Character> tokens = Lists.newArrayList();
	
	public Tokenizer(){}
	
	public Tokenizer(char... tks)
	{
		addTokens(tks);
		
	}
	
	public void addTokens(char... tks)
	{
		for (char tk : tks)
		{
			this.tokens.add(tk);
			
		}
		
	}
	
	public List<String> tokenize(String str)
	{
		List<String> ret = Lists.newArrayList();
		
		int start = 0;
		
		if ((str != null && !"".equalsIgnoreCase(str)) && !this.tokens.isEmpty())
		{
			char ch;
			
			for (int c = 0; c < str.length(); c++)
			{
				ch = str.charAt(c);
				
				for (char token : this.tokens)
				{
					if (ch == token)
					{
						String sub = str.substring(start, c);
						
						if (!"".equalsIgnoreCase(sub))
						{
							ret.add(sub);
							
						}
						
						ret.add(((Character)token).toString());
						
						start = c + 1;
						
					}
					
				}
				
			}
			
		}
		
		if (start < str.length())
		{
			ret.add(str.substring(start, str.length()));
			
		}
		
		return ret;
	}
	
}
