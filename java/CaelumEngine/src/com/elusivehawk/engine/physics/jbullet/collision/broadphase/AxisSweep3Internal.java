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

import com.elusivehawk.engine.physics.jbullet.BulletStats;
import com.elusivehawk.engine.physics.jbullet.linearmath.MiscUtil;
import com.elusivehawk.engine.physics.jbullet.linearmath.VectorUtil;
import com.elusivehawk.engine.physics.jbullet.util.ObjectArrayList;
import com.elusivehawk.util.math.Vector;
import cz.advel.stack.Stack;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * AxisSweep3Internal is an internal base class that implements sweep and prune.
 * Use concrete implementation {@link AxisSweep3} or {@link AxisSweep3_32}.
 * 
 * @author jezek2
 */
public abstract class AxisSweep3Internal extends BroadphaseInterface
{
	
	protected int bpHandleMask;
	protected int handleSentinel;
	
	protected final Vector worldAabbMin = new Vector(3); // overall system bounds
	protected final Vector worldAabbMax = new Vector(3); // overall system bounds
	
	protected final Vector quantize = new Vector(3);     // scaling factor for quantization
	
	protected int numHandles;                               // number of active handles
	protected int maxHandles;                               // max number of handles
	protected Handle[] pHandles;                            // handles pool
	protected int firstFreeHandle;		                    // free handles list
	
	protected EdgeArray[] pEdges = new EdgeArray[3];      // edge arrays for the 3 axes (each array has m_maxHandles * 2 + 2 sentinel entries)
	
	protected OverlappingPairCache pairCache;
	
	// OverlappingPairCallback is an additional optional user callback for adding/removing overlapping pairs, similar interface to OverlappingPairCache.
	protected OverlappingPairCallback userPairCallback = null;
	
	protected boolean ownsPairCache = false;
	
	protected int invalidPair = 0;
	
	// JAVA NOTE: added
	protected int mask;
	
	AxisSweep3Internal(Vector worldAabbMin2, Vector worldAabbMax2,
			int handleMask, int handleSentinel, int userMaxHandles/* = 16384 */,
			OverlappingPairCache pairCache/* =0 */)
	{
		this.bpHandleMask = handleMask;
		this.handleSentinel = handleSentinel;
		this.pairCache = pairCache;
		
		int maxHandles = userMaxHandles + 1; // need to add one sentinel handle
		
		if (this.pairCache == null)
		{
			this.pairCache = new HashedOverlappingPairCache();
			this.ownsPairCache = true;
			
		}
		
		// assert(bounds.HasVolume());
		
		// init bounds
		this.worldAabbMin.set(worldAabbMin2);
		this.worldAabbMax.set(worldAabbMax2);
		
		Vector aabbSize = Stack.alloc(new Vector(3));
		aabbSize.sub(this.worldAabbMax, this.worldAabbMin);
		
		int maxInt = this.handleSentinel;
		
		// quantize.set(maxInt / aabbSize.x, maxInt / aabbSize.y, maxInt / aabbSize.z);
		for (int c = 0; c < this.quantize.getSize(); c++)
		{
			this.quantize.set(c, maxInt / aabbSize.get(c), false);
			
		}
		
		// allocate handles buffer and put all handles on free list
		this.pHandles = new Handle[maxHandles];
		
		for (int i = 0; i < maxHandles; i++)
		{
			this.pHandles[i] = createHandle();
			
		}
		
		this.maxHandles = maxHandles;
		this.numHandles = 0;
		
		// handle 0 is reserved as the null index, and is also used as the sentinel
		this.firstFreeHandle = 1;
		{
			for (int i = this.firstFreeHandle; i < maxHandles; i++)
			{
				this.pHandles[i].setNextFree(i + 1);
				
			}
			
			this.pHandles[maxHandles - 1].setNextFree(0);
			
		}
		
		{
			// allocate edge buffers
			for (int i = 0; i < 3; i++)
			{
				this.pEdges[i] = createEdgeArray(maxHandles * 2);
				
			}
			
		}
		// removed overlap management
		
		// make boundary sentinels
		
		this.pHandles[0].clientObject = null;
		
		for (int axis = 0; axis < 3; axis++)
		{
			this.pHandles[0].setMinEdges(axis, 0);
			this.pHandles[0].setMaxEdges(axis, 1);
			
			this.pEdges[axis].setPos(0, 0);
			this.pEdges[axis].setHandle(0, 0);
			this.pEdges[axis].setPos(1, handleSentinel);
			this.pEdges[axis].setHandle(1, 0);
			// #ifdef DEBUG_BROADPHASE
			// debugPrintAxis(axis);
			// #endif //DEBUG_BROADPHASE
		}
		
		// JAVA NOTE: added
		this.mask = getMask();
	}
	
