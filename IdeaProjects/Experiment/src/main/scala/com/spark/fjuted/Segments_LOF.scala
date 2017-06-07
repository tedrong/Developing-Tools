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
  * Created by rong on 5/21/17.
  */
object Segments_LOF {
  Logger.getLogger("org.apache.spark").setLevel(Level.OFF)

  def main(args: Array[String]): Unit ={
    val conf = new SparkConf().setMaster("local[*]").setAppName("LOF")
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
    val k_nearest = segment_size

    values.foreachRDD { rdd =>
      segment.clear()
      segment ++= rdd.collect()
      // LOF Dimention
      if (segment.size == segment_size) {
        // How many segments in training data list
        if (trainlist.size > trainlist_size) {
          val jul: java.util.List[Array[Double]] = trainlist
          val model = new LOF(jul)

          val flag = model.getScore(segment.toArray, k_nearest)
          if(flag > 2){
            //println("Outlier found, LOF score: " + flag)
            println("source " + segment.mkString(",") + " LOF Score: " + flag + " Warning")
          }
          else {
            //println("Normal, LOF score: " + flag)
            trainlist.append(segment.toArray)
            trainlist.remove(0)
            println("source " + segment.mkString(",") + " LOF Score: " + flag + " Normal")
          }
        }//trainlist_size
        else{
          // Not enough simple, keep learning
          trainlist.append(segment.toArray)
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
