#include <fstream>
#include <vector>

using namespace std;

int main()
{
    ifstream in("input.txt");
    ofstream out("output.txt");
    unsigned int col;
    in >> col;
    int* matrixDimensions = new int[col + 1];
    in >> matrixDimensions[0];
    for (int i = 1; i < col; i++)
    {
        in >> matrixDimensions[i] >> matrixDimensions[i];
    }
    in >> matrixDimensions[col];
    vector<vector<int>> numberOfOperations;
    for (int i = 0; i < col; i++)
    {
        vector<int> temp(col - i);
        temp[0] = 0;
        numberOfOperations.push_back(temp);
    }
    int len;
    int min;
    for (int j = 1; j < col; j++)
    {
        len = col - j;
        for (int i = 0; i < len; i++)
        {
            numberOfOperations[i][j] = INT32_MAX;
            for (int k = 0; k < j; k++)
            {
                min = numberOfOperations[i][k] + numberOfOperations[k + i + 1][j - k - 1] +
                        matrixDimensions[i] * matrixDimensions[k + i + 1] * matrixDimensions[j + i + 1];
                if (min < numberOfOperations[i][j])
                    numberOfOperations[i][j] = min;
            }
        }
    }
    out << numberOfOperations[0][col - 1];
    delete[](matrixDimensions);
    in.close();
    out.close();
    return 0;
}