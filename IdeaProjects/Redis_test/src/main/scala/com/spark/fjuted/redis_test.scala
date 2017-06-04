package com.spark.fjuted

import com.redis._
import java.text.SimpleDateFormat
import java.util.Calendar
/**
  * Created by rong on 5/3/17.
  */
object redis_test {
  def main(args: Array[String]): Unit ={


    val UserEmail = "user"
    val SensorID = "sensorid"
    val Begin = "2017-05-01"
    val End = "2017-05-02"
    val Method = "avg"

    var result: List[Int] = Nil
    val redis = new RedisClient("localhost", 6379)


    val format = new SimpleDateFormat("yyyy-MM-dd")
    val startDate = format.parse(Begin)
    val endDate = format.parse(End)

    val start = Calendar.getInstance
    start.setTime(startDate)

    val end = Calendar.getInstance
    end.setTime(endDate)

    var seed = start.getTime()
    var date = format.format(seed)

    //start.add(Calendar.DATE, -1)
    while ( {!start.after(end)}) {
      //println(date)

      val temp = redis.hvals(SensorID + "/" + date)
      val list = temp.get
      val arr = list.map(info => info.toInt)
      result = List.concat(result, arr)

      //println(result)

      start.add(Calendar.DATE, 1)
      seed = start.getTime()
      date = format.format(seed)
    }//while


    val num = result.length
    val sum = result.sum

    val average = sum / num
    val max = result.max
    val min = result.min

    println(num)
    println(sum)
    println(average)


  }//main
}//redis_test
