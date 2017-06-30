import matplotlib.pyplot as plt
import numpy as np

from io import StringIO

data = np.genfromtxt('generate/random.txt',delimiter='\n')

print(data)

plt.plot(data, color='green', linewidth=6.0)

plt.xlabel('Data Serial number')
plt.ylabel('Temperature')
plt.axis([0,32,0,10])
plt.show()