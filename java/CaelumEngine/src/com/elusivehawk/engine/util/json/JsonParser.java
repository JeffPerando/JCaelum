
package com.elusivehawk.engine.util.json;

import java.io.File;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import com.elusivehawk.engine.util.StringHelper;
import com.elusivehawk.engine.util.Tokenizer;
import com.elusivehawk.engine.util.storage.Buffer;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class JsonParser
{
	public static final char[] SEPARATORS = {' ', '\t', '\n', '\"', ':', ',', '{', '}', '[', ']'};
	
	private JsonParser(){}
	
	public static void skipWhitespace(Buffer<String> buf)
	{
		String str;
		
		while (buf.hasNext())
		{
			str = buf.next(false);
			
			switch (str)
			{
				case " ":
				case "\t":
				case "\n": buf.skip(1); continue;
				default: return;
			}
			
		}
		
	}
	
	public static JsonObject parse(File file)
	{
		return parse(StringHelper.readToOneLine(file));
	}
	
	public static JsonObject parse(Reader r)
	{
		return parse(StringHelper.readToOneLine(r));
	}
	
	public static JsonObject parse(List<String> strs)
	{
		return parse(StringHelper.concat(strs, "\n", "", null));
	}
	
	public static JsonObject parse(String str)
	{
		if (str == null)
		{
			return null;
		}
		
		Tokenizer t = new Tokenizer(SEPARATORS);
		Buffer<String> buf = new Buffer<String>(t.tokenize(str));
		
		if (!buf.hasNext())
		{
			return null;
		}
		
		return parseObj(buf);
	}
	
	public static JsonValue parseKeypair(Buffer<String> buf)
	{
		skipWhitespace(buf);
		
		String name = parseString(buf);
		
		skipWhitespace(buf);
		
		if (!":".equalsIgnoreCase(buf.next()))
		{
			return null;
		}
		
		skipWhitespace(buf);
		
		return parseContent(name, buf);
	}
	
	public static JsonValue parseContent(String name, Buffer<String> buf)
	{
		String str = buf.next(false).toLowerCase();
		JsonValue ret = null;
		
		switch (str)
		{
			case "\"": ret = new JsonValue(EnumJsonType.STRING, name, parseString(buf)); break;
			case "{": ret = parseObj(name, buf); break;
			case "[": ret = parseArray(name, buf); break;
			case "null": ret = new JsonValue(EnumJsonType.STRING, name, null); break;
			case "true":
			case "false": ret = new JsonValue(EnumJsonType.BOOL, name, str); break;
			
		}
		
		if (ret == null)
		{
			String intType = null;
			
			try
			{
				intType = StringHelper.getIntType(str);
				
			}
			catch (Exception e){}
			
			if (intType != null)
			{
				ret = new JsonValue(EnumJsonType.valueOfSafe(intType), name, str);
				
			}
			
		}
		
		return ret;
	}
	
	public static String parseString(Buffer<String> buf)
	{
		assert "\"".equalsIgnoreCase(buf.next());
		
		StringBuilder b = new StringBuilder();
		String next;
		
		while (!"\"".equalsIgnoreCase((next = buf.next())))
		{
			b.append(StringHelper.valueOf(next));
			
		}
		
		return b.toString();
	}
	
	public static JsonObject parseObj(Buffer<String> buf)
	{
		return parseObj("", buf);
	}
	
	public static JsonObject parseObj(String name, Buffer<String> buf)
	{
		assert "{".equalsIgnoreCase(buf.next());
		
		Map<String, JsonValue> map = Maps.newHashMap();
		boolean kill = false;
		
		while (!"}".equalsIgnoreCase(buf.next(false)))
		{
			JsonValue v = parseKeypair(buf);
			
			if (v != null)
			{
				map.put(v.key, v);
				
			}
			
			skipWhitespace(buf);
			
			if (!",".equalsIgnoreCase(buf.next()))
			{
				if (kill)
				{
					return null;
				}
				
				kill = true;
				
			}
			
			skipWhitespace(buf);
			
		}
		
		return new JsonObject(name, map);
	}
	
	public static JsonArray parseArray(String name, Buffer<String> buf)
	{
		assert "[".equalsIgnoreCase(buf.next());
		
		List<JsonValue> list = Lists.newArrayList();
		boolean kill = false;
		
		while (!"]".equalsIgnoreCase(buf.next(false)))
		{
			skipWhitespace(buf);
			
			list.add(parseContent("", buf));
			
			skipWhitespace(buf);
			
			if (!",".equalsIgnoreCase(buf.next()))
			{
				if (kill)
				{
					return null;
				}
				
				kill = true;
				
			}
			
			skipWhitespace(buf);
			
		}
		
		return new JsonArray(name, list);
	}
	
}
