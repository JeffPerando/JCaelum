
package com.elusivehawk.caelum.assets;

import java.io.DataInputStream;
import java.nio.ByteBuffer;
import com.elusivehawk.caelum.render.tex.ColorFormat;
import com.elusivehawk.caelum.render.tex.ILegibleImage;
import com.elusivehawk.caelum.render.tex.LegibleByteImage;
import com.elusivehawk.util.storage.BufferHelper;
import de.matthiasmann.twl.utils.PNGDecoder;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class PNGReader implements IAssetReader
{
	@Override
	public ILegibleImage readAsset(DataInputStream in) throws Throwable
	{
		PNGDecoder dec = new PNGDecoder(in);
		
		int width = dec.getWidth();
		int height = dec.getHeight();
		
		ByteBuffer buf = BufferHelper.createByteBuffer(width * height * 4);
		
		dec.decode(buf, width * 4, PNGDecoder.Format.RGBA);
		
		buf.flip();
		
		return new LegibleByteImage(ColorFormat.RGBA, width, height, buf);
		
	}
	
}
