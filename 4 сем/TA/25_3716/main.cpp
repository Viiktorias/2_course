#include <fstream>
#include <stack>
#include <string>

using namespace std;

int main()
{
    ifstream fin("input.txt");
    stack<char> stack;
    bool iscorrect = true;
    string stroka;
    fin >> stroka;
    fin.close();
    int i = 0;
    while (i < stroka.length())
    {
        switch (stroka[i])
        {
            case '(':
            {
            }
            case '[':
            {
            }
            case '{':
            {
                stack.push(stroka[i]);
                break;
            }
            case ')':
            {
                if ((!stack.empty()) && (stack.top() == '('))
                    stack.pop();
                else
                    iscorrect = false;
                break;
            }
            case ']':
            {
                if ((!stack.empty()) && (stack.top() == '['))
                    stack.pop();
                else
                    iscorrect = false;
                break;
            }
            case '}':
            {
                if ((!stack.empty()) && (stack.top() == '{'))
                    stack.pop();
                else
                    iscorrect = false;
                break;
            }
        }
        if (!iscorrect)
            break;
        i++;
    }
    ofstream fout("output.txt");
    if ((!iscorrect) || (!stack.empty()))
        fout << "NO" << endl << i;
    else
        fout << "YES";
    fout.close();
    return 0;
}