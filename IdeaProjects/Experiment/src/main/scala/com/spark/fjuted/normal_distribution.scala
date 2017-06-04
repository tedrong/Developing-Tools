package com.spark.fjuted

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, State, StateSpec, StreamingContext}

/**
  * Created by rong on 5/17/17.
  */
object normal_distribution {
  Logger.getLogger("org.apache.spark").setLevel(Level.OFF)

  def main(args: Array[String]): Unit ={
    val conf = new SparkConf().setMaster("local[2]").setAppName("Threshold")
    val ssc = new StreamingContext(conf, Seconds(1))
    val lines = ssc.socketTextStream("localhost", 9999)

    ssc.checkpoint("/home/rong/Desktop/Master_Programs/IdeaProjects/checkpoint")

    val pair = lines.map(info => ("SensorID", info))

    val learn = 128
    val UpdateFunction =
      (sensorId: String, row: Option[String], state: State[(Double, Double, Long, String)]) => {
        // State : 1.Mean, 2.Variance, 3.Counter, 4.Status, 5.Timestamp
        val source: String = row.getOrElse(null)
        //val time = source.split(',').take(1)
        //val value = source.split(',').take(2).drop(1).head.toDouble
        val value = source.toDouble

        if (state.exists()) {
          val old_mean = state.get._1
          val old_variance = state.get._2
          val counter = state.get._3

          val new_mean = old_mean + (value - old_mean) / (counter + 1)
          val new_variance = old_variance + (value - old_mean) * (value - new_mean)

          val upper_bound = old_mean + 3 * Math.sqrt(old_variance / counter)
          val lower_bound = old_mean - 3 * Math.sqrt(old_variance / counter)
          //println("Data : " + value)
          //println("UpB  : " + upper_bound)
          //println("LowB : " + lower_bound)

          val Anomaly_Up: Boolean = value > upper_bound
          val Anomaly_Down: Boolean = value < lower_bound

          if(counter >= learn) {
            if (Anomaly_Up) {
              println("info" + " " + value.toString + " " + "Up_Event")
              state.update(old_mean, old_variance, counter, "Warning: Up_Event")
            }//if Anomaly_Up
            else if (Anomaly_Down) {
              println("info" + " " + value.toString + " " + "Down_Event")
              state.update(old_mean, old_variance, counter, "Warning: Down_Event")
            }//else if Anomaly_Down
            else {
              println("info" + " " + value.toString + " " + "Normal")
              state.update(new_mean, new_variance, counter + 1, "Normal")
            }//else
          }//if counter > learning number
          else {
            state.update(new_mean, new_variance, counter + 1, "Learning")
          }//else
        } //if_state.exist
        else {
          state.update(value, 0.0, 1, null)
        } //Initial state

        //if (state.get._3 > 200) {
          //state.remove()
        //} //window size
      }
    //UpdateFunction

    // Using mapWithState api to monitor stream data
    val result = pair.mapWithState(StateSpec.function(UpdateFunction)).stateSnapshots()
    // The Output Operation
    result.print

    ssc.start()
    ssc.awaitTermination()
  }//main
}
