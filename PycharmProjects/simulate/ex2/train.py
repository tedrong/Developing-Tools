import matplotlib.pyplot as plt
import numpy as np
import random
import os

filename = 'sin.txt'

if os.path.exists(filename):
    os.remove(filename)
else:
    print("Cannot find file...")

#learn_set = np.linspace(0, 2*np.pi, 20)
#learn = np.sin(learn_set)*2.5+20

window_rads = np.linspace(0, 8*np.pi, 128)
window = np.sin(window_rads)*6

f = open(filename, 'a')

for loop in range(0, len(window)):
    window[loop] = round(window[loop], 2)
    f.write(str(window[loop]))
    f.write('\n')

#for loop in range(0, len(ran)):
#    f.write(str(ran[loop]))
#    f.write('\n')
f.close()