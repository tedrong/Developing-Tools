import matplotlib.pyplot as plt
import numpy as np
import random
import os

filename = 'point.txt'

if os.path.exists(filename):
    os.remove(filename)
else:
    print("Cannot find file...")

#learn_set = np.linspace(0, 2*np.pi, 20)
#learn = np.sin(learn_set)*2.5+20

window_rads = np.linspace(0, 5*np.pi, 80)
window = np.sin(window_rads)*2
window[14] = 1.8
window[35] = -1

f = open(filename, 'a')

for loop in range(0, len(window)):
    window[loop] = round(window[loop], 2)
    f.write(str(window[loop]))
    f.write('\n')

#for loop in range(0, len(ran)):
#    f.write(str(ran[loop]))
#    f.write('\n')
f.close()