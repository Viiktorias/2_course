#include <fstream>
#include <vector>

using namespace std;

int main()
{
    bool isSimple = true, isMulti = true;
    ifstream fin("input.txt");
    int n, m, v, u;
    fin >> n >> m;
    vector<vector<int>> matrix(n, vector<int>(n, 0));
    for (int i = 0; i < m; ++i)
    {
        fin >> v >> u;
        if (v == u)
        {
            isMulti = false;
            isSimple = false;
            break;
        }
        if (matrix[v - 1][u - 1] != 0)
            isSimple = false;
        matrix[v - 1][u - 1]++;
        matrix[u - 1][v - 1]++;
    }
    fin.close();
    ofstream fout("output.txt");
    fout << (isSimple ? "Yes" : "No") << endl;
    fout << (isMulti ? "Yes" : "No") << endl;
    fout << "Yes";
    return 0;
}