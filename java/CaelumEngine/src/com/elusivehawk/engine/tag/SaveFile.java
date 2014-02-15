
package com.elusivehawk.engine.tag;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import com.elusivehawk.engine.util.FileHelper;
import com.elusivehawk.engine.util.io.ByteReader;
import com.elusivehawk.engine.util.io.ByteStreams;
import com.elusivehawk.engine.util.io.ByteWriter;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class SaveFile extends TagList
{
	protected File save;
	
	public SaveFile(String path)
	{
		this(FileHelper.createFile(path));
		
	}
	
	public SaveFile(String src, String path)
	{
		this(FileHelper.createFile(src, path));
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public SaveFile(File file)
	{
		super(new ArrayList<Tag<?>>());
		save = file;
		
	}
	
	public void load()
	{
		FileInputStream fis = FileHelper.createInStream(this.save);
		
		if (fis == null)
		{
			return;
		}
		
		BufferedInputStream in = new BufferedInputStream(fis);
		ByteReader wrap = new ByteStreams(in, null);
		
		try
		{
			while (in.available() > 0)
			{
				Tag<?> tag = TagReaderRegistry.instance().read(wrap);
				
				if (tag == null)
				{
					continue;
				}
				
				this.tags.add(tag);
				
			}
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
			
		}
		
		try
		{
			in.close();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
		}
		
	}
	
	public void save()
	{
		if (this.tags.isEmpty())
		{
			return;
		}
		
		FileOutputStream fos = FileHelper.createOutStream(this.save, true);
		
		if (fos == null)
		{
			return;
		}
		
		BufferedOutputStream out = new BufferedOutputStream(fos);
		ByteWriter writer = new ByteStreams(null, out);
		
		for (Tag<?> tag : this.tags)
		{
			TagReaderRegistry.instance().write(writer, tag);
			
		}
		
		try
		{
			out.flush();
			out.close();
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
			
		}
		
	}
	
}
