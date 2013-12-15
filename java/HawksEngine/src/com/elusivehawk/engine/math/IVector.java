
package com.elusivehawk.engine.math;

import java.nio.Buffer;
import com.elusivehawk.engine.core.IDirty;
import com.elusivehawk.engine.core.IStoreable;

/**
 * 
 * @deprecated To be replaced with something more generic.
 * 
 * @author Elusivehawk
 */
@Deprecated
public interface IVector extends IStoreable, IDirty
{
	public boolean isReadOnly();
	
	public int getSize();
	
	public Buffer asBuffer();
	
	public float[] array();
	
}
