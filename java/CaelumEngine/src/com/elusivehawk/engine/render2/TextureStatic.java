
package com.elusivehawk.engine.render2;

import java.io.File;
import com.elusivehawk.engine.render.opengl.ITexture;

/**
 * 
 * NOTICE: This is simply a wrapper for {@link ITexture}.
 * 
 * @author Elusivehawk
 */
public class TextureStatic implements ITexture
{
	protected final int tex;
	
	@SuppressWarnings("unqualified-field-access")
	public TextureStatic(int texture)
	{
		tex = texture;
		
		RenderHelper.register(this);
		
	}
	
	public TextureStatic(String path, RenderContext context)
	{
		this(path, EnumRenderMode.MODE_2D, EnumColorFormat.RGBA, context);
		
	}
	
	public TextureStatic(String path, EnumRenderMode mode, EnumColorFormat format, RenderContext context)
	{
		this(new File(ClassLoader.getSystemResource(path).getFile()), mode, format, context);
		
	}
	
	public TextureStatic(File file, EnumRenderMode mode, EnumColorFormat format, RenderContext context)
	{
		this(RenderHelper.processImage(file, mode, format, context));
		
	}
	
	public TextureStatic(ILegibleImage img, EnumRenderMode mode, EnumColorFormat format, RenderContext context)
	{
		this(RenderHelper.processImage(img, mode, format, context));
		
	}
	
	@Override
	public void glDelete(RenderContext context)
	{
		context.getGL1().glDeleteTextures(this.getTexture(true));
		
	}
	
	@Override
	public int getTexture(boolean next)
	{
		return this.tex;
	}
	
	@Override
	public boolean isStatic()
	{
		return true;
	}
	
}
