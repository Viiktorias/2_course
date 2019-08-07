#include <fstream>
#include <vector>

using namespace std;

int main()
{
    ifstream fin("input.txt");
    int n, u, v;
    fin >> n;
    vector<int> matrix(n, -1);
    for (int i = 0; i < n - 1; ++i)
    {
        fin >> u >> v;
        matrix[v - 1] = u - 1;
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