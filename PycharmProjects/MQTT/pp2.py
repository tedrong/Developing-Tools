import paho.mqtt.client as mqtt
import time


mqttc = mqtt.Client("SensorID")
mqttc.connect("localhost", 1883)
#mqttc.publish("mydata", "python_pp")
#mqttc.loop(2)


with open('/home/rong/Desktop/ex_data/real') as fp:
    for line in fp:
        print(line)
        mqttc.publish("SensorID", line)
        time.sleep(3)