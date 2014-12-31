
package com.elusivehawk.caelum.prefab;

import java.io.File;
import java.util.List;
import com.elusivehawk.util.io.IOHelper;
import com.elusivehawk.util.parse.ParseHelper;
import com.elusivehawk.util.storage.Pair;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Language extends LangFolder
{
	public Language(String lang)
	{
		super(lang);
		
	}
	
	public void addTranslations(File file)
	{
		List<String> strs = IOHelper.readText(file, ((line, str) ->
		{
			return str.trim();
		}));
		
		for (String line : strs)
		{
			Pair<String> spl = ParseHelper.splitFirst(line, "=");
			
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
		
		for (String split : spl)
		{
			f = folder.getFolder(split);
			
			if (f == null)
			{
				f = new LangFolder(split);
				
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
		
		for (String split : spl)
		{
			f = folder.getFolder(split);
			
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
		
		while (!(raw = ParseHelper.substring(str, "{$", "}")).isEmpty())
		{
			str.replace(String.format("{$%s}", raw), this.translate(this.getRawTrnaslation(raw)));
			
		}
		
		return str;
	}
	
}
