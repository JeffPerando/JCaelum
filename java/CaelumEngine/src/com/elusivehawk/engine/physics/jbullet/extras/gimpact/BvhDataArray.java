/*
 * Java port of Bullet (c) 2008 Martin Dvorak <jezek2@advel.cz>
 *
 * This source file is part of GIMPACT Library.
 *
 * For the latest info, see http://gimpact.sourceforge.net/
 *
 * Copyright (c) 2007 Francisco Leon Najera. C.C. 80087371.
 * email: projectileman@yahoo.com
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from
 * the use of this software.
 * 
 * Permission is granted to anyone to use this software for any purpose, 
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 * 
 * 1. The origin of this software must not be misrepresented; you must not
 *    claim that you wrote the original software. If you use this software
 *    in a product, an acknowledgment in the product documentation would be
 *    appreciated but is not required.
 * 2. Altered source versions must be plainly marked as such, and must not be
 *    misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 */

package com.elusivehawk.engine.physics.jbullet.extras.gimpact;

import com.elusivehawk.engine.physics.jbullet.extras.gimpact.BoxCollision.AABB;
import com.elusivehawk.util.math.Vector;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * 
 * @author jezek2
 */
class BvhDataArray
{
	private int size = 0;
	
	float[] bound = new float[0];
	int[] data = new int[0];
	
	public int size()
	{
		return this.size;
	}
	
	public void resize(int newSize)
	{
		float[] newBound = new float[newSize * 6];
		int[] newData = new int[newSize];
		
		System.arraycopy(this.bound, 0, newBound, 0, this.size * 6);
		System.arraycopy(this.data, 0, newData, 0, this.size);
		
		this.bound = newBound;
		this.data = newData;
		
		this.size = newSize;
	}
	
	public void swap(int idx1, int idx2)
	{
		int pos1 = idx1 * 6;
		int pos2 = idx2 * 6;
		
		float b0 = this.bound[pos1 + 0];
		float b1 = this.bound[pos1 + 1];
		float b2 = this.bound[pos1 + 2];
		float b3 = this.bound[pos1 + 3];
		float b4 = this.bound[pos1 + 4];
		float b5 = this.bound[pos1 + 5];
		int d = this.data[idx1];
		
		this.bound[pos1 + 0] = this.bound[pos2 + 0];
		this.bound[pos1 + 1] = this.bound[pos2 + 1];
		this.bound[pos1 + 2] = this.bound[pos2 + 2];
		this.bound[pos1 + 3] = this.bound[pos2 + 3];
		this.bound[pos1 + 4] = this.bound[pos2 + 4];
		this.bound[pos1 + 5] = this.bound[pos2 + 5];
		this.data[idx1] = this.data[idx2];
		
		this.bound[pos2 + 0] = b0;
		this.bound[pos2 + 1] = b1;
		this.bound[pos2 + 2] = b2;
		this.bound[pos2 + 3] = b3;
		this.bound[pos2 + 4] = b4;
		this.bound[pos2 + 5] = b5;
		this.data[idx2] = d;
	}
	
	public AABB getBound(int idx, AABB out)
	{
		int pos = idx * 6;
		out.min.set(this.bound[pos + 0], this.bound[pos + 1],
				this.bound[pos + 2]);
		out.max.set(this.bound[pos + 3], this.bound[pos + 4],
				this.bound[pos + 5]);
		return out;
	}
	
	public Vector getBoundMin(int idx, Vector out)
	{
		int pos = idx * 6;
		out.set(this.bound[pos + 0], this.bound[pos + 1], this.bound[pos + 2]);
		return out;
	}
	
	public Vector getBoundMax(int idx, Vector out)
	{
		int pos = idx * 6;
		out.set(this.bound[pos + 3], this.bound[pos + 4], this.bound[pos + 5]);
		return out;
	}
	
	public void setBound(int idx, AABB aabb)
	{
		int pos = idx * 6;
		
		for (int c = 0; c < 3; c++)
		{
			this.bound[pos + c] = aabb.min.get(c);
			this.bound[pos + (c + 3)] = aabb.max.get(c);
			
		}
		/*
		 * bound[pos+0] = aabb.min.x;
		 * bound[pos+1] = aabb.min.y;
		 * bound[pos+2] = aabb.min.z;
		 * bound[pos+3] = aabb.max.x;
		 * bound[pos+4] = aabb.max.y;
		 * bound[pos+5] = aabb.max.z;
		 */
	}
	
	public int getData(int idx)
	{
		return this.data[idx];
	}
	
	public void setData(int idx, int value)
	{
		this.data[idx] = value;
	}
	
}
