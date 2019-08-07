#include <iostream>
#include <vector>
#include <set>

using namespace std;

int main()
{
    ios_base::sync_with_stdio(false);
    freopen("input.txt", "r", stdin);
    cin.tie(nullptr);
    freopen("output.txt", "w", stdout);
    cout.tie(nullptr);
    int n, m, u, v;
    cin >> n;
    vector<int> p(n), d(n);
    for (int i = 0; i < n; ++i)
    {
        cin >> p[i] >> d[i];
    }
    cin >> m;
    vector<int> matrix(n, 0);
    vector<vector<int>> reverse_matrix(n);
    for (int i = 0; i < m; ++i)
    {
        cin >> u >> v;
        matrix[u - 1]++;
        reverse_matrix[v - 1].push_back(u - 1);
    }
    vector<int> sequence;
    sequence.reserve(n);
    set<pair<int, int>, greater<>> free_nodes;
    for (int i = 0; i < n; ++i)
    {
        if (matrix[i] == 0)
            free_nodes.insert(make_pair(d[i], i));
    }
    long size;
    while (!free_nodes.empty())
    {
        v = free_nodes.begin()->second;
        free_nodes.erase(free_nodes.begin());
        sequence.push_back(v);
        size = reverse_matrix[v].size();
        for (int i = 0; i < size; ++i)
        {
            matrix[reverse_matrix[v][i]]--;
            if (matrix[reverse_matrix[v][i]] == 0)
                free_nodes.insert(make_pair(d[reverse_matrix[v][i]], reverse_matrix[v][i]));
        }
    }
    long long mx = -1, c = 0;
    v = 0;
    for (int i = n - 1; i > -1; --i)
    {
        c += p[sequence[i]];
        if (max(c - d[sequence[i]], 0ll) > mx)
        {
            mx = max(c - d[sequence[i]], 0ll);
            v = sequence[i];
        }
    }
    cout << v + 1 << " " << mx << '\n';
    for (int i = n - 1; i > 0; --i)
    {
        cout << sequence[i] + 1 << '\n';
    }
    cout << sequence[0] + 1;
    return 0;
}
