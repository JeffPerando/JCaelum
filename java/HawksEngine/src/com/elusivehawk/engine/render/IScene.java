
package com.elusivehawk.engine.render;

import java.util.List;

/**
 * 
 * Provides models for rendering.
 * 
 * @author Elusivehawk
 */
public interface IScene
{
	public List<IModelGroup> getModels();
	
	public ParticleScene getParticles();
	
	public List<ImageScreen> getImages();
	
}
