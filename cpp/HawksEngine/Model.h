/* 
 * File:   Model.h
 * Author: Elusivehawk
 *
 * Created on September 10, 2013
 */

#ifndef MODEL_H
#define	MODEL_H

class Model
{
	public:
		Model();
		virtual ~Model();

	protected:
		virtual void loadData();
		const void color();
		const void texOff(float x, float y);
		const void vertex(float x, float y, float z, float w = 1);

};

#endif
