
package com.elusivehawk.engine.tag;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.elusivehawk.engine.util.FileHelper;
import com.elusivehawk.engine.util.io.ByteStreams;
import com.elusivehawk.engine.util.io.ByteReader;
import com.elusivehawk.engine.util.io.ByteWriter;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class SaveFile implements ITagList
{
	protected File save;
	protected List<ITag<?>> tags = new ArrayList<ITag<?>>();
	
	public SaveFile(String path)
	{
		this(FileHelper.createFile(path));
		
	}
	
	public SaveFile(String src, String path)
	{
		this (FileHelper.createFile(src, path));
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public SaveFile(File file)
	{
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
				ITag<?> tag = TagReaderRegistry.instance().readTag(wrap);
				
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
		ByteWriter stream = new ByteStreams(null, out);
		
		for (ITag<?> tag : this.tags)
		{
			TagReaderRegistry.instance().writeTag(stream, tag);
			
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
	
	@Override
	public void addTag(ITag<?> tag)
	{
		int i = -1;
		
		for (int c = 0; c < this.tags.size(); c++)
		{
			if (this.tags.get(c).getName() == tag.getName())
			{
				i = c;
				
				break;
			}
			
		}
		
		if (i == -1)
		{
			this.tags.add(tag);
			
		}
		else
		{
			this.tags.set(i, tag);
			
		}
		
	}
	
	@Override
	public ITag<?> getTag(String name)
	{
		if (this.tags.isEmpty())
		{
			return null;
		}
		
		for (ITag<?> tag : this.tags)
		{
			if (tag.getName() == name)
			{
				return tag;
			}
			
		}
		
		return null;
	}
	
}
