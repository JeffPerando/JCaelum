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

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 *
 * @author jezek2
 */
class BvhTreeNodeArray {

	private int size = 0;
	
	private float[] bound = new float[0];
	private int[] escapeIndexOrDataIndex = new int[0];

	public void clear() {
		this.size = 0;
	}

	public void resize(int newSize) {
		float[] newBound = new float[newSize*6];
		int[] newEIODI = new int[newSize];
		
		System.arraycopy(this.bound, 0, newBound, 0, this.size*6);
		System.arraycopy(this.escapeIndexOrDataIndex, 0, newEIODI, 0, this.size);
		
		this.bound = newBound;
		this.escapeIndexOrDataIndex = newEIODI;
		
		this.size = newSize;
	}
	
	public void set(int destIdx, BvhTreeNodeArray array, int srcIdx) {
		int dpos = destIdx*6;
		int spos = srcIdx*6;
		
		this.bound[dpos+0] = array.bound[spos+0];
		this.bound[dpos+1] = array.bound[spos+1];
		this.bound[dpos+2] = array.bound[spos+2];
		this.bound[dpos+3] = array.bound[spos+3];
		this.bound[dpos+4] = array.bound[spos+4];
		this.bound[dpos+5] = array.bound[spos+5];
		this.escapeIndexOrDataIndex[destIdx] = array.escapeIndexOrDataIndex[srcIdx];
	}

	public void set(int destIdx, BvhDataArray array, int srcIdx) {
		int dpos = destIdx*6;
		int spos = srcIdx*6;
		
		this.bound[dpos+0] = array.bound[spos+0];
		this.bound[dpos+1] = array.bound[spos+1];
		this.bound[dpos+2] = array.bound[spos+2];
		this.bound[dpos+3] = array.bound[spos+3];
		this.bound[dpos+4] = array.bound[spos+4];
		this.bound[dpos+5] = array.bound[spos+5];
		this.escapeIndexOrDataIndex[destIdx] = array.data[srcIdx];
	}
	
	public AABB getBound(int nodeIndex, AABB out) {
		int pos = nodeIndex*6;
		out.min.set(this.bound[pos+0], this.bound[pos+1], this.bound[pos+2]);
		out.max.set(this.bound[pos+3], this.bound[pos+4], this.bound[pos+5]);
		return out;
	}
	
	public void setBound(int nodeIndex, AABB aabb) {
		int pos = nodeIndex*6;
		
		for (int c = 0; c < 3; c++)
		{
			this.bound[pos + c] = aabb.min.get(c);
			this.bound[pos + (c + 3)] = aabb.max.get(c);
			
		}
		
	}
	
	public boolean isLeafNode(int nodeIndex) {
		// skipindex is negative (internal node), triangleindex >=0 (leafnode)
		return (this.escapeIndexOrDataIndex[nodeIndex] >= 0);
	}

	public int getEscapeIndex(int nodeIndex) {
		//btAssert(m_escapeIndexOrDataIndex < 0);
		return -this.escapeIndexOrDataIndex[nodeIndex];
	}

	public void setEscapeIndex(int nodeIndex, int index) {
		this.escapeIndexOrDataIndex[nodeIndex] = -index;
	}

	public int getDataIndex(int nodeIndex) {
		//btAssert(m_escapeIndexOrDataIndex >= 0);
		return this.escapeIndexOrDataIndex[nodeIndex];
	}

	public void setDataIndex(int nodeIndex, int index) {
		this.escapeIndexOrDataIndex[nodeIndex] = index;
	}
	
}
