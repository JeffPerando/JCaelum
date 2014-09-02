
package com.elusivehawk.engine.android;

import java.awt.Canvas;
import java.io.IOException;
import android.view.View;
import com.elusivehawk.engine.render.Color;
import com.elusivehawk.engine.render.DisplaySettings;
import com.elusivehawk.engine.render.IDisplay;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@SuppressWarnings("unused")
public class AndroidDisplay implements IDisplay
{
	private final View v;
	
	public AndroidDisplay(View view)
	{
		v = view;
		
	}

	@Override
	public void close() throws IOException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTitle()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createDisplay() throws Exception
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getFullscreen()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCloseRequested()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getHeight()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWidth()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateDisplay()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processMessages()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSettings(DisplaySettings settings)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean makeCurrent()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean releaseContext()
	{
		// TODO Auto-generated method stub
		return false;
	}
	
}
