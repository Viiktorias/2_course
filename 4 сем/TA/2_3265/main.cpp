#include <iostream>
#include <vector>
#include <stack>

using namespace std;

int main()
{
    ios_base::sync_with_stdio(false);
    freopen("input.txt", "r", stdin);
    cin.tie(nullptr);
    freopen("output.txt", "w", stdout);
    cout.tie(nullptr);
    int n;
    cin >> n;
    vector<vector<int>> matrix(n, vector<int>(n));
    for (int i = 0; i < n; ++i)
    {
        for (int j = 0; j < n; ++j)
        {
            cin >> matrix[i][j];
        }
    }
    vector<int> visited(n, 0);
    int m = 0;
    stack<int> dfs;
    dfs.push(0);
    visited[0] = 1;
    int cur;
    vector<pair<int, int>> ansver;
    while (!dfs.empty())
    {
        cur = dfs.top();
        dfs.pop();
        for (int i = 0; i < n; ++i)
        {
            if ((visited[i] == 0) and (matrix[cur][i] == 1))
            {
                dfs.push(i);
                visited[i] = 1;
                visited[cur]++;
                m++;
                ansver.emplace_back(cur, i);
                if (visited[cur] == 3)
                    break;
            }
        }
    }
    if (m < n - 1)
    {
        cout << -1;
    }
    else
    {
        cout << m << '\n';
        for (int i = 0; i < m; ++i)
        {
            cout << ansver[i].first + 1 << ' ' << ansver[i].second + 1 << '\n';
        }
    }
    return 0;
}