
package elusivehawk.engine.task;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ITaskReceiver<T>
{
	public void onTaskFinished(int taskId, T obj);
	
}
