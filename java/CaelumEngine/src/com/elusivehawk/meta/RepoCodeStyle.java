//Line down, then package.
package com.elusivehawk.meta;
//Then another line down before the imports.
import com.elusivehawk.engine.core.GameLog;//Note: No "organizing" imports, or more specifically, "grouping" them using line downs.

/**
 * 
 * Contains code style examples for this engine's repo. It's recommended you follow this, but it's not mandatory, just extremely recommended.
 * 
 * Note: A good deal of this is what most coding styles already use (i.e. naming conventions, 
 * 
 * @author Elusivehawk
 */
@SuppressWarnings("static-method")
public final class RepoCodeStyle
{
	private RepoCodeStyle(){}//See these brackets? They denote that this isn't being used, or is simply here to change the visibility of the constructor or method.
	
	public final int finNonStaticField = 0; //Name every non-static or non-final field like so.
	public static final int CONST_FIELD = 0; //"Constants" like this (Static, final) should be named like this, however.
	
	/**
	 * Void example
	 */
	public void voidExample(/*Use camelCase for naming.*/)
	{//Line down, then first bracket.
		//Tabs only, never spaces. Spaces force you to put a rock on your arrow keys.
		//Notice the line down before the line with the final bracket? It makes the code fancier/easier to read.
		
		if (this.intExample() == 1)
		{//The line down rule applies here, too.
			this.tryCatchExample();
			
			/*
			 * It's highly recommended to use "this" whenever possible, so that it's easier to distinguish between
			 * static and non-static.
			 * 
			 * Exception: In a constructor, "this" should only be used on a need-to basis.
			 * 
			 */
			
		}
		//Leave one extra line after an exit bracket, too.
	}
	
	/**
	 * 
	 * "Returning" method example.
	 * 
	 * @return Zero
	 */
	public int intExample()
	{
		return 0; //Do not line down when you return, break, continue, or (Optionally) throw.
	}
	
	public void throwingExample() throws Exception //Defining what you throw specifically is optional, but recommended; Makes for cheap instanceof checks.
	{
		throw new RuntimeException("Nice job...");
		//Once again, this line down is completely optional.
		
	}
	
	/**
	 * Exemplifies trying and catching.
	 */
	public void tryCatchExample()
	{
		try
		{
			this.throwingExample();
			
		}
		catch (Exception e) //Only catch Exception/Throwable, that way there are less imports to fuss with. However, you can catch different exceptions to make for a cheap instanceof check.
		{
			GameLog.error(e); //Use GameLog unless you have a good reason not to.
			
		}
		
	}
	
	public void countingExample()
	{
		/* 
		 * For counters, using the C field is recommended, but it's not mandatory.
		 * 
		 */
		for (int c = 0; c < intExample(); c++)
		{
			this.voidExample();
			
		}
		
	}
	
	//TODO Continue
	
}
