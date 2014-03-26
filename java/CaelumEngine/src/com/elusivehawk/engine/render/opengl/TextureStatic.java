
package com.elusivehawk.engine.render.opengl;

import java.io.File;
import com.elusivehawk.engine.assets.Asset;
import com.elusivehawk.engine.assets.EnumAssetType;
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
public class TextureStatic implements Asset
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
	public EnumAssetType getType()
	{
		return EnumAssetType.TEXTURE;
	}
	
	@Override
	public int[] getIds()
	{
		return new int[]{this.tex};
	}
	
	@Override
	public boolean isFinished()
	{
		return true;//FIXME
	}
	
	@Override
	public void finish(){}
	
}
