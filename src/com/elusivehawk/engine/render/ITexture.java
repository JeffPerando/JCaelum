
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.opengl.GLEnumTexture;
import com.elusivehawk.engine.render.opengl.IGLDeletable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ITexture extends IPreRenderer, IGLDeletable
{
	@Override
	default void preRender(RenderContext rcon, double delta){}
	
	default GLEnumTexture getType()
	{
		return GLEnumTexture.GL_TEXTURE_2D;
	}
	
	int getTexId();
	
	boolean isAnimated();
	
}
