
package com.elusivehawk.engine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.engine.assets.AssetManager;
import com.elusivehawk.engine.input.Key;
import com.elusivehawk.engine.input.Keyboard;
import com.elusivehawk.engine.input.Mouse;
import com.elusivehawk.engine.prefab.SimpleRenderer;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.Shader;
import com.elusivehawk.engine.render.opengl.GLEnumBufferTarget;
import com.elusivehawk.engine.render.opengl.GLEnumDataType;
import com.elusivehawk.engine.render.opengl.GLEnumDataUsage;
import com.elusivehawk.engine.render.opengl.GLEnumPolyType;
import com.elusivehawk.engine.render.opengl.GLEnumShader;
import com.elusivehawk.engine.render.opengl.VertexBuffer;
import com.elusivehawk.util.BufferHelper;
import com.elusivehawk.util.EnumLogType;
import com.elusivehawk.util.Internal;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.ShutdownHelper;
import com.elusivehawk.util.Version;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Internal
public final class ExampleGame extends Game
{
	public static final Version VERSION = new Version(1, 0, 0);
	
	public final FloatBuffer square = BufferHelper.makeFloatBuffer(0, 0, 1, 0, 0, 1, 1, 1);
	public final IntBuffer square_ind = BufferHelper.makeIntBuffer(0, 1, 2, 3);
	
	private SimpleRenderer renderer = null;
	
	public ExampleGame()
	{
		super("Example Game");
		
	}
	
	@Override
	public Version getGameVersion()
	{
		return VERSION;
	}
	
	@Override
	protected void initiateGame(GameArguments args, AssetManager assets)
	{
		CaelumEngine.addInputListener(Keyboard.class, ((in) ->
		{
			Keyboard kb = (Keyboard)in;
			
			for (Key key : kb.getPushedKeys())
			{
				Logger.log().log(EnumLogType.VERBOSE, "Key down: %s", key);
				
			}
			
			for (Key key : kb.getOldPushedKeys())
			{
				Logger.log().log(EnumLogType.VERBOSE, "Key up: %s", key);
				
			}
			
		}));
		
		CaelumEngine.addInputListener(Mouse.class, ((in) ->
		{
			Mouse m = (Mouse)in;
			
			Logger.log().log(EnumLogType.VERBOSE, "Mouse pos: %s", m.getMousePos());
			
		}));
		
		this.renderer = new SimpleRenderer(4, GLEnumPolyType.GL_TRIANGLE_STRIP, ((p) ->
		{
			p.attachShader(new Shader("/res/shaders/vertex2d.glsl", GLEnumShader.VERTEX));
			
			p.attachVBO(new VertexBuffer(GLEnumBufferTarget.GL_ARRAY_BUFFER, GLEnumDataUsage.GL_STATIC_DRAW, GLEnumDataType.GL_FLOAT, this.square), 0);
			p.attachVBO(new VertexBuffer(GLEnumBufferTarget.GL_ELEMENT_ARRAY_BUFFER, GLEnumDataUsage.GL_STATIC_DRAW, GLEnumDataType.GL_INT, this.square_ind));
			
		}));
		
	}
	
	@Override
	protected void tick(double delta, Object... extra) throws Throwable
	{
		//CaelumEngine.log().log(EnumLogType.INFO, "Test: %s", delta);
		
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void render(RenderContext rcon, double delta)
	{
		try
		{
			this.renderer.render(rcon, delta);
			
		}
		catch (Exception e)
		{
			Logger.log().err(e);
			ShutdownHelper.exit("CANNOT-RENDER");
		}
		
	}
	
	@Override
	protected void onGameShutdown()
	{
		// TODO Auto-generated method stub
		
	}
	
}
