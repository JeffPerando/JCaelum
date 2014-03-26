
package com.elusivehawk.engine.assets;

import com.elusivehawk.engine.util.io.IByteReader;
import com.elusivehawk.engine.util.io.Serializer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TGAHeader
{
	public final byte imageIdLength;
	public final boolean hasColorMap;
	public final byte imageType;
	
	public final short colorMapOffset, colorMapEntries;
	public final byte bitsPerPixel;
	
	public final short originX, originY, imgW, imgH;
	public final byte pixelDepth, descriptor;
	
	@SuppressWarnings("unqualified-field-access")
	public TGAHeader(IByteReader r) throws Exception
	{
		imageIdLength = r.read();
		hasColorMap = r.read() == 1;
		imageType = r.read();
		
		colorMapOffset = Serializer.SHORT.fromBytes(r);
		colorMapEntries = Serializer.SHORT.fromBytes(r);
		bitsPerPixel = r.read();
		
		originX = Serializer.SHORT.fromBytes(r);
		originY = Serializer.SHORT.fromBytes(r);
		imgW = Serializer.SHORT.fromBytes(r);
		imgH = Serializer.SHORT.fromBytes(r);
		
		pixelDepth = r.read();
		descriptor = r.read();
		
	}
	
}
