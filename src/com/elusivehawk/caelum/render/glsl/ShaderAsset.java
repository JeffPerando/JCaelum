
package com.elusivehawk.caelum.render.glsl;

import java.io.DataInputStream;
import com.elusivehawk.caelum.assets.Asset;
import com.elusivehawk.caelum.assets.IAsset;
import com.elusivehawk.caelum.render.Deletables;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderException;
import com.elusivehawk.caelum.render.RenderHelper;
import com.elusivehawk.caelum.render.gl.GL2;
import com.elusivehawk.caelum.render.gl.GLException;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.io.IOHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ShaderAsset extends Asset implements IShader
{
	private final GLSLEnumShaderType gltype;
	
	private boolean initiated = false;
	
	private String source;
	private int id;
	
	@SuppressWarnings("unqualified-field-access")
	public ShaderAsset(String filepath, GLSLEnumShaderType type)
	{
		super(filepath);
		
		gltype = type;
		
	}
	
	@Override
	public void compile(RenderContext rcon) throws RenderException
	{
		if (!this.isRead())
		{
			return;
		}
		
		Logger.debug("Attempting to compile shader %s", this);
		
		int glid = 0;
		
		try
		{
			glid = RenderHelper.compileShader(this);
			
		}
		catch (GLException e)
		{
			Logger.err(e);
			
		}
		
		if (glid != 0)
		{
			this.id = glid;
			
			Deletables.instance().register(this);
			
			synchronized (this)
			{
				this.initiated = true;
				
			}
			
		}
		
	}
	
	@Override
	public boolean isCompiled()
	{
		return this.initiated;
	}
	
	@Override
	public String getSource()
	{
		return this.source;
	}
	
	@Override
	public int getShaderId()
	{
		return this.id;
	}
	
	@Override
	public GLSLEnumShaderType getType()
	{
		return this.gltype;
	}
	
	@Override
	public void delete()
	{
		if (!this.initiated)
		{
			return;
		}
		
		GL2.glDeleteShader(this);
		
	}
	
	@Override
	public void read(DataInputStream in) throws Throwable
	{
		String src = IOHelper.readTextToOneLine(in);
		
		//Logger.debug("Source for %s: \"%s\"", this, src);
		
		if (src == null || src.equals(""))
		{
			throw new NullPointerException();
		}
		
		synchronized (this)
		{
			this.source = src;
			
		}
		
		if (this.source == null || this.source.equals(""))
		{
			throw new NullPointerException();
		}
		
	}
	
	@Override
	public void onDuplicateFound(IAsset a)
	{
		if (a instanceof ShaderAsset)
		{
			ShaderAsset s = (ShaderAsset)a;
			
			assert this.gltype == s.gltype;
			
			synchronized (this)
			{
				this.initiated = s.initiated;
				this.source = s.source;
				this.id = s.id;
				
			}
			
		}
		
	}
	
}
