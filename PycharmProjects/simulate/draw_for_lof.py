import matplotlib.pyplot as plt
import numpy as np

data = np.genfromtxt('ex2/point.txt',delimiter='\n')

print(data)

plt.plot(data, color='black', linewidth=1.5, zorder=1)


datalist = []
with open('/home/rong/Desktop/Untitled Folder/Ex2/LOF/point') as fp:
    for line in fp:
        if line.find('source') != -1:
            d1 = line.split()
            d2 = d1[1].split(",")
            print(d2)
            print(d2[7])
            array = ([d2[7], d1[5]])
            datalist.append(array)
            #datalist.append(line)
print(datalist)


counter = 0
for row in datalist:
    temp = row #.split()
    print(temp)
    if (temp[1].find('Normal')!=-1):
        plt.scatter(counter, temp[0], color='green', marker=".", s=150, zorder=2)

    else:
        plt.scatter(counter, temp[0], color='red', marker="x", s=150, zorder=2)

    counter+=1
plt.xlabel('Data Serial number')
plt.ylabel('Value')
plt.show()
