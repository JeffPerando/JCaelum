
package elusivehawk.engine.math;

import java.nio.Buffer;
import elusivehawk.engine.util.IStoreable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IVector extends IStoreable
{
	public int getSize();
	
	public Buffer asBuffer();
	
	public float[] array();
	
}
