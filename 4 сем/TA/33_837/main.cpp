#include <fstream>
#include <vector>
#include <algorithm>
#include <stack>
#include <queue>

using namespace std;

struct transition
{
    int hard;
    int to;
};

struct point
{
    int val;
    int prev;

    point(int val, int prev) : val(val), prev(prev)
    {}
};

int main()
{
    ifstream fin("input.in");
    int n, k;
    fin >> n >> k;
    vector<vector<transition>> transitions(n, vector<transition>());
    int a, b, c;
    transition temp;
    for (int i = 0; i < 3 * n / 2; ++i)
    {
        fin >> a >> b >> c;
        temp.hard = c;
        temp.to = b - 1;
        transitions[a - 1].push_back(temp);
        temp.to = a - 1;
        transitions[b - 1].push_back(temp);
    }
    fin.close();
    vector<int> ring;
    int cur = 0, prev = 0;
    for (int i = 0; i < k; ++i)
    {
        ring.push_back(cur);
        for (int j = 0; j < 3; ++j)
        {
            if ((transitions[cur][j].to < k) && (transitions[cur][j].to != prev))
            {
                prev = cur;
                cur = transitions[cur][j].to;
                break;
            }
        }
    }
    vector<vector<int>> faces(k);
    vector<point> nodes_queue;
    vector<int> face;
    nodes_queue.reserve(k);
    face.reserve(k);
    int i = 1;
    nodes_queue.push_back(point(ring[0], -1));
    for (int j = 0; j < 3; ++j)
    {
        if (transitions[ring[0]][j].to >= k)
        {
            nodes_queue.push_back(point(transitions[ring[0]][j].to, 0));
            break;
        }
    }
    while ((nodes_queue[nodes_queue.size() - 1].val != ring[k - 1]) &&
           (nodes_queue[nodes_queue.size() - 2].val != ring[k - 1]))
    {
        for (int j = 0; j < 3; ++j)
        {
            if ((transitions[nodes_queue[i].val][j].to != nodes_queue[nodes_queue[i].prev].val) &&
                ((transitions[nodes_queue[i].val][j].to >= k) || transitions[nodes_queue[i].val][j].to == ring[k - 1]))
            {
                nodes_queue.push_back(point(transitions[nodes_queue[i].val][j].to, i));
            }
        }
        i++;
    }
    if (nodes_queue[nodes_queue.size() - 1].val == ring[k - 1])
        i = nodes_queue.size() - 1;
    else i = nodes_queue.size() - 2;
    do
    {
        face.push_back(nodes_queue[i].val);
        i = nodes_queue[i].prev;
    } while (i != -1);
    faces[k - 1] = face;
    for (int l = 0; l < k - 1; ++l)
    {
        face.clear();
        nodes_queue.clear();
        i = 1;
        nodes_queue.push_back(point(ring[l + 1], -1));
        for (int j = 0; j < 3; ++j)
        {
            if (transitions[ring[l + 1]][j].to >= k)
            {
                nodes_queue.push_back(point(transitions[ring[l + 1]][j].to, 0));
                break;
            }
        }
        while ((nodes_queue[nodes_queue.size() - 1].val != ring[l]) &&
               (nodes_queue[nodes_queue.size() - 2].val != ring[l]))
        {
            for (int j = 0; j < 3; ++j)
            {
                if ((transitions[nodes_queue[i].val][j].to != nodes_queue[nodes_queue[i].prev].val) &&
                    ((transitions[nodes_queue[i].val][j].to >= k) || transitions[nodes_queue[i].val][j].to == ring[l]))
                {
                    nodes_queue.push_back(point(transitions[nodes_queue[i].val][j].to, i));
                }
            }
            i++;
        }
        if (nodes_queue[nodes_queue.size() - 1].val == ring[l])
            i = nodes_queue.size() - 1;
        else i = nodes_queue.size() - 2;
        do
        {
            face.push_back(nodes_queue[i].val);
            i = nodes_queue[i].prev;
        } while (i != -1);
        faces[l] = face;
    }
    vector<vector<int>> nodes_and_faces(n, vector<int>());
    for (int i = 0; i < k; ++i)
    {
        for (int j = 1; j < faces[i].size() - 1; ++j)
        {
            nodes_and_faces[faces[i][j]].push_back(i);
        }
    }
    queue<int> faces_queue;
    vector<int> faces_colors(k, 0);
    for (int m = 1; m <= 2; ++m)
    {
        faces_queue.push(nodes_and_faces[k][m]);
        while (!faces_queue.empty())
        {
            int cur_face = faces_queue.front();
            faces_queue.pop();
            faces_colors[cur_face] = m;
            for (int j = 1; j < faces[cur_face].size() - 1; ++j)
            {
                int neighbour = -1;
                for (int l = 0; l < 3; ++l)
                {
                    if ((transitions[faces[cur_face][j]][l].to != faces[cur_face][j - 1]) &&
                        (transitions[faces[cur_face][j]][l].to != faces[cur_face][j + 1]))
                    {
                        neighbour = transitions[faces[cur_face][j]][l].to;
                        break;
                    }
                }
                if (neighbour >= k)
                {
                    for (int l = 0; l < 3; ++l)
                    {
                        if (faces_colors[nodes_and_faces[neighbour][l]] == 0)
                        {
                            if (find(faces[nodes_and_faces[neighbour][l]].begin(),
                                     faces[nodes_and_faces[neighbour][l]].end(),
                                     faces[cur_face][j]) == faces[nodes_and_faces[neighbour][l]].end())
                            {
                                faces_queue.push(nodes_and_faces[neighbour][l]);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    vector<vector<int>> ways(3, vector<int>());
    ways[0].reserve(n);
    ways[1].reserve(n);
    ways[2].reserve(n);
    vector<int> complexity(3, 0);
    for (int i = 0; i < 3; ++i)
    {
        for (int j = 0; j < k; ++j)
        {
            if (faces_colors[j] == i)
            {
                for (int t = 0; t < faces[j].size() - 1; ++t)
                {
                    ways[i].push_back(faces[j][t]);
                    for (int l = 0; l < 3; ++l)
                    {
                        if (transitions[faces[j][t]][l].to == faces[j][t + 1])
                        {
                            complexity[i] += transitions[faces[j][t]][l].hard;
                            break;
                        }
                    }
                }
            }
            else
            {
                ways[i].push_back(ring[j]);
                for (int l = 0; l < 3; ++l)
                {
                    if (transitions[ring[j]][l].to == ring[(j + 1) % k])
                    {
                        complexity[i] += transitions[ring[j]][l].hard;
                        break;
                    }
                }
            }
        }
    }
    int easiest = 0;
    if (complexity[0] > complexity[1])
    {
        easiest = 1;
        if (complexity[1] > complexity[2])
            easiest = 2;
    }
    else if (complexity[0] > complexity[2])
        easiest = 2;
    ofstream fout("output.out");
    for (int i = 0; i < n - 1; ++i)
    {
        fout << ways[easiest][i] + 1 << " ";
    }
    fout << ways[easiest][n - 1] + 1;
    fout.close();
    return 0;
}