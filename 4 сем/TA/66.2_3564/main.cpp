#include <fstream>
#include <vector>

using namespace std;

int main()
{
    ifstream fin("input.txt");
    int n, l;
    fin >> n;
    vector<int> matrix(n, -1);
    for (int i = 0; i < n; ++i)
    {
        for (int j = 0; j < n; ++j)
        {
            fin >> l;
            if (l == 1)
            {
                matrix[j] = i;
            }
        }
    }
    fin.close();
    ofstream fout("output.txt");
    for (int i = 0; i < n; ++i)
    {
        fout << matrix[i] + 1 << " ";
    }
    fout.close();
    return 0;
}