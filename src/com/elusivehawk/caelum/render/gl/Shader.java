
package com.elusivehawk.caelum.render.gl;

import java.io.DataInputStream;
import com.elusivehawk.caelum.assets.Asset;
import com.elusivehawk.caelum.assets.EnumAssetType;
import com.elusivehawk.caelum.render.GraphicAsset;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.util.CompInfo;
import com.elusivehawk.util.EnumLogType;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.string.StringHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Shader extends GraphicAsset
{
	public final GLEnumShader gltype;
	
	protected String source = null;
	protected int glId = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public Shader(String filepath, GLEnumShader type)
	{
		super(filepath, EnumAssetType.SHADER);
		
		gltype = type;
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		if (this.glId != 0)
		{
			GL2.glDeleteShader(this.glId);
			
		}
		
	}
	
	@Override
	public void initiate(RenderContext rcon)
	{
		if (this.glId == 0 && this.source != null)
		{
			int id = GL2.glCreateShader(this.gltype);
			
			if (id == 0)
			{
				throw new GLException("Cannot load shader: Out of shader IDs");
			}
			
			GL2.glShaderSource(id, this.source);
			GL2.glCompileShader(id);
			
			int status = GL2.glGetShaderi(id, GLEnumSStatus.GL_COMPILE_STATUS);
			
			if (status == GLConst.GL_FALSE)
			{
				if (CompInfo.DEBUG)
				{
					Logger.log().log(EnumLogType.WARN, "Cannot compile shader \"%s\"", this.filepath);
					
				}
				
				Logger.log().log(EnumLogType.VERBOSE, "Log: %s", GL2.glGetShaderInfoLog(id, GL2.glGetShaderi(id, GLEnumSStatus.GL_INFO_LOG_LENGTH)));
				
				GL2.glDeleteShader(id);
				
			}
			else
			{
				synchronized (this)
				{
					this.glId = id;
					this.loaded = true;
					
				}
				
				Logger.log().verbose("Successfully compiled shader \"%s\"", this.filepath);
				
			}
			
		}
		
	}
	
	@Override
	protected boolean readAsset(DataInputStream in) throws Throwable
	{
		String src = StringHelper.readToOneLine(in);
		
		synchronized (this)
		{
			this.source = src;
			
		}
		
		return this.source != null;
	}
	
	@Override
	public void onExistingAssetFound(Asset a)
	{
		super.onExistingAssetFound(a);
		
		if (a instanceof Shader)
		{
			Shader s = (Shader)a;
			
			synchronized (this)
			{
				this.source = s.source;
				this.glId = s.glId;
				
			}
			
		}
		
	}
	
	public int getShaderId()
	{
		return this.glId;
	}
	
}
