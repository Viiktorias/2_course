#include <iostream>
#include <vector>
#include <bitset>

using namespace std;

int n;
pair<int, int> dominoSet[] = {make_pair(0, 0), make_pair(0, 1), make_pair(0, 2), make_pair(0, 3), make_pair(0, 4),
                              make_pair(0, 5),
                              make_pair(0, 6), make_pair(1, 1), make_pair(1, 2), make_pair(1, 3), make_pair(1, 4),
                              make_pair(1, 5),
                              make_pair(1, 6), make_pair(2, 2), make_pair(2, 3), make_pair(2, 4), make_pair(2, 5),
                              make_pair(2, 6),
                              make_pair(3, 3), make_pair(3, 4), make_pair(3, 5), make_pair(3, 6), make_pair(4, 4),
                              make_pair(4, 5),
                              make_pair(4, 6), make_pair(5, 5), make_pair(5, 6), make_pair(6, 6)};
int col;

void genUtil(vector<int> &ans, int already_set, bitset<28> &mask);

void generate()
{
    vector<int> ans(n, 0);
    int skipped = 0;
    bitset<28> mask = bitset<28>(0);
    for (int i = 0; i < min(28, 29 - n + skipped); ++i)
    {
        if (dominoSet[i].first != dominoSet[i].second)
        {
            mask[i] = true;
            ans[0] = dominoSet[i].first;
            ans[1] = dominoSet[i].second;
            genUtil(ans, 1, mask);
        }
        else
            skipped++;
    }
}

void genUtil(vector<int> &ans, int already_set, bitset<28> &mask)
{
    if (already_set == n - 1)
    {
        bool canFinish = false;
        for (int i = 0; i < 28; ++i)
        {
            if ((!mask[i]) && (((dominoSet[i].first == ans[n - 1]) && (dominoSet[i].second == ans[0])) ||
                               ((dominoSet[i].second == ans[n - 1]) && (dominoSet[i].first == ans[0]))))
            {
                canFinish = true;
                break;
            }
        }
        if (canFinish)
        {
            col++;
            cout << ans[0];
            for (int i = 1; i < n; ++i)
            {
                cout << '.' << ans[i] << ' ' << ans[i];
            }
            cout << '.' << ans[0] << '\n';
        }
    }
    else
    {
        for (int i = 0; i < 28; ++i)
        {
            if ((!mask[i]) && (dominoSet[i].first == ans[already_set]))
            {
                ans[already_set + 1] = dominoSet[i].second;
                mask[i] = true;
                genUtil(ans, already_set + 1, mask);
                mask[i] = false;
            }
            else if ((!mask[i]) && (dominoSet[i].second == ans[already_set]))
            {
                ans[already_set + 1] = dominoSet[i].first;
                mask[i] = true;
                genUtil(ans, already_set + 1, mask);
                mask[i] = false;
            }
        }
    }
}

int main()
{
    ios_base::sync_with_stdio(false);
    freopen("input2.txt", "r", stdin);
    freopen("output2.txt", "w", stdout);
    cin >> n;
    if (n < 3 || n > 28)
    {
        cout << "wrong count";
        return 0;
    }
    col = 0;
    generate();
    cout << col;
    return 0;
}
