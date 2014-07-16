
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.three.IModelGroup;
import com.elusivehawk.engine.util.storage.SyncList;

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
	
	//public SyncList<ImageScreen> getImages();
	
}
