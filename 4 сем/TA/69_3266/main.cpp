#include <fstream>
#include <vector>

using namespace std;

int dp_mem(vector<int> &komariki, vector<int> &dp, int i)
{
    if (i >= 0)
    {
        if (dp[i] == -1)
        {
            int dp2 = dp_mem(komariki, dp, i - 2), dp3 = dp_mem(komariki, dp, i - 3);
            if ((dp2 != -1) || (dp3 != -1))
                dp[i] = max(dp2, dp3) + komariki[i];
        }
        return dp[i];
    }
    return -1;
}

int main()
{
    int n;
    ifstream fin("input.txt");
    fin >> n;
    vector<int> komariki(n);
    for (int i = 0; i < n; ++i)
    {
        fin >> komariki[i];
    }
    fin.close();
    vector<int> dp(n, -1);
    dp[0] = komariki[0];
    ofstream fout("output.txt");
    fout << dp_mem(komariki, dp, n - 1);
    fout.close();
    return 0;
}