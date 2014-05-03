
package com.elusivehawk.engine.util;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ArrayHelper
{
	private ArrayHelper(){}
	
	public static boolean add(Object[] arr, Object obj)
	{
		int i = indexOf(arr, null);
		
		if (i == -1)
		{
			return false;
		}
		
		if (arr[i] != null)
		{
			System.arraycopy(arr, i, arr, i + 1, sizeof(i, arr));
			
		}
		
		arr[i] = obj;
				
		return true;
	}
	
	public static int indexOf(Object[] arr, Object obj)
	{
		if (obj == null)
		{
			for (int c = 0; c < arr.length; c++)
			{
				if (arr[c] == null)
				{
					return c;
				}
				
			}
			
		}
		else
		{
			for (int c = 0; c < arr.length; c++)
			{
				if (obj.equals(arr[c]))
				{
					return c;
				}
				
			}
			
		}
		
		return -1;
	}
	
	public static boolean remove(Object[] arr, Object obj)
	{
		int i = indexOf(arr, obj);
		
		if (i == -1)
		{
			return false;
		}
		
		arr[i] = null;
		
		System.arraycopy(arr, i + 1, arr, i, arr.length - i);
		
		return true;
	}
	
	public static boolean isEmpty(Object[] arr)
	{
		for (Object obj : arr)
		{
			if (obj != null)
			{
				return false;
			}
			
		}
		
		return true;
	}
	
	public static int sizeof(Object[] arr)
	{
		return sizeof(0, arr);
	}
	
	public static int sizeof(int start, Object[] arr)
	{
		int ret = 0;
		
		for (int c = start; c < arr.length; c++)
		{
			if (arr[c] != null)
			{
				ret++;
				
			}
			
		}
		
		return ret;
	}
	
	public static boolean isFull(Object[] arr)
	{
		for (int c = 0; c < arr.length; c++)
		{
			if (arr[c] == null)
			{
				return false;
			}
			
		}
		
		return true;
	}
	
}
