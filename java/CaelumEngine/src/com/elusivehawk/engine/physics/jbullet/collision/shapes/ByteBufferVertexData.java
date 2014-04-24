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

import java.nio.ByteBuffer;
import static com.elusivehawk.engine.math.MathConst.*;
import com.elusivehawk.engine.math.Vector;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 *
 * @author jezek2
 */
public class ByteBufferVertexData extends VertexData {

	public ByteBuffer vertexData;
	public int vertexCount;
	public int vertexStride;
	public ScalarType vertexType;

	public ByteBuffer indexData;
	public int indexCount;
	public int indexStride;
	public ScalarType indexType;

	@Override
	public int getVertexCount() {
		return this.vertexCount;
	}

	@Override
	public int getIndexCount() {
		return this.indexCount;
	}

	@Override
	public <T extends Vector> T getVertex(int idx, T out)
	{
		int off = idx*this.vertexStride;
		out.set(X, this.vertexData.getFloat(off+4*0));
		out.set(Y, this.vertexData.getFloat(off+4*1));
		out.set(Z, this.vertexData.getFloat(off+4*2));
		return out;
	}

	@Override
	public void setVertex(int idx, float x, float y, float z) {
		int off = idx*this.vertexStride;
		this.vertexData.putFloat(off+4*0, x);
		this.vertexData.putFloat(off+4*1, y);
		this.vertexData.putFloat(off+4*2, z);
	}

	@Override
	public int getIndex(int idx) {
		if (this.indexType == ScalarType.SHORT) {
			return this.indexData.getShort(idx*this.indexStride) & 0xFFFF;
		}
		else if (this.indexType == ScalarType.INTEGER) {
			return this.indexData.getInt(idx*this.indexStride);
		}
		else {
			throw new IllegalStateException("indicies type must be short or integer");
		}
	}

}