	// allocation/deallocation
	protected int allocHandle()
	{
		assert (this.firstFreeHandle != 0);
		
		int handle = this.firstFreeHandle;
		this.firstFreeHandle = getHandle(handle).getNextFree();
		this.numHandles++;
		
		return handle;
	}
	
	protected void freeHandle(int handle)
	{
		assert (handle > 0 && handle < this.maxHandles);
		
		getHandle(handle).setNextFree(this.firstFreeHandle);
		this.firstFreeHandle = handle;
		
		this.numHandles--;
		
	}
	
	@SuppressWarnings("static-method")
	protected boolean testOverlap(int ignoreAxis, Handle pHandleA, Handle pHandleB)
	{
		// optimization 1: check the array index (memory address), instead of the m_pos
		
		for (int axis = 0; axis < 3; axis++)
		{
			if (axis != ignoreAxis)
			{
				if (pHandleA.getMaxEdges(axis) < pHandleB.getMinEdges(axis)
						|| pHandleB.getMaxEdges(axis) < pHandleA.getMinEdges(axis))
				{
					return false;
				}
				
			}
			
		}
		
		// optimization 2: only 2 axis need to be tested (conflicts with 'delayed removal' optimization)
		
		/*
		 * for (int axis = 0; axis < 3; axis++)
		 * {
		 * if (m_pEdges[axis][pHandleA->m_maxEdges[axis]].m_pos < m_pEdges[axis][pHandleB->m_minEdges[axis]].m_pos ||
		 * m_pEdges[axis][pHandleB->m_maxEdges[axis]].m_pos < m_pEdges[axis][pHandleA->m_minEdges[axis]].m_pos)
		 * {
		 * return false;
		 * }
		 * }
		 */
		
		return true;
	}
	
	// #ifdef DEBUG_BROADPHASE
	// void debugPrintAxis(int axis,bool checkCardinality=true);
	// #endif //DEBUG_BROADPHASE
	
	protected void quantize(int[] out, Vector point, int isMax)
	{
		Vector clampedPoint = Stack.alloc(point);
		
		VectorUtil.setMax(clampedPoint, this.worldAabbMin);
		VectorUtil.setMin(clampedPoint, this.worldAabbMax);
		
		Vector v = Stack.alloc(new Vector(3));
		v.sub(clampedPoint, this.worldAabbMin);
		VectorUtil.mul(v, v, this.quantize);
		
		/*
		 * out[0] = (((int)v.get(X)& bpHandleMask) | isMax) & mask;
		 * out[1] = (((int)v.get(Y)& bpHandleMask) | isMax) & mask;
		 * out[2] = (((int)v.get(Z)& bpHandleMask) | isMax) & mask;
		 */
		for (int c = 0; c < v.getSize(); c++)
		{
			out[c] = ((v.get(c).intValue() & this.bpHandleMask) | isMax) & this.mask;
			
		}
		
	}
	
