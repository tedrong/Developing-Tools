package com.spark.fjuted

import org.apache.log4j.{Level, Logger}
import org.apache.spark._
import org.apache.spark.streaming._

/**
  * Created by rong on 5/17/17.
  */
object threshold {
  Logger.getLogger("org.apache.spark").setLevel(Level.OFF)

  def main(args: Array[String]): Unit ={
    val conf = new SparkConf().setMaster("local[2]").setAppName("Threshold")
    val ssc = new StreamingContext(conf, Seconds(1))
    val lines = ssc.socketTextStream("localhost", 9999)

    ssc.checkpoint("/home/rong/Desktop/Master_Programs/IdeaProjects/checkpoint")

    val pair = lines.map(info => ("SensorID", info))

    val UpdateFunction =
      (sensorId: String, row: Option[String], state: State[String]) => {
        // Trying to get String from mapWithState caller, if Option[] is empty return null
        val source:String = row.getOrElse(null)

        // Spliting out time and value from String
        //val time = source.split(',').take(1)
        //val value = source.split(',').take(2).drop(1).head.toDouble
        val value = source.toDouble

        // Comparing with Bounds
        if(value < 20.toDouble){
          println("info" + " " + value.toString + " " + "Down_Event")
        }//if
        else if(value > 22.toDouble){
          println("info" + " " + value.toString + " " + "Up_Event")
        }//elseif
        else{
          println("info" + " " + value.toString + " " + "Normal")
        }//else
      }//UpdateFunction

    // Using mapWithState api to monitor stream data
    val result = pair.mapWithState(StateSpec.function(UpdateFunction)).stateSnapshots()
    // The Output Operation
    result.print

    ssc.start()
    ssc.awaitTermination()
  }//main
}//threshold
