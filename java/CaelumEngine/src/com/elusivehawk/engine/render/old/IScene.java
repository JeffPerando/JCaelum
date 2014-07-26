
package com.elusivehawk.engine.render.old;

import com.elusivehawk.util.storage.SyncList;

/**
 * 
 * Provides models, images, and particles for rendering.
 * 
 * @author Elusivehawk
 */
@Deprecated
public interface IScene
{
	public SyncList<IModelGroup> getModels();
	
	//public ParticleScene getParticles();
	
	//public SyncList<ImageScreen> getImages();
	
}
