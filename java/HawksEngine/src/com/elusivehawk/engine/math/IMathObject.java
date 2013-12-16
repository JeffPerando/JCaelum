
package com.elusivehawk.engine.math;

import com.elusivehawk.engine.core.IStorable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IMathObject<T extends Number> extends IStorable<T>
{
	public int getSize();
	
	public T get(int pos);
	
	public void set(T num, int pos);
	
	public T[] array();
	
	public IMathObject<T> add(IMathObject<T> obj);
	
	public IMathObject<T> div(IMathObject<T> obj);
	
	public IMathObject<T> set(IMathObject<T> obj);
	
	public IMathObject<T> sub(IMathObject<T> obj);
	
	public IMathObject<T> mul(IMathObject<T> obj);
	
}
