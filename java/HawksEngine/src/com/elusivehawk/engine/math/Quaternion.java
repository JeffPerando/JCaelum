
package com.elusivehawk.engine.math;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Quaternion
{
	public float x, y, z, w;
	
	public Quaternion()
	{
		reset();
		
	}
	
	public Quaternion(float x, float y, float z, float w)
	{
		set(x, y, z, w);
		
	}
	
	public Quaternion(float angle, Vector3f vec)
	{
		float s = (float)Math.sin(angle / 2);
		
		x = vec.x * s;
		y = vec.y * s;
		z = vec.z * s;
		w = (float)Math.cos(angle / 2);
		
	}
	
	public Quaternion(Quaternion q)
	{
		set(q);
		
	}
	
	@Override
	public Quaternion clone()
	{
		return new Quaternion(this);
	}
	
	public Quaternion set(Quaternion q)
	{
		return this.set(q.x, q.y, q.z, q.w);
	}
	
	public Quaternion set(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		
		return this;
	}
	
	public Quaternion reset()
	{
		this.x = this.y = this.z = 0;
		this.w = 1;
		
		return this;
	}
	
	public float length()
	{
		return (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
	}
	
	public Quaternion normalize()
	{
		float length = length();
		
		this.x /= length;
		this.y /= length;
		this.z /= length;
		this.w /= length;
		
		return this;
	}
	
	public float dot(Quaternion q)
	{
		return this.x * q.x + this.y * q.y + this.z * q.z + this.w * q.w;
	}
	
	public Quaternion add(Quaternion q)
	{
		return add(q.x, q.y, q.z, q.w);
	}
	
	public Quaternion add(float x, float y, float z, float w)
	{
		this.x += x;
		this.y += y;
		this.z += z;
		this.w += w;
		
		return this;
	}

	public Quaternion sub(Quaternion q)
	{
		return sub(q.x, q.y, q.z, q.w);
	}
	
	public Quaternion sub(float x, float y, float z, float w)
	{
		this.x -= x;
		this.y -= y;
		this.z -= z;
		this.w -= w;
		
		return this;
	}
	
	public Quaternion mul(float f)
	{
		this.x *= f;
		this.y *= f;
		this.z *= f;
		this.w *= f;
		
		return this;
	}
	
	public Vector3f mul(Vector3f v)
	{
		Vector3f quatVector = new Vector3f(this.x, this.y, this.z);
		
		Vector3f uv = quatVector.cross(v);
		Vector3f uuv = quatVector.cross(uv);
		
		float m = this.w * 2;
		
		uv.mul(new Vector3f(m, m, m));
		uuv.mul(2);
		
		return new Vector3f(v).add(uv).add(uuv);
	}
	
	public Quaternion mul(Quaternion q)
	{
		float xx = w * q.x + x * q.w + y * q.z - z * q.y;
		float yy = w * q.y + y * q.w + z * q.x - x * q.z;
		float zz = w * q.z + z * q.w + x * q.y - y * q.x;
		float ww = w * q.w - x * q.x - y * q.y - z * q.z;
		
		this.x = xx;
		this.y = yy;
		this.z = zz;
		this.w = ww;
		
		return this;
	}
	
	public Quaternion conjugate()
	{
		this.x *= -1;
		this.y *= -1;
		this.z *= -1;
		
		return this;
	}
	
	public Quaternion inverse()
	{
		return normalize().conjugate();
	}
	
	public Matrix toMatrix()
	{
		float[] m = {
				1 - 2 * y * y - 2 * z * z, 2 * x * y + 2 * w * z, 2 * x * z - 2 * w * y, 0,
				2 * x * y - 2 * w * z, 1 - 2 * x * x - 2 * z * z, 2 * y * z + 2 * w * x, 0,
				2 * x * z + 2 * w * y, 2 * y * z - 2 * w * x, 1 - 2 * x * x - 2 * y * y, 0,
				0, 0, 0, 1,
		};
		
		return new Matrix(m, 4, 4);
	}
	
}
