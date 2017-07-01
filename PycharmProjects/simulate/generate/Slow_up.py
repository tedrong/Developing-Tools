import matplotlib.pyplot as plt
import numpy as np
import random
import os

filename = 'slowup.txt'

if os.path.exists(filename):
    os.remove(filename)
else:
    print("Cannot find file...")

window_rads = np.linspace(0, 0.5*np.pi, 24)

window = np.sin(window_rads)*6
window[0] = window[0] + round(random.uniform(0, 2), 2)

f = open(filename, 'a')

for loop in range(0, 4):
    f.write(str(round(random.uniform(0, 2), 2)))
    f.write('\n')

for loop in range(0, len(window)):
    window[loop] = round(window[loop], 2)
    f.write(str(window[loop]))
    f.write('\n')

for loop in range(0, 4):
    f.write(str(round(random.uniform(6, 8), 2)))
    f.write('\n')

f.close()