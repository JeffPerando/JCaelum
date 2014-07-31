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

package com.elusivehawk.engine.physics.jbullet.linearmath;

import static com.elusivehawk.util.math.MathConst.X;
import static com.elusivehawk.util.math.MathConst.Y;
import static com.elusivehawk.util.math.MathConst.Z;
import com.elusivehawk.engine.physics.jbullet.collision.shapes.UniformScalingShape;
import com.elusivehawk.util.math.Matrix;
import com.elusivehawk.util.math.Quaternion;
import com.elusivehawk.util.math.Vector;
import cz.advel.stack.Stack;
import cz.advel.stack.StaticAlloc;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * Transform represents translation and rotation (rigid transform). Scaling and
 * shearing is not supported.<p>
 * 
 * You can use local shape scaling or {@link UniformScalingShape} for static rescaling
 * of collision objects.
 * 
 * @author jezek2
 */
public class Transform
{
	
	//protected BulletStack stack;

	/** Rotation matrix of this Transform. */
	public final Matrix basis = new Matrix(3, 3);
	
	/** Translation vector of this Transform. */
	public final Vector origin = new Vector(3);

	public Transform(){}

	@SuppressWarnings("unqualified-field-access")
	public Transform(Matrix mat) {
		basis.set(mat);
	}

	public Transform(Transform tr) {
		set(tr);
	}
	
	public void set(Transform tr) {
		this.basis.set(tr.basis);
		this.origin.set(tr.origin);
	}
	
	public void set(Matrix mat) {
		this.basis.set(mat);
		this.origin.setAll(0f);
	}

	public void transform(Vector v) {
		this.basis.transform(v);
		v.add(this.origin);
	}

	public void setIdentity() {
		this.basis.setIdentity();
		this.origin.setAll(0f);
	}
	
	public void inverse() {
		this.basis.transpose();
		this.origin.scale(-1f);
		this.basis.transform(this.origin);
	}

	public void inverse(Transform tr) {
		set(tr);
		inverse();
	}
	
	public void mul(Transform tr) {
		Vector vec = Stack.alloc(tr.origin);
		transform(vec);

		this.basis.mul(tr.basis);
		this.origin.set(vec);
	}

	@StaticAlloc
	public void mul(Transform tr1, Transform tr2) {
		Vector vec = Stack.alloc(tr2.origin);
		tr1.transform(vec);

		this.basis.mul(tr1.basis, tr2.basis);
		this.origin.set(vec);
	}
	
	public void invXform(Vector inVec, Vector out) {
		out.sub(inVec, this.origin);

		Matrix mat = Stack.alloc(this.basis);
		mat.transpose();
		mat.transform(out);
	}
	
	public Quaternion getRotation(Quaternion out) {
		MatrixUtil.getRotation(this.basis, out);
		return out;
	}
	
	public void setRotation(Quaternion q) {
		MatrixUtil.setRotation(this.basis, q);
	}
	
	public void setFromOpenGLMatrix(float[] m) {
		MatrixUtil.setFromOpenGLSubMatrix(this.basis, m);
		for (int c = 0; c < 3; c++)
		{
			this.origin.set(c, m[12 + c], c == 2);
			
		}
		//this.origin.set(m[12], m[13], m[14]);
	}

	public void getOpenGLMatrix(float[] m) {
		MatrixUtil.getOpenGLSubMatrix(this.basis, m);
		m[12] = this.origin.get(X);
		m[13] = this.origin.get(Y);
		m[14] = this.origin.get(Z);
		m[15] = 1f;
	}

	public Matrix getMatrix(Matrix out) {
		out.set(this.basis);
		for (int x = 0; x < 3; x++)
		{
			out.set(x, 3, this.origin.get(x));
		}
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Transform)) return false;
		Transform tr = (Transform)obj;
		return this.basis.equals(tr.basis) && this.origin.equals(tr.origin);
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 41 * hash + this.basis.hashCode();
		hash = 41 * hash + this.origin.hashCode();
		return hash;
	}
	
}
