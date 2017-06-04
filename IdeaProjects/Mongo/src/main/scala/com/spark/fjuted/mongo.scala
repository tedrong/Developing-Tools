package com.spark.fjuted

import com.mongodb.casbah.MongoClient
import com.mongodb.casbah.commons.MongoDBObject

/**
  * Created by rong on 5/7/17.
  */
object mongo {
  def main(args: Array[String]): Unit = {
    val mongoClient = MongoClient("localhost", 27017)
    val db = mongoClient("Anomalies")
    val coll = db("user")

    db.collectionNames


    val obj = MongoDBObject(
      "Cell" -> MongoDBObject(
        "userid" -> "610421213", "time" -> "00:00"
      )
    )
    coll.insert(obj)
  }
}
