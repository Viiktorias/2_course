// Holetsky.cpp: определяет точку входа для консольного приложения.
//

#include "stdafx.h"
#include <iostream>
#include <iomanip>
#include <cmath>
#include <ctime>
using namespace std;

void matrixOut(float ** A, int n)
{
	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n + 1; j++)
			cout << setprecision(5) << A[i][j] << " ";
		cout << endl;
	}
	cout << endl;
}

int main()
{
	srand(time(NULL));
	int n = rand() % 6 + 20;
	int m = n + 1;
	float **B = new float*[n];
	for (int i = 0; i < n; i++)
		B[i] = new float[n];

	float* x = new float[n];
	int s = 23;
	cout << "x: " << endl;
	for (int i = 0; i < n; i++)
	{
		x[i] = s + i;
		cout << x[i] << "  ";
	}

	cout << endl;

	//симметричная матрица В без диагональных элементов
	srand(time(NULL));
	for (int i = 0; i < n; i++)
		for (int j = i; j < n; j++)
		{
			B[i][j] = -1 * rand() % 5;
			B[j][i] = B[i][j];
		}

	//диагональные элементы
	float sum;
	for (int i = 0; i < n; i++)
	{
		sum = 0;
		for (int j = 0; j < n; j++)
			if (i != j)
				sum += B[i][j];
		B[i][i] = -1*sum + 1;
	}

	// A = B * B
	float **A = new float*[n];
	for (int i = 0; i < n; i++)
		A[i] = new float[m];

	for (int i = 0; i < n; i++)
		for (int j = 0; j < n; j++)
		{
			float sum = 0;
			for (int k = 0; k < n; k++)
				sum += B[i][k] * B[k][j];
			A[i][j] = sum;
		}
	for (int i = 0; i < n; i++)
		A[i][m - 1] = 0;

	//d
	for (int i = 0; i < n; i++)
		for (int j = 0; j < n; j++)
			A[i][m - 1] += A[i][j] * x[j];
	cout << "Input: "<< endl;
	matrixOut(A, n);

	//LDLT
	float * t = new float[n];

	for (int k = 0; k < n - 1; k++)
		for (int i = k + 1; i < n; i++)
		{
			t[i] = A[i][k];
			float Lik = A[i][k] / A[k][k];
			A[i][k] = Lik;
			for (int j = k + 1; j <= i; j++)
				A[i][j] = A[i][j] - Lik* t[j];
		}
	for (int i = 0; i < n; i++)
		for (int j = i + 1; j < n; j++)
			A[i][j] = 0;
	cout << "LDLT: " << endl;
	matrixOut(A, n);

	//y, L * y = d  
	float * y = new float[n];

	for (int i = 0; i < n; i++)
	{
		float sum = 0;
		for (int j = 0; j < i; j++)
			sum += y[j] * A[i][j];
		y[i] = A[i][m - 1] - sum;
	}
	cout << "y = (" << endl;
	for (int k = 0; k < n; k++) {
		cout << y[k];
		if (k != n - 1)
			cout << ", ";
	}
	cout << ") " << endl;


	//транспонирование
	for (int i = 0; i < n; i++)
		for (int j = i; j < n; j++)
		{
			float temp = A[j][i];
			A[j][i] = A[i][j];
			A[i][j] = temp;
		}

	float ** DLT = new float*[n];
	for (int i = 0; i < n; i++)
		DLT[i] = new float[n];

	//D*LT
	for (int i = 0; i < n; i++)
		for (int j = 0; j < n; j++)
		{
			if (i == j)
				DLT[i][j] = A[i][j];
			else
				DLT[i][j] = A[i][j] * A[i][i];
		}

	//x, C * x = y
	float * approxX = new float[n];

	for (int i = n - 1; i >= 0; i--)
	{
		float sum = 0;
		for (int j = n - 1; j > i; j--)
			sum += approxX[j] * DLT[i][j];
		approxX[i] = (y[i] - sum) / DLT[i][i];
	}

	cout << "x*: (" << endl;
	for (int i = 0; i < n; i++)
	{
		cout << setprecision(17) << approxX[i];
		if (i != n - 1)
			cout << ", ";
	}
	cout << ") " << endl;

	// Относительная погрешность решения

	float approxNorm = 0;
	float diffNorm = 0;

	for (int i = 0; i < n; i++)
	{
		if (fabs(approxX[i]) > approxNorm)
			approxNorm = fabs(approxX[i]);
		if (fabs(approxX[i] - x[i]) > diffNorm)
			diffNorm = fabs(approxX[i] - x[i]);
	}

	cout << "Relative error in norms: " << diffNorm / approxNorm << endl;
	system("pause");
	return 0;
}