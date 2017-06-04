package com.spark.fjuted

import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.log4j.{Level, Logger}

/**
  * Created by root on 4/13/17.
  */
object hello {
  def main(args: Array[String]) {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

    val conf = new SparkConf().setMaster("spark://i7-dorm:7077").setAppName("hello_test")
    val ssc = new StreamingContext(conf, Seconds(3))
    val lines = ssc.socketTextStream("192.168.0.10", 9999)

    val data = lines.flatMap(_.split(' '))
      .map(s => (s, if(s.toInt>20){
        println("normal")
        "Y"
      }
      else{
        println("er")
        "N"
      }
      )
      )
    data.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
