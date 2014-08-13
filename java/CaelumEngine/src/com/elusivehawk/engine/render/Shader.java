
package com.elusivehawk.engine.render;

import java.io.File;
import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.engine.assets.GraphicAsset;
import com.elusivehawk.engine.render.opengl.GLEnumShader;
import com.elusivehawk.util.StringHelper;
import com.elusivehawk.util.task.Task;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Shader extends GraphicAsset
{
	public final GLEnumShader gltype;
	
	protected int glId = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public Shader(String filepath, GLEnumShader type)
	{
		super(filepath);
		
		gltype = type;
		
	}
	
	@Override
	protected boolean readAsset(File asset)
	{
		String src = RenderHelper.formatShaderSource(StringHelper.readToOneLine(asset), asset.getParentFile());
		
		if (src != null)
		{
			CaelumEngine.scheduleRenderTask(new RTaskUploadShader(this, this.gltype, src));
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public void onTaskComplete(Task task)
	{
		this.glId = ((RTaskUploadShader)task).getGLId();
		
		if (this.glId != -1)//TODO Check to see if this is the proper "blank" value; Might be 0...
		{
			this.loaded = true;
			
		}
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		rcon.getGL2().glDeleteShader(this.glId);
		
	}
	
	public int getGLId()
	{
		return this.glId;
	}
	
}
