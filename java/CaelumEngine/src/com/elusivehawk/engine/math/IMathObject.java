
package com.elusivehawk.engine.math;

import com.elusivehawk.engine.util.storage.IStorable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IMathObject<T extends Number> extends IStorable<T>
{
	public int getSize();
	
	public boolean isImmutable();
	
	public T get(int pos);
	
	public T[] multiget(int bitmask);
	
	public void set(int pos, T num);
	
	public void setAll(T num);
	
	public T normalize();
	
	public IMathObject<T> set(IMathObject<T> obj);
	
	public IMathObject<T> add(IMathObject<T> obj);
	
	public IMathObject<T> add(IMathObject<T> obj, IMathObject<T> dest);
	
	public IMathObject<T> div(IMathObject<T> obj);
	
	public IMathObject<T> div(IMathObject<T> obj, IMathObject<T> dest);
	
	public IMathObject<T> sub(IMathObject<T> obj, IMathObject<T> dest);
	
	public IMathObject<T> sub(IMathObject<T> obj);
	
	public IMathObject<T> mul(IMathObject<T> obj, IMathObject<T> dest);
	
	public IMathObject<T> mul(IMathObject<T> obj);
	
}
