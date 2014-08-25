
package com.elusivehawk.engine.render.opengl;

import java.nio.Buffer;
import java.util.Iterator;
import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.RenderHelper;
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
	private final GLEnumDataType dataType;
	
	private int id = 0;
	private boolean initiated = false;
	private Buffer initBuf = null;
	
	private final SyncList<Tuple<Buffer, Integer>> uploads = SyncList.newList();
	
	public VertexBuffer(GLEnumBufferTarget target, GLEnumDataUsage mode, Buffer buf)
	{
		this(target, mode, GLEnumDataType.GL_FLOAT, buf);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	private VertexBuffer(GLEnumBufferTarget target, GLEnumDataUsage mode, GLEnumDataType type, Buffer buf)
	{
		this(target, mode, type);
		initBuf = buf;
		
	}
	
	public VertexBuffer(GLEnumBufferTarget target, GLEnumDataUsage mode)
	{
		this(target, mode, GLEnumDataType.GL_FLOAT);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public VertexBuffer(GLEnumBufferTarget target, GLEnumDataUsage mode, GLEnumDataType type)
	{
		t = target;
		loadMode = mode;
		dataType = type;
		
	}
	
	@Override
	public boolean bind(RenderContext rcon)
	{
		IGL1 gl1 = rcon.getGL1();
		
		if (this.id == 0)
		{
			this.id = gl1.glGenBuffers();
			
		}
		
		gl1.glBindBuffer(this);
		
		if (!this.initiated)
		{
			if (this.initBuf != null)
			{
				IGL3 gl3 = rcon.getGL3();
				
				int vba = gl1.glGetInteger(GLConst.GL_VERTEX_ARRAY_BINDING);
				
				if (vba != 0)
				{
					gl3.glBindVertexArray(0);
					
				}
				
				gl1.glBufferData(this.t, this.dataType, this.initBuf, this.loadMode);
				
				if (vba != 0)
				{
					gl3.glBindVertexArray(vba);
					
				}
				
				this.initBuf = null;
				
			}
			
			this.initiated = true;
		}
		
		if (!this.uploads.isEmpty())
		{
			Iterator<Tuple<Buffer, Integer>> itr = this.uploads.iterator();
			Tuple<Buffer, Integer> pair;
			
			while (itr.hasNext())
			{
				pair = itr.next();
				
				gl1.glBufferSubData(this.getTarget(), pair.two, this.dataType, pair.one);
				
				itr.remove();
			}
			
		}
		
		try
		{
			RenderHelper.checkForGLError(gl1);
			
		}
		catch (GLException e)
		{
			CaelumEngine.log().err(e);
			
			this.unbind(rcon);
			
			return false;
		}
		
		return true;
	}
	
	@Override
	public void unbind(RenderContext rcon)
	{
		rcon.getGL1().glBindBuffer(this.t, 0);
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		if (this.id != 0)
		{
			rcon.getGL1().glDeleteBuffers(this);
			
		}
		
	}
	
	public GLEnumBufferTarget getTarget()
	{
		return this.t;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void updateVBO(Buffer buf, int offset)
	{
		this.uploads.add(Tuple.create(buf, offset));
		
	}
	
}
