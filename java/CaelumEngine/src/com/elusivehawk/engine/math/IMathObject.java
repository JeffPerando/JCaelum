
package com.elusivehawk.engine.math;

import com.elusivehawk.engine.util.MakeDefault;
import com.elusivehawk.engine.util.storage.Buffer;
import com.elusivehawk.engine.util.storage.IStorable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IMathObject<T extends Number> extends IStorable<T>
{
	@MakeDefault
	@Override
	public void store(Buffer<T> buf);
	
	public int getSize();
	
	public boolean isImmutable();
	
	public T get(int pos);
	
	public T[] multiget(int bitmask);
	
	@MakeDefault
	public void set(int pos, T num);
	
	public void set(int pos, T num, boolean notify);
	
	@MakeDefault
	public void setAll(T num);
	
	public void setAll(T num, boolean notify);
	
	@MakeDefault
	public void normalize();
	
	public void normalize(IMathObject<T> dest);
	
	@MakeDefault
	public IMathObject<T> set(IMathObject<T> obj);
	
	@MakeDefault
	public IMathObject<T> add(IMathObject<T> obj);
	
	public IMathObject<T> add(IMathObject<T> obj, IMathObject<T> dest);
	
	@MakeDefault
	public IMathObject<T> div(IMathObject<T> obj);
	
	public IMathObject<T> div(IMathObject<T> obj, IMathObject<T> dest);
	
	@MakeDefault
	public IMathObject<T> sub(IMathObject<T> obj);
	
	public IMathObject<T> sub(IMathObject<T> obj, IMathObject<T> dest);
	
	@MakeDefault
	public IMathObject<T> mul(IMathObject<T> obj);
	
	public IMathObject<T> mul(IMathObject<T> obj, IMathObject<T> dest);
	
	@MakeDefault
	public void onChanged();
	
}
