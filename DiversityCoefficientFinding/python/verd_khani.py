import random
a, b = map(int, input().split())
c = list(map(int, input().split()))
d = list(map(int, input().split()))
e = int(input())
f = []
for g in range(e):
    f.append(int(input()))
h = [False for i in range(b+1)]
i = [-1 for i in range(b+1)]
def j(k: int) -> bool:
    for l in m[k]:
        if not h[l]:
            h[l] = True
            if i[l] == -1 or j(i[l]):
                i[l] = k
                return True
    return False
m = [[] for i in range(5005)]
for g in range(a):
    if g + 1 not in f:
        m[c[g]].append(d[g])
n = 0
o = []
for g in range(e - 1, -1, -1):
    while True:
        h = [False for p in range(b+1)]
        if not j(n):
            break
        n += 1
    o.append(n)
    m[c[f[g]-1]].append(d[f[g]-1])
for p in reversed(o):
    print(p)
