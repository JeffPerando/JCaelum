
package com.elusivehawk.caelum.prefab;

import java.io.File;
import java.util.List;
import java.util.Map;
import com.elusivehawk.util.io.FileHelper;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Translator
{
	private final Map<String, Language> translators = Maps.newHashMap();
	protected Language current = null;
	
	protected final File folder;
	
	@SuppressWarnings("unqualified-field-access")
	public Translator(File file)
	{
		folder = file;
		
	}
	
	public int populate()
	{
		if (!this.translators.isEmpty())
		{
			return this.translators.size();
		}
		
		List<File> files = FileHelper.getFiles(this.folder);
		int ret = 0;
		
		for (File file : files)
		{
			if (!FileHelper.canRead(file))
			{
				continue;
			}
			
			String name = FileHelper.getExtensionlessName(file);
			Language trans = new Language(name);
			
			this.translators.put(name, trans);
			
			trans.addTranslations(file);
			
			ret++;
			
		}
		
		return ret;
	}
	
	public boolean setTranslator(String lang)
	{
		Language t = this.translators.get(lang);
		
		if (t == null)
		{
			return false;
		}
		
		this.current = t;
		
		return true;
	}
	
	public String translate(String str)
	{
		return this.current == null ? str : this.current.translate(str);
	}
	
}
