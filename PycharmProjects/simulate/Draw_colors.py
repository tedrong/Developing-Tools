import matplotlib.pyplot as plt
import numpy as np

data = np.genfromtxt('generate/xy.txt',delimiter='\n')

print(data)

plt.plot(data)


datalist = []
with open('/home/rong/Desktop/Untitled Folder/Ex1/Quick_1r') as fp:
    for line in fp:
        if line.find('info') != -1:
            datalist.append(line)

print(len(datalist))

counter = 0
for row in datalist:
    temp = row.split()
    print(temp)
    if (temp[2].find('Normal')!=-1):
        plt.scatter(counter, temp[1], color='green', marker=".", s=100)

    else:
        plt.scatter(counter, temp[1], color='red', marker="x")

    counter+=1
plt.xlabel('Data Serial number')
plt.ylabel('Value')
plt.show()
