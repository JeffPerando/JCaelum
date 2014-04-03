
package com.elusivehawk.engine.core;

import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import com.elusivehawk.engine.assets.AssetManager;
import com.elusivehawk.engine.physics.IPhysicsSimulator;
import com.elusivehawk.engine.render.IRenderHUB;
import com.elusivehawk.engine.util.FileHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ScriptedGame extends Game implements ScriptContext
{
	protected final String filename;
	protected final CompiledScript script;
	protected final Object scriptObj;
	
	@SuppressWarnings("unqualified-field-access")
	public ScriptedGame(File file) throws ScriptException, NullPointerException
	{
		filename = file.getName();
		
		script = ((Compilable)CaelumEngine.scripting()).compile(FileHelper.createReader(file));
		scriptObj = script.eval(this);
		
	}
	
	@Override
	protected boolean initiateGame(GameArguments args)
	{
		boolean ret = false;
		
		try
		{
			ret = (boolean)((Invocable)CaelumEngine.scripting()).invokeMethod(this.scriptObj, "initiateGame", args);
			
		}
		catch (Exception e)
		{
			CaelumEngine.log().log(EnumLogType.ERROR, null, e);
			
		}
		
		return ret;
	}
	
	@Override
	protected void updateGame(double delta)
	{
		try
		{
			((Invocable)CaelumEngine.scripting()).invokeMethod(this.scriptObj, "updateGame", delta);
			
		}
		catch (Exception e)
		{
			CaelumEngine.log().log(EnumLogType.ERROR, null, e);
			System.exit("FORMAT-YOUR-SCRIPT-PROPERLY-NOOB".hashCode());
			
		}
		
	}
	
	@Override
	protected void onGameShutdown()
	{
		try
		{
			((Invocable)CaelumEngine.scripting()).invokeMethod(this.scriptObj, "onGameShutdown");
			
		}
		catch (Exception e)
		{
			CaelumEngine.log().log(EnumLogType.ERROR, null, e);
			
		}
		
	}
	
	@Override
	public Object getAttribute(String arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object getAttribute(String arg0, int arg1)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getAttributesScope(String arg0)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public Bindings getBindings(int arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Writer getErrorWriter()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Reader getReader()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Integer> getScopes()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Writer getWriter()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object removeAttribute(String arg0, int arg1)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setAttribute(String arg0, Object arg1, int arg2)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setBindings(Bindings arg0, int arg1)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setErrorWriter(Writer arg0)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setReader(Reader arg0)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setWriter(Writer arg0)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void loadAssets(AssetManager mgr)
	{
		try
		{
			((Invocable)CaelumEngine.scripting()).invokeMethod(this.scriptObj, "loadAssets", mgr);
			
		}
		catch (Exception e)
		{
			CaelumEngine.log().log(EnumLogType.ERROR, null, e);
			
		}
		
	}
	
	@Override
	public void onScreenFlipped(boolean flip)
	{
		try
		{
			((Invocable)CaelumEngine.scripting()).invokeMethod(this.scriptObj, "onScreenFlipped", flip);
			
		}
		catch (Exception e){}
		
	}
	
	@Override
	public IRenderHUB getRenderHUB()
	{
		IRenderHUB ret = null;
		
		try
		{
			ret = ((Invocable)CaelumEngine.scripting()).getInterface(((Invocable)CaelumEngine.scripting()).invokeMethod(this.scriptObj, "getRenderHUB"), IRenderHUB.class);
			
		}
		catch (Exception e)
		{
			CaelumEngine.log().log(EnumLogType.ERROR, null, e);
			
		}
		
		return ret;
	}
	
	@Override
	public IPhysicsSimulator getPhysicsSimulator()
	{
		IPhysicsSimulator ret = null;
		
		try
		{
			ret = ((Invocable)CaelumEngine.scripting()).getInterface(((Invocable)CaelumEngine.scripting()).invokeMethod(this.scriptObj, "getPhysicsSimulator"), IPhysicsSimulator.class);
			
		}
		catch (Exception e)
		{
			CaelumEngine.log().log(EnumLogType.ERROR, null, e);
			
		}
		
		return ret;
	}
	
	@Override
	public String toString()
	{
		return String.format("%s:%s", super.toString(), this.filename);
	}
	
}
