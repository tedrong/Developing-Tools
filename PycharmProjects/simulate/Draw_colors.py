import matplotlib.pyplot as plt

datalist = []
with open('/home/teddy/Git/some-tools/experiment_data/result/nd/real') as fp:
    for line in fp:
        if line.find('info') != -1:
            datalist.append(line)

print(len(datalist))

counter = 0
for row in datalist:
    temp = row.split()
    print(temp)
    if (temp[2].find('Normal')!=-1):
        plt.scatter(counter, temp[1], color='green', marker=".")

    else:
        plt.scatter(counter, temp[1], color='red', marker="x")

    counter+=1
plt.xlabel('Data Serial number')
plt.ylabel('Temperature')
plt.show()
