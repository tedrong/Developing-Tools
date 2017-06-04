package com.spark.fjuted

//import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import org.mongodb.scala._
import com.spark.fjuted.helpers._

/**
  * Created by rong on 4/25/17.
  */
object dbtest {
  def main(args: Array[String]): Unit ={
    // Use a Connection String
    val mongoClient: MongoClient = MongoClient("mongodb://localhost:27017")
    val database: MongoDatabase = mongoClient.getDatabase("UserInfo")
    val collection: MongoCollection[Document] = database.getCollection("users")
    collection.find().first().printHeadResult()

    //println(collection)



  }//main



}//dbtest
