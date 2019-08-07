#include <iostream>
#include <vector>
#include <queue>

using namespace std;

class point
{
public:
    unsigned int l;
    unsigned int n;
    unsigned int i;

    point(unsigned int l, unsigned int n, unsigned int i) : l(l), n(n), i(i)
    {}

    bool operator<(const point &rhs) const
    {
        if (l > rhs.l)
            return true;
        if (rhs.l > l)
            return false;
        return i > rhs.i;
    }
};

int main()
{
    ios_base::sync_with_stdio(false);
    freopen("input.txt", "r", stdin);
    cin.tie(nullptr);
    freopen("output.txt", "w", stdout);
    cout.tie(nullptr);
    unsigned int n, m, w, u, v;
    cin >> n >> m;
    vector<vector<unsigned int>> matrix(n, vector<unsigned int>());
    vector<vector<unsigned int>> weight(n, vector<unsigned int>());
    for (int i = 0; i < m; ++i)
    {
        cin >> u >> v >> w;
        matrix[u].push_back(v);
        weight[u].push_back(w);
    }
    unsigned int s, t, q;
    cin >> s >> t >> q;
    q++;
    vector<vector<unsigned int>> length(q + 1, vector<unsigned int>(n, UINT32_MAX));
    vector<vector<unsigned int>> parent(q + 1, vector<unsigned int>(n, UINT32_MAX));
    vector<vector<bool>> sign(q + 1, vector<bool>(n, false));
    priority_queue<point> reachable;
    for (int i = 0; i <= q; ++i)
    {
        length[i][s] = 0;
    }
    reachable.emplace(0, s, 0);
    unsigned int l, i, size;
    while (!reachable.empty())
    {
        u = reachable.top().n;
        i = reachable.top().i;
        l = reachable.top().l;
        reachable.pop();
        sign[i][u] = true;
        size = matrix[u].size();
        if ((l == length[i][u]) && (i < q))
        {
            for (int j = 0; j < size; ++j)
            {
                v = matrix[u][j];
                w = weight[u][j];
                if ((!sign[i + 1][v]) && (length[i + 1][v] > length[i][u] + w))
                {
                    length[i + 1][v] = length[i][u] + w;
                    parent[i + 1][v] = u;
                    reachable.emplace(length[i + 1][v], v, i + 1);
                }
            }
        }
    }
    i = q;
    for (int k = 0; k < q; ++k)
    {
        if (length[i][t] > length[k][t])
            i = k;
    }
    if (parent[i][t] < n)
    {
        vector<unsigned int> path;
        u = t;
        path.push_back(t);
        while (u != s)
        {
            u = parent[i][u];
            i--;
            path.push_back(u);
        }
        size = path.size();
        cout << "Yes" << '\n' << length[size - 1][t] << ' ' << size - 2 << '\n';
        for (int i = size - 1; i >= 0; --i)
        {
            cout << path[i] << ' ';
        }
    }
    else cout << "No" << '\n';
    return 0;
}