	// sorting a min edge downwards can only ever *add* overlaps
	@SuppressWarnings("unused")
	protected void sortMinDown(int axis, int edge, Dispatcher dispatcher, boolean updateOverlaps)
	{
		EdgeArray edgeArray = this.pEdges[axis];
		int pEdge_idx = edge;
		int pPrev_idx = pEdge_idx - 1;
		
		Handle pHandleEdge = getHandle(edgeArray.getHandle(pEdge_idx));
		
		while (edgeArray.getPos(pEdge_idx) < edgeArray.getPos(pPrev_idx))
		{
			Handle pHandlePrev = this.getHandle(edgeArray.getHandle(pPrev_idx));
			
			if (edgeArray.isMax(pPrev_idx) != 0)
			{
				// if previous edge is a maximum check the bounds and add an overlap if necessary
				if (updateOverlaps && this.testOverlap(axis, pHandleEdge, pHandlePrev))
				{
					this.pairCache.addOverlappingPair(pHandleEdge, pHandlePrev);
					
					if (this.userPairCallback != null)
					{
						this.userPairCallback.addOverlappingPair(pHandleEdge, pHandlePrev);
						// AddOverlap(pEdge->m_handle, pPrev->m_handle);
					}
					
				}
				
				// update edge reference in other handle
				pHandlePrev.incMaxEdges(axis);
				
			}
			else
			{
				pHandlePrev.incMinEdges(axis);
				
			}
			
			pHandleEdge.decMinEdges(axis);
			
			// swap the edges
			edgeArray.swap(pEdge_idx, pPrev_idx);
			
			// decrement
			pEdge_idx--;
			pPrev_idx--;
			
		}
		
		// #ifdef DEBUG_BROADPHASE
		// debugPrintAxis(axis);
		// #endif //DEBUG_BROADPHASE
	}
	
	// sorting a min edge upwards can only ever *remove* overlaps
	protected void sortMinUp(int axis, int edge, Dispatcher dispatcher, boolean updateOverlaps)
	{
		EdgeArray edgeArray = this.pEdges[axis];
		int pEdge_idx = edge;
		int pNext_idx = pEdge_idx + 1;
		Handle pHandleEdge = getHandle(edgeArray.getHandle(pEdge_idx));
		
		while (edgeArray.getHandle(pNext_idx) != 0 && (edgeArray.getPos(pEdge_idx) >= edgeArray.getPos(pNext_idx)))
		{
			Handle pHandleNext = getHandle(edgeArray.getHandle(pNext_idx));
			
			if (edgeArray.isMax(pNext_idx) != 0)
			{
				// if next edge is maximum remove any overlap between the two handles
				if (updateOverlaps)
				{
					Handle handle0 = this.getHandle(edgeArray.getHandle(pEdge_idx));
					Handle handle1 = this.getHandle(edgeArray.getHandle(pNext_idx));
					
					this.pairCache.removeOverlappingPair(handle0, handle1, dispatcher);
					
					if (this.userPairCallback != null)
					{
						this.userPairCallback.removeOverlappingPair(handle0, handle1, dispatcher);
						
					}
					
				}
				
				// update edge reference in other handle
				pHandleNext.decMaxEdges(axis);
				
			}
			else
			{
				pHandleNext.decMinEdges(axis);
				
			}
			
			pHandleEdge.incMinEdges(axis);
			
			// swap the edges
			edgeArray.swap(pEdge_idx, pNext_idx);
			
			// increment
			pEdge_idx++;
			pNext_idx++;
			
		}
		
	}
	
