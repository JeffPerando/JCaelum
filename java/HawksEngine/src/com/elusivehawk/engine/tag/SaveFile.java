
package com.elusivehawk.engine.tag;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.elusivehawk.engine.core.Buffer;
import com.elusivehawk.engine.core.FileHelper;
import com.elusivehawk.engine.core.GameLog;

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
		Buffer<Byte> buf = null;
		
		try
		{
			byte[] bytes = new byte[in.available()];
			
			in.read(bytes);
			
			buf = new Buffer<Byte>();
			
			for (byte b : bytes)
			{
				buf.put(b);
				
			}
			
		}
		catch (IOException e)
		{
			GameLog.error(e);
			
		}
		
		if (buf != null)
		{
			while (buf.remaining() != 0)
			{
				ITag<?> tag = TagReaderRegistry.instance().readTag(buf);
				
				if (tag == null)
				{
					continue;
				}
				
				this.tags.add(tag);
				
			}
			
		}
		
		try
		{
			in.close();
			
		}
		catch (Exception e)
		{
			GameLog.error(e);
			
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
		
		Buffer<Byte> buf = new Buffer<Byte>();
		
		for (ITag<?> tag : this.tags)
		{
			TagReaderRegistry.instance().writeTag(buf, tag);
			
		}
		
		buf.norm();
		
		byte[] array = new byte[buf.size()];
		
		for (int c = 0; c < array.length; c++)
		{
			array[c] = buf.next();
			
		}
		
		try
		{
			out.write(array);
			
		}
		catch (IOException e)
		{
			GameLog.error(e);
			
		}
		
	}
	
	public boolean add(ITag<?> tag)
	{
		boolean ret = false;
		
		if (!this.tags.isEmpty())
		{
			for (ITag<?> t : this.tags)
			{
				if (t.getName() == tag.getName())
				{
					ret = true;
					
					break;
				}
				
			}
			
		}
		
		this.tags.add(tag);
		
		return ret;
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
