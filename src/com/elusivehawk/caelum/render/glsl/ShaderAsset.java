
package com.elusivehawk.caelum.render.glsl;

import java.io.DataInputStream;
import com.elusivehawk.caelum.assets.Asset;
import com.elusivehawk.caelum.assets.EnumAssetType;
import com.elusivehawk.caelum.render.GraphicAsset;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderException;
import com.elusivehawk.caelum.render.RenderHelper;
import com.elusivehawk.caelum.render.gl.GL2;
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
	
	private String src = null;
	private int id = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public ShaderAsset(String filepath, GLSLEnumShaderType type)
	{
		super(filepath, EnumAssetType.SHADER);
		
		gltype = type;
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		GL2.glDeleteShader(this);
		
		this.id = 0;
		
	}
	
	@Override
	public void compile(RenderContext rcon) throws RenderException
	{
		int glid = RenderHelper.compileShader(this);
		
		rcon.registerCleanable(this);
		
		synchronized (this)
		{
			this.id = glid;
			this.loaded = true;
			
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
		return this.src;
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
		
		synchronized (this)
		{
			this.src = src;
			
		}
		
		return this.src != null;
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
				this.src = s.src;
				this.id = s.id;
				
			}
			
		}
		
	}
	
}
