
package com.elusivehawk.engine.util.io;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface Serializer<T>
{
	public byte[] toBytes(T obj);
	
	public T fromBytes(ByteWrapper b);
	
	public static final Serializer<Boolean> BOOLEAN = new Serializer<Boolean>()
			{
				@Override
				public byte[] toBytes(Boolean b)
				{
					return new byte[]{(byte)(b ? 1 : 0)};
				}
				
				@Override
				public Boolean fromBytes(ByteWrapper b)
				{
					return b.read() != 0;
				}
				
			};
	public static final Serializer<Byte> BYTE = new Serializer<Byte>()
			{
				@Override
				public byte[] toBytes(Byte b)
				{
					return new byte[]{b};
				}
				
				@Override
				public Byte fromBytes(ByteWrapper b)
				{
					return b.read();
				}
				
			};
	public static final Serializer<Short> SHORT = new Serializer<Short>()
			{
				@Override
				public byte[] toBytes(Short s)
				{
					return new byte[]{(byte)(s & 255), (byte)((s >> 8) & 255)};
				}
				
				@Override
				public Short fromBytes(ByteWrapper b)
				{
					return (short)(b.read() | (b.read() << 8));
				}
				
			};
	public static final Serializer<Integer> INTEGER = new Serializer<Integer>()
			{
				@Override
				public byte[] toBytes(Integer i)
				{
					byte[] ret = new byte[4];
					
					for (int c = 0; c < 4; c++)
					{
						ret[c] = (byte)(i >> (c * 8) & 255);
						
					}
					
					return ret;
				}
				
				@Override
				public Integer fromBytes(ByteWrapper b)
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
				public byte[] toBytes(Long l)
				{
					byte[] ret = new byte[8];
					
					for (int c = 0; c < 8; c++)
					{
						ret[c] = (byte)(l >> (c * 8) & 255);
						
					}
					
					return ret;
				}
				
				@Override
				public Long fromBytes(ByteWrapper b)
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
				public byte[] toBytes(Float f)
				{
					return INTEGER.toBytes(Float.floatToRawIntBits(f));
				}
				
				@Override
				public Float fromBytes(ByteWrapper b)
				{
					return Float.intBitsToFloat(INTEGER.fromBytes(b));
				}
				
			};
	public static final Serializer<Double> DOUBLE = new Serializer<Double>()
			{
				@Override
				public byte[] toBytes(Double d)
				{
					return LONG.toBytes(Double.doubleToRawLongBits(d));
				}
				
				@Override
				public Double fromBytes(ByteWrapper b)
				{
					return Double.longBitsToDouble(LONG.fromBytes(b));
				}
				
			};
	public static final Serializer<String> STRING = new Serializer<String>()
			{
				@Override
				public byte[] toBytes(String str)
				{
					byte[] ret = new byte[(str.length() + 1) * 2];
					
					byte[] shr = SHORT.toBytes((short)str.length());
					
					ret[0] = shr[0];
					ret[1] = shr[1];
					
					for (int c = 0; c < str.length(); c++)
					{
						shr = SHORT.toBytes((short)str.charAt(c));
						
						ret[c + 2] = shr[0];
						ret[c + 3] = shr[1];
						
					}
					
					return ret;
				}
				
				@Override
				public String fromBytes(ByteWrapper b)
				{
					char[] str = new char[SHORT.fromBytes(b) & 0xFFFF];
					
					for (int c = 0; c < str.length; c++)
					{
						str[c] = (char)(short)SHORT.fromBytes(b);
						
					}
					
					return new String(str);
				}
				
			};
	
}
