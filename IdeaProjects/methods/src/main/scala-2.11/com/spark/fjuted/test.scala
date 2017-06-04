package com.spark.fjuted

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
// $example on$
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.stat.{MultivariateStatisticalSummary, Statistics}
// $example off$

object SummaryStatisticsExample {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local[*]").setAppName("SummaryStatisticsExample")
    val sc = new SparkContext(conf)

    // $example on$
    val observations = sc.parallelize(
      Seq(
        Vectors.dense(600.0),
        Vectors.dense(470.0),
        Vectors.dense(170.0),
        Vectors.dense(430.0),
        Vectors.dense(300.0)
      )
    )

    // Compute column summary statistics.
    val summary: MultivariateStatisticalSummary = Statistics.colStats(observations)
    println(summary.mean)  // a dense vector containing the mean value for each column
    println(summary.variance)  // column-wise variance
    println(summary.numNonzeros)  // number of nonzeros in each column
    val temp = summary.variance.toArray
    println(Math.pow(temp(0),0.5))
    // $example off$

    sc.stop()
  }
}