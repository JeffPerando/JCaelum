
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.Experimental;
import com.elusivehawk.engine.input.IInputListener;
import com.elusivehawk.util.IDirty;
import com.elusivehawk.util.math.IQuatListener;
import com.elusivehawk.util.math.IVecListener;
import com.elusivehawk.util.math.Matrix;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Experimental
public interface ICamera extends IDirty, IQuatListener, IVecListener, IInputListener, IRenderable
{
	public Matrix getView();
	
}
