
package com.elusivehawk.engine.assets;

import java.io.File;
import com.elusivehawk.engine.render.opengl.GLEnumShader;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ReaderGLSL implements IAssetReader
{
	protected final GLEnumShader stype;
	
	@SuppressWarnings("unqualified-field-access")
	public ReaderGLSL(GLEnumShader type)
	{
		stype = type;
		
	}
	
	@Override
	public Asset readAsset(AssetManager mgr, File file) throws Exception
	{
		return new Shader(file, this.stype);
	}
	
}
