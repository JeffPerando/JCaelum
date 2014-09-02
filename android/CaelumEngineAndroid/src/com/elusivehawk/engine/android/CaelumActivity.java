
package com.elusivehawk.engine.android;

import android.app.Activity;
import android.os.Bundle;
import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.util.ShutdownHelper;
import com.elusivehawk.util.StringHelper;

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
		
		CaelumEngine.instance().createGameEnv(StringHelper.asArray(StringHelper.read("/androidArgs.txt")));
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle b)
	{
		super.onSaveInstanceState(b);
		
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle b)
	{
		super.onRestoreInstanceState(b);
		
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		
		CaelumEngine.instance().startGame();
		
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		CaelumEngine.instance().pauseGame(false);
		
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		
		CaelumEngine.instance().pauseGame(true);
		
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
		
		CaelumEngine.instance().shutDownGame();
		
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		
		CaelumEngine.instance().clearGameEnv();
		
	}
	
}
