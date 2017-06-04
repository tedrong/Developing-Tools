package com.spark.fjuted

import java.text.SimpleDateFormat
import java.util.Calendar

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming._

/**
  * Created by rong on 4/24/17.
  */

object normal_distribution {
  // SetUp the log information level print in consoles
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

  // Main function
  def main(args: Array[String]): Unit ={
    val conf = new SparkConf().setMaster("local[*]").setAppName("Normal_Distribution")
    val ssc = new StreamingContext(conf, Seconds(1))


    val lines = ssc.socketTextStream("localhost", 9999)
    ssc.checkpoint("/home/rong/Documents/IdeaProjects/checkpoint")

    val pair = lines.map(info => ("sensorID", info))

    val UpdateFunction =
      (sensorId: String, row: Option[String], state: State[(Double, Double, Long, String, String)]) => {
        // State : 1.Mean, 2.Variance, 3.Counter, 4.Status, 5.Timestamp
        val source:String = row.getOrElse(null)
        val time = source.split(',').take(1)
        val value = source.split(',').take(2).drop(1).head.toDouble

        if(state.exists()){
          val old_mean = state.get._1
          val old_variance = state.get._2
          val counter = state.get._3

          val new_mean = old_mean + (value - old_mean)/(counter + 1)
          val new_variance = old_variance + (value - old_mean) * (value - new_mean)

          val upper_bound = old_mean + 3 * Math.sqrt(old_variance/counter)
          val lower_bound = old_mean - 3 * Math.sqrt(old_variance/counter)
          println("Data : " + value)
          println("UpB  : " + upper_bound)
          println("LowB : " + lower_bound)
          val Anomaly_flag:Boolean = value>upper_bound || value<lower_bound

          if(Anomaly_flag && counter>5){
            println("Anomaly")
            state.update(old_mean, old_variance, counter, "Warning", time.head)
          }
          else{
            state.update(new_mean, new_variance, counter+1, "Normal",time.head)
          }
        }//if_state.exist
        else{
          state.update(value, 0.0, 1, null, null)
        }//Initial state

        if(state.get._3 > 100){
          state.remove()
        }//window size
    }//UpdateFunction
    val result = pair.mapWithState(StateSpec.function(UpdateFunction)).stateSnapshots()
    result.print


    /*
    val data = lines.flatMap(_.split(',').take(2).drop(1))

    val parsedData = data.map(info => Vectors.dense(info.toDouble))
    val values = parsedData.window(Seconds(30), Seconds(1))
    val entire = lines.map(info => info)


    val trans = values.transform(rdd => {
      val summary: MultivariateStatisticalSummary = Statistics.colStats(rdd)
      rdd.map(info => (summary.mean, summary.variance))
    })

    val result = trans.transform(rdd => {
      val blacklist: Set[Char] = Set('(', ')', '[', ']')
      rdd.map(info => info.toString.filterNot(c => blacklist.contains(c)))
    })


    val mean = result.flatMap(_.split(',').take(1)).map(_.toDouble)
    val variance = result.flatMap(_.split(',').take(2).drop(1)).map(_.toDouble)

    // import collection.mutable.ArrayBuffer
    val Bmean = new ArrayBuffer[Double]();
    mean.foreachRDD{rdd =>
      Bmean.clear()
      Bmean ++= rdd.collect()
    }
    val Bvariance = new ArrayBuffer[Double]();
    variance.foreachRDD{rdd =>
      Bvariance.clear()
      Bvariance ++= rdd.collect()
    }

    val check = data.map(info => {
      val botton = Bmean(0) - (3 * Math.pow(Bvariance(0), 0.5))
      val top = Bmean(0) + (3 * Math.pow(Bvariance(0), 0.5))
      if(info.toDouble > botton && info.toDouble < top){
        "Normal"
      }
      else{
        "Warning"
      }
    })

    val num = new ArrayBuffer[Long]();

    values.count.foreachRDD{rdd =>
      num.clear()
      num ++= rdd.collect()
    }
    val adjustment = check.map(info => {
      if(num(0) <= 5){
        "Normal"
      }
      else{
        info
      }
    })

    adjustment.print
*/
    ssc.start()
    ssc.awaitTermination()
  }//main


  // TimeStamp Create Function
  def Get_timestamp:String = {
    val seed = Calendar.getInstance().getTime()
    val stamp_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = stamp_format.format(seed)
    return date
  }

}//normal_distribution
