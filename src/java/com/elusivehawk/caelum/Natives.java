
package com.elusivehawk.caelum;

import com.elusivehawk.util.CompInfo;
import com.google.common.collect.ImmutableList;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class Natives
{
	public static final String[]
			WIN_32 = {"/windows/x86/lwjgl.dll", "/windows/x86/OpenAL32.dll"},
			WIN_64 = {"/windows/x64/lwjgl.dll", "/windows/x64/OpenAL32.dll"},
			MAC_32 = {"/macosx/x64/liblwjgl.dylib", "/macosx/x64/libopenal.dylib"},
			MAC_64 = MAC_32,
			LINUX_32 = {"/linux/x86/liblwjgl.so", "/linux/x86/libopenal.so"},
			LINUX_64 = {"/linux/x64/liblwjgl.so", "/linux/x64/libopenal.so"};
	
	private Natives(){}
	
	public static ImmutableList<String> getNatives()
	{
		String[] n = null;
		
		switch (CompInfo.OS)
		{
			case WINDOWS: n = CompInfo.IS_64_BIT ? WIN_64 : WIN_32; break;
			case MAC: n = CompInfo.IS_64_BIT ? MAC_64 : MAC_32; break;
			case LINUX: n = CompInfo.IS_64_BIT ? LINUX_64 : LINUX_32; break;
			
		}
		
		return n == null ? null : ImmutableList.copyOf(n);
	}
	
}
