
package com.elusivehawk.util;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum EnumVideoCard
{
	AMD, NVIDIA, INTEL, OTHER;
	
	private static final EnumVideoCard VIDEO_CARD = determineVidCard();
	
	private static EnumVideoCard determineVidCard()
	{
		String card = System.getProperty("os.arch");
		
		for (EnumVideoCard vc : values())
		{
			if (card.startsWith(vc.toString()))
			{
				return vc;
			}
			
		}
		
		return OTHER;
	}
	
	public static EnumVideoCard getCurrentVC()
	{
		return VIDEO_CARD;
	}
	
	@Override
	public String toString()
	{
		return this.name().toLowerCase();
	}
	
}
