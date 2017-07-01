import matplotlib.pyplot as plt
import numpy as np

data = np.genfromtxt('ex2/group.txt',delimiter='\n')

print(data)

plt.plot(data, color='black', linewidth=1.5, zorder=1)

datalist = []
with open('/home/rong/Desktop/EX/Untitled Folder/Ex2/ND_Average/group') as fp:
    for line in fp:
        if line.find('source') != -1:
            d1 = line.split()
            d2 = d1[1].split(",")
            #print(d2)
            #print(d2[7])
            array = ([d2, d1[2]])
            datalist.append(array)
            #datalist.append(line)
print(datalist)


counter = 0
for row in datalist:
    temp = row #.split()
    print(temp)
    if (temp[1].find('Normal')!=-1):
        plt.scatter(counter, temp[0][0], color='green', marker=".", s=150, zorder=2)
        counter += 1
        plt.scatter(counter, temp[0][1], color='green', marker=".", s=150, zorder=2)
        counter += 1
        plt.scatter(counter, temp[0][2], color='green', marker=".", s=150, zorder=2)
        counter += 1
        plt.scatter(counter, temp[0][3], color='green', marker=".", s=150, zorder=2)
        counter += 1
        plt.scatter(counter, temp[0][4], color='green', marker=".", s=150, zorder=2)
        counter += 1
        plt.scatter(counter, temp[0][5], color='green', marker=".", s=150, zorder=2)
        counter += 1

    else:
        plt.scatter(counter, temp[0][0], color='red', marker="x", s=150, zorder=2)
        counter += 1
        plt.scatter(counter, temp[0][1], color='red', marker="x", s=150, zorder=2)
        counter += 1
        plt.scatter(counter, temp[0][2], color='red', marker="x", s=150, zorder=2)
        counter += 1
        plt.scatter(counter, temp[0][3], color='red', marker="x", s=150, zorder=2)
        counter += 1
        plt.scatter(counter, temp[0][4], color='red', marker="x", s=150, zorder=2)
        counter += 1
        plt.scatter(counter, temp[0][5], color='red', marker="x", s=150, zorder=2)
        counter += 1
    print(counter)
plt.xlabel('Data Serial number')
plt.ylabel('Value')
plt.show()
