package com.spark.fjuted

import java.util
import collection.JavaConversions._
import collection.mutable._
import java.text.SimpleDateFormat
import java.util.Calendar

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming._

/**
  * Created by teddy on 6/4/17.
  */
object SD_Average_LOF {
  Logger.getLogger("org.apache.spark").setLevel(Level.FATAL)

  def main(args: Array[String]): Unit ={
    val conf = new SparkConf().setMaster("local[*]").setAppName("Normal_Distribution")
    val ssc = new StreamingContext(conf, Seconds(1))


    val lines = ssc.socketTextStream("localhost", 9999)

    val data = lines.window(Seconds(8), Seconds(1))
    val values = data.map(info => info.toDouble)
    //val values = data.flatMap(_.split(',').take(2).drop(1)).map(info => info.toDouble)

    type row = ArrayBuffer[Double]
    var segment = new row
    val trainlist = new ArrayBuffer[Array[Double]]
    val segment_size = 8
    val trainlist_size = 120
    val k_nearest = 1


    values.foreachRDD { rdd =>
      segment.clear()
      segment ++= rdd.collect()
      // LOF Dimention
      if (segment.size == segment_size) {
        // How many segments in training data list
        if (trainlist.size > trainlist_size) {
          val jul: java.util.List[Array[Double]] = trainlist
          val model = new LOF(jul)

          val average = segment.sum/segment.length
          var temp_sum = 0.0
          var count = 0
          for(count <- 0 until segment.length-1){
            temp_sum = temp_sum + math.pow(segment(count) - average, 2)
          }//for
          val SD = math.sqrt(temp_sum/segment.length)
          val array = Array(average, SD)


          val flag = model.getScore(array, k_nearest)

          if(flag > 2){
            //println("Outlier found, LOF score: " + flag)
            println("source " + segment.mkString(",") + " Warning")
          }
          else {
            trainlist.append(array)
            trainlist.remove(0)
            //println("Normal, LOF score: " + flag)
            println("source " + segment.mkString(",") + " Normal")
          }
        }//trainlist_size
        else{
          // Not enough simple, keep learning
          val average = segment.sum/segment.length
          var temp_sum = 0.0
          var count = 0
          for(count <- 0 until segment.length-1){
            temp_sum = temp_sum + math.pow(segment(count) - average, 2)
          }//for
          val SD = math.sqrt(temp_sum/segment.length)
          val array = Array(average, SD)

          //println(array.mkString("  "))
          trainlist.append(array)
          println("Learning, segments in list: " + trainlist.size)
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
