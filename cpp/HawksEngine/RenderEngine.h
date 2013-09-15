/* 
 * File:   RenderEngine.h
 * Author: Elusivehawk
 *
 * Created on September 10, 2013
 */

#ifndef RENDERENGINE_H
#define	RENDERENGINE_H

#include <gl/glew.h>
#include <gl/gl.h>
#include <glm/glm.hpp>
#include "Scene.h"
#include "ModelGroup.h"

using namespace HawksEngine;

void renderScene(Scene s, bool render2D = true, bool render3D = true)
{
	glClear(GL_COLOR_BUFFER_BIT);
	
	if (render3D)
	{
		render3DScene(s);
		
	}
	
	if (render2D)
	{
		
		
	}
	
}

void render3DScene(Scene s)
{
	for (ModelGroup m : s.getModels())
	{
		for (int c = 0; c < m.getModelCount(); c++)
		{
			int modelId = m.getModel(c);
			
		}
		
	}
	
}

#endif

