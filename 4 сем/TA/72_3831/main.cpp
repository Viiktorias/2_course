#include <iostream>
#include <vector>
#include <queue>
#include <set>

using namespace std;

int main()
{
    ios_base::sync_with_stdio(false);
    freopen("input.txt", "r", stdin);
    cin.tie(nullptr);
    freopen("output.txt", "w", stdout);
    cout.tie(nullptr);
    unsigned int n, m, w, u, v;
    cin >> n >> m;
    vector<vector<unsigned int>> matrix(n);
    vector<vector<unsigned int>> weight(n);
    for (int i = 0; i < m; ++i)
    {
        cin >> u >> v >> w;
        if (u != v)
        {
            matrix[v - 1].push_back(u - 1);
            weight[v - 1].push_back(w);
            matrix[u - 1].push_back(v - 1);
            weight[u - 1].push_back(w);
        }
    }
    vector<unsigned int> length(n, UINT32_MAX);
    set<pair<unsigned int, unsigned int>> set;
    set.insert(make_pair(0, 0));
    length[0] = 0;
    unsigned int size, l;
    while (!set.empty())
    {
        v = set.begin()->second;
        l = set.begin()->first;
        set.erase(set.begin());
        if (l == length[v])
        {
            size = matrix[v].size();
            for (int j = 0; j < size; ++j)
            {
                if (length[matrix[v][j]] > length[v] + weight[v][j])
                {
                    length[matrix[v][j]] = length[v] + weight[v][j];
                    set.insert(make_pair(length[matrix[v][j]], matrix[v][j]));
                }
            }
        }
    }
    cout << length[n - 1];
    return 0;
}