	// sorting a max edge downwards can only ever *remove* overlaps
	protected void sortMaxDown(int axis, int edge, Dispatcher dispatcher, boolean updateOverlaps)
	{
		EdgeArray edgeArray = this.pEdges[axis];
		int pEdge_idx = edge;
		int pPrev_idx = pEdge_idx - 1;
		Handle pHandleEdge = this.getHandle(edgeArray.getHandle(pEdge_idx));
		
		while (edgeArray.getPos(pEdge_idx) < edgeArray.getPos(pPrev_idx))
		{
			Handle pHandlePrev = this.getHandle(edgeArray.getHandle(pPrev_idx));
			
			if (edgeArray.isMax(pPrev_idx) == 0)
			{
				// if previous edge was a minimum remove any overlap between the two handles
				if (updateOverlaps)
				{
					// this is done during the overlappingpairarray iteration/narrowphase collision
					Handle handle0 = this.getHandle(edgeArray.getHandle(pEdge_idx));
					Handle handle1 = this.getHandle(edgeArray.getHandle(pPrev_idx));
					this.pairCache.removeOverlappingPair(handle0, handle1, dispatcher);
					
					if (this.userPairCallback != null)
					{
						this.userPairCallback.removeOverlappingPair(handle0, handle1, dispatcher);
						
					}
					
				}
				
				// update edge reference in other handle
				pHandlePrev.incMinEdges(axis);
				
			}
			else
			{
				pHandlePrev.incMaxEdges(axis);
				
			}
			
			pHandleEdge.decMaxEdges(axis);
			
			// swap the edges
			edgeArray.swap(pEdge_idx, pPrev_idx);
			
			// decrement
			pEdge_idx--;
			pPrev_idx--;
			
		}
		
		// #ifdef DEBUG_BROADPHASE
		// debugPrintAxis(axis);
		// #endif //DEBUG_BROADPHASE
	}
	
	// sorting a max edge upwards can only ever *add* overlaps
	@SuppressWarnings("unused")
	protected void sortMaxUp(int axis, int edge, Dispatcher dispatcher, boolean updateOverlaps)
	{
		EdgeArray edgeArray = this.pEdges[axis];
		int pEdge_idx = edge;
		int pNext_idx = pEdge_idx + 1;
		Handle pHandleEdge = getHandle(edgeArray.getHandle(pEdge_idx));
		
		while (edgeArray.getHandle(pNext_idx) != 0
				&& (edgeArray.getPos(pEdge_idx) >= edgeArray.getPos(pNext_idx)))
		{
			Handle pHandleNext = getHandle(edgeArray.getHandle(pNext_idx));
			
			if (edgeArray.isMax(pNext_idx) == 0)
			{
				// if next edge is a minimum check the bounds and add an overlap if necessary
				if (updateOverlaps && this.testOverlap(axis, pHandleEdge, pHandleNext))
				{
					Handle handle0 = getHandle(edgeArray.getHandle(pEdge_idx));
					Handle handle1 = getHandle(edgeArray.getHandle(pNext_idx));
					this.pairCache.addOverlappingPair(handle0, handle1);
					
					if (this.userPairCallback != null)
					{
						this.userPairCallback.addOverlappingPair(handle0, handle1);
					}
					
				}
				
				// update edge reference in other handle
				pHandleNext.decMinEdges(axis);
				
			}
			else
			{
				pHandleNext.decMaxEdges(axis);
				
			}
			
			pHandleEdge.incMaxEdges(axis);
			
			// swap the edges
			edgeArray.swap(pEdge_idx, pNext_idx);
			
			// increment
			pEdge_idx++;
			pNext_idx++;
			
		}
		
	}
	
	public int getNumHandles()
	{
		return this.numHandles;
	}
	
