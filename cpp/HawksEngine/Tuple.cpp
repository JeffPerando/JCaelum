/* 
 * File:   Tuple.cpp
 * Author: Elusivehawk
 *
 * Created on September 10, 2013
 */

using namespace HawksEngine;

template <typename T, typename E>

class Tuple
{
	public:
		T one;
		E two;
		Tuple();
		Tuple(T a, E b);
		virtual ~Tuple();
	
};

Tuple::Tuple(){}

Tuple::Tuple(T a, E b)
{
	one = a;
	two = b;
	
}

Tuple::~Tuple()
{
	delete one;
	delete two;
	
}

