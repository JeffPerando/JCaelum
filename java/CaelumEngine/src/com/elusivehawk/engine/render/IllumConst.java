
package com.elusivehawk.engine.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class IllumConst
{
	public static final short COLOR =			0b1000000000;
	public static final short AMBIENCE =		0b0100000000;
	public static final short HIGHLIGHTS =		0b0010000000;
	public static final short REFLECTION =		0b0001000000;
	public static final short R_FRESNEL =		REFLECTION | 0b0000100000;
	public static final short R_RAYTRACE =		REFLECTION | 0b0000010000;
	public static final short TRANSPARENCY =	0b0000001000;
	public static final short T_GLASS =			TRANSPARENCY | 0b0000000100;
	public static final short T_REFRACTION =	TRANSPARENCY | 0b0000000010;
	public static final short CSIS =			0b0000000001;
	
	public static final short[] ILLUM_ENUMS = {
		COLOR,
		COLOR | AMBIENCE,
		HIGHLIGHTS,
		R_RAYTRACE,
		R_RAYTRACE | T_GLASS,
		R_FRESNEL | R_RAYTRACE,
		R_RAYTRACE | T_REFRACTION,
		R_FRESNEL | R_RAYTRACE | T_REFRACTION,
		REFLECTION,
		REFLECTION | T_GLASS,
		CSIS};
	
	private IllumConst(){}
	
}
