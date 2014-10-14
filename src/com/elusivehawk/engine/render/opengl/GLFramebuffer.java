
package com.elusivehawk.engine.render.opengl;

import com.elusivehawk.engine.render.IDisplay;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.RenderHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class GLFramebuffer implements IGLBindable
{
	private final boolean useDepth;
	private boolean initiated = false;
	private int fbo = 0, depth = 0, tex = 0;
	
	public GLFramebuffer()
	{
		this(true);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public GLFramebuffer(boolean depth)
	{
		useDepth = depth;
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		if (this.initiated)
		{
			IGL3 gl3 = rcon.getGL3();
			gl3.glDeleteFramebuffer(this.fbo);
			
			rcon.getGL1().glDeleteTextures(this.tex);
			
			if (this.useDepth)
			{
				gl3.glDeleteRenderbuffer(this.depth);
				
			}
			
		}
		
	}
	
	@Override
	public boolean bind(RenderContext rcon)
	{
		IGL2 gl2 = rcon.getGL2();
		IGL3 gl3 = rcon.getGL3();
		
		if (!this.initiated)
		{
			IDisplay display = rcon.getDisplay();
			
			this.fbo = gl3.glGenFramebuffer();
			
			this.tex = RenderHelper.genTexture(rcon, GLEnumTexture.GL_TEXTURE_2D, display.getWidth(), display.getHeight(), false, false);
			
			gl3.glBindFramebuffer(GLEnumFBType.GL_FRAMEBUFFER, this.fbo);
			
			if (this.useDepth)
			{
				this.depth = gl3.glGenRenderbuffer();
				
				gl3.glBindRenderbuffer(this.depth);
				gl3.glRenderbufferStorage(GLConst.GL_DEPTH_COMPONENT, display.getWidth(), display.getHeight());
				gl3.glFramebufferRenderbuffer(GLEnumFBType.GL_FRAMEBUFFER, GLEnumFBAttach.GL_DEPTH_ATTACHMENT, this.depth);
				
			}
			
			gl3.glFramebufferTexture(GLEnumFBType.GL_FRAMEBUFFER, GLEnumFBAttach.GL_COLOR_ATTACHMENT, this.tex, 0);
			
			gl2.glDrawBuffer(GLConst.GL_COLOR_ATTACHMENT0);
			
			this.initiated = true;
			
			return true;
		}
		
		gl3.glBindFramebuffer(GLEnumFBType.GL_FRAMEBUFFER, this.fbo);
		
		return true;
	}
	
	@Override
	public void unbind(RenderContext rcon)
	{
		rcon.getGL3().glBindFramebuffer(GLEnumFBType.GL_FRAMEBUFFER, 0);
		
	}
	
	public int getTexture()
	{
		return this.tex;
	}
	
}
