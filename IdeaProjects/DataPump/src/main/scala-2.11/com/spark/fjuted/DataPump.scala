package com.spark.fjuted

import java.io.{PrintWriter}
import java.net.ServerSocket
import scala.io.Source

/**
  * Created by root on 4/10/17.
  */

object DataPump {
  def main(args: Array[String]): Unit = {
    val filename = "/home/teddy/Desktop/Master_Programs/PycharmProjects/html/mydata.txt"
    val lines = Source.fromFile(filename).getLines.toList
    val filerow = lines.length
    println(filerow)
    println(lines)
    var index = 0

    val listener = new ServerSocket(9999)
    while(true){
      val socket = listener.accept()
      new Thread(){
        override def run = {
          println("Got client connected from: " + socket.getInetAddress)
          val out = new PrintWriter(socket.getOutputStream(), true)
          while(true){
            //Thread.sleep(args(2).toLong)
            Thread.sleep(3000)


            val content = lines(index)
            println(content)
            out.write(content + '\n')
            out.flush()
            //print("sending...")
            index += 1
            //print("sending...")
            if(index == filerow)
              index = 0
          }//while
          socket.close()
        }//run
      }.start() //thread
    }//while

  }
}



/*


// -- print text file split by space on by on version
object DataPump{
  def main(args: Array[String]): Unit ={
    val filename = "/home/rong/PycharmProjects/html/output.txt"//"/home/rong/Desktop/DataSet.txt"
    val lines = Source.fromFile(filename).getLines.mkString
    val words = lines.split('\n')
    val listener = new ServerSocket(9999)
    var index = 0

    while(true){
      val socket = listener.accept()
      new Thread(){
        override def run = {
          println("Got client connected from: " + socket.getInetAddress)
          val out = new PrintWriter(socket.getOutputStream(), true)
          while(true){
            //Thread.sleep(args(2).toLong)
            Thread.sleep(1000)
            println(words(index))
            out.write(words(index) + '\n')
            out.flush()
            index += 1
            //print("sending...")
            if(index == words.length)
              index = 0
          }//while
          socket.close()
        }//run
      }.start() //thread
    }//while
  }//main
}//DataPump

*/

/* the random file rows version---------------------------------------
object DataPump {
  def index(length: Int) = {
    import java.util.Random
    val rdm = new Random
    rdm.nextInt(length)
  }//index

  def main(args: Array[String]): Unit ={
    /*
    if(args.length != 3){
      System.err.println("Usage: <filename> <port> <millisecond>")
      System.exit(1)
    }//if
    */
    //val filename = args(0)
    val filename = "/home/rong/Desktop/DataSet.txt"
    val lines = Source.fromFile(filename).getLines.toList
    val filerow = lines.length

    //val listener = new ServerSocket(args(1).toInt)
    val listener = new ServerSocket(9999)
    while(true){
      val socket = listener.accept()
      new Thread(){
        override def run = {
          println("Got client connected from: " + socket.getInetAddress)
          val out = new PrintWriter(socket.getOutputStream(), true)
          while(true){
            //Thread.sleep(args(2).toLong)
            Thread.sleep(1000)
            val content = lines(index(filerow))
            println(content)
            out.write(content + '\n')
            out.flush()
            //print("sending...")
          }//while
          socket.close()
        }//run
      }.start() //thread
    }//while
  }//main
}//DataPump
*/