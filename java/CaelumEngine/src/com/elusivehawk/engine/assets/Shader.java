
package com.elusivehawk.engine.assets;

import java.io.File;
import com.elusivehawk.engine.render.RenderHelper;
import com.elusivehawk.engine.render.opengl.GLEnumShader;
import com.elusivehawk.engine.util.TextParser;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Shader extends Asset
{
	protected final GLEnumShader gltype;
	protected final String source;
	protected int[] ids = new int[2];
	
	@SuppressWarnings("unqualified-field-access")
	public Shader(File file, GLEnumShader type)
	{
		super(file.getName());
		
		gltype = type;
		source = TextParser.concat(TextParser.read(file), "\n", "", null);
		
		ids[1] = type.ordinal();
		
	}
	
	public String getSource()
	{
		return this.source;
	}
	
	@Override
	public int[] getIds()
	{
		return this.ids;
	}
	
	@Override
	protected boolean finishAsset()
	{
		this.ids[0] = RenderHelper.loadShader(this.source, this.gltype);
		
		return this.ids[0] != 0;
	}
	
}
