
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
	
}
