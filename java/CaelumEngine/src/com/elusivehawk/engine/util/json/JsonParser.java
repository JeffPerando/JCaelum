
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
		
		while (true)
		{
			str = buf.next(false);
			
			if (" ".equalsIgnoreCase(str) || "\t".equalsIgnoreCase(str))
			{
				buf.next();
				continue;
			}
			
			break;
		}
		
	}
	
	public static JsonObject parse(File file)
	{
		return parse(StringHelper.read(file));
	}
	
	public static JsonObject parse(Reader r)
	{
		return parse(StringHelper.read(r));
	}
	
	public static JsonObject parse(List<String> strs)
	{
		return parse(StringHelper.concat(strs, "\n", "", null));
	}
	
	public static JsonObject parse(String str)
	{
		if (str == null || "".equalsIgnoreCase(str))
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
	
	public static JsonValue parseValue(Buffer<String> buf)
	{
		skipWhitespace(buf);
		
		String name = parseString(buf);
		
		if (!":".equalsIgnoreCase(buf.next()))
		{
			return null;
		}
		
		skipWhitespace(buf);
		
		return parseContent(name, buf);
	}
	
	public static JsonValue parseContent(String name, Buffer<String> buf)
	{
		String str = buf.next(false);
		JsonValue ret = null;
		
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
		else switch (str)
		{
			case "\"": ret = new JsonValue(EnumJsonType.STRING, name, parseString(buf)); break;
			case "{": ret = parseObj(name, buf);
			case "[": ret = parseArray(name, buf);
			
		}
		
		return ret;
	}
	
	public static String parseString(Buffer<String> buf)
	{
		String expectStr = "\"".equalsIgnoreCase(buf.next()) ? "\"" : " ";
		
		StringBuilder b = new StringBuilder();
		String next;
		
		while (!expectStr.equalsIgnoreCase((next = buf.next())))
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
			JsonValue v = parseValue(buf);
			
			map.put(v.key, v);
			
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
			if (kill)
			{
				return null;
			}
			
			skipWhitespace(buf);
			
			list.add(parseContent("", buf));
			
			skipWhitespace(buf);
			
			if (!",".equalsIgnoreCase(buf.next()))
			{
				kill = true;
				
			}
			
			skipWhitespace(buf);
			
		}
		
		return new JsonArray(name, list);
	}
	
}
