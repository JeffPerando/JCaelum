
package com.elusivehawk.engine.input;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum EnumKeyType
{
	/**
	 * a, u, g, etc.
	 */
	LETTER,
	/**
	 * 0-9, and no, I'm not making the numpad separate.
	 */
	NUMBER,
	/**
	 * Ctrl, shift, backspace, etc.
	 */
	FUNCTIONAL,
	/**
	 * F1-12.
	 */
	F_KEY,
	/**
	 * `/~, ,/<, etc.
	 */
	SYMBOL,
	/**
	 * Whatever doesn't fit in any other category; All 2 of 'em.
	 */
	MISC;
	
}
