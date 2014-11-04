
package com.elusivehawk.caelum.render.gl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum GLEnumDataType
{
	GL_BYTE(GLConst.GL_BYTE, 1, ByteBuffer.class),
	GL_DOUBLE(GLConst.GL_DOUBLE, 8, DoubleBuffer.class),
	GL_FLOAT(GLConst.GL_FLOAT, 4, FloatBuffer.class),
	GL_INT(GLConst.GL_INT, 4, IntBuffer.class),
	GL_SHORT(GLConst.GL_SHORT, 2, ShortBuffer.class);
	
	private final int glCode, bytes;
	private final Class<? extends Buffer> bufType;
	
	@SuppressWarnings("unqualified-field-access")
	GLEnumDataType(int gl, int byteCount, Class<? extends Buffer> type)
	{
		glCode = gl;
		bytes = byteCount;
		bufType = type;
		
	}
	
	public int getGLId()
	{
		return this.glCode;
	}
	
	public int getByteCount()
	{
		return this.bytes;
	}
	
	public boolean isCompatible(Buffer buf)
	{
		return this.bufType.isInstance(buf);
	}
	
	public static GLEnumDataType findCompatibleType(Buffer buf)
	{
		for (GLEnumDataType dt : values())
		{
			if (dt.isCompatible(buf))
			{
				return dt;
			}
			
		}
		
		return null;
	}
	
}
