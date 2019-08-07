#include <fstream>
#include <vector>

using namespace std;

int main()
{
    ifstream fin("input.txt");
    int n, m, u, v;
    fin >> n >> m;
    vector<vector<int>> matrix(n, vector<int>(n, 0));
    for (int i = 0; i < m; ++i)
    {
        fin >> u >> v;
        matrix[u - 1][v - 1] = matrix[v - 1][u - 1] = 1;
    }
    fin.close();
    ofstream fout("output.txt");
    for (int i = 0; i < n; ++i)
    {
        for (int j = 0; j < n; ++j)
        {
            fout << matrix[i][j] << " ";
        }
        fout << '\n';
    }
    fout.close();
    return 0;
}