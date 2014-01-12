
package com.elusivehawk.engine.tag;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;
import com.elusivehawk.engine.util.Buffer;
import com.elusivehawk.engine.util.FileHelper;

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
			
			int count = in.read(bytes);
			
			buf = new Buffer<Byte>();
			
			for (int c = 0; c < count; c++)
			{
				buf.add(bytes[c]);
				
			}
			
		}
		catch (IOException e)
		{
			CaelumEngine.instance().getLog().log(EnumLogType.ERROR, null, e);
			
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
			CaelumEngine.instance().getLog().log(EnumLogType.ERROR, null, e);
			
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
		int c = 0;
		
		for (Byte b : buf)
		{
			array[c++] = b;
			
		}
		
		try
		{
			out.write(array);
			out.flush();
			out.close();
			
		}
		catch (IOException e)
		{
			CaelumEngine.instance().getLog().log(EnumLogType.ERROR, null, e);
			
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
