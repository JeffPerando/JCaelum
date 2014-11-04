
package com.elusivehawk.caelum.render;

import java.io.DataInputStream;
import com.elusivehawk.caelum.CaelumEngine;
import com.elusivehawk.caelum.assets.Asset;
import com.elusivehawk.caelum.assets.EnumAssetType;
import com.elusivehawk.caelum.render.gl.GLEnumShader;
import com.elusivehawk.util.string.StringHelper;
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
	
	protected volatile int glId = 0;
	
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
	protected boolean readAsset(DataInputStream in) throws Throwable
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
	protected void finishGPULoading(RenderContext rcon){}
	
	@Override
	public void onExistingAssetFound(Asset a)
	{
		super.onExistingAssetFound(a);
		
		if (a instanceof Shader && ((Shader)a).isLoaded())
		{
			this.glId = ((Shader)a).glId;
			
		}
		
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
