
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.util.SyncList;

/**
 * 
 * Provides models, images, and particles for rendering.
 * 
 * @author Elusivehawk
 */
public interface IScene
{
	public SyncList<IModelGroup> getModels();
	
	//public ParticleScene getParticles();
	
	public LightMap getLightMap();
	
	//public SyncList<ImageScreen> getImages();
	
}
