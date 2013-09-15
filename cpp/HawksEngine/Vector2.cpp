/* 
 * File:   Vector2.cpp
 * Author: Elusivehawk
 * 
 * Created on September 10, 2013
 */

using namespace HawksEngine;

template <typename T>

class Vector2
{
	public:
		T x, y;
		Vector2();
		Vector2(T a, T b);
		virtual ~Vector2();
		virtual Vector2<T> add(Vector2<T> vec);
		virtual Vector2<T> sub(Vector2<T> vec);
		virtual Vector2<T> mul(Vector2<T> vec);

};

Vector2::Vector2(T a, T b)
{
	x = a;
	y = b;
	
}

Vector2::~Vector2()
{
	delete x;
	delete y;
	
}

Vector2<T> Vector2::add(Vector2<T> vec)
{
	x += vec.x;
	y += vec.y;
	
	return this;
}

Vector2<T> Vector2::sub(Vector2<T> vec)
{
	x -= vec.x;
	y -= vec.y;
	
	return this;
}

Vector2<T> Vector2::mul(Vector2<T> vec)
{
	x *= vec.x;
	y *= vec.y;
	
	return this;
}
