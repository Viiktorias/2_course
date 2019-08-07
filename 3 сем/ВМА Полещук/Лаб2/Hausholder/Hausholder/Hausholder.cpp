// Hausholder.cpp: определяет точку входа для консольного приложения.
//

#include "stdafx.h"
#include <iostream>
#include <vector>
#include <cmath>
#include <iomanip>

using namespace std;

const int N = 3, M = 23;
vector<vector<float>> matrix(8);
vector<vector<float>> Q;
vector<float> d;

int sign(float x);

void generate_matrix() {
	matrix[0] = { 6 + 10 * M, 1, 0, 0 };
	matrix[1] = { 1, 2 + 10 * M + N, 1, 0 };
	matrix[2] = { 2, 0, 3 + 10 * M + N, 1 };
	matrix[3] = { 3, 0, 2, 5 + 10 * M + N };
	matrix[4] = { 2 * N, 1, 0, 0 };
	matrix[5] = { 3 * N, 2, 1, 0 };
	matrix[6] = { 4 * N, 1, 0, 1 };
	matrix[7] = { 5 * N, 0, 2, 1 };
}

vector<vector<float>> generate_I_matrix(int size) {
	vector<vector<float>> result(size);
	for (int i = 0; i < size; ++i) {
		for (int j = 0; j < size; ++j) {
			if (i == j) {
				result[i].push_back(1);
			}
			else {
				result[i].push_back(0);
			}
		}
	}
	return result;
}

float calculate_eukl_norm(vector<float> const& v) {
	float r = sign(v[0]) * v[0];
	for (int i = 1; i != v.size(); ++i) {
		r *= sqrt(1 + pow(v[i] / r, 2));
	}
	return r;
}

int sign(float x) {
	return (x >= 0) ? 1 : -1;
}

vector<float> getColumn(int row, int column) {
	vector<float> col;
	for (int i = row; i < matrix.size(); ++i) {
		col.push_back(matrix[i][column]);
	}
	return col;
}

vector<float> getColumnFromM(vector<vector<float>> matrix, int row, int column) {
	vector<float> col;
	for (int i = row; i < matrix.size(); ++i) {
		col.push_back(matrix[i][column]);
	}
	return col;
}

void writeColumn(int row, int column, vector<float>& info) {
	for (int i = row; i < matrix.size(); ++i) {
		matrix[i][column] = info[i - row];
	}
}

void writeW(int index, vector<float> w) {
	d.push_back(w[0]);
	for (int i = index + 1; i < matrix.size(); ++i) {
		matrix[i][index] = w[i];
	}
}

vector<float> calculate_w(vector<float> s) {
	float normS = calculate_eukl_norm(s);
	s[0] += sign(s[0]) * normS;
	normS = calculate_eukl_norm(s); //modifiedNorm
	for (auto& x : s) {
		x /= normS;
	}
	return s;
}


vector<float> calculate_and_write_w(int index) {
	vector<float> s = getColumn(index, index);
	s = calculate_w(s);
	writeColumn(index, index - 1, s);
	return s;
}

decltype(auto) multiplyWWt(vector<float>& w) {
	vector<vector<float>> matrix(w.size());
	for (int i = 0; i < w.size(); ++i) {
		for (int j = 0; j < w.size(); ++j) {
			matrix[i].push_back(w[i] * w[j]);
		}
	}
	return matrix;
}

decltype(auto) calculate_H(vector<float>& w) {
	vector<vector<float>> wWt = multiplyWWt(w);
	for (int i = 0; i < w.size(); ++i) {
		for (int j = 0; j < w.size(); ++j) {
			if (i == j) {
				wWt[i][j] = 1 - 2 * wWt[i][j];
			}
			else {
				wWt[i][j] *= -2;
			}
		}
	}
	return wWt;
}

vector<float> operator- (vector<float> const& v1, vector<float> const& v2) {
	vector<float> result;
	if (v1.size() == v2.size()) {
		for (int i = 0; i < v2.size(); ++i) {
			result.push_back(v1[i] - v2[i]);
		}
	}
	return result;
}
float operator* (vector<float> const& v1, vector<float> const& v2) {
	try {
		if (v1.size() != v2.size())
		{ throw "Error. Impossible to multiply"; }
	}
	catch (char* s) {
		cerr << s;
	}

	float result = 0;
	for (int i = 0; i != v1.size(); ++i) {
		result += v1[i] * v2[i];
	}
	return result;
}
vector<float> operator* (float n1, vector<float> v1) {
	for (float& temp : v1) {
		temp *= n1;
	}
	return v1;
}

vector<vector<float>> operator* (float n1, vector<vector<float>> m) {
	for (int i = 0; i < m.size(); ++i) {
		for (int j = 0; j < m[0].size(); ++j) {
			m[i][j] *= n1;
		}
	}
	return m;
}

ostream& operator<< (ostream& os, vector<vector<float>> const& vec) {
	for (auto x : vec) {
		for (auto temp : x) {
			os << setprecision(3) << setw(10) << temp << " ";
		}
		os << endl;
	}
	return os;
}
ostream& operator<< (ostream& os, vector<float> const& vec) {
	for (auto x : vec) {
		os << x << " ";
	}
	cout << endl;
	return os;
}

