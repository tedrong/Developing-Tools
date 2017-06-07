import matplotlib.pyplot as plt

datalist = []
with open('/home/rong/Git/Developing-Tools/experiment_data/For_Group/group_result/SD_Average_LOF(normal_testData)/real,k=8') as fp:
    for line in fp:
        if line.find('source') != -1:
            d1 = line.split()
            d2 = d1[1].split(",")
            print(d2)
            print(d2[7])
            array = ([d2[7], d1[2]])
            datalist.append(array)
            #datalist.append(line)
print(datalist)


counter = 0
for row in datalist:
    temp = row #.split()
    print(temp)
    if (temp[1].find('Normal')!=-1):
        plt.scatter(counter, temp[0], color='green', marker=".")

    else:
        plt.scatter(counter, temp[0], color='red', marker="x")

    counter+=1
plt.xlabel('Data Serial number')
plt.ylabel('Temperature')
plt.show()
