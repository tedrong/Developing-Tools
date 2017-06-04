import matplotlib.pyplot as plt
import numpy as np
import os

filename = 'xy.txt'
if os.path.exists(filename):
    os.remove(filename)
else:
    print("Cannot find file...")

f = open(filename, 'a')

ran1 = np.random.rand(60)

for loop in range(0, len(ran1)):
    ran1[loop] = round(ran1[loop], 2) * 5 + 25
    f.write(str(ran1[loop]))
    f.write('\n')


window = np.linspace(25, 40, 4)

for loop in range(0, len(window)):
    window[loop] = round(window[loop], 2)
    f.write(str(window[loop]))
    f.write('\n')

window = np.linspace(40, 25, 4)

for loop in range(0, len(window)):
    window[loop] = round(window[loop], 2)
    f.write(str(window[loop]))
    f.write('\n')

ran2 = np.random.rand(60)

for loop in range(0, len(ran2)):
    ran2[loop] = round(ran2[loop], 2) * 5 + 25
    f.write(str(ran2[loop]))
    f.write('\n')
f.close()

all = np.append(ran1, window)
all = np.append(all, ran2)

print(window)
plt.plot(all)
plt.show()