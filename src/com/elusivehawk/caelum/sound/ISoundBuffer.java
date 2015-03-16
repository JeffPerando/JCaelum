
package com.elusivehawk.caelum.sound;

import com.elusivehawk.caelum.IDisposable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ISoundBuffer extends IDisposable
{
	int getId();
	
	void destroy();
	
}
