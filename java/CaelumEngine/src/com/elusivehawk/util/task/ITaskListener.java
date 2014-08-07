
package com.elusivehawk.util.task;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface ITaskListener
{
	public void onTaskComplete(Task task);
	
}
