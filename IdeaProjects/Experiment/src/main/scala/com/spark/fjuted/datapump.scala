package com.spark.fjuted

import java.io.{PrintWriter}
import java.net.ServerSocket
import scala.io.Source

/**
  * Created by rong on 5/17/17.
  */
object datapump {
  def main(args: Array[String]): Unit = {
    val filename = "/home/rong/Git/Developing-Tools/experiment_data/real"
    val lines = Source.fromFile(filename).getLines.toList
    val filerow = lines.length
    println(filerow)
    println(lines)
    var index = 0

    val listener = new ServerSocket(9999)

    var break = false
    while(!break){
      val socket = listener.accept()
      new Thread(){
        override def run = {
          println("Got client connected from: " + socket.getInetAddress)
          val out = new PrintWriter(socket.getOutputStream(), true)
          while(!break){
            //Thread.sleep(args(2).toLong)
            Thread.sleep(1000)


            val content = lines(index)
            println(content)
            out.write(content + '\n')
            out.flush()
            index += 1
            if(index == filerow)
              break = true
          }//while
          socket.close()
        }//run
      }.start() //thread
    }//while
    println("End of file...")
  }//main
}//datapump
