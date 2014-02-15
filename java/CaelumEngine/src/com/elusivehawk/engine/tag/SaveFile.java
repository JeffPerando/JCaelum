
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
	protected List<Tag<?>> tags = new ArrayList<Tag<?>>();
	
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
	
	@Override
	public void addTag(Tag<?> tag)
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
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Tag<T> getTag(String name)
	{
		if (this.tags.isEmpty())
		{
			return null;
		}
		
		Tag<T> ret = null;
		
		for (Tag<?> tag : this.tags)
		{
			if (tag.getName() == name)
			{
				try
				{
					ret = (Tag<T>)tag;
					
				}
				catch (ClassCastException e)
				{
					e.printStackTrace();
					
				}
				
			}
			
		}
		
		return ret;
	}
	
	@Override
	public Boolean readBoolean(String name)
	{
		return this.<Boolean>readOther(name);
	}
	
	@Override
	public Byte readByte(String name)
	{
		return this.<Byte>readOther(name);
	}
	
	@Override
	public Float readFloat(String name)
	{
		return this.<Float>readOther(name);
	}
	
	@Override
	public Double readDouble(String name)
	{
		return this.<Double>readOther(name);
	}
	
	@Override
	public Integer readInt(String name)
	{
		return this.<Integer>readOther(name);
	}
	
	@Override
	public ITagList readList(String name)
	{
		return this.<ITagList>readOther(name);
	}
	
	@Override
	public Long readLong(String name)
	{
		return this.<Long>readOther(name);
	}
	
	@Override
	public Short readShort(String name)
	{
		return this.<Short>readOther(name);
	}
	
	@Override
	public String readString(String name)
	{
		return this.<String>readOther(name);
	}
	
	@Override
	public <T> T readOther(String name)
	{
		Tag<T> ret = this.<T>getTag(name);
		
		return ret == null ? null : ret.getData();
	}
	
}
