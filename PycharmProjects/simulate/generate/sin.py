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
window = np.sin(window_rads)*3+25

f = open(filename, 'a')

#for loop in range(0, len(learn)):
    #learn[loop] = round(learn[loop], 2)
    #f.write(str(learn[loop]))
    #f.write('\n')

#ran = np.random.rand(10)

#for loop in range(0, len(ran)):
#    ran[loop] = round(ran[loop], 2) * 2 + 20
#    f.write(str(ran[loop]))
#    f.write('\n')

window[16] = 27.6
window[32] = 22.1

for temp in range(78, 96):
    window[temp] = random.uniform(25.5, 26.5)

for loop in range(0, len(window)):
    window[loop] = round(window[loop], 2)
    f.write(str(window[loop]))
    f.write('\n')

#for loop in range(0, len(ran)):
#    f.write(str(ran[loop]))
#    f.write('\n')
f.close()

#all = np.append(ran, window)
#all = np.append(all, ran)

plt.plot(window)
plt.show()