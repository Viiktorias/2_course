#define _CRT_SECURE_NO_WARNINGS

#include "Fraction.h"
#include <assert.h>
#include <ctime>

const Fraction & Fraction::reduct()
{
	assert(denominator != 0);
	if (denominator < 0)
	{
		numerator = -numerator;
		denominator = -denominator;
	}
	if (numerator != 0)
	{
		int a = numerator, b = denominator;
		if (a < 0)
			a = -a;
		while (b)
		{
			a %= b;
			swap(a, b);
		}
		numerator /= a;
		denominator /= a;
	}
	else
		denominator = 1;
	return *this;
}

Fraction::Fraction(int num, int den)
{
	numerator = num;
	denominator = den;
	this->reduct();
}

Fraction::Fraction(int num)
{
	numerator = num;
	denominator = 1;
}

Fraction::Fraction(const Fraction & init)
{
	numerator = init.numerator;
	denominator = init.denominator;
}

Fraction::Fraction(const string & init)
{
	int pos = init.find('/');
	try
	{
		if (pos == -1)
		{
			numerator = stoi(init);
			denominator = 1;
		}
		else
		{
			if (pos != 0)
			{
				numerator = stoi(init);
				if (pos + 1 < init.length())
					denominator = stoi(init.substr(pos + 1, init.length() - 1 - pos));
				else
					denominator = 1;
			}
			else
			{
				denominator = stoi(init.substr(pos + 1, init.length() - 1 - pos));
				numerator = 1;
			}
		}
	}
	catch (...)
	{
		numerator = 0;
		denominator = 1;
	}
	reduct();
}

Fraction & Fraction::operator=(const Fraction & right)
{
	numerator = right.numerator;
	denominator = right.denominator;
	return *this;
}

Fraction Fraction::operator+(const Fraction & right)const
{
	Fraction *temp = new Fraction(numerator*right.denominator + right.numerator*denominator, denominator*right.denominator);
	return *temp;
}

Fraction Fraction::operator-(const Fraction & right)const
{
	Fraction *temp = new Fraction(numerator*right.denominator - right.numerator*denominator, denominator*right.denominator);
	return *temp;
}

Fraction Fraction::operator*(const Fraction & right)const
{
	Fraction *temp = new Fraction(numerator*right.numerator, denominator*right.denominator);
	return *temp;
}

Fraction Fraction::operator/(const Fraction & right)const
{
	Fraction *temp = new Fraction(numerator*right.denominator, denominator*right.numerator);
	return *temp;
}

Fraction & Fraction::operator+=(const Fraction & right)
{
	numerator = numerator*right.denominator + right.numerator*denominator;
	denominator *= right.denominator;
	this->reduct();
	return *this;
}

Fraction & Fraction::operator-=(const Fraction & right)
{
	numerator = numerator*right.denominator - right.numerator*denominator;
	denominator *= right.denominator;
	this->reduct();
	return *this;
}

Fraction & Fraction::operator*=(const Fraction & right)
{
	numerator *= right.numerator;
	denominator *= right.denominator;
	this->reduct();
	return *this;
}

Fraction & Fraction::operator/=(const Fraction & right)
{
	numerator *= right.denominator;
	denominator *= right.numerator;
	this->reduct();
	return *this;
}

Fraction & Fraction::operator+=(const int & right)
{
	numerator = numerator + right*denominator;
	this->reduct();
	return *this;
}

Fraction & Fraction::operator-=(const int & right)
{
	numerator = numerator - right*denominator;
	this->reduct();
	return *this;
}

Fraction & Fraction::operator*=(const int & right)
{
	numerator *= right;
	this->reduct();
	return *this;
}

Fraction & Fraction::operator/=(const int & right)
{
	denominator *= right;
	this->reduct();
	return *this;
}

Fraction Fraction::operator+(const int & right)const
{
	Fraction *temp = new Fraction(numerator + right*denominator, denominator);
	return *temp;
}

Fraction Fraction::operator-(const int & right)const
{
	Fraction *temp = new Fraction(numerator - right*denominator, denominator);
	return *temp;
}

Fraction Fraction::operator*(const int & right)const
{
	Fraction *temp = new Fraction(numerator*right, denominator);
	return *temp;
}

Fraction Fraction::operator/(const int & right)const
{
	Fraction *temp = new Fraction(numerator, denominator*right);
	return *temp;
}

const Fraction & operator+(const int & left, const Fraction & right)
{
	Fraction *temp = new Fraction(right.numerator + left*right.denominator, right.denominator);
	return *temp;
}

const Fraction & operator-(const int & left, const Fraction & right)
{
	Fraction *temp = new Fraction(right.numerator - left*right.denominator, right.denominator);
	return *temp;
}

const Fraction & operator*(const int & left, const Fraction & right)
{
	Fraction *temp = new Fraction(right.numerator*left, right.denominator);
	return *temp;
}

const Fraction & operator/(const int & left, const Fraction & right)
{
	Fraction *temp = new Fraction(right.denominator, right.numerator*left);
	return *temp;
}

istream & operator >> (istream & in, Fraction & right)
{
	string init;
	in >> init;
	int pos = init.find('/');
	try
	{
		if (pos == -1)
		{
			right.numerator = stoi(init);
			right.denominator = 1;
		}
		else
		{
			if (pos != 0)
			{
				right.numerator = stoi(init);
				if (pos + 1 < init.length())
					right.denominator = stoi(init.substr(pos + 1, init.length() - 1 - pos));
				else
					right.denominator = 1;
			}
			else
			{
				right.denominator = stoi(init.substr(pos + 1, init.length() - 1 - pos));
				right.numerator = 1;
			}
		}
	}
	catch (...)
	{
		right.numerator = 0;
		right.denominator = 1;
	}
	right.reduct();
	return in;
}

ostream & operator<<(ostream & out, const Fraction & right)
{
	out << right.numerator << "/" << right.denominator;
	return out;
}

bool Fraction::operator==(const int & right) const
{
	return (numerator == denominator*right) ? true : false;
}

bool Fraction::operator>=(const int & right) const
{
	return (numerator >= denominator*right) ? true : false;
}

bool Fraction::operator<=(const int & right) const
{
	return (numerator <= denominator*right) ? true : false;
}

bool Fraction::operator!=(const int & right) const
{
	return (numerator != denominator*right) ? true : false;
}

bool Fraction::operator>(const int & right) const
{
	return (numerator > denominator*right) ? true : false;
}

bool Fraction::operator<(const int & right) const
{
	return (numerator < denominator*right) ? true : false;
}

bool Fraction::operator==(const Fraction & right) const
{
	return (numerator*right.denominator == denominator*right.numerator) ? true : false;
}

bool Fraction::operator>=(const Fraction & right) const
{
	return (numerator*right.denominator >= denominator*right.numerator) ? true : false;
}

bool Fraction::operator<=(const Fraction & right) const
{
	return (numerator*right.denominator <= denominator*right.numerator) ? true : false;
}

bool Fraction::operator!=(const Fraction & right) const
{
	return (numerator*right.denominator != denominator*right.numerator) ? true : false;
}

bool Fraction::operator>(const Fraction & right) const
{
	return (numerator*right.denominator > denominator*right.numerator) ? true : false;
}

bool Fraction::operator<(const Fraction & right) const
{
	return (numerator*right.denominator < denominator*right.numerator) ? true : false;
}