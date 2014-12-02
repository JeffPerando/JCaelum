
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.Experimental;
import com.elusivehawk.util.math.Matrix;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Experimental
public interface ICamera extends IPreRenderer
{
	Matrix getView();
	
	Matrix getProjection();
	
}
