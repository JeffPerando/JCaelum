
package com.elusivehawk.engine.core;

/**
 * 
 * The recommended constants to use when implementing a {@linkplain Input controller}.
 * 
 * @author Elusivehawk
 */
public final class InputConst
{
	private InputConst(){}
	
	public static final int COMMAND_BITMASK = 0b00000000000000001111111111111111;
	public static final int COUNT_BITMASK =   0b11111111111111110000000000000000;
	
	public static final int ZERO = 0x00,
			ONE = 0x01 << 16,
			TWO = 0x02 << 16,
			THREE = 0x03 << 16,
			FOUR = 0x04 << 16,
			FIVE = 0x05 << 16,
			SIX = 0x06 << 16,
			SEVEN = 0x07 << 16,
			EIGHT = 0x08 << 16;
	
	/**
	 * Type: Getter<br>
	 * Return type: Integer
	 * <p>
	 * Should return the X position of the cursor.
	 */
	public static final int MOUSE_X = 0x00;
	
	/**
	 * Type: Getter<br>
	 * Return type: Integer
	 * <p>
	 * Should return the Y position of the cursor.
	 */
	public static final int MOUSE_Y = 0x01;
	
	/**
	 * Type: Getter<br>
	 * Return type: Integer
	 * <p>
	 * Should return the delta of the cursor's X position, relative to its previous X position.
	 */
	public static final int MOUSE_DX = 0x02;
	
	/**
	 * Type: Getter<br>
	 * Return type: Integer
	 * <p>
	 * Should return the delta of the cursor's Y position, relative to its previous Y position.
	 */
	public static final int MOUSE_DY = 0x03;
	
	/**
	 * Type: Getter<br>
	 * Return type: Integer
	 * <p>
	 * Should return the delta of the scroll wheel.
	 */
	public static final int MOUSE_DWHEEL = 0x04;
	
	/**
	 * Type: Getter<br>
	 * Return type: Boolean.
	 * <p>
	 * Should return if the scroll wheel is being clicked.
	 */
	public static final int MOUSE_CWHEEL = 0x05;
	
	/**
	 * Type: Setter or getter.<br>
	 * Return type: Boolean.
	 * <p>
	 * Should alter or return whether or not the mouse's position is locked.
	 * <p>
	 * Note: Useful for first or third person perspectives in 3D games.
	 */
	public static final int MOUSE_LOCK = 0x06;
	
	/**
	 * Type: Getter<br>
	 * Return type: Boolean.
	 * <p>
	 * Should return if the mouse button in question is being clicked.
	 * <p>
	 * Note: Doing a bitwise or to this should return whether or not various buttons are being
	 * clicked, i.e. MOUSE_CLICK | TWO should return if the 2nd button (Usually the right one) is
	 * being clicked.
	 * <p>
	 * By itself, it should return if the left mouse button is being clicked.
	 */
	public static final int MOUSE_CLICK = 0x10;
	
}
