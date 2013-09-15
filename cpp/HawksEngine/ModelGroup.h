/* 
 * File:   ModelGroup.h
 * Author: Elusivehawk
 *
 * Created on September 10, 2013
 */

#ifndef MODELGROUP_H
#define	MODELGROUP_H

using namespace HawksEngine;

class ModelGroup
{
	public:
		Model();
		virtual ~Model();
		virtual int getModelCount();
		virtual int getModel(int index);
		
};

#endif
