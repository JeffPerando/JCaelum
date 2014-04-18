
package com.elusivehawk.engine.android;

import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.util.ShutdownHelper;
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
		setContentView(new CaelumView(this));
		ShutdownHelper.instance().setShutdownMech(new AndroidShutdown(this));
		
		CaelumEngine.instance().createGameEnv(String.format("env:%s", AndroidEnvironment.class.getCanonicalName()));
		
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		
		CaelumEngine.instance().startGame();
		
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		CaelumEngine.instance().pauseGame(false);
		
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		
		CaelumEngine.instance().pauseGame(true);
		
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		
		CaelumEngine.instance().shutDownGame();
		
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
		CaelumEngine.instance().clearGameEnv();
		
	}
	
}
