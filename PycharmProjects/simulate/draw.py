import matplotlib.pyplot as plt
import numpy as np

from io import StringIO

data = np.genfromtxt('/home/rong/Desktop/EX/Untitled Folder/Ex2/sin_test',delimiter='\n')

print(data)

plt.plot(data, color='black', linewidth=1.5, zorder=1)

counter = 0
for row in data:
    plt.scatter(counter, row, color='blue', marker=".", s=150, zorder=2)
    counter += 1

plt.xlabel('Data Serial number')
plt.ylabel('Value')
#plt.axis([0,32,0,10])
plt.show()