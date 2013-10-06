
package com.elusivehawk.engine.render;

import java.util.List;

/**
 * 
 * Provides models for rendering in the {@link RenderEngine}.
 * 
 * @author Elusivehawk
 */
@Deprecated
public interface IScene
{
	public List<IModelGroup> getModels();
	
	public ParticleScene getParticles();
	
	public ImageScreen getImages();
	
}
