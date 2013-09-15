
package elusivehawk.engine.task;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import elusivehawk.engine.util.TextParser;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TaskWrite extends Task<Boolean>
{
	protected final File txt;
	protected final boolean append, makeFileIfNotFound;
	protected final String encoding;
	protected final List<String> txtToWrite;
	
	public TaskWrite(int idNo, File file, String encode, boolean appen, boolean make, ITaskReceiver<Boolean> handler, String... text)
	{
		super(idNo, handler);
		txt = file;
		encoding = encode;
		append = appen;
		makeFileIfNotFound = make;
		txtToWrite = Arrays.asList(text);
		
	}
	
	@Override
	public boolean isTaskFinished()
	{
		return true;
	}
	
	@Override
	protected Boolean tryTask()
	{
		return TextParser.write(this.txt, this.txtToWrite, this.encoding, this.append, this.makeFileIfNotFound);
	}
	
}
