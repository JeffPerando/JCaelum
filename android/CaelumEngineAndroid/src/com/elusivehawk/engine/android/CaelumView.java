
package com.elusivehawk.engine.android;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.GLSurfaceView;
import com.elusivehawk.engine.render.RenderSystem;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class CaelumView extends GLSurfaceView
{
	public CaelumView(Context context)
	{
		super(context);
		
	}
	
	@SuppressWarnings("unused")
	public static class CaelumGLR implements GLSurfaceView.Renderer
	{
		public CaelumGLR(RenderSystem rsys)
		{
			
		}
		
		@Override
		public void onSurfaceCreated(GL10 unused, EGLConfig config)
		{
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onSurfaceChanged(GL10 unused, int width, int height)
		{
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onDrawFrame(GL10 unused)
		{
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
