// Gauss-Zeydel.cpp: ���������� ����� ����� ��� ����������� ����������.
//

#include "stdafx.h"
#include <iostream>
#include <iomanip>

using namespace std;

// ������� ��� ������ �������

void print_matrixA(float** A, int n) {
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			printf("%4.0f", A[i][j]);
		}
		cout << endl;
	}
	cout << endl;
}
void print_matrixB(float** A, int n) {
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			printf("%8.4f", A[i][j]);
		}
		cout << endl;
	}
	cout << endl;
}

// ������� ��� ������ ����������� �������

void printx_matrix(float** A, int n) {
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n + 1; j++) {
			cout << "\t" << setprecision(3) << A[i][j];
		}
		cout << endl;
	}
	cout << endl;
}

//������� ��� ������ �������

void print_vector(float* x, int n) {
	cout << "(";
	for (int i = 0; i < n - 1; i++)
		cout << setprecision(10) << x[i] << ", ";
	cout << x[n - 1] << ")" << endl << endl;
}

//������� ��� ������������� ��������
void vect_equal(float* X1, float* X2, int n) {
	for (int i = 0; i < n; i++) {
		X1[i] = X2[i];
	}
}
//������� ��� �������� ������� � ������ �� ������������� ��������
bool task(float* currx, float* prevx, int n, float eps, int kmax, int kteq) {
	if (kteq == 0)
		return false;
	if (kteq == kmax) {
		//����������
		throw kteq;
	}
	else {
		float maxsub = 0;
		for (int i = 0; i < n; i++) {
			if (abs(currx[i] - prevx[i]) > maxsub) {
				maxsub = abs(currx[i] - prevx[i]);
			}
		}
		return maxsub < eps ? true : false;
	}
}
int main()
{
	int m = 23; //����� � ������ ������������ ������
	int n = 111;
	float eps = 0.0001;
	int kmax = 1000;
	setlocale(LC_ALL, ".1251");
	// ������� ������� � � ������ x
	float* x = new float[n];
	for (int i = 0; i < n; i++)
		x[i] = (float)m + i;

	float** A = new float*[n];
	for (int i = 0; i < n; i++) {
		A[i] = new float[n + 1];
	}
	for (int i = 0;i < n;i++) {
		for (int j = 0;j< n;j++) {
			A[i][j] = 0;
		}
	}
	for (int i = 0; i < n - 1; i++) {
		A[i + 1][i] = -m;
		A[i][i + 1] = -1;
		A[i][i] = (2 + 2*(i % 2))*m;
	}
	A[n-1][n-1] = (4 - 2*(n % 2))*m;

	// ������� �������� ������� � 
	cout << "������� A:" << endl;
	print_matrixA(A, n);

	// ������ ������ f �� ������� Ax = f
	float* f = new float[n];
	for (int i = 0; i < n; i++) {
		f[i] = 0;
		for (int j = 0; j < n; j++)
			f[i] += A[i][j] * x[j];
	}
	cout << "f = ";
	print_vector(f, n);

	for (int i = 0; i < n; i++) {
		A[i][n] = f[i];
	}

	//������� �
	float** B = new float*[n];
	for (int i = 0; i < n; i++) {
		B[i] = new float[n + 1];
	}
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			if (i != j) {
				B[i][j] = -A[i][j] / A[i][i];
			}
			else {
				B[i][j] = 0;
			}
		}
	}
	cout << endl;
	cout << "������� B:" << endl;
	print_matrixB(B, n);
	//b
	float* b = new float[n];
	for (int i = 0; i < n; i++) {
		b[i] = f[i] / A[i][i];
	}

	cout << "b = ";
	print_vector(b, n);
	cout << "x = ";
	print_vector(x, n);
	cout << "m = " << m << "; n = " << n << endl;

	cout << "����� ������-�������: " << endl;
	float* currx = new float[n];
	float* prevx = new float[n];
	float* TempX = new float[n];
	for (int i = 0; i < n; i++) {
		currx[i] = f[i];
		prevx[i] = f[i];
	}
	int currk = 0;
	try {
		while (!task(currx, prevx, n, eps, kmax, currk)) {
			//���� ������� � ������ �� ������������� �������� �� �����������, �� ��������� ��������
			vect_equal(prevx, currx, n); // ������������ �������
			for (int i = 0; i < n; i++) {
				float sum1 = 0;
				float sum2 = 0;
				for (int j = 0; j < n; j++) {
					if (j < i) {
						sum1 += A[i][j] * currx[j];
					}
					else if (j > i) {
						sum2 += A[i][j] * prevx[j];
					}
				}
				currx[i] = 1 / A[i][i] * (f[i] - sum1 - sum2);
			}
			currk++;

		}
	}
	catch (int num) {
		cout << endl;
		cout << "����� �� ������������� ��������: (������� k = " << num << " ��� max k = 1000)" << endl << endl;
	}

	cout << "����������� ������ ��������:" << endl;
	print_vector(currx, n);
	cout << "����� ��������:" << currk << endl << endl;

	float err = abs(x[0] - currx[0]);
	for (int i = 1; i < n; i++)
		if (abs(x[i] - currx[i]) > err)
			err = abs(x[i] - currx[i]);
	float temp = x[0];
	for (int i = 1; i < n; i++)
		if (abs(x[i]) > temp)
			temp = abs(x[i]);
	cout << "������������� ����������� ||x - x*||/||x|| = ";
	cout << err / temp << endl << endl;

	float CountNormaV2 = 0.0f;
	for (int j = 0; j < n; j++)
		CountNormaV2 += (abs(currx[j] - x[j])*abs(currx[j] - x[j]));
	CountNormaV2 = powf(CountNormaV2, 0.5f);

	float CountNormaV3 = 0.0f;
	for (int j = 0; j < n; j++)
		CountNormaV3 += (abs(x[j])*abs(x[j]));
	CountNormaV3 = powf(CountNormaV2, 0.5f);

	float sk = -log(CountNormaV2 / CountNormaV3);
	cout << "������� ���������� ������ �� k ��������: " << sk << endl;

	system("pause");
	return 0;
}