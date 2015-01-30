
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
	
	public static final Icon SKYBOX_TOP = new Icon(0.25f, 1 / 3f, 0.5f, 2 / 3f);
	public static final Icon SKYBOX_BOTTOM = new Icon(0.75f, 1 / 3f, 1f, 2 / 3f);
	public static final Icon SKYBOX_FRONT = new Icon(0.25f, 0f, 0.5f, 1 / 3f);
	public static final Icon SKYBOX_BACK = new Icon(0.25f, 2 / 3f, 0.5f, 1f);
	public static final Icon SKYBOX_LEFT = new Icon(0f, 1 / 3f, 0.25f, 2 / 3f);
	public static final Icon SKYBOX_RIGHT = new Icon(0.5f, 1 / 3f, 0.75f, 2 / 3f);
	
}
