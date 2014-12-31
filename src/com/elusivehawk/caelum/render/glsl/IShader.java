
package com.elusivehawk.caelum.render.glsl;

import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderException;
import com.elusivehawk.caelum.render.gl.IGLDeletable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IShader extends IGLDeletable
{
	void compile(RenderContext rcon) throws RenderException;
	
	boolean isCompiled();
	
	String getSource();
	
	int getShaderId();
	
	GLSLEnumShaderType getType();
	
}
