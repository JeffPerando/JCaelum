
package com.elusivehawk.caelum.render.gl;

import java.nio.Buffer;
import java.util.Iterator;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderHelper;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.storage.SyncList;
import com.elusivehawk.util.storage.Tuple;

/**
 * 
 * Similar in concept to {@link GLProgram}, but with VBOs.
 * 
 * @author Elusivehawk
 */
public class VertexBuffer implements IGLBindable
{
	private final GLEnumBufferTarget t;
	private final GLEnumDataUsage loadMode;
	
	private GLEnumDataType dataType = GLEnumDataType.GL_FLOAT;
	private Buffer initBuf = null;
	
	private int id = 0;
	private boolean initiated = false;
	
	private final SyncList<Tuple<Buffer, Integer>> uploads = SyncList.newList();
	
	@SuppressWarnings("unqualified-field-access")
	public VertexBuffer(GLEnumBufferTarget target, GLEnumDataUsage mode, GLEnumDataType type, Buffer buf)
	{
		this(target, mode, type);
		
		assert type.isCompatible(buf);
		
		initBuf = buf;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public VertexBuffer(GLEnumBufferTarget target, GLEnumDataUsage mode, Buffer buf)
	{
		this(target, mode, GLEnumDataType.findCompatibleType(buf));
		
		initBuf = buf;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public VertexBuffer(GLEnumBufferTarget target, GLEnumDataUsage mode, GLEnumDataType type)
	{
		this(target, mode);
		
		dataType = type;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public VertexBuffer(GLEnumBufferTarget target, GLEnumDataUsage mode)
	{
		t = target;
		loadMode = mode;
		
	}
	
	@Override
	public boolean bind(RenderContext rcon)
	{
		if (this.id == 0)
		{
			this.id = GL1.glGenBuffers();
			
		}
		
		GL1.glBindBuffer(this);
		
		if (!this.initiated)
		{
			rcon.registerCleanable(this);
			
			this.initiated = true;
			
		}
		
		if (this.initBuf != null)
		{
			GL1.glBufferData(this.t, this.dataType, this.initBuf, this.loadMode);
			
			this.initBuf = null;
			
		}
		
		if (!this.uploads.isEmpty())
		{
			Iterator<Tuple<Buffer, Integer>> itr = this.uploads.iterator();
			Tuple<Buffer, Integer> pair;
			
			while (itr.hasNext())
			{
				pair = itr.next();
				
				GL1.glBufferSubData(this.getTarget(), pair.two, this.dataType, pair.one);
				
				itr.remove();
				
			}
			
		}
		
		try
		{
			RenderHelper.checkForGLError();
			
		}
		catch (GLException e)
		{
			Logger.log().err(e);
			
			this.unbind(rcon);
			
			return false;
		}
		
		return true;
	}
	
	@Override
	public void unbind(RenderContext rcon)
	{
		GL1.glBindBuffer(this.t, 0);
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		if (this.id != 0)
		{
			GL1.glDeleteBuffers(this);
			
		}
		
	}
	
	public GLEnumBufferTarget getTarget()
	{
		return this.t;
	}
	
	public GLEnumDataType getDataType()
	{
		return this.dataType;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void updateVBO(Buffer buf, int offset)
	{
		this.uploads.add(Tuple.create(buf, offset));
		
	}
	
	public synchronized void uploadBuffer(Buffer buf)
	{
		this.initBuf = buf;
		
	}
	
}
