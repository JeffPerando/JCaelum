
package com.elusivehawk.caelum.render.gl;

import com.elusivehawk.caelum.Display;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class GLFramebuffer extends GLObject
{
	private final boolean useDepth;
	
	private int id = 0, depth = 0, tex = 0, last = 0;
	
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
	public void unbind(RenderContext rcon)
	{
		GL3.glBindFramebuffer(GLEnumFBType.GL_FRAMEBUFFER, this.last);
		
		this.last = 0;
		
		GL1.glViewport(rcon.getDisplay());
		
	}

	@Override
	public boolean isBound(RenderContext rcon)
	{
		return this.id != 0 && GL1.glGetInteger(GLConst.GL_FRAMEBUFFER_BINDING) == this.id;
	}
	
	@Override
	public boolean bindImpl(RenderContext rcon)
	{
		Display display = rcon.getDisplay();
		
		this.last = GL1.glGetInteger(GLConst.GL_FRAMEBUFFER_BINDING);
		
		if (this.isInitiated())
		{
			GL3.glBindFramebuffer(GLEnumFBType.GL_FRAMEBUFFER, this.id);
			
		}
		
		GL1.glViewport(display);
		
		return true;
	}
	
	@Override
	protected void initiate(RenderContext rcon)
	{
		this.id = GL3.glGenFramebuffer();
		
		Display display = rcon.getDisplay();
		
		this.tex = RenderHelper.genTexture(GLEnumTexture.GL_TEXTURE_2D, display.getWidth(), display.getHeight(), false);
		
		GL3.glBindFramebuffer(GLEnumFBType.GL_FRAMEBUFFER, this.id);
		
		if (this.useDepth)
		{
			this.depth = GL3.glGenRenderbuffer();
			
			GL3.glBindRenderbuffer(this.depth);
			GL3.glRenderbufferStorage(GLConst.GL_DEPTH_COMPONENT, display.getWidth(), display.getHeight());
			GL3.glFramebufferRenderbuffer(GLEnumFBType.GL_FRAMEBUFFER, GLEnumFBAttach.GL_DEPTH_ATTACHMENT, this.depth);
			
		}
		
		GL3.glFramebufferTexture(GLEnumFBType.GL_FRAMEBUFFER, GLEnumFBAttach.GL_COLOR_ATTACHMENT, this.tex, 0);
		
		GL2.glDrawBuffer(GLConst.GL_COLOR_ATTACHMENT0);
		
	}

	@Override
	protected void deleteImpl(RenderContext rcon)
	{
		GL3.glDeleteFramebuffer(this.id);
		
		GL1.glDeleteTextures(this.tex);
		
		if (this.useDepth)
		{
			GL3.glDeleteRenderbuffer(this.depth);
			
		}
		
	}
	
	public int getTexture()
	{
		return this.tex;
	}
	
}
