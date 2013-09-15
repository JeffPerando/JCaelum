
package elusivehawk.engine.render;

import java.awt.image.BufferedImage;
import elusivehawk.engine.math.Vector2f;
import elusivehawk.engine.math.Vector2i;
import elusivehawk.engine.util.GameLog;

/**
 * 
 * Because I don't like Greek gods!
 * 
 * @author Elusivehawk
 */
public class TextureMap extends GenericTexture
{
	protected final Vector2i texSize = new Vector2i();
	protected final Vector2i texPos = new Vector2i();
	
	public TextureMap(BufferedImage img, int w, int h, boolean is3D)
	{
		super(img, is3D);
		
		if (img.getWidth() % w == 0 && img.getHeight() % h == 0)
		{
			texSize.x = img.getWidth() / w;
			texSize.y = img.getHeight() / h;
			
		}
		else
		{
			GameLog.error("Cannot proceed with texture mapping, image given is an odd size.");
			
		}
		
	}
	
	public Vector2i getTextureSize()
	{
		return this.texSize;
	}
	
	public Vector2f generateTextureOffset(boolean xEnd, boolean yEnd)
	{
		Vector2f ret = new Vector2f();
		
		if (xEnd)
		{
			this.texPos.x += (this.texSize.x - 1);
			
		}
		
		if (yEnd)
		{
			this.texPos.y += (this.texSize.y - 1);
			
		}
		
		ret.x = this.texPos.x / (this.w * this.texPos.x);
		ret.y = this.texPos.y / (this.h * this.texPos.y);
		
		return ret;
	}
	
	public void setCurrentTexture(int x, int y)
	{
		this.texPos.x = x;
		this.texPos.y = y;
		
	}
	
}
