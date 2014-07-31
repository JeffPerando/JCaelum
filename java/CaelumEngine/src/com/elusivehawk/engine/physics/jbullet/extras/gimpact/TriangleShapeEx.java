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

import static com.elusivehawk.util.math.MathConst.*;
import com.elusivehawk.engine.physics.jbullet.collision.shapes.TriangleShape;
import com.elusivehawk.engine.physics.jbullet.extras.gimpact.BoxCollision.AABB;
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import com.elusivehawk.util.math.Vector;
import cz.advel.stack.Stack;

/**
 *
 * @author jezek2
 */
public class TriangleShapeEx extends TriangleShape {

	public TriangleShapeEx() {
		super();
	}

	public TriangleShapeEx(Vector p0, Vector p1, Vector p2) {
		super(p0, p1, p2);
	}

	@Override
	public void getAabb(Transform t, Vector aabbMin, Vector aabbMax) {
		Vector tv0 = Stack.alloc(this.vertices1[0]);
		t.transform(tv0);
		Vector tv1 = Stack.alloc(this.vertices1[1]);
		t.transform(tv1);
		Vector tv2 = Stack.alloc(this.vertices1[2]);
		t.transform(tv2);

		AABB trianglebox = Stack.alloc(AABB.class);
		trianglebox.init(tv0,tv1,tv2,this.collisionMargin);
		
		aabbMin.set(trianglebox.min);
		aabbMax.set(trianglebox.max);
	}

	public void applyTransform(Transform t) {
		t.transform(this.vertices1[0]);
		t.transform(this.vertices1[1]);
		t.transform(this.vertices1[2]);
	}

	public void buildTriPlane(Vector plane) {
		Vector tmp1 = Stack.alloc(new Vector(3));
		Vector tmp2 = Stack.alloc(new Vector(3));

		Vector normal = Stack.alloc(new Vector(3));
		tmp1.sub(this.vertices1[1], this.vertices1[0]);
		tmp2.sub(this.vertices1[2], this.vertices1[0]);
		normal.cross(tmp1, tmp2);
		normal.normalize();

		plane.set(normal.get(X), normal.get(Y), normal.get(Z), this.vertices1[0].dot(normal));
	}

	public boolean overlap_test_conservative(TriangleShapeEx other) {
		float total_margin = getMargin() + other.getMargin();

		Vector plane0 = Stack.alloc(new Vector(4));
		buildTriPlane(plane0);
		Vector plane1 = Stack.alloc(new Vector(4));
		other.buildTriPlane(plane1);

		// classify points on other triangle
		float dis0 = ClipPolygon.distance_point_plane(plane0, other.vertices1[0]) - total_margin;

		float dis1 = ClipPolygon.distance_point_plane(plane0, other.vertices1[1]) - total_margin;

		float dis2 = ClipPolygon.distance_point_plane(plane0, other.vertices1[2]) - total_margin;

		if (dis0 > 0.0f && dis1 > 0.0f && dis2 > 0.0f) {
			return false; // classify points on this triangle
		}
		dis0 = ClipPolygon.distance_point_plane(plane1, this.vertices1[0]) - total_margin;

		dis1 = ClipPolygon.distance_point_plane(plane1, this.vertices1[1]) - total_margin;

		dis2 = ClipPolygon.distance_point_plane(plane1, this.vertices1[2]) - total_margin;

		if (dis0 > 0.0f && dis1 > 0.0f && dis2 > 0.0f) {
			return false;
		}
		return true;
	}
	
}
