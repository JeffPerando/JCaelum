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

import static com.elusivehawk.engine.math.MathConst.*;
import com.elusivehawk.engine.math.Vector;
import com.elusivehawk.engine.physics.jbullet.linearmath.VectorUtil;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 *
 * @author jezek2
 */
class Quantization {

	public static void bt_calc_quantization_parameters(Vector outMinBound, Vector outMaxBound, Vector bvhQuantization, Vector srcMinBound, Vector srcMaxBound, float quantizationMargin) {
		// enlarge the AABB to avoid division by zero when initializing the quantization values
		Vector clampValue = Stack.alloc(new Vector(3));
		clampValue.set(quantizationMargin, quantizationMargin, quantizationMargin);
		outMinBound.sub(srcMinBound, clampValue);
		outMaxBound.add(srcMaxBound, clampValue);
		Vector aabbSize = Stack.alloc(new Vector(3));
		aabbSize.sub(outMaxBound, outMinBound);
		bvhQuantization.set(65535.0f, 65535.0f, 65535.0f);
		VectorUtil.div(bvhQuantization, bvhQuantization, aabbSize);
	}

	public static void bt_quantize_clamp(short[] out, Vector point, Vector min_bound, Vector max_bound, Vector bvhQuantization) {
		Vector clampedPoint = Stack.alloc(point);
		VectorUtil.setMax(clampedPoint, min_bound);
		VectorUtil.setMin(clampedPoint, max_bound);

		Vector v = Stack.alloc(new Vector(3));
		v.sub(clampedPoint, min_bound);
		VectorUtil.mul(v, v, bvhQuantization);

		out[0] = (short) (v.get(X)+ 0.5f);
		out[1] = (short) (v.get(Y)+ 0.5f);
		out[2] = (short) (v.get(Z)+ 0.5f);
	}

	public static Vector bt_unquantize(short[] vecIn, Vector offset, Vector bvhQuantization, Vector out) {
		out.set((vecIn[0] & 0xFFFF) / (bvhQuantization.get(X)),
		        (vecIn[1] & 0xFFFF) / (bvhQuantization.get(Y)),
		        (vecIn[2] & 0xFFFF) / (bvhQuantization.get(Z)));
		out.add(offset);
		return out;
	}
	
}
