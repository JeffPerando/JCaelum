/* 
 * File:   Scene.h
 * Author: Elusivehawk
 *
 * Created on September 10, 2013
 */

#ifndef SCENE_H
#define	SCENE_H

using namespace HawksEngine;

class Scene
{
	public:
		Scene();
		virtual ~Scene();
		virtual std::list<ModelGroup> getModels();
		
};
	
#endif

