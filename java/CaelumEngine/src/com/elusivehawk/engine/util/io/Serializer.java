
package com.elusivehawk.engine.util.io;

import java.util.UUID;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface Serializer<T>
{
	public int toBytes(IByteWriter w, T obj);
	
	public T fromBytes(IByteReader r);
	
	public static final Serializer<Boolean> BOOLEAN = new Serializer<Boolean>()
			{
				@Override
				public int toBytes(IByteWriter w, Boolean b)
				{
					w.write((byte)(b ? 1 : 0));
					
					return 1;
				}
				
				@Override
				public Boolean fromBytes(IByteReader b)
				{
					return b.read() != 0;
				}
				
			};
	public static final Serializer<Byte> BYTE = new Serializer<Byte>()
			{
				@Override
				public int toBytes(IByteWriter w, Byte b)
				{
					w.write(b);
					
					return 1;
				}
				
				@Override
				public Byte fromBytes(IByteReader b)
				{
					return b.read();
				}
				
			};
	public static final Serializer<Short> SHORT = new Serializer<Short>()
			{
				@Override
				public int toBytes(IByteWriter w, Short s)
				{
					w.write((byte)(s & 255), (byte)((s >> 8) & 255));
					
					return 1;
				}
				
				@Override
				public Short fromBytes(IByteReader b)
				{
					return (short)(b.read() | (b.read() << 8));
				}
				
			};
	public static final Serializer<Integer> INTEGER = new Serializer<Integer>()
			{
				@Override
				public int toBytes(IByteWriter w, Integer i)
				{
					for (int c = 0; c < 4; c++)
					{
						w.write((byte)(i >> (c * 8) & 255));
						
					}
					
					return 4;
				}
				
				@Override
				public Integer fromBytes(IByteReader b)
				{
					int ret = 0;
					
					for (int c = 0; c < 4; c++)
					{
						ret = (ret << 8) | b.read();
						
					}
					
					return ret;
				}
				
			};
	public static final Serializer<Long> LONG  = new Serializer<Long>()
			{
				@Override
				public int toBytes(IByteWriter w, Long l)
				{
					for (int c = 0; c < 8; c++)
					{
						w.write((byte)(l >> (c * 8) & 255));
						
					}
					
					return 8;
				}
				
				@Override
				public Long fromBytes(IByteReader b)
				{
					long ret = 0;
					
					for (int c = 0; c < 8; c++)
					{
						ret = (ret << 8) | b.read();
						
					}
					
					return ret;
				}
				
			};
	public static final Serializer<Float> FLOAT = new Serializer<Float>()
			{
				@Override
				public int toBytes(IByteWriter w, Float f)
				{
					return INTEGER.toBytes(w, Float.floatToRawIntBits(f));
				}
				
				@Override
				public Float fromBytes(IByteReader b)
				{
					return Float.intBitsToFloat(INTEGER.fromBytes(b));
				}
				
			};
	public static final Serializer<Double> DOUBLE = new Serializer<Double>()
			{
				@Override
				public int toBytes(IByteWriter w, Double d)
				{
					return LONG.toBytes(w, Double.doubleToRawLongBits(d));
				}
				
				@Override
				public Double fromBytes(IByteReader b)
				{
					return Double.longBitsToDouble(LONG.fromBytes(b));
				}
				
			};
	public static final Serializer<String> STRING = new Serializer<String>()
			{
				@Override
				public int toBytes(IByteWriter w, String str)
				{
					SHORT.toBytes(w, (short)str.length());
					
					for (int c = 0; c < str.length(); c++)
					{
						SHORT.toBytes(w, (short)str.charAt(c));
						
					}
					
					return (str.length() + 1) * 2;
				}
				
				@Override
				public String fromBytes(IByteReader b)
				{
					char[] str = new char[SHORT.fromBytes(b) & 0xFFFF];
					
					for (int c = 0; c < str.length; c++)
					{
						str[c] = (char)(short)SHORT.fromBytes(b);
						
					}
					
					return new String(str);
				}
				
			};
	public static final Serializer<UUID> UUID = new Serializer<UUID>()
			{
				@Override
				public int toBytes(IByteWriter w, UUID uuid)
				{
					return LONG.toBytes(w, uuid.getMostSignificantBits()) + LONG.toBytes(w, uuid.getLeastSignificantBits());
				}
				
				@Override
				public UUID fromBytes(IByteReader r)
				{
					return new java.util.UUID(LONG.fromBytes(r), LONG.fromBytes(r));
				}
				
			};
	
}
