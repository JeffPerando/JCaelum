
package com.elusivehawk.engine.assets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import com.elusivehawk.engine.util.Buffer;
import com.elusivehawk.engine.util.FileHelper;
import com.elusivehawk.engine.util.io.ByteBufWrapper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ReaderTGA implements AssetReader
{
	public static final String SIGNATURE = "TRUEVISION-XFILE";
	
	public static final int L_R_BIT = 0b00010000;
	public static final int B_T_BIT = 0b00100000;
	
	@Override
	public Asset readAsset(File file) throws Exception
	{
		FileInputStream fis = FileHelper.createInStream(file);
		
		if (fis == null)
		{
			return null;
		}
		
		BufferedInputStream in = new BufferedInputStream(fis);
		
		Byte[] b = new Byte[in.available()];
		
		for (int c = 0; c < b.length; c++)
		{
			b[c] = (byte)in.read();
			
		}
		
		Buffer<Byte> buf = new Buffer<Byte>(b);
		ByteBufWrapper r = new ByteBufWrapper(null, buf);
		
		TGAHeader h = new TGAHeader(r);
		
		buf.mark();
		buf.position(buf.size() - 26);
		
		TGAFooter f = new TGAFooter(r);
		
		buf.rewind();
		
		
		
		return null;
	}
	
}
