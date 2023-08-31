from heapq import heapify, heappush, heappop,merge
from collections import defaultdict
import sys
class Node:
    def __init__(self,v1,v2,index) :
        self.v1=v1
        self.v2=v2
        self.index=index
    def __lt__(self,nxt):
        return self.v2<nxt.v2 
    def __str__(self) -> str:
        return str(self.v1)+","+str(self.v2)+","+str(self.index)
    def __repr__(self) -> str:
        return str(self.v1)+","+str(self.v2)+","+str(self.index)


n = int(input())
# intervals_x=[]
# intervals_y=[]
# /intervals_x=[(2,3,1),(1,2,3),(2,2,4)]
# intervals_x.
x_dict={key:[] for key in range(n)}
y_dict={key:[] for key in range(n)}
for i in range(n):
    x1, y1, x2, y2 = map(int, input().split())
    # intervals_x.append((x1,x2,i))
    # heappush(x_dict[x1-1],Node(x1-1,x2-1,i))
    x_dict[x1-1].append(Node(x1-1,x2-1,i))

    # intervals_y.append((y1,y2,i))
    # heappush(y_dict[y1-1],Node(y1-1,y2-1,i))
    y_dict[y1-1].append(Node(y1-1,y2-1,i))
# intervals_x.sort(key=lambda x: x[1])
# intervals_x.sort(key=lambda x: x[0])
# print(intervals_x)
# print(x_dict)
# print(y_dict)

# count_x=0
# count_y=0
answers_x={}
answers_y={}
acc_x=[]
acc_y=[]
# print(x_dict)

for i in range(n):
    for x in x_dict[i]:
        heappush(acc_x,x)
    # print(acc_x)
    if(len(acc_x)!=0):
        # if i!=0:
        #     x_dict[0]= [_ for _ in merge(x_dict[0],x_dict[i])]
        # print(type(x_dict[0]))
        node_x=heappop(acc_x)
        # print(node_x)
        if(i<=node_x.v2):
            # count_x+=1
            answers_x[node_x.index]=i+1
        else:
            print('impossible')
            sys.exit(0)
            break
    for y in y_dict[i]:
        heappush(acc_y,y)
    if(len(acc_y)!=0):
        # if i!=0:y_dict[0]=[_ for _ in merge(y_dict[0],y_dict[i])]

        node_y=heappop(acc_y)
        # print(node_y)
        if(i<=node_y.v2):
            # count_y+=1
            answers_y[node_y.index]=i+1
        else:
            print('impossible')
            sys.exit(0)
            break
    # print("count",count_x,count_y)
# print(count_x,count_y)
# answers_x.sort(key=lambda x: x[0])
# answers_y.sort(key=lambda x: x[0])
if(len(acc_x)!=0 or len(acc_y)!=0):
   print('impossible')
else:
   for i in range(n):
      print(answers_x[i],answers_y[i])
# print('answers')

# print(answers_x)
# print(answers_y)
       


        
# # sorting the intervals
   

# # print(intervals)
# # counting the events
# count = 0
# # keeping track of ending of intervals
# end = 0
# # list of the intervals in which person will attend the events
# answer = []

# # traversing the list of intervals
# for interval in intervals:
# 	# starting time of next interval will >= ending
# 	# time of previous interval
# 	if(end <= interval[0]):
# 		# update the and
# 		end = interval[1]
# 		# increment the count by one
# 		count += 1
# 		# insert the non-conflict interval in the list
# 		answer.append(interval)

# # print statements will print non-conflict
# # intervals count as well intervals
# print("The maximum events a person can attend is : ", count)
# print("List of intervals in which person will attend events : ", answer)
