import numpy as np
import random
import os

filename = 'generate/xy.txt'
if os.path.exists(filename):
    os.remove(filename)
else:
    print("Cannot find file...")

f = open(filename, 'a')

for loop in range(0, 8):
    f.write(str(round(random.uniform(0, 2), 2)))
    f.write('\n')


window = np.linspace(round(random.uniform(0, 2), 2), 8, 8)

for loop in range(0, len(window)):
    window[loop] = round(window[loop], 2)
    f.write(str(window[loop]))
    f.write('\n')

window = np.linspace(8, round(random.uniform(0, 2), 2), 8)

for loop in range(0, len(window)):
    window[loop] = round(window[loop], 2)
    f.write(str(window[loop]))
    f.write('\n')

for loop in range(0, 8):
    f.write(str(round(random.uniform(0, 2), 2)))
    f.write('\n')
f.close()