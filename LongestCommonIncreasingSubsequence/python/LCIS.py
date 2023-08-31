def dualspell(s1,s2,n,m):
    dp = [0] * m
    for j in range(m):
        dp[j] = 0
 
 
    for i in range(n):
     
        # legth of wanted subsequene until index i of first string
        length = 0
 
        # print("i",i,":")
        for j in range(m):

            if (s1[i] == s2[j] and length + 1 >= dp[j]):
                # dp[j]=max(length+1,dp[j])
                
                    dp[j] = length + 1
                    # length+=1
 

            if (s1[i] > s2[j] and dp[j]>=length):
                length=dp[j]
            # print("j",j,":")
            # print(length)
            # print(dp)
 

 
    return max(dp)


def d3(s1, s2):
    l1 = len(s1)
    l2 = len(s2)
    
    dp = [0]*l2
    for i in range(l1):
        
        length = 0
        
        for j in range(l2):
            
            if s1[i] == s2[j]:
                dp[j] = max(length+1, dp[j])
            
            if s1[i] > s2[j]:
                length = max(length, dp[j])
    
    result = max(dp)
    return result


# def dualspell2(s1, s2):
#     dp = [0] * len(s2)
    
#     # loop through each element of s1
#     for x in s1:
        
#         # keep track of the previous longest length
#         prev = 0
        
#         # loop through each element of s2
#         for i, y in enumerate(s2):
            
          
#             curr = dp[i]
            
            
#             if x == y:
#                 dp[i] = prev + 1
            
            
#             if x > y:
#                 prev = max(prev, curr)
    
#     print(dp)
#     return max(dp)

    


s1=[]
s2=[]
l1=int(input())
s1=list(map(int, input().split()))
l2=int(input())
s2=list(map(int, input().split()))

# print(dualspell2(s1, s2))    
# print(dualspell(s1,s2,l1,l2))    
print(d3(s1,s2)) 
 
