
package com.elusivehawk.engine;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Input
{
	protected final EnumInputType inputType;
	
	@SuppressWarnings("unqualified-field-access")
	public Input(EnumInputType type)
	{
		inputType = type;
		
	}
	
	public final EnumInputType getType()
	{
		return this.inputType;
	}
	
	public abstract boolean initiateInput();
	
	protected abstract void updateInput();
	
	public abstract void cleanup();
	
	public abstract void setFlag(String name, boolean value);
	
}
