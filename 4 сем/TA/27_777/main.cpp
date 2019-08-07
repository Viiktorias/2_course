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
    int n, m, k, c, p;
    cin >> n >> m;
    vector<int> col_of_arcs(n, 0);
    vector<vector<int>> reverse_graph(n);
    char del;
    for (int i = 0; i < n; ++i)
    {
        cin >> k >> del >> c;
        for (int j = 0; j < c; ++j)
        {
            cin >> del >> p;
            col_of_arcs[p - 1]++;
            reverse_graph[k - 1].push_back(p - 1);
        }
    }
    vector<int> sequence;
    sequence.reserve(n);
    priority_queue<pair<int, int>, vector<pair<int, int>>, greater<>> freeDetails;
    freeDetails.emplace(reverse_graph[m - 1].size(), -1 * (m - 1));
    long size;
    while (!freeDetails.empty())
    {
        k = -1 * freeDetails.top().second;
        freeDetails.pop();
        sequence.push_back(k);
        size = reverse_graph[k].size();
        for (int i = 0; i < size; ++i)
        {
            col_of_arcs[reverse_graph[k][i]]--;
            if (col_of_arcs[reverse_graph[k][i]] == 0)
                freeDetails.emplace(reverse_graph[reverse_graph[k][i]].size(), -1 * reverse_graph[k][i]);
        }
    }
    long long cost = 0, col = 0;
    n = sequence.size();
    for (int i = n - 1; i > -1; --i)
    {
        //cost += col;
        cost = max(cost, col);
        if (!reverse_graph[sequence[i]].empty())
            col -= reverse_graph[sequence[i]].size() - 1;
        else col++;
    }
    cout << cost << '\n';
    for (int i = n - 1; i > 0; --i)
    {
        cout << sequence[i] + 1 << ' ';
    }
    cout << sequence[0] + 1;
    return 0;
}
