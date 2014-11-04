
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.Experimental;
import com.elusivehawk.caelum.input.IInputListener;
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
public interface ICamera extends Quaternion.Listener, Vector.Listener, IInputListener, IPreRenderer
{
	Matrix getView();
	
	Matrix getProjection();
	
}
