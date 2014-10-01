
package com.elusivehawk.engine.render.opengl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.engine.render.RenderContext;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum GLEnumUType
{
	ONE
	{
		@Override
		public void loadUniform(RenderContext rcon, int loc, FloatBuffer buf)
		{
			rcon.getGL2().glUniform1f(loc, buf.get());
			
		}
		
		@Override
		public void loadUniform(RenderContext rcon, int loc, IntBuffer buf)
		{
			rcon.getGL2().glUniform1i(loc, buf.get());
			
		}
		
	},
	TWO
	{
		@Override
		public void loadUniform(RenderContext rcon, int loc, FloatBuffer buf)
		{
			rcon.getGL2().glUniform2f(loc, buf.get(), buf.get());
			
		}
		
		@Override
		public void loadUniform(RenderContext rcon, int loc, IntBuffer buf)
		{
			rcon.getGL2().glUniform2i(loc, buf.get(), buf.get());
			
		}
		
	},
	THREE
	{
		@Override
		public void loadUniform(RenderContext rcon, int loc, FloatBuffer buf)
		{
			rcon.getGL2().glUniform3f(loc, buf.get(), buf.get(), buf.get());
			
		}
		
		@Override
		public void loadUniform(RenderContext rcon, int loc, IntBuffer buf)
		{
			rcon.getGL2().glUniform3i(loc, buf.get(), buf.get(), buf.get());
			
		}
		
	},
	FOUR
	{
		@Override
		public void loadUniform(RenderContext rcon, int loc, FloatBuffer buf)
		{
			rcon.getGL2().glUniform4f(loc, buf.get(), buf.get(), buf.get(), buf.get());
			
		}
		
		@Override
		public void loadUniform(RenderContext rcon, int loc, IntBuffer buf)
		{
			rcon.getGL2().glUniform4i(loc, buf.get(), buf.get(), buf.get(), buf.get());
			
		}
		
	},
	M_TWO
	{
		@Override
		public void loadUniform(RenderContext rcon, int loc, FloatBuffer buf)
		{
			rcon.getGL2().glUniformMatrix2fv(loc, 1, false, buf);
			
		}
		
		@Override
		public void loadUniform(RenderContext rcon, int loc, IntBuffer buf){}
		
	},
	M_THREE
	{
		@Override
		public void loadUniform(RenderContext rcon, int loc, FloatBuffer buf)
		{
			rcon.getGL2().glUniformMatrix3fv(loc, 1, false, buf);
			
		}
		
		@Override
		public void loadUniform(RenderContext rcon, int loc, IntBuffer buf){}
		
	},
	M_FOUR
	{
		@Override
		public void loadUniform(RenderContext rcon, int loc, FloatBuffer buf)
		{
			rcon.getGL2().glUniformMatrix4fv(loc, 1, false, buf);
			
		}
		
		@Override
		public void loadUniform(RenderContext rcon, int loc, IntBuffer buf){}
		
	};
	
	public abstract void loadUniform(RenderContext rcon, int loc, FloatBuffer buf);
	
	public abstract void loadUniform(RenderContext rcon, int loc, IntBuffer buf);
	
}
