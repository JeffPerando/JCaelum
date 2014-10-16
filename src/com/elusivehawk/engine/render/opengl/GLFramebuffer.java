
package com.elusivehawk.engine.render.opengl;

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
	private final int width, height;
	private int fbo = 0, depth = 0, tex = 0, last = 0;
	
	public GLFramebuffer(int w, int h)
	{
		this(true, w, h);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public GLFramebuffer(boolean depth, int w, int h)
	{
		assert w > 0;
		assert h > 0;
		
		useDepth = depth;
		width = w;
		height = h;
		
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
		IGL1 gl1 = rcon.getGL1();
		IGL2 gl2 = rcon.getGL2();
		IGL3 gl3 = rcon.getGL3();
		
		this.last = gl1.glGetInteger(GLConst.GL_FRAMEBUFFER_BINDING);
		
		if (this.initiated)
		{
			gl3.glBindFramebuffer(GLEnumFBType.GL_FRAMEBUFFER, this.fbo);
			
		}
		else
		{
			rcon.registerCleanable(this);
			
			this.fbo = gl3.glGenFramebuffer();
			
			this.tex = RenderHelper.genTexture(rcon, GLEnumTexture.GL_TEXTURE_2D, this.width, this.height, false, false);
			
			gl3.glBindFramebuffer(GLEnumFBType.GL_FRAMEBUFFER, this.fbo);
			
			if (this.useDepth)
			{
				this.depth = gl3.glGenRenderbuffer();
				
				gl3.glBindRenderbuffer(this.depth);
				gl3.glRenderbufferStorage(GLConst.GL_DEPTH_COMPONENT, this.width, this.height);
				gl3.glFramebufferRenderbuffer(GLEnumFBType.GL_FRAMEBUFFER, GLEnumFBAttach.GL_DEPTH_ATTACHMENT, this.depth);
				
			}
			
			gl3.glFramebufferTexture(GLEnumFBType.GL_FRAMEBUFFER, GLEnumFBAttach.GL_COLOR_ATTACHMENT, this.tex, 0);
			
			gl2.glDrawBuffer(GLConst.GL_COLOR_ATTACHMENT0);
			
			this.initiated = true;
			
		}
		
		rcon.getGL1().glViewport(0, 0, this.width, this.height);
		
		return true;
	}
	
	@Override
	public void unbind(RenderContext rcon)
	{
		IGL3 gl3 = rcon.getGL3();
		
		if (this.last == 0 || gl3.glIsFramebuffer(this.last))
		{
			gl3.glBindFramebuffer(GLEnumFBType.GL_FRAMEBUFFER, this.last);
			
		}
		
	}
	
	public int getTexture()
	{
		return this.tex;
	}
	
}
