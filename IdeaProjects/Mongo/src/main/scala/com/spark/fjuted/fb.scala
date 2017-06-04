package com.spark.fjuted

import facebook4j.FacebookFactory
import facebook4j.auth.AccessToken
import net.liftweb.json.JsonAST
import net.liftweb.json.JsonAST.{JField, JObject, JString}

import scalaj.http.{Http, HttpOptions}
import play.api.libs.json
import net.liftweb.json.JsonAST
import net.liftweb.json.JsonAST.{JField, JObject, JString}
import net.liftweb.json.Printer._
import org.apache.log4j.PropertyConfigurator

/**
  * Created by rong on 5/8/17.
  */
object fb {
  def main(args: Array[String]): Unit = {

    val facebook = new FacebookFactory().getInstance()
    facebook.setOAuthAppId("", "");
    val accesstoken = "EAACEdEose0cBALYr3My5VyJVFWJ5ZAyZCvLsbInNK0kAQOHZBFyIDVVuTaqB8H3EM8VjJ853Bknd9XySLING8B9Sp0MqduKyMwVv7ookXXMJRmfLKBnfaDjDdTccSP29dRzwSuWj9gvgO1rkcTAz4PHRvrWekwZANM70Uvsv3oHeZANeKcCehO4ObTTLmdDwZD"
    val at = new AccessToken(accesstoken)
    facebook.setOAuthAccessToken(at)

    facebook.postStatusMessage("yirom Facebook4J.");

    /*
    //var csvString = fb_friendLists.toString
    //val postMessage = friendlist_msg.toString + System.currentTimeMillis.toString
    val postMessage = "test"
    val fbjsondata = JObject(List(JField("message", JString(postMessage))))
    val stringifyJSON = pretty(JsonAST.render(fbjsondata))

    println(stringifyJSON)

    Http("https://graph.facebook.com/v2.9/me/feed?access_token=" +
    "EAACEdEose0cBAAeVVUGhUwDxpWRbamqE2yKwBIG7FyEInO0RlhvfV2RV5swxgszK7UQouUHBZCOqJfFjIraIfWi9EmSmk4ZA0yu4BRZC53ZBGbar5d80DTgpUGzsPSQo5STZAWtGl4yhihKmvZBJu5HHUbB0yM6QyCLuZBZAdm8ykO1Fuz1jHv4Sna8qkue74RcZD"
    ).postData(stringifyJSON)
      .header("Content-Type", "application/json")
      .header("Charset", "UTF-8")
      .option(HttpOptions.readTimeout(10000))
      .asString
    */
  }
}
