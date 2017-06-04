package com.spark.fjuted

import scala.collection.mutable._

/**
  * Created by rong on 5/18/17.
  */
object test {
  def main(args: Array[String]): Unit ={

    val queue = new Queue[Double]()
    val window_size = 3

    queue += 23
    queue += 22
    queue += 28

    if(queue.length == window_size){
      var loop = 0
      var sum = 0.0
      for(loop <- 0 until window_size){
        sum = sum + queue(loop)
      }//for
      val average = sum / window_size
      println(average)

      sum = 0
      for(loop <- 0 until window_size){
        sum = sum + Math.pow((queue(loop)-average), 2)
      }//for
      val sd = Math.sqrt(sum/window_size-1)
      println(sd)

    }//if



    /*
    queue += 1.123
    queue += 2.123
    queue += 3.123
    queue += 4.123
    queue += 5.123

    println(queue)
    println(queue.length)
    queue.dequeue()
    println(queue)

    println(queue(3))
    queue.clear()
    println(queue)

*/


  }
}
