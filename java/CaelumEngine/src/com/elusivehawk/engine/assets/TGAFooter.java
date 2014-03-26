
package com.elusivehawk.engine.assets;

import com.elusivehawk.engine.util.io.IByteReader;
import com.elusivehawk.engine.util.io.Serializer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TGAFooter
{
	public final int extensionOff, devOff;
	public final String sig;
	public final boolean hasPeriod;
	public final boolean lastByteCompleted;
	
	@SuppressWarnings("unqualified-field-access")
	public TGAFooter(IByteReader r)
	{
		extensionOff = Serializer.INTEGER.fromBytes(r);
		devOff = Serializer.INTEGER.fromBytes(r);
		
		char[] ch = new char[16];
		
		for (int c = 0; c < 16; c++)
		{
			ch[c] = (char)r.read();
			
		}
		
		sig = new String(ch);
		
		hasPeriod = (char)r.read() == '.';
		
		lastByteCompleted = r.read() == 0;
		
	}
	
}
