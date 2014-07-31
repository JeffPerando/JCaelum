/*
 * Java port of Bullet (c) 2008 Martin Dvorak <jezek2@advel.cz>
 * 
 * AxisSweep3
 * Copyright (c) 2006 Simon Hobbs
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

package com.elusivehawk.engine.physics.jbullet.collision.broadphase;

import com.elusivehawk.util.math.Vector;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * AxisSweep3_32 allows higher precision quantization and more objects compared
 * to the {@link AxisSweep3} sweep and prune. This comes at the cost of more memory
 * per handle, and a bit slower performance.
 *
 * @author jezek2
 */
public class AxisSweep3_32 extends AxisSweep3Internal
{
	public AxisSweep3_32(Vector worldAabbMin, Vector worldAabbMax)
	{
		this(worldAabbMin, worldAabbMax, 1500000, null);
		
	}
	
	public AxisSweep3_32(Vector worldAabbMin, Vector worldAabbMax, int maxHandles)
	{
		this(worldAabbMin, worldAabbMax, maxHandles, null);
		
	}
	
	public AxisSweep3_32(Vector worldAabbMin, Vector worldAabbMax,
			int maxHandles/* = 1500000 */, OverlappingPairCache pairCache/* = 0 */)
	{
		super(worldAabbMin, worldAabbMax, 0xfffffffe, 0x7fffffff, maxHandles, pairCache);
		// 1 handle is reserved as sentinel
		assert (maxHandles > 1 && maxHandles < 2147483647);
		
	}
	
	@Override
	protected EdgeArray createEdgeArray(int size)
	{
		return new EdgeArrayImpl(size);
	}
	
	@Override
	protected Handle createHandle()
	{
		return new HandleImpl();
	}
	
	@Override
	protected int getMask()
	{
		return 0xFFFFFFFF;
	}
	
	protected static class EdgeArrayImpl extends EdgeArray
	{
		private int[] pos;
		private int[] handle;
		
		@SuppressWarnings("unqualified-field-access")
		public EdgeArrayImpl(int size)
		{
			pos = new int[size];
			handle = new int[size];
			
		}
		
		@Override
		public void swap(int idx1, int idx2)
		{
			int tmpPos = this.pos[idx1];
			int tmpHandle = this.handle[idx1];
			
			this.pos[idx1] = this.pos[idx2];
			this.handle[idx1] = this.handle[idx2];
			
			this.pos[idx2] = tmpPos;
			this.handle[idx2] = tmpHandle;
			
		}
		
		@Override
		public void set(int dest, int src)
		{
			this.pos[dest] = this.pos[src];
			this.handle[dest] = this.handle[src];
			
		}
		
		@Override
		public int getPos(int index)
		{
			return this.pos[index];
		}
		
		@Override
		public void setPos(int index, int value)
		{
			this.pos[index] = value;
			
		}
		
		@Override
		public int getHandle(int index)
		{
			return this.handle[index];
		}
		
		@Override
		public void setHandle(int index, int value)
		{
			this.handle[index] = value;
			
		}
		
	}
	
	protected static class HandleImpl extends Handle
	{
		private int[] minEdges = new int[3],
				maxEdges = new int[3];
		
		@Override
		public int getMinEdges(int edgeIndex)
		{
			return this.minEdges[edgeIndex];
		}
		
		@Override
		public void setMinEdges(int edgeIndex, int value)
		{
			this.minEdges[edgeIndex] = value;
			
		}
		
		@Override
		public int getMaxEdges(int edgeIndex)
		{
			return this.maxEdges[edgeIndex];
		}
		
		@Override
		public void setMaxEdges(int edgeIndex, int value)
		{
			this.maxEdges[edgeIndex] = value;
			
		}
		
	}
	
}
