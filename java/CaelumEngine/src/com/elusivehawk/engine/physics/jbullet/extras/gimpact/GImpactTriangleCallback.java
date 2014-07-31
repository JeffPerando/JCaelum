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

import com.elusivehawk.engine.physics.jbullet.collision.dispatch.CollisionObject;
import com.elusivehawk.engine.physics.jbullet.collision.shapes.TriangleCallback;
import com.elusivehawk.util.math.Vector;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 *
 * @author jezek2
 */
class GImpactTriangleCallback extends TriangleCallback {

	public GImpactCollisionAlgorithm algorithm;
	public CollisionObject body0;
	public CollisionObject body1;
	public GImpactShapeInterface gimpactshape0;
	public boolean swapped;
	public float margin;
	
	@Override
	public void processTriangle(Vector[] triangle, int partId, int triangleIndex) {
		TriangleShapeEx tri1 = new TriangleShapeEx(triangle[0], triangle[1], triangle[2]);
		tri1.setMargin(this.margin);
		if (this.swapped) {
			this.algorithm.setPart0(partId);
			this.algorithm.setFace0(triangleIndex);
		}
		else {
			this.algorithm.setPart1(partId);
			this.algorithm.setFace1(triangleIndex);
		}
		this.algorithm.gimpact_vs_shape(this.body0, this.body1, this.gimpactshape0, tri1, this.swapped);
	}

}
