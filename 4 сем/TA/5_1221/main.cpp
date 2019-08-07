#include <iostream>
#include <vector>
#include <set>
#include <map>
#include <unordered_map>
#include <unordered_set>
#include <cmath>
#include <bitset>
#include <queue>
#include <cmath>
#include <algorithm>
#include <string>

using namespace std;

unsigned short k, w, h, n, s, startSum;
unsigned short freePlaces;
unsigned short colOfHorses;
unsigned short fixedHorses;
unsigned short m;
vector<unsigned short> freeCellIndex;
unordered_map<unsigned short, vector<unsigned short>>
        attack;
bitset<1024> startMap;
bitset<1024> fixedMap;
priority_queue<string, vector<string>, greater<string>> sortedAnswer;
priority_queue<unsigned short> colOfAttacked;
vector<unsigned short> canIncrease;
string ans;

vector<unsigned short> canAttack(unsigned short x, unsigned short y)
{
    vector<unsigned short> underAttack;
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

bool isAnswer(unsigned short &sum)
{
    return (sum == k * freePlaces);
}

void util(unsigned short r, unsigned short cur, bitset<1024> &curMap, unsigned short sum,
          unsigned short start, unsigned short finish)
{
    if (cur == r)
    {
        if (isAnswer(sum))
        {
            m++;
            ans = "";
            for (int i = 0; i < s; ++i)
            {
                if (curMap[i])
                    ans += to_string(i / w) + " " + to_string(i % w) + " ";
            }
            sortedAnswer.emplace(ans);
        }
    }
    else if (sum + canIncrease[r - cur - 1] >= freePlaces * k)
    {
        bitset<1024> genMap, newMap;
        unsigned short wasAttacked, willAttack, diff, skipped = 0;
        unsigned long long l = (1ull << start);
        for (unsigned short i = start; (i < s && r - cur <= finish); ++i, l <<= 1ull)
        {
            genMap = bitset<1024>(l);
            if (!(curMap & genMap).any() && !(startMap & genMap).any())
            {
                skipped++;
                newMap = genMap | curMap;
                wasAttacked = 0;
                willAttack = 0;
                for (auto j : attack[i])
                {
                    if (curMap[j])
                        wasAttacked++;
                    else
                    {
                        diff = 0;
                        for (auto p : attack[j])
                        {
                            if (curMap[p])
                                diff++;
                        }
                        if (diff < k)
                            willAttack++;
                    }
                }
                if (wasAttacked > k)
                    wasAttacked = k;
                util(r, cur + 1, newMap, sum + k + willAttack - wasAttacked, i + 1, finish - skipped);
            }
        }
    }
}

void nextSizeVariants(unsigned short r)
{
    util(r, fixedHorses, fixedMap, startSum, 0, freePlaces - fixedHorses);
}

int main()
{
    ios_base::sync_with_stdio(false);
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
    cin >> k >> w >> h >> n;
    s = w * h;
    startMap = bitset<1024>(0);
    unsigned short x, y;
    for (int i = 0; i < n; ++i)
    {
        cin >> x >> y;
        startMap[x * w + y] = true;
    }
    fixedHorses = 0;
    fixedMap = bitset<1024>(0);
    for (unsigned short i = 0; i < s; ++i)
    {
        if (!startMap[i])
        {
            freeCellIndex.push_back(i);
            attack.emplace(i, canAttack(i / w, i % w));
            if (attack.at(i).size() < k)
            {
                fixedHorses++;
                fixedMap[i] = true;
            }
            colOfAttacked.push(attack[i].size());
        }
    }
    freePlaces = s - n;
    colOfHorses = (unsigned short) ceil(1.0 * (freePlaces - fixedHorses) / (k + 8)) + fixedHorses;
    unsigned short temp = 0;
    while (!colOfAttacked.empty())
    {
        temp += colOfAttacked.top() + k;
        colOfAttacked.pop();
        canIncrease.push_back(temp);
    }
    m = 0;
    startSum = 0;
    unsigned short diff;
    for (int i = 0; i < s; ++i)
    {
        if (fixedMap[i])
        {
            startSum += k;
        }
        else if (!startMap[i])
        {
            diff = 0;
            for (auto j : attack[i])
            {
                if (fixedMap[j])
                    diff++;
            }
            startSum += min(k, diff);
        }
    }
    while (m == 0)
    {
        nextSizeVariants(colOfHorses);
        colOfHorses++;
    }
    cout << m << '\n';
    while (!sortedAnswer.empty())
    {
        cout << sortedAnswer.top() << '\n';
        sortedAnswer.pop();
    }
    return 0;
}
