#include <fstream>

using namespace std;

int main()
{
    ifstream fin("input.txt");
    long long n;
    fin >> n;
    fin.close();
    int i = 0;
    ofstream fout("output.txt");
    while (n > 0)
    {
        if (n & 0b1)
            fout << i << endl;
        i++;
        n >>= 1;
    }
    fout.close();
    return 0;
}