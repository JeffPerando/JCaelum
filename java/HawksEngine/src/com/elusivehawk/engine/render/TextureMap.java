
package com.elusivehawk.engine.render;

import java.awt.image.BufferedImage;
import com.elusivehawk.engine.core.EnumRenderMode;
import com.elusivehawk.engine.core.GameLog;
import com.elusivehawk.engine.core.Tuple;
import com.elusivehawk.engine.math.Vector2f;

/**
 * 
 * Because I don't like Greek gods!
 * 
 * @author Elusivehawk
 */
public class TextureMap extends TextureStatic
{
	protected final Tuple<Integer, Integer> texSize = new Tuple<Integer, Integer>(0, 0);
	protected final Tuple<Integer, Integer> texPos = new Tuple<Integer, Integer>(0, 0);
	
	public TextureMap(BufferedImage img, int w, int h, EnumRenderMode mode)
	{
		super(img, mode);
		
		if (img.getWidth() % w == 0 && img.getHeight() % h == 0)
		{
			texSize.one = img.getWidth() / w;
			texSize.two = img.getHeight() / h;
			
		}
		else
		{
			GameLog.error("Cannot proceed with texture mapping, image given is an odd size.");
			
		}
		
	}
	
	public Tuple<Integer, Integer> getTextureSize()
	{
		return this.texSize;
	}
	
	public Vector2f generateTextureOffset(boolean xEnd, boolean yEnd)
	{
		Vector2f ret = new Vector2f();
		
		if (xEnd)
		{
			this.texPos.one += (this.texSize.one - 1);
			
		}
		
		if (yEnd)
		{
			this.texPos.one += (this.texSize.one - 1);
			
		}
		
		ret.x = this.texPos.one / (this.w * this.texPos.one);
		ret.y = this.texPos.two / (this.h * this.texPos.two);
		
		return ret;
	}
	
	public void setCurrentTexture(int x, int y)
	{
		this.texPos.one = x;
		this.texPos.two = y;
		
	}
	
}
