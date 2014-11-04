
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.gl.GLEnumSStatus;
import com.elusivehawk.caelum.render.gl.IGL2;
import com.elusivehawk.caelum.render.old.RenderTask;
import com.elusivehawk.util.CompInfo;
import com.elusivehawk.util.EnumLogType;
import com.elusivehawk.util.Logger;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class RTaskUploadShader extends RenderTask
{
	private final Shader shader;
	private final String src;
	
	@SuppressWarnings("unqualified-field-access")
	public RTaskUploadShader(Shader sh, String source)
	{
		super(sh);
		shader = sh;
		src = source;
		
	}
	
	@Override
	protected int finishRTask(RenderContext rcon) throws RenderException
	{
		IGL2 gl2 = rcon.getGL2();
		
		int id = gl2.glCreateShader(this.shader.gltype);
		gl2.glShaderSource(id, this.src);
		gl2.glCompileShader(id);
		
		try
		{
			RenderHelper.checkForGLError(rcon);
			
		}
		catch (Exception e)
		{
			Logger.log().err(e);
			
			return 0;
		}
		
		int status = rcon.getGL2().glGetShaderi(id, GLEnumSStatus.GL_COMPILE_STATUS);
		
		if (status == GLConst.GL_FALSE)
		{
			if (CompInfo.DEBUG)
			{
				Logger.log().log(EnumLogType.WARN, "Cannot compile shader \"%s\"", this.shader.filepath);
				
			}
			
			Logger.log().log(EnumLogType.VERBOSE, "Log: %s", gl2.glGetShaderInfoLog(id, gl2.glGetShaderi(id, GLEnumSStatus.GL_INFO_LOG_LENGTH)));
			
			gl2.glDeleteShader(id);
			
			return 0;
		}
		
		Logger.log().verbose("Successfully compiled shader \"%s\"", this.shader.filepath);
		
		return id;
	}
	
}
