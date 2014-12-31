
package com.elusivehawk.caelum.meta;

import java.io.IOException;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import com.elusivehawk.caelum.DisplaySettings;
import com.elusivehawk.caelum.lwjgl.LWJGLDisplayImpl;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.ShutdownHelper;

/**
 * 
 * Test log:
 * <p>
 * LWJGLDisplayImpl testing.<br>
 * Tokenizer testing again.<br>
 * MathHelper testing.<br>
 * Timer testing.<br>
 * File filterer.<br>
 * String index testing.<br>
 * Refactor helper.<br>
 * Tokenizer testing.<br>
 * RHINO benchmarking.<br>
 * Comparing vs. Instanceof check.<br>
 * Simple vs. Array List.<br>
 * Testing matrix stuff.<br>
 * Testing sound decoding.<br>
 * Testing file byte reading.<br>
 * Testing "++".<br>
 * Using PrintStream.<br>
 * More buffer testing.<br>
 * List iterating.<br>
 * Buffer.put(int, int) testing.<br>
 * Instanceof speed benchmarking.<br>
 * 
 * @author Elusivehawk
 */
public class BenchmarkTest
{
	public static final int TESTS = 10;
	
	public static void main(String[] args)
	{
		Logger.verbose("Beginning bench testing...");
		
		Thread thread = new Thread((() ->
		{
			if (GLFW.glfwInit() == 0)
			{
				Logger.warn("NOPE!");
				return;
			}
			
			LWJGLDisplayImpl display = new LWJGLDisplayImpl();
			
			try
			{
				display.createDisplay(new DisplaySettings());
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
				
			}
			
			GL11.glViewport(0, 0, display.getWidth(), display.getHeight());
			
			for (int c = 0; c < 1000; c++)
			{
				display.preRenderDisplay();
				
				if (display.isCloseRequested())
				{
					try
					{
						display.close();
						
					}
					catch (Throwable e)
					{
						Logger.err(e);
						
					}
					
					ShutdownHelper.exit(0);
					
				}
				
				GL11.glClear(GLConst.GL_COLOR_BUFFER_BIT | GLConst.GL_DEPTH_BUFFER_BIT | GLConst.GL_STENCIL_BUFFER_BIT);
				
				GL11.glBegin(GL11.GL_TRIANGLES);
				
				GL11.glVertex2f(0.2f, 0.2f);
				GL11.glVertex2f(0.2f, 0.5f);
				GL11.glVertex2f(0.5f, 0.2f);
				
				GL11.glEnd();
				
				display.updateDisplay();
				
				try
				{
					Thread.sleep(1L);
				}
				catch (InterruptedException e){}
				
			}
			
			try
			{
				display.close();
			}
			catch (IOException e){}
			
		}));
		
		thread.start();
		
		Logger.verbose("Th-th-th-th-That's all, folks!");
		
	}
	
}
