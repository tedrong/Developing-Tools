package com.spark.fjuted

import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.log4j.{Level, Logger}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.mqtt.MQTTUtils

/**
  * Created by rong on 4/10/17.
  */
object threshold {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  def main(args: Array[String]): Unit ={
    //if (args.length < 1) {
      //System.err.println("port")
      //System.exit(1)
    //}
    //var Seq(port) = args.toSeq
    //port = port.toString

    val conf = new SparkConf().setMaster("local[2]").setAppName("Threshold")
    val ssc = new StreamingContext(conf, Seconds(3))
    //val lines = ssc.socketTextStream("localhost", port.toInt)
    //val lines = ssc.textFileStream("/home/rong/Documents/IdeaProjects/test.txt")
    val lines = MQTTUtils.createStream(ssc, "tcp://localhost:1883", "mydata", StorageLevel.MEMORY_ONLY_SER)
    ssc.checkpoint("/home/rong/Documents/IdeaProjects/checkpoint")

    val pair = lines.map(info => ("sensorID", info))

    val UpdateFunction =
      (sensorId: String, row: Option[String], state: State[String]) => {
        // State : 1.Mean, 2.Variance, 3.Counter, 4.Status, 5.Timestamp
        val source:String = row.getOrElse(null)
        val time = source.split(',').take(1)
        val value = source.split(',').take(2).drop(1).head.toDouble
        if(value>10 && value<20){
          println(time.head.toString + " " + value.toString + " " + "Normal")
        }
        else{
          println(time.head.toString + " " + value.toString + " " + "Warning")
        }
      }//UpdateFunction
    val result = pair.mapWithState(StateSpec.function(UpdateFunction)).stateSnapshots()
    result.print

    ssc.start()
    ssc.awaitTermination()
  }//main
}//threshold
