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
import com.elusivehawk.engine.physics.jbullet.linearmath.Transform;
import com.elusivehawk.engine.physics.jbullet.util.ObjectArrayList;
import com.elusivehawk.util.math.Vector;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 *
 * @author jezek2
 */
public class PrimitiveTriangle {

	private final ObjectArrayList<Vector> tmpVecList1 = new ObjectArrayList<Vector>(TriangleContact.MAX_TRI_CLIPPING);
	private final ObjectArrayList<Vector> tmpVecList2 = new ObjectArrayList<Vector>(TriangleContact.MAX_TRI_CLIPPING);
	private final ObjectArrayList<Vector> tmpVecList3 = new ObjectArrayList<Vector>(TriangleContact.MAX_TRI_CLIPPING);
	
	{
		for (int i=0; i<TriangleContact.MAX_TRI_CLIPPING; i++) {
			this.tmpVecList1.add(new Vector());
			this.tmpVecList2.add(new Vector());
			this.tmpVecList3.add(new Vector());
		}
	}
	
	public final Vector[] vertices = new Vector[3];
	public final Vector plane = new Vector();
	public float margin = 0.01f;

	@SuppressWarnings("unqualified-field-access")
	public PrimitiveTriangle() {
		for (int i=0; i<vertices.length; i++) {
			vertices[i] = new Vector();
		}
	}
	
	public void set(PrimitiveTriangle tri) {
		throw new UnsupportedOperationException();
	}
	
	public void buildTriPlane() {
		Vector tmp1 = Stack.alloc(new Vector(3));
		Vector tmp2 = Stack.alloc(new Vector(3));

		Vector normal = Stack.alloc(new Vector(3));
		tmp1.sub(this.vertices[1], this.vertices[0]);
		tmp2.sub(this.vertices[2], this.vertices[0]);
		normal.cross(tmp1, tmp2);
		normal.normalize();

		this.plane.set(normal.get(X), normal.get(Y), normal.get(Z), this.vertices[0].dot(normal));
	}

	/**
	 * Test if triangles could collide.
	 */
	public boolean overlap_test_conservative(PrimitiveTriangle other) {
		float total_margin = this.margin + other.margin;
		// classify points on other triangle
		float dis0 = ClipPolygon.distance_point_plane(this.plane, other.vertices[0]) - total_margin;

		float dis1 = ClipPolygon.distance_point_plane(this.plane, other.vertices[1]) - total_margin;

		float dis2 = ClipPolygon.distance_point_plane(this.plane, other.vertices[2]) - total_margin;

		if (dis0 > 0.0f && dis1 > 0.0f && dis2 > 0.0f) {
			return false; // classify points on this triangle
		}
		
		dis0 = ClipPolygon.distance_point_plane(other.plane, this.vertices[0]) - total_margin;

		dis1 = ClipPolygon.distance_point_plane(other.plane, this.vertices[1]) - total_margin;

		dis2 = ClipPolygon.distance_point_plane(other.plane, this.vertices[2]) - total_margin;

		if (dis0 > 0.0f && dis1 > 0.0f && dis2 > 0.0f) {
			return false;
		}
		return true;
	}
	
	/**
	 * Calcs the plane which is paralele to the edge and perpendicular to the triangle plane.
	 * This triangle must have its plane calculated.
	 */
	public void get_edge_plane(int edge_index, Vector plane) {
		Vector e0 = this.vertices[edge_index];
		Vector e1 = this.vertices[(edge_index + 1) % 3];

		Vector tmp = Stack.alloc(new Vector(3));
		tmp.set(this.plane.get(X), this.plane.get(Y), this.plane.get(Z));

		GeometryOperations.edge_plane(e0, e1, tmp, plane);
	}

	public void applyTransform(Transform t) {
		t.transform(this.vertices[0]);
		t.transform(this.vertices[1]);
		t.transform(this.vertices[2]);
	}
	
	/**
	 * Clips the triangle against this.
	 * 
	 * @param clipped_points must have MAX_TRI_CLIPPING size, and this triangle must have its plane calculated.
	 * @return the number of clipped points
	 */
	public int clip_triangle(PrimitiveTriangle other, ObjectArrayList<Vector> clipped_points) {
		// edge 0
		ObjectArrayList<Vector> temp_points = this.tmpVecList1;

		Vector edgeplane = Stack.alloc(new Vector(4));

		get_edge_plane(0, edgeplane);

		int clipped_count = ClipPolygon.plane_clip_triangle(edgeplane, other.vertices[0], other.vertices[1], other.vertices[2], temp_points);

		if (clipped_count == 0) {
			return 0;
		}
		ObjectArrayList<Vector> temp_points1 = this.tmpVecList2;

		// edge 1
		get_edge_plane(1, edgeplane);

		clipped_count = ClipPolygon.plane_clip_polygon(edgeplane, temp_points, clipped_count, temp_points1);

		if (clipped_count == 0) {
			return 0; // edge 2
		}
		get_edge_plane(2, edgeplane);

		clipped_count = ClipPolygon.plane_clip_polygon(edgeplane, temp_points1, clipped_count, clipped_points);

		return clipped_count;
	}
	
	/**
	 * Find collision using the clipping method.
	 * This triangle and other must have their triangles calculated.
	 */
	public boolean find_triangle_collision_clip_method(PrimitiveTriangle other, TriangleContact contacts) {
		float margin = this.margin + other.margin;

		ObjectArrayList<Vector> clipped_points = this.tmpVecList3;

		int clipped_count;
		//create planes
		// plane v vs U points

		TriangleContact contacts1 = Stack.alloc(TriangleContact.class);

		contacts1.separating_normal.set(this.plane);

		clipped_count = clip_triangle(other, clipped_points);

		if (clipped_count == 0) {
			return false; // Reject
		}

		// find most deep interval face1
		contacts1.merge_points(contacts1.separating_normal, margin, clipped_points, clipped_count);
		if (contacts1.point_count == 0) {
			return false; // too far
		// Normal pointing to this triangle
		}
		contacts1.separating_normal.mulAll(-1f);
		
		// Clip tri1 by tri2 edges
		TriangleContact contacts2 = Stack.alloc(TriangleContact.class);
		contacts2.separating_normal.set(other.plane);

		clipped_count = other.clip_triangle(this, clipped_points);

		if (clipped_count == 0) {
			return false; // Reject
		}

		// find most deep interval face1
		contacts2.merge_points(contacts2.separating_normal, margin, clipped_points, clipped_count);
		if (contacts2.point_count == 0) {
			return false; // too far

		// check most dir for contacts
		}
		if (contacts2.penetration_depth < contacts1.penetration_depth) {
			contacts.copy_from(contacts2);
		}
		else {
			contacts.copy_from(contacts1);
		}
		return true;
	}
	
}
