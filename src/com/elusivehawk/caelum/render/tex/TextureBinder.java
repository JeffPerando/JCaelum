
package com.elusivehawk.caelum.render.tex;

import java.util.Map;
import com.elusivehawk.caelum.render.RenderConst;
import com.elusivehawk.caelum.render.gl.GL1;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class TextureBinder
{
	private static final TextureBinder INSTANCE = new TextureBinder();
	
	private final Map<ITexture, Integer> texBinds = Maps.newHashMap();
	private int nextTex = 0;
	
	private TextureBinder(){}
	
	public int bindTexture(ITexture tex)
	{
		if (tex == null)
		{
			return -1;
		}
		
		Integer bound = this.texBinds.get(tex);
		
		if (bound != null)
		{
			return bound.intValue();
		}
		
		if (this.nextTex == RenderConst.GL_MAX_TEX_COUNT)
		{
			return -1;
		}
		
		int ret = this.nextTex;
		
		GL1.glActiveTexture(GLConst.GL_TEXTURE0 + this.nextTex);
		GL1.glBindTexture(tex);
		
		this.texBinds.put(tex, this.nextTex++);
		
		return ret;
	}
	
	public void releaseTextures()
	{
		if (this.texBinds.isEmpty())
		{
			return;
		}
		
		this.texBinds.forEach(((tex, bound) ->
		{
			GL1.glActiveTexture(GLConst.GL_TEXTURE0 + bound);
			GL1.glBindTexture(tex.getType(), 0);
			
		}));
		
		this.texBinds.clear();
		this.nextTex = 0;
		
	}
	
	public static TextureBinder instance()
	{
		return INSTANCE;
	}
	
}
