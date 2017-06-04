import os
import random

filename = 'train.txt'
if os.path.exists(filename):
    os.remove(filename)
else:
    print("Cannot find file...")


list = []

with open('history.txt') as fp:
    for line in fp:
        temp = line.split(',')
        list.append(temp[3])
        #print(temp[3])

print(len(list))

list[25] = str(34.6)
list[56] = str(29)

for temp in range(87, 105):
    list[temp] = str(random.uniform(31.5, 32.5))

with open(filename, mode='a', encoding='utf-8') as myfile:
    for pointer in range(0, 128):
        myfile.write(list[pointer])
        myfile.write('\n')
myfile.close