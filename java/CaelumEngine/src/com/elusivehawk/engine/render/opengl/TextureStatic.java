
package com.elusivehawk.engine.render.opengl;

import java.io.File;
import com.elusivehawk.engine.render.EnumColorFormat;
import com.elusivehawk.engine.render.EnumRenderMode;
import com.elusivehawk.engine.render.ILegibleImage;
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
	
	public TextureStatic(String path)
	{
		this(path, EnumRenderMode.MODE_2D, EnumColorFormat.RGBA);
		
	}
	
	public TextureStatic(String path, EnumRenderMode mode, EnumColorFormat format)
	{
		this(new File(ClassLoader.getSystemResource(path).getFile()), mode, format);
		
	}
	
	public TextureStatic(File file, EnumRenderMode mode, EnumColorFormat format)
	{
		this(RenderHelper.processImage(file, mode, format));
		
	}
	
	public TextureStatic(ILegibleImage img, EnumRenderMode mode, EnumColorFormat format)
	{
		this(RenderHelper.processImage(img, mode, format));
		
	}
	
	@Override
	public void glDelete()
	{
		RenderHelper.gl1().glDeleteTextures(this.getTexture());
		
	}
	
	@Override
	public int getTexture()
	{
		return this.tex;
	}
	
	@Override
	public boolean bind(int... extras)
	{
		RenderHelper.gl1().glBindTexture(GLConst.GL_TEXTURE0 + (extras == null || extras.length == 0 ? 0 : extras[0]), this);
		
		try
		{
			RenderHelper.checkForGLError();
			
		}
		catch (Exception e)
		{
			this.unbind(extras);
			
			return false;
		}
		
		return true;
	}
	
	@Override
	public void unbind(int... extras)
	{
		RenderHelper.gl1().glBindTexture(GLConst.GL_TEXTURE0 + (extras == null || extras.length == 0 ? 0 : extras[0]), 0);
		
	}
	
}
