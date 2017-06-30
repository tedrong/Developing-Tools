import matplotlib.pyplot as plt
import random
import os

filename = 'generate/random.txt'

if os.path.exists(filename):
    os.remove(filename)
else:
    print("Cannot find file...")

list = []

for loop in range(0, 128):
    temp = round(random.uniform(0, 2), 2)
    list.append(temp)

print(list)


f = open(filename, 'a')
for loop in range(0, len(list)):
    f.write(str(list[loop]))
    f.write('\n')
f.close()

plt.plot(list)
plt.show()