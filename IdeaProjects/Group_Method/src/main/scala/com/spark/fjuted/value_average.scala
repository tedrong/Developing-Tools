package com.spark.fjuted

import java.text.SimpleDateFormat
import java.util.Calendar

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming._

import scala.collection.JavaConversions._
import scala.collection.mutable._

/**
  * Created by rong on 5/27/17.
  */
object value_average {
  Logger.getLogger("org.apache.spark").setLevel(Level.OFF)

  def main(args: Array[String]): Unit ={
    val conf = new SparkConf().setMaster("local[*]").setAppName("value_average")
    val ssc = new StreamingContext(conf, Seconds(1))


    val lines = ssc.socketTextStream("localhost", 9999)

    val data = lines.window(Seconds(8), Seconds(6))
    val values = data.map(info => info.toDouble)
    //val values = data.flatMap(_.split(',').take(2).drop(1)).map(info => info.toDouble)

    type row = ArrayBuffer[Double]
    var segment = new row
    val trainlist = new ArrayBuffer[Double]
    val segment_size = 8
    val trainlist_size = 21
    //val k_nearest = 6

    values.foreachRDD { rdd =>
      segment.clear()
      segment ++= rdd.collect()
      // How many values in a group
      if (segment.size == segment_size) {
        // How many segments in training data list
        if (trainlist.size >= trainlist_size) {
          val data_point = segment.sum/segment.length
          val group_average = trainlist.sum/trainlist.length
          var square_sum = 0.0
          var count = 0

          // Counting σ^2
          for(count <- 0 until segment.length){
            square_sum = square_sum + math.pow(trainlist(count) - group_average, 2)
          }//for

          val standard = math.sqrt(square_sum/trainlist.length)

          val flag_UpEvent:Boolean = data_point > group_average + 5 * standard
          val flag_DownEvent:Boolean = data_point < group_average - 5 * standard


          if(flag_UpEvent){
            //println("Outlier found, LOF score: " + flag)
            println("source " + segment.mkString(",") + " Up_Warning")
          }
          else if(flag_DownEvent){
            //println("Outlier found, LOF score: " + flag)
            println("source " + segment.mkString(",") + " Down_Warning")
          }
          else {
            //println("Normal, LOF score: " + flag)
            println("source " + segment.mkString(",") + " Normal")
            trainlist.append(data_point)
            trainlist.remove(0)
          }

        }//trainlist_size
        else{
          // Not enough simple, keep learning
          //println(segment)
          val average = segment.sum/segment.length
          trainlist.append(average)
          println("Learning, segments in list: " + trainlist.size)
          //println("In th trainlist: " + trainlist)
        }//else_trainlist_size
      }//segment_size
    }//foreachRDD

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

}
