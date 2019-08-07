#pragma once
#include "iostream"
#include "fstream"
#include <assert.h>
#include <string>
using namespace std;

class Fraction
{
	friend const Fraction& operator + (const int &left, const Fraction &right);
	friend const Fraction& operator - (const int &left, const Fraction &right);
	friend const Fraction& operator * (const int &left, const Fraction &right);
	friend const Fraction& operator / (const int &left, const Fraction &right);
	friend istream& operator >> (istream &in, Fraction &right);
	friend ostream& operator << (ostream &out, const Fraction &right);
private:
	int numerator;
	int denominator;
	const Fraction& reduct();
public:
	Fraction() {};
	Fraction(int num, int den);
	Fraction(int num);
	Fraction(const Fraction &init);
	Fraction(const string &init);
	Fraction& operator = (const Fraction &right);
	Fraction operator + (const Fraction &right)const;
	Fraction operator - (const Fraction &right)const;
	Fraction operator * (const Fraction &right)const;
	Fraction operator / (const Fraction &right)const;
	Fraction& operator += (const Fraction &right);
	Fraction& operator -= (const Fraction &right);
	Fraction& operator *= (const Fraction &right);
	Fraction& operator /= (const Fraction &right);
	Fraction& operator += (const int &right);
	Fraction& operator -= (const int &right);
	Fraction& operator *= (const int &right);
	Fraction& operator /= (const int &right);
	Fraction operator + (const int &right)const;
	Fraction operator - (const int &right)const;
	Fraction operator * (const int &right)const;
	Fraction operator / (const int &right)const;
	bool operator == (const int &right)const;
	bool operator >= (const int &right)const;
	bool operator <= (const int &right)const;
	bool operator != (const int &right)const;
	bool operator > (const int &right)const;
	bool operator < (const int &right)const;
	bool operator == (const Fraction &right)const;
	bool operator >= (const Fraction &right)const;
	bool operator <= (const Fraction &right)const;
	bool operator != (const Fraction &right)const;
	bool operator > (const Fraction &right)const;
	bool operator < (const Fraction &right)const;
	friend string to_string(const Fraction &right) { return (to_string(right.numerator) + "/" + to_string(right.denominator)); }
	~Fraction() {};
};