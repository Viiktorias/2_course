#include <fstream>
#include <vector>

using namespace std;

int main()
{
    ifstream fin("in.txt");
    int n, m;
    fin >> n >> m;
    vector<vector<int>> matrix(n, vector<int>(m, 0));
    for (int i = 0; i < n; ++i)
    {
        for (int j = 0; j < m; ++j)
        {
            fin >> matrix[i][j];
        }
    }
    vector<vector<int>> dp(n, vector<int>(m, 0));
    for (int i = 0; i < n; ++i)
        dp[i][0] = matrix[i][0];
    for (int j = 0; j < m; ++j)
        dp[0][j] = matrix[0][j];
    for (int i = 1; i < n; ++i)
    {
        for (int j = 1; j < m; ++j)
        {
            if (matrix[i][j])
            {
                dp[i][j] = min(min(dp[i][j - 1], dp[i - 1][j]), dp[i - 1][j - 1]) + 1;
            }
        }
    }
    int max_i = 0, max_j = 0;
    for (int j = 0; j < m; j++)
    {
        for (int i = 0; i < n; i++)
        {
            if (dp[i][j] > dp[max_i][max_j])
            {
                max_i = i;
                max_j = j;
            }
        }
    }
    ofstream fout("out.txt");
    fout << dp[max_i][max_j] << endl;
    fout << max_j - dp[max_i][max_j] + 2 << endl;
    fout << max_i - dp[max_i][max_j] + 2;
    fout.close();
    return 0;
}