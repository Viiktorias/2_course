#include <fstream>
#include <vector>
#include <queue>

using namespace std;

int isAll(vector<int> &metka, int n)
{
    int i;
    for (i = 0; ((i < n) && (metka[i] != 0)); i++)
    {
    }
    return i;
}

int main()
{
    ifstream fin("input.txt");
    int n;
    fin >> n;
    int curmetka = 1;
    vector<vector<int>> matrix(n, vector<int>(n));
    vector<int> metka(n, 0);
    for (int i = 0; i < n; ++i)
    {
        for (int j = 0; j < n; ++j)
        {
            fin >> matrix[i][j];
        }
    }
    fin.close();
    queue<int> queue;
    int i = 0;
    while (i != n)
    {
        queue.push(i);
        while (!queue.empty())
        {
            int cur = queue.front();
            queue.pop();
            metka[cur] = curmetka;
            curmetka++;
            for (int j = 0; j < n; ++j)
            {
                if ((matrix[cur][j] == 1) && (metka[j] == 0))
                {
                    queue.push(j);
                    metka[j] = -1;
                }
            }
        }
        i = isAll(metka, n);
    }
    ofstream fout("output.txt");
    for (int j = 0; j < n; ++j)
    {
        fout << metka[j] << " ";
    }
    fout.close();
    return 0;
}