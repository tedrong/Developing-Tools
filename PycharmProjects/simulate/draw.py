import matplotlib.pyplot as plt
import numpy as np

from io import StringIO

data = np.genfromtxt('/home/rong/Git/Developing-Tools/experiment_data/ex1_slow_up',delimiter='\n')

print(data)

plt.plot(data)
plt.xlabel('Data Serial number')
plt.ylabel('Temperature')
plt.show()