
package com.elusivehawk.util;

import java.util.List;
import com.elusivehawk.util.storage.Tuple;
import com.google.common.collect.Lists;

/**
 * 
 * Because regex is evil! And so is stuffing.
 * 
 * @author Elusivehawk
 */
public class Tokenizer
{
	protected List<String> tokens = Lists.newArrayList();
	
	public Tokenizer(){}
	
	public Tokenizer(String... tks)
	{
		addTokens(tks);
		
	}
	
	public void addTokens(String... tks)
	{
		for (String tk : tks)
		{
			if (tk == null || "".equalsIgnoreCase(tk))
			{
				continue;
			}
			
			this.tokens.add(tk);
			
		}
		
	}
	
	public List<String> tokenize(String str)
	{
		List<String> ret = Lists.newArrayList();
		String rem = str;
		
		if (str == null || "".equalsIgnoreCase(str))
		{
			return ret;
		}
		
		if (this.tokens.isEmpty())
		{
			return ret;
		}
		
		Tuple<String, Integer> t = this.getNextTokenIndex(rem, 0);
		int i;
		
		while (t != null)
		{
			i = t.two;
			
			if (i >= 0)
			{
				if (i > 0)
				{
					ret.add(rem.substring(0, i));
					
				}
				
				if (rem.length() >= t.one.length())
				{
					ret.add(rem.substring(i, i + t.one.length()));
					rem = rem.substring(i + t.one.length());
					
					t = this.getNextTokenIndex(rem, 0);
					
				}
				
			}
			
		}
		
		if (!"".equalsIgnoreCase(rem))
		{
			ret.add(rem);
			
		}
		
		return ret;
	}
	
	public Tuple<String, Integer> getNextTokenIndex(String str, int start)
	{
		String tkn = null;
		int in = str.length();
		
		for (int c = 0; c < this.tokens.size(); c++)
		{
			int i = Math.min(in, str.indexOf(this.tokens.get(c), start));
			
			if (i == -1)
			{
				continue;
			}
			
			if (i < in)
			{
				tkn = this.tokens.get(c);
				
			}
			
			in = i;
			
		}
		
		return tkn == null ? null : Tuple.create(tkn, in);
	}
	
}
