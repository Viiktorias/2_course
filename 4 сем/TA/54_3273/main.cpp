#include <fstream>
#include <vector>
#include <queue>

using namespace std;

int main()
{
    ifstream fin("input.txt");
    ofstream fout("output.txt");
    int n, m, q, v, k;
    fin >> n >> m;
    vector<vector<int>> graph(2 * n);
    for (int i = 0; i < m; ++i)
    {
        fin >> v >> k;
        graph[(v - 1) * 2].push_back((k - 1) * 2 + 1);
        graph[(k - 1) * 2].push_back((v - 1) * 2 + 1);
        graph[(v - 1) * 2 + 1].push_back((k - 1) * 2);
        graph[(k - 1) * 2 + 1].push_back((v - 1) * 2);
    }
    vector<int> metka(2 * n, -1);
    queue<int> queue;
    queue.push(0);
    metka[0] = 0;
    while (!queue.empty())
    {
        int i = queue.front();
        queue.pop();
        for (auto j : graph[i])
        {
            if (metka[j] == -1)
            {
                queue.push(j);
                metka[j] = metka[i] + 1;
            }
        }
    }
    fin >> q;
    bool isolated = graph[0].empty();
    for (int i = 0; i < q; ++i)
    {
        fin >> v >> k;
        if (k % 2 == 0)
            v = 2 * v - 2;
        else
            v = 2 * v - 1;
        if (!isolated or v != 0 or k == 0)
        {
            if (metka[v] <= k and metka[v] != -1)
                fout << "Yes" << endl;
            else
                fout << "No" << endl;
        }
        else
            fout << "No" << endl;
    }
    fin.close();
    fout.close();
    return 0;
}
/*
#include <iostream>
#include <vector>
#include <queue>

using namespace std;

int main()
{
    ios_base::sync_with_stdio(false);
    freopen("input.txt", "r", stdin);
    cin.tie(nullptr);
    freopen("output.txt", "w", stdout);
    cout.tie(nullptr);
    int n, m;
    cin >> n >> m;
    vector<vector<unsigned short>> matrix(n, vector<unsigned short>());
    int x, y;
    for (int i = 0; i < m; ++i)
    {
        cin >> x >> y;
        x--;
        y--;
        matrix[x].emplace_back(y);
        matrix[y].emplace_back(x);
    }
    int q;
    cin >> q;
    vector<pair<unsigned short, int>> games(q); //v, k
    for (int i = 0; i < q; ++i)
    {
        cin >> games[i].first >> games[i].second;
        games[i].first--;
    }
    queue<pair<unsigned short, int>> bfs; //номер, длина пути
    vector<vector<int>> length(2, vector<int>(n, INT32_MAX));
    bfs.emplace(0, 0);
    length[0][0] = 0;
    int len;
    unsigned short num;
    unsigned long size;
    while (!bfs.empty())
    {
        num = bfs.front().first;
        len = bfs.front().second;
        bfs.pop();
        size = matrix[num].size();
        for (int i = 0; i < size; ++i)
        {
            if (length[(len + 1) % 2][matrix[num][i]] == INT32_MAX)
            {
                bfs.emplace(matrix[num][i], len + 1);
                length[(len + 1) % 2][matrix[num][i]] = len + 1;
            }
        }
    }
    q--;
    for (int i = 0; i < q; ++i)
    {
        if ((games[i].first != 0) || (games[i].second % 2 != 0) || (games[i].second == 0))
        {
            if (length[games[i].second % 2][games[i].first] > games[i].second)
                cout << "No\n";
            else
                cout << "Yes\n";
        }
        else
        {
            if ((matrix[0].empty()) || (length[0][games[i].first] > games[i].second))
                cout << "No\n";
            else
                cout << "Yes\n";
        }
    }
    if ((games[q].first != 0) || (games[q].second % 2 != 0) || (games[q].second == 0))
    {
        if (length[games[q].second % 2][games[q].first] > games[q].second)
            cout << "No";
        else
            cout << "Yes";
    }
    else
    {
        if ((matrix[0].empty()) || (length[0][games[q].first] > games[q].second))
            cout << "No";
        else
            cout << "Yes";
    }
    return 0;
}*/
