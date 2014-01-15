
package com.elusivehawk.engine.core;

/**
 * 
 * The recommended constants to use when implementing a {@linkplain IInput controller}.
 * 
 * @author Elusivehawk
 */
public final class InputConst
{
	private InputConst(){}
	
	/**
	 * Type: Getter
	 * Return type: Integer.
	 * 
	 * Should return the X position of the cursor.
	 */
	public static final int MOUSE_X = 0x00;
	/**
	 * Type: Getter
	 * Return type: Integer.
	 * 
	 * Should return the Y position of the cursor.
	 */
	public static final int MOUSE_Y = 0x01;
	/**
	 * Type: Getter
	 * Return type: Integer.
	 * 
	 * Should return the delta of the cursor's X position, relative to its previous X position.
	 */
	public static final int MOUSE_DX = 0x02;
	/**
	 * Type: Getter
	 * Return type: Integer.
	 * 
	 * Should return the delta of the cursor's Y position, relative to its previous Y position.
	 */
	public static final int MOUSE_DY = 0x03;
	/**
	 * Type: Getter
	 * Return type: Integer.
	 * 
	 * Should return the delta of the scroll wheel.
	 */
	public static final int MOUSE_DWHEEL = 0x04;
	/**
	 * Type: Getter
	 * Return type: Boolean.
	 * 
	 * Should return if the scroll wheel is being clicked.
	 */
	public static final int MOUSE_CWHEEL = 0x05;
	/**
	 * Type: Setter or getter.
	 * Return type: Boolean.
	 * 
	 * Should alter or return whether or not the mouse's position is locked.
	 * 
	 * Note: Useful for first or third person perspectives in 3D games.
	 */
	public static final int MOUSE_LOCK = 0x06;
	
	/**
	 * Type: Getter
	 * Return type: Boolean.
	 * 
	 * Should return if the mouse button in question is being clicked.
	 * 
	 * Note: Adding to this should return whether or not various buttons are being clicked, i.e.
	 * MOUSE_CLICK + 1 should return if the 2nd button (Usually the right one) is being clicked.
	 * 
	 * By itself, it should return if the left mouse button is being clicked.
	 */
	public static final int MOUSE_CLICK = 0x10;
	
}
