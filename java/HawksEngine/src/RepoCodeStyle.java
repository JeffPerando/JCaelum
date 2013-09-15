//Line down, then package.
import elusivehawk.engine.util.GameLog;

/**
 * 
 * Contains code style examples for this engine's repo. It's recommended you follow this, but it's not mandatory, just extremely recommended.
 * 
 * On another note, don't use line downs to "organize" imports; They're IMPORTS, you're almost never going to see them!
 * 
 * @author Elusivehawk
 */
public final class RepoCodeStyle
{
	private RepoCodeStyle(){}
	
	public final int finNonStaticField = 0; //Name every non-static or non-final field like so.
	public static int FIN_FIELD = 0; //"Constants" like this (Static, final) should be named like this, however.
	
	/**
	 * Void example
	 */
	public void voidExample(/*Use camelCase for naming.*/)
	{//Line down, then first bracket.
		//Tabs only, never spaces. Spaces force you to put a rock on your arrow keys.
		//Notice the line down before the line with the final bracket? It makes the code easier to read.
		
		if (FIN_FIELD == 1)
		{//The line down rule applies to these, too.
			this.tryCatchExample();
			
			/*
			 * It's highly recommended to use "this" whenever possible, so that it's easier to distinguish between
			 * static and non-static.
			 * 
			 * Exception: In the constructor, "this" should only be used on a need-to basis.
			 * 
			 */
			
		}
		
	}
	
	/**
	 * 
	 * "Returning" method example.
	 * 
	 * @return Zero
	 */
	public int intExample()
	{
		return 0; //No line down when you return, break, continue, or (Optionally) throw.
	}
	
	public void throwingExample() throws Exception
	{
		throw new RuntimeException("Nice job...");
		//Once again, this bit is completely optional.
		
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
		catch (Exception e) //Only catch Exception/Throwable, to pack code more compact. However, you can catch different exceptions to make for a cheap instanceof check.
		{
			GameLog.error(e);//Use GameLog unless you have a good reason.
			
		}
		finally //Don't do this unless you must.
		{
			
			
		}
		
	}
	
	public void countingExample()
	{
		for (int c = 0; c < intExample(); c++) //For counters, using the C field is recommended, but it's not mandatory.
		{//Line downs before the first bracket still apply.
			this.voidExample();
			//So do the ones here.
			
		}
		
	}
	
	//TODO Continue
	
}