	@Override
	public void calculateOverlappingPairs(Dispatcher dispatcher)
	{
		if (this.pairCache.hasDeferredRemoval())
		{
			ObjectArrayList<BroadphasePair> overlappingPairArray = this.pairCache.getOverlappingPairArray();
			
			// perform a sort, to find duplicates and to sort 'invalid' pairs to the end
			MiscUtil.quickSort(overlappingPairArray, BroadphasePair.broadphasePairSortPredicate);
			
			MiscUtil.resize(overlappingPairArray, overlappingPairArray.size() - this.invalidPair, BroadphasePair.class);
			this.invalidPair = 0;
			
			int i;
			
			BroadphasePair previousPair = new BroadphasePair();
			previousPair.pProxy0 = null;
			previousPair.pProxy1 = null;
			previousPair.algorithm = null;
			
			for (i = 0; i < overlappingPairArray.size(); i++)
			{
				BroadphasePair pair = overlappingPairArray.getQuick(i);
				
				boolean isDuplicate = (pair.equals(previousPair));
				
				previousPair.set(pair);
				
				boolean needsRemoval = false;
				
				if (isDuplicate)
				{
					// remove duplicate
					needsRemoval = true;
					// should have no algorithm
					assert (pair.algorithm == null);
				}
				else
				{
					needsRemoval/*boolean hasOverlap*/ = !this.testAabbOverlap(pair.pProxy0, pair.pProxy1);
					
					/*if (hasOverlap)
					{
						needsRemoval = false;// callback->processOverlap(pair);
						
					}
					else
					{
						needsRemoval = true;
						
					}*/
					
				}
				
				if (needsRemoval)
				{
					this.pairCache.cleanOverlappingPair(pair, dispatcher);
					
					// m_overlappingPairArray.swap(i,m_overlappingPairArray.size()-1);
					// m_overlappingPairArray.pop_back();
					pair.pProxy0 = null;
					pair.pProxy1 = null;
					this.invalidPair++;
					BulletStats.gOverlappingPairs--;
					
				}
				
			}
			
			// if you don't like to skip the invalid pairs in the array, execute following code:
			// #define CLEAN_INVALID_PAIRS 1
			// #ifdef CLEAN_INVALID_PAIRS
			
			// perform a sort, to sort 'invalid' pairs to the end
			MiscUtil.quickSort(overlappingPairArray,
					BroadphasePair.broadphasePairSortPredicate);
			
			MiscUtil.resize(overlappingPairArray, overlappingPairArray.size()
					- this.invalidPair, BroadphasePair.class);
			this.invalidPair = 0;
			// #endif//CLEAN_INVALID_PAIRS
			
			// printf("overlappingPairArray.size()=%d\n",overlappingPairArray.size());
		}
		
	}
	
	public int addHandle(Vector aabbMin, Vector aabbMax, Object pOwner,
			short collisionFilterGroup, short collisionFilterMask,
			Dispatcher dispatcher, Object multiSapProxy)
	{
		// quantize the bounds
		int[] min = new int[3], max = new int[3];
		quantize(min, aabbMin, 0);
		quantize(max, aabbMax, 1);
		
		// allocate a handle
		int handle = allocHandle();
		
		Handle pHandle = getHandle(handle);
		
		pHandle.uniqueId = handle;
		// pHandle->m_pOverlaps = 0;
		pHandle.clientObject = pOwner;
		pHandle.collisionFilterGroup = collisionFilterGroup;
		pHandle.collisionFilterMask = collisionFilterMask;
		pHandle.multiSapParentProxy = multiSapProxy;
		
		// compute current limit of edge arrays
		int limit = this.numHandles * 2;
		
		// insert new edges just inside the max boundary edge
		for (int axis = 0; axis < 3; axis++)
		{
			this.pHandles[0].setMaxEdges(axis, this.pHandles[0].getMaxEdges(axis) + 2);
			
			this.pEdges[axis].set(limit + 1, limit - 1);
			
			this.pEdges[axis].setPos(limit - 1, min[axis]);
			this.pEdges[axis].setHandle(limit - 1, handle);
			
			this.pEdges[axis].setPos(limit, max[axis]);
			this.pEdges[axis].setHandle(limit, handle);
			
			pHandle.setMinEdges(axis, limit - 1);
			pHandle.setMaxEdges(axis, limit);
		}
		
		// now sort the new edges to their correct position
		
		for (int c = 0; c < 3; c++)
		{
			this.sortMinDown(c, pHandle.getMinEdges(0), dispatcher, c == 2);
			this.sortMaxDown(c, pHandle.getMaxEdges(0), dispatcher, c == 2);
			
		}
		
		/*sortMinDown(0, pHandle.getMinEdges(0), dispatcher, false);
		sortMaxDown(0, pHandle.getMaxEdges(0), dispatcher, false);
		sortMinDown(1, pHandle.getMinEdges(1), dispatcher, false);
		sortMaxDown(1, pHandle.getMaxEdges(1), dispatcher, false);
		sortMinDown(2, pHandle.getMinEdges(2), dispatcher, true);
		sortMaxDown(2, pHandle.getMaxEdges(2), dispatcher, true);*/
		
		return handle;
	}
	
