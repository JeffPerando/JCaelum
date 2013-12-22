
package com.elusivehawk.engine.android;

import com.elusivehawk.engine.core.CaelumEngine;
import android.app.Activity;
import android.os.Bundle;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class CaelumActivity extends Activity
{
	@Override
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		
		CaelumEngine.main("class:" + AndroidEnvironment.class.getCanonicalName());
		
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
	}
	
}
