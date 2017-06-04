package com.spark.fjuted

import org.apache.log4j.{Level, Logger}

/**
  * Created by rong on 5/3/17.
  */
object gmail_test {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  def main(args: Array[String]): Unit ={
    val mail = new Gmail

    mail.setSender("fjutedrong@gmail.com", "virus9513")
    mail.setReceiver("tedroung@gmail.com")
    mail.Send_Email("Abnormal", "SensorID, type, data...")
  }
}
