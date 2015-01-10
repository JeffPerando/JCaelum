
package com.elusivehawk.caelum.render.glsl;

import java.io.DataInputStream;
import com.elusivehawk.caelum.assets.Asset;
import com.elusivehawk.caelum.assets.EnumAssetType;
import com.elusivehawk.caelum.render.GraphicAsset;
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
public class ShaderAsset extends GraphicAsset implements IShader
{
	private final GLSLEnumShaderType gltype;
	
	private String source;
	private int id;
	
	public ShaderAsset(String filepath, GLSLEnumShaderType type)
	{
		this(filepath, type, false);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public ShaderAsset(String filepath, GLSLEnumShaderType type, boolean readNow)
	{
		super(filepath, EnumAssetType.SHADER, readNow);
		
		gltype = type;
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		if (this.id != 0)
		{
			GL2.glDeleteShader(this);
			
			this.id = 0;
			
		}
		
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
			rcon.registerDeletable(this);
			
			synchronized (this)
			{
				this.id = glid;
				this.loaded = true;
				
			}
			
		}
		
	}
	
	@Override
	public boolean isCompiled()
	{
		return this.isLoaded();
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
	protected boolean readAsset(DataInputStream in) throws Throwable
	{
		String src = IOHelper.readTextToOneLine(in);
		
		//Logger.debug("Source for %s: \"%s\"", this, src);
		
		if (src == null || src.equals(""))
		{
			return false;
		}
		
		synchronized (this)
		{
			this.source = src;
			
		}
		
		if (this.source == null || this.source.equals(""))
		{
			throw new NullPointerException();
		}
		
		return true;
	}
	
	@Override
	public void onExistingAssetFound(Asset a)
	{
		super.onExistingAssetFound(a);
		
		if (a instanceof ShaderAsset)
		{
			ShaderAsset s = (ShaderAsset)a;
			
			synchronized (this)
			{
				this.source = s.source;
				this.id = s.id;
				
			}
			
		}
		
	}
	
}
