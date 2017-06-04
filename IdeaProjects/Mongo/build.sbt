name := "Mongo"

version := "1.0"

scalaVersion := "2.11.8"

val json4sNative = "org.json4s" %% "json4s-native" % "{latestVersion}"
val json4sJackson = "org.json4s" %% "json4s-jackson" % "{latestVersion}"
libraryDependencies += "org.json4s" % "json4s-native_2.11" % "3.5.2"
libraryDependencies += "org.json4s" % "json4s-jackson_2.11" % "3.5.2"


libraryDependencies += "org.apache.spark" %% "spark-core" % "2.1.0"

libraryDependencies += "org.mongodb" %% "casbah" % "3.1.1"


libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.3.0"
libraryDependencies += "com.typesafe.play" % "play_2.11" % "2.5.14"

libraryDependencies += "net.liftweb" % "lift-webkit_2.10" % "2.6.2"
libraryDependencies += "org.kuali.common" % "kuali-jdbc" % "3.1.11"

libraryDependencies += "org.facebook4j" % "facebook4j-core" % "2.4.9"