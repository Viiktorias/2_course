#include <iostream>
#include <vector>
#include <set>
#include <unordered_map>
#include <algorithm>
#include <cmath>

using namespace std;

class command
{
    friend istream &operator>>(istream &in, command &c);

public:
    int code;
    int parameter1;
    int parameter2;
    int parameter3;
    int parameter4;

    command(int code);
};

istream &operator>>(istream &in, command &c)
{
    in >> c.code;
    if (c.code != 3)
    {
        in >> c.parameter1 >> c.parameter2 >> c.parameter3;
        if (c.code == 2)
            in >> c.parameter4;
    }
    return in;
}

command::command(int code) : code(code)
{}

int clp2(int x)
{
    int i = 0;
    while (x != 0)
    {
        x >>= 1;
        i++;
    }
    x = 1;
    x <<= i;
    return x;
}

int sum_cols(vector<vector<int>> &tree, int v_x, int v_y, //матрица, коодинаты в матрице
             int v_x1, int v_y1, int v_x2, int v_y2, int x1, int y1, int x2,
             int y2) //координаты прямоугольников: содержащегося и требуемого
{
    if (x1 > x2)
        return 0;
    if ((v_x1 == x1) && (v_x2 == x2))
        return tree[v_y][v_x];
    int v_xm = (v_x1 + v_x2) / 2;
    return sum_cols(tree, v_x * 2 + 1, v_y, v_x1, v_y1, v_xm, v_y2, x1, y1, min(x2, v_xm), y2) +
           sum_cols(tree, v_x * 2 + 2, v_y, v_xm + 1, v_y1, v_x2, v_y2, max(x1, v_xm + 1), y1, x2, y2);
}

int sum_rows(vector<vector<int>> &tree, int v_x, int v_y, //матрица, коодинаты в матрице
             int v_x1, int v_y1, int v_x2, int v_y2, int x1, int y1, int x2,
             int y2) //координаты прямоугольников: содержащегося и требуемого
{
    if (y1 > y2)
        return 0;
    if ((v_y1 == y1) && (v_y2 == y2))
        return sum_cols(tree, v_x, v_y, v_x1, v_y1, v_x2, v_y2, x1, y1, x2, y2);
    int v_ym = (v_y1 + v_y2) / 2;
    return sum_rows(tree, v_x, v_y * 2 + 1, v_x1, v_y1, v_x2, v_ym, x1, y1, x2, min(y2, v_ym)) +
           sum_rows(tree, v_x, v_y * 2 + 2, v_x1, v_ym + 1, v_x2, v_y2, x1, max(y1, v_ym + 1), x2, y2);
}

int main()
{
    ios_base::sync_with_stdio(false);
    freopen("input.txt", "r", stdin);
    cin.tie(nullptr);
    freopen("output.txt", "w", stdout);
    cout.tie(nullptr);
    vector<command> commands;
    commands.reserve(200000);
    int s;
    command cur(0);
    cin >> cur.code >> s;
    set<int> set_x;
    set<int> set_y;
    while (cur.code != 3)
    {
        cin >> cur;
        commands.push_back(cur);
        if (cur.code == 1)
        {
            set_x.insert(cur.parameter1);
            set_y.insert(cur.parameter2);
        }
    }
    unordered_map<int, int> map_x, map_y; //настоящее, сжатое
    map_x.reserve(200000);
    map_y.reserve(200000);
    vector<int> x_s, y_s;
    x_s.reserve(200000);
    y_s.reserve(200000);
    int i = 0;
    for (set<int>::iterator it = set_x.begin(); it != set_x.end(); it++)
    {
        map_x.insert(make_pair(*it, i));
        x_s.push_back(*it);
        i++;
    }
    i = 0;
    for (set<int>::iterator it = set_y.begin(); it != set_y.end(); it++)
    {
        map_y.insert(make_pair(*it, i));
        y_s.push_back(*it);
        i++;
    }
    int x_size = clp2(x_s.size()), y_size = clp2(y_s.size());
    vector<vector<int>> tree(y_size * 2 - 1, vector<int>(x_size * 2 - 1, 0));
    int length = commands.size() - 1;
    int x, y, a, k, b, r, t;
    int y_temp;
    for (int i = 0; i < length; ++i)
    {
        if (commands[i].code == 1)
        {
            x = map_x.at(commands[i].parameter1) + x_size - 1;
            y = map_y.at(commands[i].parameter2) + y_size - 1;
            a = commands[i].parameter3;
            tree[y][x] += a;
            y_temp = y;
            while (y_temp != 0)
            {
                y_temp = (y_temp - 1) / 2;
                tree[y_temp][x] += a;
            }
            while (x != 0)
            {
                x = (x - 1) / 2;
                tree[y][x] += a;
                y_temp = y;
                while (y_temp != 0)
                {
                    y_temp = (y_temp - 1) / 2;
                    tree[y_temp][x] += a;
                }
            }
        }
        else
        {
            k = lower_bound(x_s.begin(), x_s.end(), commands[i].parameter1) - x_s.begin();
            b = lower_bound(y_s.begin(), y_s.end(), commands[i].parameter2) - y_s.begin();
            r = upper_bound(x_s.begin(), x_s.end(), commands[i].parameter3) - x_s.begin() - 1;
            t = upper_bound(y_s.begin(), y_s.end(), commands[i].parameter4) - y_s.begin() - 1;
            cout << sum_rows(tree, 0, 0, 0, 0, x_size - 1, y_size - 1, k, b, r, t) << endl;
        }
    }
    return 0;
}
