#include <iostream>
#include <vector>
#include <queue>
#include <sstream>

using namespace std;

int bfs(vector<vector<int >> &matrix, vector<int> &color, int start, bool &containsCycle, int &maximal)
{
    containsCycle = false;
    int count = 1;
    queue<int> searchQueue;
    color[start] = -1;
    searchQueue.push(start);
    int v, size;
    int countOfColor[] = {1, 0};
    while (!searchQueue.empty())
    {
        v = searchQueue.front();
        searchQueue.pop();
        size = matrix[v].size();
        for (int i = 0; i < size; ++i)
        {
            if (color[matrix[v][i]] == 0)
            {
                color[matrix[v][i]] = color[v] * -1;
                searchQueue.push(matrix[v][i]);
                count++;
                countOfColor[(1 - color[v]) / 2]++;
            }
            else if (color[matrix[v][i]] == color[v])
                containsCycle = true;
        }
    }
    if (containsCycle)
        maximal++;
    else maximal += max(countOfColor[0], countOfColor[1]);
    return count;
}

int main()
{
    ios_base::sync_with_stdio(false);
    freopen("input.txt", "r", stdin);
    cin.tie(nullptr);
    freopen("output.txt", "w", stdout);
    cout.tie(nullptr);
    int n;
    cin >> n;
    vector<vector<int >> matrix(n);
    string list;
    getline(cin, list);
    stringstream stream;
    int v;
    for (int i = 0; i < n; ++i)
    {
        getline(cin, list);
        stream = stringstream(list);
        while (stream)
        {
            stream >> v;
            if (v != 0)
                matrix[i].push_back(v - 1);
        }
    }
    vector<int> color(n, 0);
    bool containsCycle;
    int count, maximal = 0;
    count = bfs(matrix, color, 0, containsCycle, maximal);
    if ((count == n) && (containsCycle))
        cout << "YES" << '\n';
    else if (count == n)
        cout << "NO" << '\n' << maximal << '\n';
    else
    {
        for (int i = 0; i < n; ++i)
            if (color[i] == 0)
                bfs(matrix, color, i, containsCycle, maximal);
        cout << "NO" << '\n' << maximal << '\n';
    }
    return 0;
}
