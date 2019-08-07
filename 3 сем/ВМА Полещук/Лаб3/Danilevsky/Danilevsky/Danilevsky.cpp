#include "stdafx.h"
#include <iostream>
#include <iomanip>
#include<vector>
using namespace std;

void showMatrix(vector<vector<float>> A, int n) {
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			cout << A[i][j] << " ";
		}
		cout << endl;
	}
	cout << endl;
}

vector<vector<float>> copyMatrix(vector<vector<float>>  A, int n) {
	vector<vector<float>> copyA(n);
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			copyA[i].push_back(A[i][j]);
		}
	}
	return copyA;
}
vector<vector<float>>  matrixMk(vector<vector<float>>  A, int n, int k) {
	float a = A[k][k - 1];
	vector<vector<float>> Mk(n);
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			if (j == i) {
				if (j == k - 1) {
					Mk[i].push_back(1 / a);
				}
				else {
					Mk[i].push_back(1);
				}
			}
			else if (i == k - 1) {
				Mk[i].push_back(-A[i + 1][j] / a);
			}
			else {
				Mk[i].push_back(0);
			}
		}
	}
	return Mk;
}
vector<vector<float>>  matrixMk_1(vector<vector<float>>  A, int n, int k) {
	vector<vector<float>>  Mk_1(n);
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			if (j == i) {
				if (j == k - 1) {
					Mk_1[i].push_back(A[i + 1][j]);
				}
				else {
					Mk_1[i].push_back(1);
				}
			}
			else if (i == k - 1) {
				Mk_1[i].push_back(A[i + 1][j]);
			}
			else {
				Mk_1[i].push_back(0);
			}
		}
	}
	return Mk_1;
}

int main()
{
	setlocale(LC_ALL, "Rus");
	int n = 17;
	int m = 23;
	float koeff = n*(n - 1);
	float d = 2 * m;
	vector<vector<float>>  A(n);
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			if (j < i) {
				A[i].push_back((n - i + j) / koeff);
			}
			else if (j > i) {
				A[i].push_back((n + i - j) / koeff);
			}
			else {
				A[i].push_back(d);
				d += 10;
			}
		}
	}
	cout << "Матрица A :" << endl;
	showMatrix(A, n);

	float sum;

	cout << "Метод Данилевского: " << endl << endl;
	for (int k = n - 1; k >= 1; k--) {
		vector<vector<float>>  Mk = matrixMk(A, n, k);
		vector<vector<float>> Mk_1 = matrixMk_1(A, n, k);
		for (int i = 0; i < n; i++) {
			sum = 0.0;
			for (int j = 0; j < n; j++) {
				sum += Mk_1[k - 1][j] * A[j][i];
			}
			A[k - 1][i] = sum;
		}
		vector<vector<float>> copyA = copyMatrix(A, n);
		for (int p = 0; p < n; p++) {
			for (int i = 0; i < n; i++) {
				sum = 0.0;
				if (i == k - 1) {
					sum += copyA[p][k - 1] * Mk[k - 1][k - 1];
				}
				else {
					sum += copyA[p][i];
					sum += copyA[p][k - 1] * Mk[k - 1][i];
				}
				if (p == k && i == k - 1) {
					A[p][i] = 1;
				}
				else if (p == k && i != k - 1) {
					A[p][i] = 0;
				}
				else {
					A[p][i] = sum;
				}
			}
		}
	}
	showMatrix(A, n);

	cout << endl << "Коэффициенты характеристического многочлена матрицы:" << endl;
	int sign = (n % 2) ? 1 : -1;
	cout << sign << " ";
	for (int i = 0; i < n; i++) {
		cout << sign*A[0][i] << " ";
	}
	cout << endl;
	system("pause");
	return 0;
}