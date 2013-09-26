
package elusivehawk.engine.render;

import java.util.List;

/**
 * 
 * Provides models for rendering in the {@link RenderEngine}.
 * 
 * @author Elusivehawk
 */
public interface IScene
{
	public List<IModelGroup> getModels();
	
	public ParticleScene getParticles();
	
	public ImageScreen getImages();
	
}
