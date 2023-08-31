# import sys
import math

import heapq
class Node:
    def __init__(self, v,u, w):
        self.v = v
        self.u = u
        self.w = w

    def getV(self):
        return self.v
    def getU(self):
        return self.u

    def getWeight(self):
        return self.w

def dijkstra(n, graph, source):
    distances = [float('inf')] * n
    distances[source] = 0
    q = []
    heapq.heappush(q, (0, source))
    while q:
        current = heapq.heappop(q)
        for node in graph[current[1]]:
            if distances[current[1]] + node.w < distances[node.v]:
                distances[node.v] = distances[current[1]] + node.w
                heapq.heappush(q, (distances[node.v], node.v))
    return distances

# def dijkstra(n, graph, source):
#     distances = [math.inf] * n
#     distances[source] = 0
#     visited = [False] * n
#     for _ in range(n):
#         u = -1
#         for i in range(n):
#             if not visited[i] and (u == -1 or distances[i] < distances[u]):
#                 u = i
#         visited[u] = True
#         for node in graph[u]:
#             v = node.v
#             w = node.w
#             if distances[u] + w < distances[v]:
#                 distances[v] = distances[u] + w
#     return distances

n, m,s, t,k = map(int, input().split())

adj = [[] for _ in range(n)]
for _ in range(m):
    u, v, w = map(int, input().split())
    adj[u-1].append(Node(v-1,u-1, w))
    adj[v-1].append(Node(u-1,v-1, w))

guards = []

guards=map(int, input().split())
# print(guards)
dist = dijkstra(n, adj, t-1)
canBeCaught = False
for guard in guards:
    if dist[guard-1] <= dist[s-1]:
        canBeCaught = True
        break
if canBeCaught:
    print("impossible")
else:
   print(dist[s-1])