//multiply only matrix of the same size. Different sizes are not supposed.
decltype(auto) operator*(vector<vector<float>> const& v1, vector<vector<float>> const& v2) {
	vector<vector<float>> result(v1.size());
	if (v1.size() == v2.size()) {
		for (int i = 0; i < v1.size(); ++i) {
			for (int j = 0; j < v2[0].size(); ++j) {
				//get column
				vector<float> columnV2;
				for (auto x : v2) {
					columnV2.push_back(x[j]);
				}

				result[i].push_back(v1[i] * columnV2);
			}
		}
	}
	return result;
}

vector<float> operator* (vector<float> const& v1, vector<vector<float>> const& v2) {
	vector<float> result, col;
	for (int i = 0; i < v2[0].size(); ++i) {
		for (int j = 0; j < v2.size(); ++j) {
			col.push_back(v2[j][i]);
		}
		result.push_back(v1 * col);
		col.clear();
	}
	return result;
}

//multiplying col * row
vector<vector<float>> operator* (vector<vector<float>> m, vector<float> v) {
	vector<vector<float>> result(m.size());
	for (int i = 0; i < m.size(); ++i) {
		for (int j = 0; j < v.size(); ++j) {
			result[i].push_back(m[i][0] * v[j]);
		}
	}
	return result;
}

vector<vector<float>> operator- (vector<vector<float>> m1, vector<vector<float>> m2) {
	for (int i = 0; i < m1.size(); ++i) {
		for (int j = 0; j < m1[0].size(); ++j) {
			m1[i][j] -= m2[i][j];
		}
	}
	return m1;
}


vector<vector<float>> transposeVec(vector<float> vec) {
	vector<vector<float>> result(vec.size());
	for (int i = 0; i < vec.size(); ++i) {
		result[i].push_back(vec[i]);
	}
	return result;
}

int main() {

	generate_matrix();
	cout << "Matrix A(0):\n" << matrix << endl;

	auto w = calculate_w(getColumn(0, 0));
	vector<float> x1 = w * matrix;
	vector<vector<float>> y1 = transposeVec(w) * x1;
	y1 = 2 * y1;
	matrix = matrix - y1;



	d = w;
	Q = calculate_H(w);
	cout << "Matrix H(0):\n" << Q << endl;

	//point 1
	cout << "Matrix A(1):\n" << matrix << endl;
	cout << "d[0] = " << d[0] << endl << endl;


	for (int k = 1; k < matrix[0].size() - 1; ++k) {
		vector<float> w = calculate_w(getColumn(k, k));
		vector<float> wCopy(8 - w.size());
		for (float temp : w) {
			wCopy.push_back(temp);
		}
		vector<float> x = wCopy * matrix;
		vector<vector<float>> y = transposeVec(wCopy) * x;
		y = 2 * y;
		matrix = matrix - y;
		auto Hk = calculate_H(w);
		auto Hn = generate_I_matrix(8);

		int firstI = matrix.size() - Hk.size();
		for (int i = firstI; i < matrix.size(); ++i) {
			for (int j = firstI; j < matrix.size(); ++j) {
				Hn[i][j] = Hk[i - firstI][j - firstI];
			}
		}

		Q = Hn * Q;
		cout << "Matrix H(" << k + 1 << "):\n" << Hn << endl;
		cout << "Matrix A(" << k + 1 << "):\n" << matrix << endl;
	}

	//point 2
	cout << "Matrix A(n-1) matrix:\n" << matrix;
	cout << "vector d : " << d;
	cout << "Matrix R:\n";
	for (int i = 0; i < matrix.size(); ++i) {
		for (int j = 0; j < matrix[0].size(); ++j) {
			if (i > j) { cout << setprecision(3) << setw(10) << 0 << " "; }
			else { cout << setprecision(3) << setw(10) << matrix[i][j] << " "; }
		}
		cout << endl;
	}
	cout << endl;

	//point 3
	vector<float> temp;
	cout << "Norms vector-columns of Q: ";
	for (int i = 0; i < 8; ++i) {
		for (auto& q : Q) {
			temp.push_back(q[i]);
		}
		cout << calculate_eukl_norm(temp) << " ";
		temp.clear();
	}
	cout << endl << endl;


	cout << "Norms vector-columns of A(n-1): ";
	for (int i = 0; i < matrix[0].size(); ++i) {
		cout << calculate_eukl_norm(getColumn(0, i)) << " ";
	}
	cout << endl << endl;


	cout << "Q: \n" << Q << endl;

	//point 4
	cout << "Scalar products of the last column of Q and others: ";
	temp = getColumnFromM(Q, 0, Q.size() - 1);
	for (int i = 0; i < Q[0].size() - 1; ++i) {
		cout << setprecision(3) << setw(12) << temp * getColumnFromM(Q, 0, i);
	}

	cout << endl;
	system("pause");
	return 0;
}