// Gauss.cpp: определ€ет точку входа дл€ консольного приложени€.
//

#include "stdafx.h"
#include <iostream>
#include <fstream>
#include <cmath>
#include <iomanip>
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
	//n Ч размер квадратной матрицы, одно из чисел в пределах от 20 до 25;
	srand(time(NULL));
	int n = rand() % 6 + 20;
	int m = n + 1;
	float **A = new float*[n];
	for (int i = 0; i < n; i++)
		A[i] = new float[m];

	//вектор X
	float* x = new float[n];

	int s = 23;
	for (int i = 0; i < n; i++) {
		x[i] = s + i;
	}

	//заполн€ем матрицу элементами, кроме диагональных

	srand(time(NULL));
	for (int i = 0; i < n; i++)
		for (int j = 0; j < n; j++)
			if (i != j)
				A[i][j] = rand() % 4 * -0.01;
	for (int i = 0; i < n; i++)
		A[i][m - 1] = 0;

	//задаем диагональные элементы
	float sum;
	for (int i = 0; i < n; i++)
	{
		sum = 0;
		for (int j = 0; j < n; j++)
			if (i != j)
				sum += A[i][j];
		A[i][i] = -1*sum + 0.01;
	}

	//задаем вектор b = A * x
	for (int i = 0; i < n; i++)
		for (int j = 0; j < n; j++)
			A[i][m - 1] += A[i][j] * x[j];

	cout <<"Input: " << endl;
	matrixOut(A, n);

	//вектор приближЄнного решени€
	float *approxX = new float[n];

	bool stop = false;

	for (int k = 0; k < n - 1; k++) {
		if (stop)
			break;
		for (int i = k + 1; i < n; i++)
		{

			//макс элемент в столбце
			float maxCol = A[k][k];
			int maxColIndex = k;
			for (int h = i; h < n; h++) {
				if (fabs(A[h][k]) > fabs(A[k][k])) {
					maxCol = A[h][k];
					maxColIndex = h;
				}
			}

			if ((maxCol == 0) && (A[k][m - 1] != 0))
			{
				cout << "infinitely many solutions" << endl; //бесконечно много решений
				stop = true;
				break;
			}
			else
				if ((maxCol == 0) && (A[k][m - 1] == 0))
				{
					stop = true;
					cout << "no solutions" << endl; //нет решений
					break;
				}

			//мен€ем строки, если макс элемент не первый в столбце
			if (maxCol != A[k][k])
			{
				float* temp = A[k];
				A[k] = A[maxColIndex];
				A[maxColIndex] = temp;
			}

			//проверка, что диагональные элементы не 0
			if (A[k][k] == 0)
			{
				int indexNotNull = -1;
				for (int m = k; k < n; m++)
					if (A[m][k] != 0)
					{
						indexNotNull = m;
						break;
					}
				if (indexNotNull == -1)
					break;
				else
				{
					//мен€ем строки с ненулемым элементом
					float* temp = A[k];
					A[k] = A[indexNotNull];
					A[indexNotNull] = temp;
				}
			}

			float Lik = A[i][k] / A[k][k];
			A[i][k] = Lik;
			for (int j = k + 1; j < n; j++)
				A[i][j] = A[i][j] - Lik* A[k][j];
		}
	}

	cout << endl << "LU: " << endl;
	matrixOut(A, n);

	// L * y = b
	float *y = new float[n];

	for (int i = 0; i < n; i++) {
		float sum = 0;
		for (int j = 0; j < i; j++) {
			sum += y[j] * A[i][j];
		}
		y[i] = A[i][m - 1] - sum;
	}

	cout << "y = (" << endl;
	for (int k = 0; k < n; k++) {
		cout << y[k];
		if (k != n - 1)
			cout << ", ";
	}
	cout << ") " << endl;

	// ќбратный ход
	for (int i = n - 1; i >= 0; i--) {
		float sum = 0;
		for (int j = i + 1; j < n; j++) {
			sum += approxX[j] * A[i][j];
		}
		approxX[i] = 1 / A[i][i] * (y[i] - sum);
	}

	cout << "x* = (";
	for (int i = 0; i < n; i++) {
		cout << setprecision(17) << approxX[i];
		if (i != n - 1) {
			cout << ", ";
		}
	}
	cout << ") " << endl;

	//ќтносительна€ погрешность решени€
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