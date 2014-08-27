
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.assets.Asset;
import com.elusivehawk.engine.assets.IAssetReceiver;
import com.elusivehawk.engine.render.opengl.GLException;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.util.IDirty;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Shaders implements IDirty, IAssetReceiver
{
	private final Shader[] shaders = new Shader[RenderConst.SHADER_COUNT];
	private final boolean[] bound = new boolean[RenderConst.SHADER_COUNT];
	private int shCount = 0;
	private boolean dirty = false;
	
	@Override
	public void onAssetLoaded(Asset a)
	{
		if (a instanceof Shader)
		{
			this.addShader((Shader)a);
			
		}
		
	}
	
	@Override
	public boolean isDirty()
	{
		return this.dirty;
	}

	@Override
	public synchronized void setIsDirty(boolean b)
	{
		this.dirty = b;
		
	}
	
	public int getShaderCount()
	{
		return this.shCount;
	}
	
	public void attachShaders(RenderContext rcon, GLProgram p) throws GLException
	{
		Shader sh;
		
		for (int c = 0; c < RenderConst.SHADER_COUNT; c++)
		{
			sh = this.shaders[c];
			
			if (sh == null)
			{
				continue;
			}
			
			if (!this.bound[c])
			{
				rcon.getGL2().glAttachShader(p, sh);
				this.bound[c] = true;
				
			}
			
		}
		
	}
	
	public void deleteShaders(RenderContext rcon, GLProgram p) throws GLException
	{
		for (Shader sh : this.shaders)
		{
			if (sh == null)
			{
				continue;
			}
			
			rcon.getGL2().glDetachShader(p, sh);
			sh.delete(rcon);
			
		}
		
	}
	
	public synchronized boolean addShader(Shader sh)
	{
		assert sh != null;
		
		if (this.shCount == RenderConst.SHADER_COUNT)
		{
			return false;
		}
		
		if (this.shaders[sh.gltype.ordinal()] == null)
		{
			this.shCount++;
			
		}
		
		this.shaders[sh.gltype.ordinal()] = sh;
		this.dirty = true;
		
		return true;
	}
	
}
