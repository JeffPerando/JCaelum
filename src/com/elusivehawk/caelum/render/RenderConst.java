
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.render.gl.GLEnumShader;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class RenderConst
{
	private RenderConst(){}
	
	public static final int FLOATS_PER_IMG = 30;
	
	public static final int MATERIAL_CAP = 16;
	public static final int SHADER_COUNT = GLEnumShader.values().length;
	
	public static final int VERTEX_I = 0;
	public static final int TEXCOORD_I = 1;
	public static final int NORMAL_I = 2;
	
	public static final int SCALE_I = 3;
	public static final int ROTATE_I = 4;
	public static final int TRANS_I = 5;
	
	public static final int MAT_I = 6;
	
	public static final int RECURSIVE_LIMIT = 8;
	
}