	public void removeHandle(int handle, Dispatcher dispatcher)
	{
		Handle pHandle = getHandle(handle);
		
		// explicitly remove the pairs containing the proxy
		// we could do it also in the sortMinUp (passing true)
		// todo: compare performance
		if (!this.pairCache.hasDeferredRemoval())
		{
			this.pairCache .removeOverlappingPairsContainingProxy(pHandle, dispatcher);
			
		}
		
		// compute current limit of edge arrays
		int limit = this.numHandles * 2;
		
		int axis;
		
		for (axis = 0; axis < 3; axis++)
		{
			this.pHandles[0].setMaxEdges(axis, this.pHandles[0].getMaxEdges(axis) - 2);
		}
		
		// remove the edges by sorting them up to the end of the list
		for (axis = 0; axis < 3; axis++)
		{
			EdgeArray pEdges = this.pEdges[axis];
			int max = pHandle.getMaxEdges(axis);
			pEdges.setPos(max, this.handleSentinel);
			
			sortMaxUp(axis, max, dispatcher, false);
			
			int i = pHandle.getMinEdges(axis);
			pEdges.setPos(i, this.handleSentinel);
			
			sortMinUp(axis, i, dispatcher, false);
			
			pEdges.setHandle(limit - 1, 0);
			pEdges.setPos(limit - 1, this.handleSentinel);
			
			// #ifdef DEBUG_BROADPHASE
			// debugPrintAxis(axis,false);
			// #endif //DEBUG_BROADPHASE
		}
		
		// free the handle
		freeHandle(handle);
		
	}
	
	public void updateHandle(int handle, Vector aabbMin, Vector aabbMax, Dispatcher dispatcher)
	{
		Handle pHandle = getHandle(handle);
		
		// quantize the new bounds
		int[] min = new int[3], max = new int[3];
		quantize(min, aabbMin, 0);
		quantize(max, aabbMax, 1);
		
		// update changed edges
		for (int axis = 0; axis < 3; axis++)
		{
			int emin = pHandle.getMinEdges(axis);
			int emax = pHandle.getMaxEdges(axis);
			
			int dmin = min[axis] - this.pEdges[axis].getPos(emin);
			int dmax = max[axis] - this.pEdges[axis].getPos(emax);
			
			this.pEdges[axis].setPos(emin, min[axis]);
			this.pEdges[axis].setPos(emax, max[axis]);
			
			// expand (only adds overlaps)
			if (dmin < 0)
			{
				this.sortMinDown(axis, emin, dispatcher, true);
				
			}
			
			if (dmax > 0)
			{
				this.sortMaxUp(axis, emax, dispatcher, true); // shrink (only removes overlaps)
				
			}
			
			if (dmin > 0)
			{
				this.sortMinUp(axis, emin, dispatcher, true);
				
			}
			
			if (dmax < 0)
			{
				this.sortMaxDown(axis, emax, dispatcher, true);
				
			}
			
			// #ifdef DEBUG_BROADPHASE
			// debugPrintAxis(axis);
			// #endif //DEBUG_BROADPHASE
		}
	}
	
	public Handle getHandle(int index)
	{
		return this.pHandles[index];
	}
	
	// public void processAllOverlappingPairs(OverlapCallback callback) {
	// }
	
	@Override
	public BroadphaseProxy createProxy(Vector aabbMin, Vector aabbMax,
			BroadphaseNativeType shapeType, Object userPtr,
			short collisionFilterGroup, short collisionFilterMask,
			Dispatcher dispatcher, Object multiSapProxy)
	{
		int handleId = addHandle(aabbMin, aabbMax, userPtr,
				collisionFilterGroup, collisionFilterMask, dispatcher,
				multiSapProxy);
		
		Handle handle = getHandle(handleId);
		
		return handle;
	}
	
	@Override
	public void destroyProxy(BroadphaseProxy proxy, Dispatcher dispatcher)
	{
		Handle handle = (Handle)proxy;
		removeHandle(handle.uniqueId, dispatcher);
		
	}
	
