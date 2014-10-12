
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.Experimental;
import com.elusivehawk.engine.input.IInputListener;
import com.elusivehawk.util.IDirty;
import com.elusivehawk.util.math.Matrix;
import com.elusivehawk.util.math.Quaternion;
import com.elusivehawk.util.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Experimental
public interface ICamera extends IDirty, Quaternion.Listener, Vector.Listener, IInputListener, IRenderable
{
	Matrix getView();
	
	Matrix getProjection();
	
}
