import matplotlib.pyplot as plt
import numpy as np

window_rads = np.linspace(0, 2*np.pi, 40)
window = np.cos(window_rads)*2.5+20

#window[27] = 22

f = open('cos.txt', 'a')
for loop in range(0, len(window)):
    window[loop] = round(window[loop], 2)
    f.write(str(window[loop]))
    f.write('\n')
f.close()


plt.plot(window)
plt.show()