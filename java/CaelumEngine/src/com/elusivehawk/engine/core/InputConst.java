
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
	
	public static enum MouseConst implements IInputConst
	{
		/**
		 * Type: Getter
		 * Return type: Integer.
		 * 
		 * Should return the X position of the cursor.
		 */
		MOUSE_X(0x00),
		
		/**
		 * Type: Getter
		 * Return type: Integer.
		 * 
		 * Should return the Y position of the cursor.
		 */
		MOUSE_Y(0x01),
		
		/**
		 * Type: Getter
		 * Return type: Integer.
		 * 
		 * Should return the delta of the cursor's X position, relative to its previous X position.
		 */
		MOUSE_DX(0x02),
		
		/**
		 * Type: Getter
		 * Return type: Integer.
		 * 
		 * Should return the delta of the cursor's Y position, relative to its previous Y position.
		 */
		MOUSE_DY(0x03),
		
		/**
		 * Type: Getter
		 * Return type: Integer.
		 * 
		 * Should return the delta of the scroll wheel.
		 */
		MOUSE_DWHEEL(0x04),
		
		/**
		 * Type: Getter
		 * Return type: Boolean.
		 * 
		 * Should return if the scroll wheel is being clicked.
		 */
		MOUSE_CWHEEL(0x05),
		
		/**
		 * Type: Setter or getter.
		 * Return type: Boolean.
		 * 
		 * Should alter or return whether or not the mouse's position is locked.
		 * 
		 * Note: Useful for first or third person perspectives in 3D games.
		 */
		MOUSE_LOCK(0x06),
		
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
		MOUSE_CLICK(0x10);
		
		private final int value;
		
		@SuppressWarnings("unqualified-field-access")
		MouseConst(int v)
		{
			value = v;
			
		}
		
		@Override
		public EnumInputType getInputType()
		{
			return EnumInputType.MOUSE;
		}
		
		@Override
		public int getValue()
		{
			return this.value;
		}
		
	}
	
}
