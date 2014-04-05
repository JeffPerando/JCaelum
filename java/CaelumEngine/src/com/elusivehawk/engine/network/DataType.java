
package com.elusivehawk.engine.network;

import com.elusivehawk.engine.tag.TagReaderRegistry;
import com.elusivehawk.engine.util.io.IByteReader;
import com.elusivehawk.engine.util.io.IByteWriter;
import com.elusivehawk.engine.util.io.Serializer;
import com.elusivehawk.engine.util.io.Serializers;
import com.google.common.collect.ImmutableList;

/**
 * 
 * Functions as a sort of enumeration for reading and writing objects for packet I/O.
 * 
 * @author Elusivehawk
 */
public class DataType
{
	public static final DataType BOOL = new DataType(Serializers.BOOLEAN);
	public static final DataType BYTE = new DataType(Serializers.BYTE);
	public static final DataType SHORT = new DataType(Serializers.SHORT);
	public static final DataType INT = new DataType(Serializers.INTEGER);
	public static final DataType LONG = new DataType(Serializers.LONG);
	public static final DataType FLOAT = new DataType(Serializers.FLOAT);
	public static final DataType DOUBLE = new DataType(Serializers.DOUBLE);
	public static final DataType STRING = new DataType(Serializers.STRING);
	public static final DataType ARRAY = new DataType(null)
	{
		@Override
		public Object decode(ImmutableList<DataType> types, int pos, IByteReader b)
		{
			if (pos == types.size() - 1)
			{
				return null;
			}
			
			Object[] ret = new Object[Serializers.SHORT.fromBytes(b)];
			
			for (int c = 0; c < ret.length; c++)
			{
				ret[c] = types.get(pos + 1).decode(types, pos + 1, b);
				
			}
			
			return ret;
		}
		
		@Override
		public int encode(ImmutableList<DataType> types, int pos, Object obj, IByteWriter w)
		{
			if (pos == types.size() - 1)
			{
				return 0;
			}
			
			Object[] arr = (Object[])obj;
			
			int ret = Serializers.SHORT.toBytes(w, (short)arr.length);
			
			for (int c = 0; c < arr.length; c++)
			{
				ret += types.get(pos + 1).encode(types, pos + 1, arr[c], w);
				
			}
			
			return ret;
		}
		
		@Override
		public int getSkipCount(int i, ImmutableList<DataType> format)
		{
			return 1 + format.get(i + 1).getSkipCount(i + 1, format);
		}
		
	};
	public static final DataType TAG = new DataType(TagReaderRegistry.instance());
	public static final DataType UUID = new DataType(Serializers.UUID);
	
	protected final Serializer<Object> serial;
	
	@SuppressWarnings({"unqualified-field-access", "unchecked"})
	public DataType(Serializer<?> s)
	{
		serial = (Serializer<Object>)s;
		
	}
	
	@SuppressWarnings("unused")
	public Object decode(ImmutableList<DataType> format, int pos, IByteReader b)
	{
		return this.serial.fromBytes(b);
	}
	
	@SuppressWarnings("unused")
	public int encode(ImmutableList<DataType> format, int pos, Object obj, IByteWriter w)
	{
		return this.serial.toBytes(w, obj);
	}
	
	@SuppressWarnings({"static-method", "unused"})
	public int getSkipCount(int i, ImmutableList<DataType> format)
	{
		return 0;
	}
	
}
