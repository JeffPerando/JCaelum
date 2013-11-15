
package com.elusivehawk.engine.render;

import java.util.Collection;

/**
 * 
 * Provides models, images, and particles for rendering.
 * 
 * @author Elusivehawk
 */
public interface IScene
{
	public Collection<IModelGroup> getModels();
	
	public ParticleScene getParticles();
	
	public Collection<ImageScreen> getImages();
	
}
