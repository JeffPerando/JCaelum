
package com.elusivehawk.engine.network;

import com.elusivehawk.engine.tag.TagReaderRegistry;
import com.elusivehawk.engine.util.io.ByteWrapper;
import com.elusivehawk.engine.util.io.ByteWriter;
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
		public int encode(ImmutableList<DataType> types, int pos, Object obj, ByteWriter w)
		{
			if (pos == types.size() - 1)
			{
				return 0;
			}
			
			Object[] arr = (Object[])obj;
			
			int ret = Serializer.SHORT.toBytes(w, (short)arr.length);
			
			for (int c = 0; c < arr.length; c++)
			{
				ret += types.get(pos + 1).encode(types, pos + 1, arr[c], w);
				
				
			}
			
			return ret;
		}
		
	},
	TAG(TagReaderRegistry.instance()),
	UUID(Serializer.UUID);
	
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
	public int encode(ImmutableList<DataType> format, int pos, Object obj, ByteWriter w)
	{
		return this.serial.toBytes(w, obj);
	}
	
}
