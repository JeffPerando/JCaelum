/*
 * Java port of Bullet (c) 2008 Martin Dvorak <jezek2@advel.cz>
 *
 * Bullet Continuous Collision Detection and Physics Library
 * Copyright (c) 2003-2008 Erwin Coumans  http://www.bulletphysics.com/
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

package com.elusivehawk.engine.physics.jbullet.collision.shapes;

import static com.elusivehawk.util.math.MathConst.X;
import static com.elusivehawk.util.math.MathConst.Y;
import static com.elusivehawk.util.math.MathConst.Z;
import com.elusivehawk.engine.physics.jbullet.linearmath.VectorUtil;
import com.elusivehawk.util.math.Vector;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * Allows accessing vertex data.
 * 
 * @author jezek2
 */
public abstract class VertexData {

	public abstract int getVertexCount();

	public abstract int getIndexCount();

	public abstract <T extends Vector> T getVertex(int idx, T out);

	public abstract void setVertex(int idx, float x, float y, float z);

	public void setVertex(int idx, Vector t) {
		setVertex(idx, t.get(X), t.get(Y), t.get(Z));
	}

	public abstract int getIndex(int idx);

	public void getTriangle(int firstIndex, Vector scale, Vector[] triangle) {
		for (int i=0; i<3; i++) {
			getVertex(getIndex(firstIndex+i), triangle[i]);
			VectorUtil.mul(triangle[i], triangle[i], scale);
		}
	}
	
}
