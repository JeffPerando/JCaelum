
package com.elusivehawk.util.json;

import java.io.File;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import com.elusivehawk.util.StringHelper;
import com.elusivehawk.util.Tokenizer;
import com.elusivehawk.util.storage.Buffer;
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
	public static final String[] SEPARATORS = {"\"", ":", ",", "{", "}", "[", "]", "e", "E", "+", "-"};
	
	private JsonParser(){}
	
	public static void skipWhitespace(Buffer<String> buf)
	{
		String str;
		
		while (buf.hasNext())
		{
			str = buf.next();
			
			if (!StringHelper.isWhitespace(str))
			{
				buf.rewind(1);
				
				return;
			}
			
		}
		
	}
	
	public static JsonObject parse(File file) throws JsonParseException
	{
		return parse(StringHelper.readToOneLine(file));
	}
	
	public static JsonObject parse(Reader r) throws JsonParseException
	{
		return parse(StringHelper.readToOneLine(r));
	}
	
	public static JsonObject parse(List<String> strs) throws JsonParseException
	{
		return parse(StringHelper.concat(strs, "\n", "", null));
	}
	
	public static JsonObject parse(String str) throws JsonParseException
	{
		if (str == null)
		{
			return null;
		}
		
		Tokenizer t = new Tokenizer();
		
		t.addTokens(SEPARATORS);
		t.addTokens(StringHelper.NUMBERS);
		t.addTokens(StringHelper.WHITESPACE);
		
		Buffer<String> buf = new Buffer<String>(t.tokenize(str));
		
		return parseObj("", buf);
	}
	
	public static JsonData parseKeypair(Buffer<String> buf)  throws JsonParseException
	{
		skipWhitespace(buf);
		
		String name = parseString(buf);
		
		skipWhitespace(buf);
		
		if (!":".equalsIgnoreCase(buf.next()))
		{
			throw new JsonParseException("Colon not found for keypair %s", name);
		}
		
		skipWhitespace(buf);
		
		return parseContent(name, buf);
	}
	
	public static JsonData parseContent(String name, Buffer<String> buf) throws JsonParseException
	{
		String str = buf.next(false).toLowerCase();
		
		switch (str)
		{
			case "\"": return new JsonData(EnumJsonType.STRING, name, parseString(buf));
			case "{": return parseObj(name, buf);
			case "[": return parseArray(name, buf);
			case "null": return new JsonData(EnumJsonType.STRING, name, null);
			case "true":
			case "false": return new JsonData(EnumJsonType.BOOL, name, str);
			
		}
		
		if (StringHelper.isInt(str) || "-".equalsIgnoreCase(str))
		{
			return parseInt(name, buf);
		}
		
		throw new JsonParseException("Invalid value for key \"%s\": \"%s\"", name, StringHelper.sanitizeEscapeSequence(str));
	}
	
	public static String parseString(Buffer<String> buf)
	{
		String next = buf.next();
		
		if (!"\"".equalsIgnoreCase(next))
		{
			throw new JsonParseException("Not a string: \"%s\"", next);
		}
		
		StringBuilder b = new StringBuilder();
		
		while (!"\"".equalsIgnoreCase((next = buf.next())))
		{
			b.append(StringHelper.valueOf(next));
			
		}
		
		return b.toString();
	}
	
	public static JsonObject parseObj(String name, Buffer<String> buf) throws JsonParseException
	{
		if (!"{".equalsIgnoreCase(buf.next()))
		{
			throw new JsonParseException("Not an object: %s", name);
		}
		
		Map<String, JsonData> m = Maps.newHashMap();
		boolean kill = false;
		
		while (!"}".equalsIgnoreCase(buf.next(false)))
		{
			JsonData v = parseKeypair(buf);
			
			if (m.containsKey(v.key))
			{
				throw new JsonParseException("Duplicate key: %s", v.key);
			}
			
			m.put(v.key, v);
			
			skipWhitespace(buf);
			
			if (!",".equalsIgnoreCase(buf.next()))
			{
				if (kill)
				{
					throw new JsonParseException("Missing comma found whilst scanning object %s", name);
				}
				
				kill = true;
				
			}
			
			skipWhitespace(buf);
			
		}
		
		return new JsonObject(name, m.values());
	}
	
	public static JsonArray parseArray(String name, Buffer<String> buf) throws JsonParseException
	{
		if (!"[".equalsIgnoreCase(buf.next()))
		{
			throw new JsonParseException("Not an array: %s", name);
		}
		
		List<JsonData> list = Lists.newArrayList();
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
					throw new JsonParseException("Missing comma found whilst scanning array %s", name);
				}
				
				kill = true;
				
			}
			
			skipWhitespace(buf);
			
		}
		
		return new JsonArray(name, list);
	}
	
	public static JsonData parseInt(String name, Buffer<String> buf) throws JsonParseException
	{
		String str = buf.next(false);
		boolean neg = str.equalsIgnoreCase("-"), isFloat = false;
		
		if (neg)
		{
			buf.skip(1);
			
		}
		
		String i = gatherInts(buf);
		
		if ("".equalsIgnoreCase(i))
		{
			throw new JsonParseException("Invalid integer found on %s", name);
		}
		
		StringBuilder b = StringHelper.newBuilder();
		
		b.append(i);
		
		str = buf.next();
		
		if (".".equalsIgnoreCase(str))
		{
			b.append(".");
			
			str = gatherInts(buf);
			
			if ("".equalsIgnoreCase(str))
			{
				throw new JsonParseException("Invalid floating point found on %s", name);
			}
			
			b.append(str);
			
			str = buf.next();
			
			isFloat = true;
			
		}
		
		if ("e".equalsIgnoreCase(str))
		{
			b.append(str);
			
			str = buf.next();
			
			if ("+".equalsIgnoreCase(str) || "-".equalsIgnoreCase(str))
			{
				b.append(str);
				
			}
			
			str = gatherInts(buf);
			
			if ("".equalsIgnoreCase(str))
			{
				throw new JsonParseException("Invalid error code found on %s", name);
			}
			
			b.append(str);
			
		}
		
		return new JsonData(isFloat ? EnumJsonType.FLOAT : EnumJsonType.INT, name, b.toString());
	}
	
	public static String gatherInts(Buffer<String> buf)
	{
		String str = buf.next();
		StringBuilder b = StringHelper.newBuilder();
		
		while (StringHelper.isInt(str))
		{
			b.append(str);
			str = buf.next();
			
		}
		
		return b.toString();
	}
	
}
