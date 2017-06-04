name := "methods"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.apache.spark" % "spark-streaming_2.11" % "2.1.0"
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.1.0"
libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "1.0.1"
libraryDependencies += "org.apache.spark" %% "spark-mllib" % "2.1.0"
libraryDependencies += "org.apache.bahir" %% "spark-streaming-mqtt" % "2.1.0"
libraryDependencies += "org.eclipse.paho" % "org.eclipse.paho.client.mqttv3" % "latest.integration"