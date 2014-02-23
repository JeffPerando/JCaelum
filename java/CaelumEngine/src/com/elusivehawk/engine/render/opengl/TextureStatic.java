
package com.elusivehawk.engine.render.opengl;

import java.io.File;
import com.elusivehawk.engine.render.EnumColorFormat;
import com.elusivehawk.engine.render.EnumRenderMode;
import com.elusivehawk.engine.render.ILegibleImage;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.RenderHelper;

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
		context.getGL1().glDeleteTextures(this.getTexture());
		
	}
	
	@Override
	public void updateTexture(RenderContext context){}
	
	@Override
	public int getTexture()
	{
		return this.tex;
	}
	
	@Override
	public boolean isStatic()
	{
		return true;
	}
	
}
