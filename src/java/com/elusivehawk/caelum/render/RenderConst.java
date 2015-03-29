
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.render.gl.GL1;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.glsl.GLSLEnumInType;
import com.elusivehawk.caelum.render.glsl.GLSLEnumShaderType;
import com.elusivehawk.caelum.render.glsl.ShaderAsset;
import com.elusivehawk.caelum.render.glsl.ShaderParameter;
import com.elusivehawk.caelum.render.glsl.Shaders;
import com.elusivehawk.caelum.render.tex.Color;
import com.elusivehawk.caelum.render.tex.ITexture;
import com.elusivehawk.caelum.render.tex.PixelGrid;
import com.elusivehawk.caelum.render.tex.Texture;
import com.elusivehawk.util.math.MathHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class RenderConst
{
	private RenderConst(){}
	
	public static final GLSLEnumShaderType[] SHADER_TYPES = GLSLEnumShaderType.values();
	public static final int SHADER_COUNT = SHADER_TYPES.length;
	public static final int RECURSIVE_LIMIT = 8;
	
	public static final int VERTEX = 0;
	public static final int TEXCOORD = 1;
	public static final int NORMAL = 2;
	public static final int SCALE = 3;
	public static final int ROTATE = 4;
	public static final int TRANS = 5;
	public static final int MAT = 6;
	
	public static final String
				GL_VERSION = GL1.glGetString(GLConst.GL_VERSION),
				GL_VENDOR = GL1.glGetString(GLConst.GL_VENDOR),
				GL_RENDERER = GL1.glGetString(GLConst.GL_RENDERER);
	public static final int GL_MAX_TEX_COUNT = GL1.glGetInteger(GLConst.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS);
	
	public static final ITexture NO_TEX = new Texture(new PixelGrid(16, 16, ((grid) ->
	{
		for (int x = 0; x < grid.getWidth(); x++)
		{
			for (int y = 0; y < grid.getHeight(); y++)
			{
				grid.setPixel(x, y, MathHelper.isOdd(x) && MathHelper.isOdd(y) ? Color.PINK : Color.BLACK);
				
			}
			
		}
		
	})).scale(2));
	
	public static final Shaders SHADERS_3D = new Shaders(((shaders) ->
	{
		for (GLSLEnumShaderType type : SHADER_TYPES)
		{
			ShaderAsset sh = new ShaderAsset(String.format("/res/shaders/%s.glsl", type), type);
			
			sh.readLater();
			
			shaders.addShader(sh);
			
		}
		
	}));
	
	public static final Shaders SHADERS_2D = new Shaders(((shaders) ->
	{
		for (GLSLEnumShaderType type : SHADER_TYPES)
		{
			ShaderAsset sh = new ShaderAsset(String.format("/res/shaders/%s2d.glsl", type), type);
			
			sh.readLater();
			
			shaders.addShader(sh);
			
		}
		
	}));
	
	public static final ShaderParameter[] MATERIAL_PARAMS =
		{
			new ShaderParameter("sampler2D", "texes[MATERIAL_CAP]"),
			new ShaderParameter("sampler2D", "rTexes[MATERIAL_CAP]"),
			new ShaderParameter("sampler2D", "gTexes[MATERIAL_CAP]"),
			new ShaderParameter("vec3", "filters[MATERIAL_CAP]"),
			new ShaderParameter("float", "shines[MATERIAL_CAP]"),
			new ShaderParameter("int", "matCount"),
			new ShaderParameter(GLSLEnumInType.IN, "vec2", "frag_color"),
			new ShaderParameter(GLSLEnumInType.IN, "float", "frag_mat"),
			new ShaderParameter(GLSLEnumInType.OUT, "vec4", "out_color")
		};
	
	public static final Icon SKYBOX_TOP = new Icon(0.25f, 1 / 3f, 0.5f, 2 / 3f);
	public static final Icon SKYBOX_BOTTOM = new Icon(0.75f, 1 / 3f, 1f, 2 / 3f);
	public static final Icon SKYBOX_FRONT = new Icon(0.25f, 0f, 0.5f, 1 / 3f);
	public static final Icon SKYBOX_BACK = new Icon(0.25f, 2 / 3f, 0.5f, 1f);
	public static final Icon SKYBOX_LEFT = new Icon(0f, 1 / 3f, 0.25f, 2 / 3f);
	public static final Icon SKYBOX_RIGHT = new Icon(0.5f, 1 / 3f, 0.75f, 2 / 3f);
	
}
