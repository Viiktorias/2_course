#include <fstream>

using namespace std;

int main()
{
    ifstream fin("necklace.in");
    int n, k, cur, maxcol = 0;
    fin >> n >> k;
    if (k > 1)
    {
        const unsigned long long mod = 1000000007ull;
        int *colors = new int[k];
        for (int i = 0; i < k; i++)
            colors[i] = 0;
        for (int i = 0; i < n; i++)
        {
            fin >> cur;
            colors[cur - 1]++;
        }
        fin.close();
        for (int i = 0; i < k; i++)
            if (colors[i] > maxcol)
                maxcol = colors[i];
        unsigned long long *factorial = new unsigned long long[maxcol + 1];
        factorial[0] = 1ull;
        for (int i = 1; i <= maxcol; i++)
            factorial[i] = (i * factorial[i - 1]) % mod;
        unsigned long long **C = new unsigned long long *[n + 1];
        for (int i = 0; i <= n; i++)
            C[i] = new unsigned long long[i + 1];
        for (int i = 0; i <= n; i++)
            C[i][0] = C[i][i] = 1ull;
        for (int i = 2; i <= n; i++)
            for (int j = 1; j < i; j++)
                C[i][j] = (C[i - 1][j - 1] + C[i - 1][j]) % mod;
        unsigned long long **F = new unsigned long long *[k - 1];
        int *sum_fst = new int[k];
        sum_fst[0] = colors[0];
        for (int i = 1; i < k; i++)
            sum_fst[i] = sum_fst[i - 1] + colors[i];
        for (int i = 0; i < k - 1; i++)
        {
            F[i] = new unsigned long long[sum_fst[i] + 1];
        }
        for (int i = 0; i <= colors[0]; i++)
            F[0][i] = 0ull;
        F[0][colors[0]] = factorial[colors[0] - 1];
        for (int i = 1; i < k - 1; i++)
        {
            for (int j = 0; j <= sum_fst[i]; j++)
            {
                F[i][j] = 0;
                for (int l = 0; l <= sum_fst[i - 1]; l++)
                    for (int p = 0; p < colors[i]; p++)
                    {
                        if ((p - j <= 0) && (p - j + l >= 0) && (colors[i] - 2 * p + j <= sum_fst[i - 1]) &&
                            (colors[i] - 2 * p - l + j >= 0))
                        {
                            F[i][j] += ((factorial[colors[i]] * C[colors[i] - 1][p] % mod) *
                                        (C[l][p - j + l] * C[sum_fst[i - 1] - l][colors[i] - 2 * p - l + j] % mod) %
                                        mod) *
                                       F[i - 1][l] % mod;
                            F[i][j] %= mod;
                        }
                    }
            }
        }
        unsigned long long F_k_0 = 0;
        for (int l = 0; l <= colors[k - 1]; l++)
            if (colors[k - 1] <= sum_fst[k - 2])
            {
                F_k_0 += (factorial[colors[k - 1]] * C[sum_fst[k - 2] - l][colors[k - 1] - l] % mod) *
                         F[k - 2][l] % mod;
                F_k_0 %= mod;
            }
        ofstream fout("necklace.out");
        fout << (F_k_0 * n) % mod;
        fout.close();
        delete[](colors);
        delete[](factorial);
        for (int i = 0; i <= n; i++)
            delete[](C[i]);
        delete[](C);
        for (int i = 0; i < k - 1; i++)
            delete[](F[i]);
        delete[](F);
    }
    else
    {
        fin.close();
        ofstream fout("necklace.out");
        fout << 0;
        fout.close();
    }
    return 0;
}