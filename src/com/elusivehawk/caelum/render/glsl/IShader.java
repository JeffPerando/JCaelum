
package com.elusivehawk.caelum.render.glsl;

import com.elusivehawk.caelum.render.IDeletable;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderException;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IShader extends IDeletable
{
	void compile(RenderContext rcon) throws RenderException;
	
	boolean isCompiled();
	
	String getSource();
	
	int getShaderId();
	
	GLSLEnumShaderType getType();
	
}
