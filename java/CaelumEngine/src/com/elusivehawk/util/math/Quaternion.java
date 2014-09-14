
package com.elusivehawk.util.math;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Quaternion implements IMathObject<Float>
{
	protected final float[] data;
	
	@SuppressWarnings("unqualified-field-access")
	public Quaternion()
	{
		data = new float[4];
		setAll(0f);
		
	}
	
	public Quaternion(float[] angles)
	{
		this();
		
		int size = Math.min(angles.length, 3);
		
		for (int c = 0; c < size; c++)
		{
			rotate(MathConst.AXES[c], angles[c]);
			
		}
		
	}
	
	public Quaternion(Quaternion q)
	{
		this();
		set(q);
		
	}
	
	@Override
	public int length()
	{
		return this.data.length;
	}
	
	@Override
	public Float get(int pos)
	{
		return MathHelper.bounds(pos, 0, this.length()) ? this.data[pos] : 0f;
	}
	
	@Override
	public Quaternion set(int pos, Float num, boolean notify)
	{
		assert MathHelper.bounds(pos, 0, this.length());
		
		this.data[pos] = num.floatValue();
		
		if (notify)
		{
			this.onChanged();
			
		}
		
		return this;
	}
	
	@Override
	public IMathObject<Float> normalize(IMathObject<Float> dest)
	{
		assert !dest.isImmutable();
		
		float f = MathHelper.length(this);
		
		int length = Math.min(this.length(), dest.length());
		
		for (int c = 0; c < length; c++)
		{
			dest.set(c, this.get(c) / f, false);
			
		}
		
		dest.onChanged();
		
		return dest;
	}
	
	@Override
	public IMathObject<Float> add(IMathObject<Float> obj, IMathObject<Float> dest)
	{
		assert !dest.isImmutable();
		
		int l = Math.min(this.length(), obj.length());
		
		for (int c = 0; c < l; c++)
		{
			dest.set(c, this.get(c) + obj.get(c), false);
			
		}
		
		this.onChanged();
		
		return dest;
	}
	
	@Override
	public IMathObject<Float> div(IMathObject<Float> obj, IMathObject<Float> dest)
	{
		assert !dest.isImmutable();
		
		int l = Math.min(this.length(), obj.length());
		
		for (int c = 0; c < l; c++)
		{
			dest.set(c, this.get(c) / obj.get(c), false);
			
		}
		
		this.onChanged();
		
		return dest;
	}
	
	@Override
	public IMathObject<Float> sub(IMathObject<Float> obj, IMathObject<Float> dest)
	{
		assert !dest.isImmutable();
		
		int l = Math.min(this.length(), obj.length());
		
		for (int c = 0; c < l; c++)
		{
			dest.set(c, this.get(c) - obj.get(c), false);
			
		}
		
		this.onChanged();
		
		return dest;
	}
	
	@Override
	public IMathObject<Float> mul(IMathObject<Float> obj, IMathObject<Float> dest)
	{
		assert !dest.isImmutable();
		
		int l = Math.min(this.length(), obj.length());
		
		for (int c = 0; c < l; c++)
		{
			dest.set(c, this.get(c) * obj.get(c), false);
			
		}
		
		this.onChanged();
		
		return dest;
	}
	
	public Quaternion conjugate()
	{
		return this.conjugate(this);
	}
	
	public Quaternion conjugate(boolean local)
	{
		return this.conjugate(local ? this : new Quaternion());
	}
	
	public Quaternion conjugate(Quaternion dest)
	{
		for (int c = 0; c < 3; c++)
		{
			dest.set(c, -this.get(c), false);
			
		}
		
		dest.set(4, this.get(4));
		
		return dest;
	}
	
	public Quaternion rotate(Vector axis, float angle)
	{
		return rotate(this, axis, angle);
	}
	
	public Quaternion rotate(Vector axis, float angle, boolean local)
	{
		return rotate(local ? this : new Quaternion(), axis, angle);
	}
	
	public static Quaternion rotate(Quaternion dest, Vector axis, float angle)
	{
		float radian = MathHelper.toRadians(angle / 2);
		
		float sin = (float)Math.sin(radian);
		
		for (int c = 0; c < 3; c++)
		{
			if (axis.get(c) > 0)
			{
				dest.set(c, axis.get(c) * sin, false);
				
			}
			
		}
		
		dest.set(4, (float)Math.cos(radian));
		
		return (Quaternion)dest.normalize();
	}
	
	public Vector rotateVec(Vector vec)
	{
		return this.rotateVec(vec, true);
	}
	
	public Vector rotateVec(Vector vec, boolean local)
	{
		return this.rotateVec(vec, local ? vec : new Vector());
	}
	
	public Vector rotateVec(Vector vec, Vector dest)
	{
		Quaternion conj = this.conjugate(false);
		Quaternion vQuat = (Quaternion)new Quaternion().set(vec).set(4, 1f);
		
		vQuat.mul(this);
		vQuat.mul(conj);
		
		int size = Math.min(3, dest.length());
		
		for (int c = 0; c < size; c++)
		{
			dest.set(c, vQuat.get(c), false);
			
		}
		
		dest.onChanged();
		
		return vec;
	}
	
	public Matrix asMatrix()
	{
		return this.asMatrix(MatrixHelper.createIdentityMatrix());
	}
	
	public Matrix asMatrix(Matrix dest)
	{
		return MatrixHelper.createRotationMatrix(this, dest);
	}
	
}
