#include <fstream>

using namespace std;

int main()
{
    ifstream fin("input.txt");
    int n;
    fin >> n;
    int *heap = new int[n];
    for (int i = 0; i < n; ++i)
    {
        fin >> heap[i];
    }
    fin.close();
    bool isHeap = true;
    for (int i = 1; i < n; ++i)
    {
        if (heap[i] < heap[(i - 1) / 2])
        {
            isHeap = false;
            break;
        }
    }
    ofstream fout("output.txt");
    fout << (isHeap ? "Yes" : "No");
    fout.close();
    delete[] heap;
    return 0;
}