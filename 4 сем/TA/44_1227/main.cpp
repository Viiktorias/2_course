#include <iostream>
#include <vector>

using namespace std;

vector<int> canAttack(int x, int y, vector<bool> &startMap, int w, int h)
{
    vector<int> underAttack;
    if ((y > 1) && (x > 0) && (!startMap[(x - 1) * w + y - 2]))
        underAttack.push_back((x - 1) * w + y - 2);
    if ((y > 1) && (x < h - 1) && (!startMap[(x + 1) * w + y - 2]))
        underAttack.push_back((x + 1) * w + y - 2);
    if ((y < w - 2) && (x > 0) && (!startMap[(x - 1) * w + y + 2]))
        underAttack.push_back((x - 1) * w + y + 2);
    if ((y < w - 2) && (x < h - 1) && (!startMap[(x + 1) * w + y + 2]))
        underAttack.push_back((x + 1) * w + y + 2);
    if ((y > 0) && (x > 1) && (!startMap[(x - 2) * w + y - 1]))
        underAttack.push_back((x - 2) * w + y - 1);
    if ((y < w - 1) && (x > 1) && (!startMap[(x - 2) * w + y + 1]))
        underAttack.push_back((x - 2) * w + y + 1);
    if ((y > 0) && (x < h - 2) && (!startMap[(x + 2) * w + y - 1]))
        underAttack.push_back((x + 2) * w + y - 1);
    if ((y < w - 1) && (x < h - 2) && (!startMap[(x + 2) * w + y + 1]))
        underAttack.push_back((x + 2) * w + y + 1);
    return underAttack;
}

bool try_kuhn(int v, vector<char> &used, vector<int> &mt, vector<vector<int> > &g)
{
    if (used[v]) return false;
    used[v] = true;
    for (size_t i = 0; i < g[v].size(); ++i)
    {
        int to = g[v][i];
        if (mt[to] == -1 || try_kuhn(mt[to], used, mt, g))
        {
            mt[to] = v;
            return true;
        }
    }
    return false;
}

int main()
{
    ios_base::sync_with_stdio(false);
    freopen("input.txt", "r", stdin);
    cin.tie(nullptr);
    freopen("output.txt", "w", stdout);
    cout.tie(nullptr);
    int h, w, x, y;
    cin >> h >> w;
    vector<bool> matrix(h * w, false);
    int n, k;
    n = ((h * w % 2 == 0) ? h * w / 2 : (h * w + 1) / 2);
    k = h * w - n;
    while (cin >> x >> y)
    {
        matrix[(x - 1) * w + y - 1] = true;
        if ((x + y) % 2 == 0)
            n--;
        else
            k--;
    }
    vector<vector<int>> graph(w * h);
    for (int i = 0; i < w * h; ++i)
    {
        if ((!matrix[i]) && ((i / w + i % w) % 2 == 0))
            graph[i] = canAttack(i / w, i % w, matrix, w, h);
    }
    int cn = 0, ck = 0;
    vector<int> nums(w * h);
    for (int i = 0; i < w * h; ++i)
    {
        if (!matrix[i])
        {
            if ((i / w + i % w) % 2 == 0)
            {
                nums[i] = cn;
                cn++;
            }
            else
            {
                nums[i] = ck;
                ck++;
            }
        }
    }
    vector<vector<int>> g(n);
    for (int i = 0; i < w * h; ++i)
    {
        if ((!matrix[i]) && ((i / w + i % w) % 2 == 0))
        {
            g[nums[i]].reserve(graph[i].size());
            for (auto j : graph[i])
            {
                g[nums[i]].push_back(nums[j]);
            }
        }
    }
    vector<int> mt;
    vector<char> used;
    int ans = k + n;
    mt.assign(k, -1);
    vector<char> used1(n);
    for (int i = 0; i < n; ++i)
        for (size_t j = 0; j < g[i].size(); ++j)
            if (mt[g[i][j]] == -1)
            {
                mt[g[i][j]] = i;
                used1[i] = true;
                break;
            }
    for (int i = 0; i < n; ++i)
    {
        if (used1[i])
        {
            ans--;
        }
        else
        {
            used.assign(n, false);
            if (try_kuhn(i, used, mt, g))
                ans--;
        }
    }
    cout << ans;
    return 0;
}