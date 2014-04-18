
package com.elusivehawk.engine.android;

import android.app.Activity;
import com.elusivehawk.engine.util.ShutdownHelper.ShutdownMechanism;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class AndroidShutdown extends ShutdownMechanism
{
	private final Activity a;
	
	public AndroidShutdown(Activity activity)
	{
		a = activity;
		
	}
	
	@Override
	public void exit(int err)
	{
		this.a.finish();
		super.exit(err);
		
	}
	
}
