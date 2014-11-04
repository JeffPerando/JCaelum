
package com.elusivehawk.caelum;

import java.util.Map;
import com.elusivehawk.util.storage.Pair;
import com.elusivehawk.util.string.StringHelper;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class GameArguments
{
	protected final Map<String, String> args = Maps.newHashMap();
	
	@SuppressWarnings("unqualified-field-access")
	public GameArguments(Iterable<String> itr)
	{
		itr.forEach((str) ->
		{
			if (str.contains("="))
			{
				Pair<String> spl = StringHelper.splitFirst(str, "=");
				
				args.put(spl.one, spl.two);
				
			}
			
		});
		
	}
	
	public String getString(String name, String d)
	{
		String ret = this.args.get(name);
		
		return ret == null ? d : name;
	}
	
	public byte getByte(String name, byte d)
	{
		byte ret = d;
		
		try
		{
			ret = Byte.parseByte(this.getString(name, "0"));
			
		}
		catch (Exception e){}
		
		return ret;
	}
	
	public short getShort(String name, short d)
	{
		short ret = d;
		
		try
		{
			ret = Short.parseShort(this.getString(name, "0"));
			
		}
		catch (Exception e){}
		
		return ret;
	}
	
	public int getInt(String name, int d)
	{
		int ret = d;
		
		try
		{
			ret = Integer.parseInt(this.getString(name, "0"));
			
		}
		catch (Exception e){}
		
		return ret;
	}
	
	public long getLong(String name, long d)
	{
		long ret = d;
		
		try
		{
			ret = Long.parseLong(this.getString(name, "0"));
			
		}
		catch (Exception e){}
		
		return ret;
	}
	
	public double getDouble(String name, double d)
	{
		double ret = d;
		
		try
		{
			ret = Double.parseDouble(this.getString(name, "0"));
			
		}
		catch (Exception e){}
		
		return ret;
	}
	
	public float getFloat(String name, float d)
	{
		float ret = d;
		
		try
		{
			ret = Float.parseFloat(this.getString(name, "0f"));
			
		}
		catch (Exception e){}
		
		return ret;
	}
	
}
