
package com.elusivehawk.engine.assets;

import java.io.File;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.RenderHelper;
import com.elusivehawk.engine.render.opengl.GLEnumShader;
import com.elusivehawk.util.StringHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Shader extends GraphicAsset
{
	public final GLEnumShader gltype;
	
	protected String src;
	protected int glId = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public Shader(String filepath, GLEnumShader type)
	{
		super(filepath);
		
		gltype = type;
		
	}
	
	@Override
	protected boolean loadAssetIntoGPU(RenderContext rcon)
	{
		this.glId = RenderHelper.loadShader(this.src, this.gltype, rcon);
		
		return this.glId != 0;
	}
	
	@Override
	protected boolean readAsset(File asset)
	{
		this.src = RenderHelper.formatShaderSource(StringHelper.readToOneLine(asset), asset.getParentFile());
		
		return this.src != null;
	}
	
	public int getGLId()
	{
		return this.glId;
	}
	
}