	@Override
	public void setAabb(BroadphaseProxy proxy, Vector aabbMin, Vector aabbMax, Dispatcher dispatcher)
	{
		Handle handle = (Handle)proxy;
		updateHandle(handle.uniqueId, aabbMin, aabbMax, dispatcher);
		
	}
	
	@SuppressWarnings("static-method")
	public boolean testAabbOverlap(BroadphaseProxy proxy0, BroadphaseProxy proxy1)
	{
		Handle pHandleA = (Handle)proxy0;
		Handle pHandleB = (Handle)proxy1;
		
		// optimization 1: check the array index (memory address), instead of the m_pos
		
		for (int axis = 0; axis < 3; axis++)
		{
			if (pHandleA.getMaxEdges(axis) < pHandleB.getMinEdges(axis) || pHandleB.getMaxEdges(axis) < pHandleA.getMinEdges(axis))
			{
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public OverlappingPairCache getOverlappingPairCache()
	{
		return this.pairCache;
	}
	
	public void setOverlappingPairUserCallback(OverlappingPairCallback pairCallback)
	{
		this.userPairCallback = pairCallback;
		
	}
	
	public OverlappingPairCallback getOverlappingPairUserCallback()
	{
		return this.userPairCallback;
	}
	
	// getAabb returns the axis aligned bounding box in the 'global' coordinate frame
	// will add some transform later
	@Override
	public void getBroadphaseAabb(Vector aabbMin, Vector aabbMax)
	{
		aabbMin.set(this.worldAabbMin);
		aabbMax.set(this.worldAabbMax);
		
	}
	
	@Override
	public void printStats()
	{
		/*
		 * printf("btAxisSweep3.h\n");
		 * printf("numHandles = %d, maxHandles = %d\n",m_numHandles,m_maxHandles);
		 * printf("aabbMin=%f,%f,%f,aabbMax=%f,%f,%f\n",m_worldAabbMin.getX(),m_worldAabbMin.getY(),m_worldAabbMin.getZ(),
		 * m_worldAabbMax.getX(),m_worldAabbMax.getY(),m_worldAabbMax.getZ());
		 */
	}
	
	// //////////////////////////////////////////////////////////////////////////
	
	protected abstract EdgeArray createEdgeArray(int size);
	
	protected abstract Handle createHandle();
	
	protected abstract int getMask();
	
	protected static abstract class EdgeArray
	{
		public abstract void swap(int idx1, int idx2);
		
		public abstract void set(int dest, int src);
		
		public abstract int getPos(int index);
		
		public abstract void setPos(int index, int value);
		
		public abstract int getHandle(int index);
		
		public abstract void setHandle(int index, int value);
		
		public int isMax(int offset)
		{
			return (getPos(offset) & 1);
		}
		
	}
	
	protected static abstract class Handle extends BroadphaseProxy
	{
		public abstract int getMinEdges(int edgeIndex);
		
		public abstract void setMinEdges(int edgeIndex, int value);
		
		public abstract int getMaxEdges(int edgeIndex);
		
		public abstract void setMaxEdges(int edgeIndex, int value);
		
		public void incMinEdges(int edgeIndex)
		{
			this.setMinEdges(edgeIndex, getMinEdges(edgeIndex) + 1);
			
		}
		
		public void incMaxEdges(int edgeIndex)
		{
			this.setMaxEdges(edgeIndex, getMaxEdges(edgeIndex) + 1);
			
		}
		
		public void decMinEdges(int edgeIndex)
		{
			this.setMinEdges(edgeIndex, getMinEdges(edgeIndex) - 1);
			
		}
		
		public void decMaxEdges(int edgeIndex)
		{
			this.setMaxEdges(edgeIndex, getMaxEdges(edgeIndex) - 1);
			
		}
		
		public void setNextFree(int next)
		{
			this.setMinEdges(0, next);
			
		}
		
		public int getNextFree()
		{
			return this.getMinEdges(0);
		}
		
	}
	
}
