# get n and m from the first line
n, m = map(int, input().split())

# initialize X and y arrays
X = []
Y = []

# loop over n lines and get white points
for i in range(n):
  # get two floats for x and y coordinates
  x, y = map(float, input().split())
  # append the coordinates to X array
  X.append([x, y])
  # append the color to y array (0 for white)
  Y.append(-1)

# loop over m lines and get black points
for i in range(m):
  # get two floats for x and y coordinates
  x, y = map(float, input().split())
  # append the coordinates to X array
  X.append([x, y])
  # append the color to y array (1 for black)
  Y.append(1)

# Import libraries
import cvxpy as cp
import numpy as np
import matplotlib.pyplot as plt

# Define the optimization variables
a = cp.Variable() # slope of the separating line
b = cp.Variable() # intercept of the separating line
t = cp.Variable() # margin

# Define the objective function
objective = cp.Maximize(t)
X=np.array(X)
# print(X.shape)
# Define the constraints
constraints = [Y[i] * (a * X[i,0] + b-X[i,1]) >= t for i in range(n+m)] # points should be on the correct side of the line with a margin of at least t

# Define and solve the problem
problem = cp.Problem(objective, constraints)
problem.solve()

print("({:.4f},{:.4f})".format(a.value, b.value))
