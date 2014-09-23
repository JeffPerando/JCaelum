
package com.elusivehawk.engine.render;

import java.io.BufferedInputStream;
import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.engine.assets.EnumAssetType;
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
		super(filepath, EnumAssetType.SHADER);
		
		gltype = type;
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		rcon.getGL2().glDeleteShader(this);
		
	}
	
	@Override
	protected boolean readAsset(BufferedInputStream in) throws Throwable
	{
		String src = StringHelper.readToOneLine(in);
		
		if (src != null)
		{
			CaelumEngine.scheduleRenderTask(new RTaskUploadShader(this, src));
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public void onTaskComplete(Task task)
	{
		super.onTaskComplete(task);
		
		this.glId = ((RTaskUploadShader)task).getGLId();
		
		if (this.glId != 0)
		{
			this.loaded = true;
			
		}
		
	}
	
	public int getGLId()
	{
		return this.glId;
	}
	
}
