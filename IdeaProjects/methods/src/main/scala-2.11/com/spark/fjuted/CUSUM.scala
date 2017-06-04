package com.spark.fjuted

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming._
import org.apache.spark.streaming.mqtt.MQTTUtils

/**
  * Created by rong on 4/30/17.
  */

object CUSUM {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

  def main(args: Array[String]): Unit ={
    val conf = new SparkConf().setMaster("local[*]").setAppName("Normal_Distribution")
    val ssc = new StreamingContext(conf, Seconds(1))
    //val lines = ssc.socketTextStream("localhost", 9999)
    val lines = MQTTUtils.createStream(ssc, "tcp://localhost:1883", "mydata", StorageLevel.MEMORY_ONLY_SER)
    ssc.checkpoint("/home/rong/Documents/IdeaProjects/checkpoint")

    val pair = lines.map(info => ("sensorID", info))

    val UpdateFunction =
      (sensorId: String, row: Option[String],
       state: State[(Double, Double, Double, Double, Long, String, String)]) => {
        // State : 1.Mean, 2.Variance, 3.S_high, 4.S_low, 5.Counter, 6.Status, 7.Timestamp
        val source:String = row.getOrElse(null)
        val time = source.split(',').take(1)
        val value = source.split(',').take(2).drop(1).head.toDouble

        if(state.exists()){
          val old_mean = state.get._1
          val old_variance = state.get._2
          val counter = state.get._5

          val new_mean = old_mean + (value - old_mean)/(counter + 1)
          val new_variance = old_variance + (value - old_mean) * (value - new_mean)

          val old_Sh = state.get._3
          val old_Sl = state.get._4

          val std = Math.sqrt(new_variance/counter)
          val k = std * 0.5
          val new_Sh = Math.max(0.0, old_Sh + value - old_mean - k)
          val new_Sl = Math.min(0.0, old_Sl + value - old_mean + k)


          //println(value)
          //state.update(new_mean, new_variance, new_Sh, new_Sl, counter+1, "normal", time.head)


          val up_event_flag:Boolean = Math.abs(new_Sh) >= Math.abs(4 * std)
          val down_event_flag:Boolean = Math.abs(new_Sl) >= Math.abs(4 * std)


          if(up_event_flag && counter > 1){
            println("Up_Event_Anomaly " + value)
            state.update(old_mean, old_variance, 0.0, 0.0, counter, "Warning_UpEvent", time.head)
          }
          else if(down_event_flag && counter > 1){
            println("Down_Event_Anomaly " + value)
            state.update(old_mean, old_variance, 0.0, 0.0, counter, "Warning_DownEvent", time.head)
          }
          else {
            state.update(new_mean, new_variance, new_Sh, new_Sl, counter+1, "Normal", time.head)
          }

        }//if_state.exist
        else{
          state.update(value, 0.0, value, 0.0, 1, null, null)
        }//Initial state

        if(state.get._5 > 100){
          state.remove()
        }//window size
      }//UpdateFunction
    val result = pair.mapWithState(StateSpec.function(UpdateFunction)).stateSnapshots()
    result.print

    ssc.start()
    ssc.awaitTermination()
  }//main
}
