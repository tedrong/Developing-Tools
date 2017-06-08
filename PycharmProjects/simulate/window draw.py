import matplotlib.pyplot as plt

datalist = []
with open('/home/rong/Git/Developing-Tools/experiment_data/For_Group/group_result/value_average/5sd, 8secWindow/long_slowup&down') as fp:
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
        plt.scatter(counter, temp[0][0], color='green', marker=".")
        counter += 1
        plt.scatter(counter, temp[0][1], color='green', marker=".")
        counter += 1
        plt.scatter(counter, temp[0][2], color='green', marker=".")
        counter += 1
        plt.scatter(counter, temp[0][3], color='green', marker=".")
        counter += 1
        plt.scatter(counter, temp[0][4], color='green', marker=".")
        counter += 1
        plt.scatter(counter, temp[0][5], color='green', marker=".")
        counter += 1
        plt.scatter(counter, temp[0][6], color='green', marker=".")
        counter += 1
        plt.scatter(counter, temp[0][7], color='green', marker=".")
        counter += 1

    else:
        plt.scatter(counter, temp[0][0], color='red', marker="x")
        counter += 1
        plt.scatter(counter, temp[0][1], color='red', marker="x")
        counter += 1
        plt.scatter(counter, temp[0][2], color='red', marker="x")
        counter += 1
        plt.scatter(counter, temp[0][3], color='red', marker="x")
        counter += 1
        plt.scatter(counter, temp[0][4], color='red', marker="x")
        counter += 1
        plt.scatter(counter, temp[0][5], color='red', marker="x")
        counter += 1
        plt.scatter(counter, temp[0][6], color='red', marker="x")
        counter += 1
        plt.scatter(counter, temp[0][7], color='red', marker="x")
        counter += 1
    print(counter)
plt.xlabel('Data Serial number')
plt.ylabel('Temperature')
plt.show()
