
package com.elusivehawk.engine.lwjgl;

import java.io.IOException;
import com.elusivehawk.engine.render.Color;
import com.elusivehawk.engine.render.IDisplay;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class LWJGLDisplay implements IDisplay
{
	private final String name;
	
	@SuppressWarnings("unqualified-field-access")
	public LWJGLDisplay(String nickname)
	{
		name = nickname;
		
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
	public String getName()
	{
		return this.name;
	}
	
	@Override
	public boolean getFullscreen()
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean getVSync()
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean canClose()
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
	public void setTitle(String title)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resize(int height, int width)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setCloseable(boolean close)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setFullscreen(boolean full)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setVSync(boolean vsync)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setFPS(int fps)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateDisplay()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setBackgroundColor(Color col)
	{
		// TODO Auto-generated method stub
		
	}
	
}
