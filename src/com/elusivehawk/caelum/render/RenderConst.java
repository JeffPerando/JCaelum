
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.render.glsl.GLSLEnumInType;
import com.elusivehawk.caelum.render.glsl.GLSLEnumShaderType;
import com.elusivehawk.caelum.render.glsl.ShaderParameter;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class RenderConst
{
	private RenderConst(){}
	
	public static final int MATERIAL_CAP = 8;
	public static final int SHADER_COUNT = GLSLEnumShaderType.values().length;
	public static final int RECURSIVE_LIMIT = 8;
	
	public static final int VERTEX_I = 0;
	public static final int TEXCOORD_I = 1;
	public static final int NORMAL_I = 2;
	public static final int SCALE_I = 3;
	public static final int ROTATE_I = 4;
	public static final int TRANS_I = 5;
	public static final int MAT_I = 6;
	
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
	
}
