/*
#include <iostream>
#include <vector>
#include <cmath>
#include <queue>

using namespace std;

int main()
{
    ios_base::sync_with_stdio(false);
    freopen("input.txt", "r", stdin);
    cin.tie(nullptr);
    freopen("output.txt", "w", stdout);
    cout.tie(nullptr);
    int m;
    long long s;
    cin >> m >> s;
    long long o = floor(ceil(1.0 * s / m) * 2.0 * m / (m + 1));
    priority_queue<pair<long long, int>> diff;
    vector<int> numbers;
    long long t;
    int lastEmpty = m - 1, firstEmpty = 0;
    long long maximum = 0;
    long long df, pos;
    while (cin >> t)
    {
        if (t <= o)
        {
            if ((diff.empty()) || (t > diff.top().first))
            {
                numbers.push_back(firstEmpty);
                if (t < o)
                    diff.emplace(o - t, firstEmpty);
                firstEmpty++;
                maximum = max(maximum, t);
            }
            else
            {

                df = diff.top().first - t;
                pos = diff.top().second;
                diff.pop();
                numbers.push_back(pos);
                if (df != 0)
                    diff.emplace(df, pos);
                maximum = max(maximum, o - df);
            }
        }
        else
        {
            numbers.push_back(lastEmpty);
            lastEmpty--;
            maximum = max(maximum, t);
        }
    }
    cout << maximum << '\n';
    for (auto c : numbers)
        cout << c + 1 << ' ';
    return 0;
}
*/

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

void nlr(vector<vector<int>> &tree, vector<int> &ans, vector<bool> &visited, int pos)
{
    ans.push_back(pos);
    visited[pos] = true;
    for (int i = 0; i < tree[pos].size(); ++i)
    {
        if (!visited[tree[pos][i]])
            nlr(tree, ans, visited, tree[pos][i]);
    }
}

vector<pair<int, int>> cities;

unsigned int graph(int i, int j)
{
    if (i == j)
        return 0;
    return abs(cities[i].first - cities[j].first) +
           abs(cities[i].second - cities[j].second);
}

int main()
{
    ios_base::sync_with_stdio(false);
    freopen("input3.txt", "r", stdin);
    cin.tie(nullptr);
    freopen("output3.txt", "w", stdout);
    cout.tie(nullptr);
    int n;
    cin >> n;
    cities = vector<pair<int, int>>(n);
    for (int i = 0; i < n; ++i)
    {
        cin >> cities[i].first >> cities[i].second;
    }
    vector<vector<int>> mst(n);
    vector<bool> used(n, false);
    vector<unsigned int> minimal(n, INT32_MAX);
    vector<int> selected(n, -1);
    minimal[0] = 0;
    int v;
    for (int i = 0; i < n; ++i)
    {
        v = -1;
        for (int j = 0; j < n; ++j)
        {
            if (!used[j] && (v == -1 || minimal[j] < minimal[v]))
                v = j;
        }
        used[v] = true;
        if (selected[v] != -1)
        {
            mst[v].push_back(selected[v]);
            mst[selected[v]].push_back(v);
        }
        for (int j = 0; j < n; ++j)
        {
            if (graph(v, j) < minimal[j])
            {
                minimal[j] = graph(v, j);
                selected[j] = v;
            }
        }
    }
    vector<int> ans;
    vector<bool> visited(n, false);
    nlr(mst, ans, visited, 0);
    unsigned long long sum = 0;
    ans.push_back(ans[0]);
    for (int i = 0; i < n; ++i)
    {
        cout << ans[i] + 1 << ' ';
        sum += (abs(cities[ans[i]].first - cities[ans[i + 1]].first) +
                abs(cities[ans[i]].second - cities[ans[i + 1]].second));
    }
    cout << ans[n] + 1 << '\n' << sum;
    return 0;
}