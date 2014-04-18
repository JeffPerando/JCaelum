
package com.elusivehawk.engine.android;

import java.awt.Canvas;
import java.io.IOException;
import android.view.View;
import com.elusivehawk.engine.render.Color;
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
	private final String name;
	private final View v;
	
	public AndroidDisplay(String nickname, View view)
	{
		name = nickname;
		v = view;
		
	}
	
	/* (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException
	{
		this.v.setEnabled(false);
		
	}
	
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.render.IDisplay#getTitle()
	 */
	@Override
	public String getTitle()
	{
		return null;//FIXME
	}
	
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.render.IDisplay#getName()
	 */
	@Override
	public String getName()
	{
		return this.name;
	}
	
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.render.IDisplay#getFullscreen()
	 */
	@Override
	public boolean getFullscreen()
	{
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.render.IDisplay#getVSync()
	 */
	@Override
	public boolean getVSync()
	{
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.render.IDisplay#canClose()
	 */
	@Override
	public boolean canClose()
	{
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.render.IDisplay#isCloseRequested()
	 */
	@Override
	public boolean isCloseRequested()
	{
		return !this.v.isEnabled();
	}
	
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.render.IDisplay#getHeight()
	 */
	@Override
	public int getHeight()
	{
		return this.v.getHeight();
	}
	
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.render.IDisplay#getWidth()
	 */
	@Override
	public int getWidth()
	{
		return this.v.getWidth();
	}
	
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.render.IDisplay#getCanvas()
	 */
	@Override
	public Canvas getCanvas()
	{
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.render.IDisplay#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String title){}
	
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.render.IDisplay#resize(int, int)
	 */
	@Override
	public void resize(int height, int width)
	{
		
	}
	
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.render.IDisplay#setCloseable(boolean)
	 */
	@Override
	public void setCloseable(boolean close){}
	
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.render.IDisplay#setFullscreen(boolean)
	 */
	@Override
	public void setFullscreen(boolean full){}
	
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.render.IDisplay#setVSync(boolean)
	 */
	@Override
	public void setVSync(boolean vsync){}
	
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.render.IDisplay#setFPS(int)
	 */
	@Override
	public void setFPS(int fps){}
	
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.render.IDisplay#updateDisplay()
	 */
	@Override
	public void updateDisplay(){}
	
	/* (non-Javadoc)
	 * @see com.elusivehawk.engine.render.IDisplay#setBackgroundColor(com.elusivehawk.engine.render.Color)
	 */
	@Override
	public void setBackgroundColor(Color col)
	{
		this.v.setBackgroundColor(col.color);
		
	}
	
}
