
package com.elusivehawk.engine.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Reader;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import com.google.common.collect.Lists;

/**
 * 
 * Helper class for simplifying the usage of {@link String}s.
 * <p>
 * In particular:<br>
 * Reading/writing text files<br>
 * Concatenation (With arguments for how the text is spliced together)<br>
 * Parsing the arguments of a given method (Helpful for method sorting)<br>
 * Removing the last instance of a given String<br>
 * Splitting a string once
 * 
 * @author Elusivehawk
 */
public final class StringHelper
{
	public static final String[] NUMBERS =	{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	public static final String[] HEX =		{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
	
	public static final String[] ESCAPES =		{"\b", "\t", "\n", "\f", "\r", "\"", "\'", "\\"};
	public static final String[] S_ESCAPES =	{"\\b", "\\t", "\\n", "\\f", "\\r", "\\\"", "\\\'", "\\\\"};
	
	private StringHelper(){}
	
	public static List<String> read(String path)
	{
		return read(FileHelper.createFile(path));
	}
	
	public static List<String> read(File file)
	{
		return read(FileHelper.createReader(file));
	}
	
	public static List<String> read(Reader r)
	{
		List<String> text = Lists.newArrayList();
		BufferedReader br = (r instanceof BufferedReader) ? (BufferedReader)r : new BufferedReader(r);
		
		try
		{
			for (String line = br.readLine(); line != null; line = br.readLine())
			{
				text.add(line);
				
			}
			
			br.close();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
		}
		
		return text;
	}
	
	public static String readToOneLine(String path)
	{
		return readToOneLine(FileHelper.createFile(path));
	}
	
	public static String readToOneLine(File file)
	{
		return readToOneLine(FileHelper.createReader(file));
	}
	
	public static String readToOneLine(Reader r)
	{
		StringBuilder b = new StringBuilder();
		BufferedReader br = (r instanceof BufferedReader) ? (BufferedReader)r : new BufferedReader(r);
		
		try
		{
			for (String line = br.readLine(); line != null; line = br.readLine())
			{
				b.append(line);
				b.append("\n");
				
			}
			
			br.close();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
		}
		
		return replaceLast(b.toString(), "\n", "");
	}
	
	public static boolean write(String path, boolean append, boolean makeFileIfNotFound, String... text)
	{
		return write(FileHelper.createFile(path), append, makeFileIfNotFound, text);
	}
	
	public static boolean write(String path, List<String> text, boolean append, boolean makeFileIfNotFound)
	{
		return write(FileHelper.createFile(path), text, append, makeFileIfNotFound);
	}
	
	public static boolean write(File file, boolean append, boolean makeFileIfNotFound, String... text)
	{
		return write(file, Arrays.asList(text), append, makeFileIfNotFound);
	}
	
	public static boolean write(File file, List<String> text, boolean append, boolean makeFileIfNotFound)
	{
		if (!file.exists() && makeFileIfNotFound)
		{
			try
			{
				file.createNewFile();
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
				
				return false;
			}
			
		}
		
		if (!file.canWrite())
		{
			System.err.println(String.format("File with path %s cannot be written to! This is a bug!", file.getPath()));
			
			return false;
		}
		
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
			
			for (String line : text)
			{
				writer.write(line);
				writer.newLine();
				
			}
			
			writer.close();
			
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
		}
		
		return false;
	}
	
	public static String replaceLast(String str, String textToRemove, String replace)
	{
		int lastIn = str.lastIndexOf(textToRemove);
		
		if (lastIn == -1)
		{
			System.err.println(String.format("Failed to remove last instance of %s from %s", textToRemove, str));
			
			return str;
		}
		
		StringBuilder b = new StringBuilder(str);
		b.replace(lastIn, lastIn + textToRemove.length(), replace);
		
		return b.toString();
	}
	
	public static String concat(String separator, String endWith, String d, String... strs)
	{
		if (strs == null || strs.length == 0)
		{
			return d;
		}
		
		StringBuilder b = new StringBuilder(strs.length * 2);
		
		for (int c = 0; c < strs.length; ++c)
		{
			b.append(strs[c]);
			b.append(c == strs.length - 1 ? endWith : separator);
			
		}
		
		return b.toString();
	}
	
	public static String concat(List<String> strs, String separator, String endWith, String d)
	{
		if (strs == null || strs.size() == 0)
		{
			return d;
		}
		
		StringBuilder b = new StringBuilder(strs.size() * 2);
		
		for (int c = 0; c < strs.size(); ++c)
		{
			b.append(strs.get(c));
			b.append(c == strs.size() - 1 ? endWith : separator);
			
		}
		
		return b.toString();
	}
	
	public static String[] splitOnce(String str, String out)
	{
		if (str == null || "".equals(str))
		{
			return null;
		}
		
		if (str.length() < out.length())
		{
			return null;
		}
		
		int ind = str.indexOf(out);
		
		if (ind == -1)
		{
			return new String[]{"", str};
		}
		
		String[] ret = new String[2];
		
		ret[0] = ind == 0 ? "" : str.substring(0, ind - 1);
		ret[1] = str.substring(ind + 1, out.length());
		
		return null;
	}
	
	public static String parseDate(Calendar cal, String dateSep, String timeSep)
	{
		StringBuilder b = new StringBuilder(16);
		
		b.append(cal.get(Calendar.DATE));
		b.append(dateSep);
		b.append(cal.get(Calendar.MONTH) + 1);
		b.append(dateSep);
		b.append(cal.get(Calendar.YEAR));
		b.append(" ");
		
		int minute = cal.get(Calendar.MINUTE);
		
		b.append(cal.get(Calendar.HOUR));
		b.append(timeSep);
		b.append((minute < 10 ? "0" : ""));
		b.append(minute);
		b.append(timeSep);
		b.append(cal.get(Calendar.SECOND));
		b.append(timeSep);
		b.append(cal.get(Calendar.MILLISECOND));
		b.append(" ");
		b.append(cal.get(Calendar.AM_PM) == Calendar.PM ? "PM" : "AM");
		
		return b.toString();
	}
	
	public static String valueOf(String str)
	{
		String ret = str;
		
		if (str.startsWith("\\u"))
		{
			ret = ((Character)((char)Short.parseShort(String.format("0x%s", str.substring(3))))).toString();
			
		}
		else
		{
			for (int c = 0; c < S_ESCAPES.length; c++)
			{
				if (S_ESCAPES[c].equalsIgnoreCase(str))
				{
					ret = ESCAPES[c];
					break;
				}
				
			}
			
		}
		
		return ret;
	}
	
	public static String sanitizeEscapeSequence(String str)
	{
		String ret = str;
		
		for (int c = 0; c < ESCAPES.length; c++)
		{
			ret = ret.replaceAll(ESCAPES[c], S_ESCAPES[c]);
			
		}
		
		return ret;
	}
	
	public static boolean isValidInt(String n) throws NumberFormatException
	{
		String num = n.toLowerCase();
		boolean hex = false, isFloat = false;
		
		if (num.startsWith("0x"))
		{
			num = num.substring(2);
			hex = true;
			
		}
		
		char ch = num.charAt(num.length() - 1);
		
		if (!isCharValidInt(ch, hex))
		{
			switch (ch)
			{
				case 'f': 
				case 'd': isFloat = true;
				case 'l': num = num.substring(0, num.length() - 1); break;
				default: throw new NumberFormatException();
			}
			
			if (hex && isFloat)
			{
				throw new NumberFormatException();
			}
			
		}
		
		char chr;
		
		for (int c = 0; c < num.length(); c++)
		{
			chr = num.charAt(c);
			
			if (!isCharValidInt(chr, hex) && c != num.length() - 1)
			{
				if (isFloat && chr == '.' && isCharValidInt(num.charAt(c + 1), hex))
				{
					continue;
				}
				
				return false;
			}
			
		}
		
		return true;
	}
	
	public static boolean isCharValidInt(char ch, boolean hex)
	{
		String chr = ((Character)ch).toString();
		
		if (hex)
		{
			for (String i : StringHelper.HEX)
			{
				if (i.equalsIgnoreCase(chr))
				{
					return true;
				}
				
			}
			
		}
		else
		{
			for (String i : StringHelper.NUMBERS)
			{
				if (i.equalsIgnoreCase(chr))
				{
					return true;
				}
				
			}
			
		}
		
		return false;
	}
	
	public static String getIntType(String n) throws NumberFormatException
	{
		boolean hex = n.startsWith("0x"), isFloat = n.indexOf(".") != -1;
		
		if (hex && isFloat)
		{
			throw new NumberFormatException();
		}
		
		if (!isValidInt(n))
		{
			return null;
		}
		
		if (isFloat)
		{
			if (n.endsWith("."))
			{
				throw new NumberFormatException();
			}
			
			if (n.endsWith("f"))
			{
				return "float";
			}
			
			return "double";
		}
		
		if (hex)
		{
			int bits = (n.length() - 2) * 4;
			
			if (bits <= 8)
			{
				return "byte";
			}
			
			if (bits <= 16)
			{
				return "short";
			}
			
			if (bits <= 32)
			{
				return "int";
			}
			
			return "long";
		}
		
		long l = Long.parseLong(n);
		
		if ((l & 0xFF) == l)
		{
			return "byte";
		}
		
		if ((l & 0xFFFF) == l)
		{
			return "short";
		}
		
		if ((l & 0xFFFFFFFF) == l)
		{
			return "int";
		}
		
		return "long";
	}
	
}
