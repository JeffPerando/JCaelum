
package com.elusivehawk.engine.test;

import java.io.File;
import java.util.List;
import com.elusivehawk.util.StringHelper;
import com.elusivehawk.util.storage.Pair;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class StringTranslator extends LangFolder
{
	public StringTranslator(String lang)
	{
		super(lang);
		
	}
	
	public void addTranslations(File file)
	{
		List<String> strs = StringHelper.read(file, ((line, str) ->
		{
			return str.trim();
		}));
		
		for (String line : strs)
		{
			Pair<String> spl = StringHelper.splitFirst(line, "=");
			
			if (spl == null || spl.one.isEmpty())
			{
				continue;
			}
			
			this.addTranslation(spl.one, spl.two);
			
		}
		
	}
	
	public void addTranslation(String str, String out)
	{
		String[] spl = str.split(".");
		
		LangFolder f, folder = this;
		
		for (int c = 0; c < spl.length; c++)
		{
			f = folder.getFolder(spl[c]);
			
			if (f == null)
			{
				f = new LangFolder(spl[c]);
				
				folder.addSubFolder(f);
				
			}
			
			folder = f;
			
		}
		
		folder.addSubFolder(new LangFolder(out));
		
	}
	
	public String getRawTrnaslation(String str)
	{
		String[] spl = str.split(".");
		
		LangFolder f, folder = this;
		
		for (int c = 0; c < spl.length; c++)
		{
			f = folder.getFolder(spl[c]);
			
			if (f == null)
			{
				break;
			}
			
			folder = f;
			
		}
		
		return folder.getName() == null ? "NOT-FOUND" : folder.getName();
	}
	
	public String translate(String str)
	{
		String raw;
		
		while (!(raw = StringHelper.substring(str, "{$", "}")).isEmpty())
		{
			str.replace(String.format("{$%s}", raw), this.translate(this.getRawTrnaslation(raw)));
			
		}
		
		return str;
	}
	
}
