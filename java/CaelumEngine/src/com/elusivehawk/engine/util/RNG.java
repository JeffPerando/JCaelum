
package com.elusivehawk.engine.util;

import java.util.Random;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class RNG
{
	private static final Random RNG = new Random("Elusivehawk".hashCode());
	
	private RNG(){}
	
	public static Random instance()
	{
		return RNG;
	}
	
}
