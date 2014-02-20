
package com.elusivehawk.engine.render;

/**
 * Used to distinguish which stage of rendering is currently going underway.
 * <p>
 * Not to be confused with {@link EnumRenderMode}, which is used to distinguish 2D from 3D.
 * 
 * @author Elusivehawk
 */
public enum EnumRenderStage
{
	PRERENDER,
	RENDER,
	POSTEFFECTS;
	
}
