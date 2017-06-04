import matplotlib.pyplot as plt
import numpy as np

from io import StringIO

data = np.genfromtxt('../../../sin_train_test.txt',delimiter='\n')

print(data)

plt.plot(data)
plt.xlabel('Data Serial number')
plt.ylabel('Temperature')
plt.show()