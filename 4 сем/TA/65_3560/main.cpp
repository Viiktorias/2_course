#include <fstream>
#include <vector>

using namespace std;

int main()
{
    ifstream fin("input.txt");
    int n, m, u, v;
    fin >> n >> m;
    vector<vector<int>> matrix(n);
    for (int i = 0; i < m; ++i)
    {
        fin >> u >> v;
        matrix[v - 1].push_back(u - 1);
        matrix[u - 1].push_back(v - 1);
    }
    fin.close();
    ofstream fout("output.txt");
    int length;
    for (int i = 0; i < n; ++i)
    {
        length = matrix[i].size();
        fout << length << " ";
        for (int j = 0; j < length; ++j)
        {
            fout << matrix[i][j] + 1 << " ";
        }
        fout << '\n';
    }
    fout.close();
    return 0;
}