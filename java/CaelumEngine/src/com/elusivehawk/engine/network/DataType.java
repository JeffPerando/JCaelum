
package com.elusivehawk.engine.network;

import com.elusivehawk.engine.tag.TagReaderRegistry;
import com.elusivehawk.engine.util.BufferHelper;
import com.elusivehawk.engine.util.io.ByteWrapper;
import com.elusivehawk.engine.util.io.Serializer;
import com.google.common.collect.ImmutableList;

/**
 * 
 * An enumeration containing every valid data type for networking.
 * 
 * @author Elusivehawk
 */
public enum DataType
{
	BOOL(Serializer.BOOLEAN),
	BYTE(Serializer.BYTE),
	SHORT(Serializer.SHORT),
	INT(Serializer.INTEGER),
	LONG(Serializer.LONG),
	FLOAT(Serializer.FLOAT),
	DOUBLE(Serializer.DOUBLE),
	STRING(Serializer.STRING),
	ARRAY(null)
	{
		@Override
		public Object decode(ImmutableList<DataType> types, int pos, ByteWrapper b)
		{
			if (pos == types.size() - 1)
			{
				return null;
			}
			
			Object[] ret = new Object[Serializer.SHORT.fromBytes(b)];
			
			for (int c = 0; c < ret.length; c++)
			{
				ret[c] = types.get(pos + 1).decode(types, pos + 1, b);
				
			}
			
			return ret;
		}
		
		@Override
		public byte[] encode(ImmutableList<DataType> types, int pos, Object obj)
		{
			if (pos == types.size() - 1)
			{
				return null;
			}
			
			Object[] arr = (Object[])obj;
			
			byte[][] b = new byte[arr.length][];
			
			for (int c = 0; c < arr.length; c++)
			{
				b[c] = types.get(pos + 1).encode(types, pos + 1, arr[c]);
				
				
			}
			
			return BufferHelper.condense(b);
		}
		
	},
	TAG(TagReaderRegistry.instance());
	
	protected final Serializer<Object> serial;
	
	@SuppressWarnings({"unqualified-field-access", "unchecked"})
	DataType(Serializer<?> s)
	{
		serial = (Serializer<Object>)s;
		
	}
	
	@SuppressWarnings("unused")
	public Object decode(ImmutableList<DataType> format, int pos, ByteWrapper b)
	{
		return this.serial.fromBytes(b);
	}
	
	@SuppressWarnings("unused")
	public byte[] encode(ImmutableList<DataType> format, int pos, Object obj)
	{
		return this.serial.toBytes(obj);
	}
	
}
