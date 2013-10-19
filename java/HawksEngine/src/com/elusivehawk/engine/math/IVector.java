
package com.elusivehawk.engine.math;

import java.nio.Buffer;
import com.elusivehawk.engine.core.IDirty;
import com.elusivehawk.engine.core.IStoreable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IVector extends IStoreable, IDirty
{
	public boolean isReadOnly();
	
	public int getSize();
	
	public Buffer asBuffer();
	
	public float[] array();
	
}
