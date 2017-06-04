package com.spark.fjuted

import org.apache.avro.data.Json
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.mqtt.MQTTUtils
import org.eclipse.paho.client.mqttv3.{MqttClient, MqttMessage}
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

/**
  * Created by rong on 5/3/17.
  */
object first {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

  def main(args: Array[String]): Unit ={
    var client: MqttClient = null
    val persistence = new MemoryPersistence()
    client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId(), persistence)
    client.connect()

    val msgtopic = client.getTopic("mydata")

    val message = new MqttMessage("From Spark".getBytes("utf-8"))

    msgtopic.publish(message)
    println(s"Published data. topic: ${msgtopic.getName()}; Message: $message")

    client.disconnect()

    /*
    val conf = new SparkConf().setMaster("local[*]").setAppName("MQTT_test")
    val ssc = new StreamingContext(conf, Seconds(1))

    val mqttStream = MQTTUtils.createStream(ssc, "tcp://localhost:1883", "mydata")
    mqttStream.print()

    ssc.start()
    ssc.awaitTermination()
    */
  }//main
}//first
