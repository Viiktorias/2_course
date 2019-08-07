#include <fstream>
#include <vector>

using namespace std;

int main()
{
    ifstream fin("input.txt");
    int m, c, n, x;
    fin >> m >> c >> n;
    vector<int> table(m, -1);
    for (int j = 0; j < n; ++j)
    {
        fin >> x;
        int i = 0;
        int h = ((x % m) + c * i) % m;
        while ((table[h] != -1) && (table[h] != x))
        {
            i++;
            h = ((x % m) + c * i) % m;
        }
        if (table[h] == -1)
        {
            table[h] = x;
        }
    }
    fin.close();
    ofstream fout("output.txt");
    for (int i = 0; i < m; ++i)
    {
        fout << table[i] << " ";
    }
    fout.close();
    return 0;
}