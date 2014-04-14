
package com.elusivehawk.engine.assets;

import java.io.File;
import com.elusivehawk.engine.render.RenderHelper;
import com.elusivehawk.engine.render.opengl.GLEnumShader;
import com.elusivehawk.engine.util.StringHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Shader extends Asset
{
	public final GLEnumShader gltype;
	public final String source;
	
	protected int glId = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public Shader(File file, GLEnumShader type)
	{
		super(file.getName());
		
		gltype = type;
		source = StringHelper.readToOneLine(file);
		
	}
	
	public int getGLId()
	{
		return this.glId;
	}
	
	@Override
	protected boolean finishAsset()
	{
		this.glId = RenderHelper.loadShader(this.source, this.gltype);
		
		return this.glId != 0;
	}
	
}
