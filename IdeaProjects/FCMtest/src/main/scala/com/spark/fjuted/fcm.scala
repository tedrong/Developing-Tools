package com.spark.fjuted

import com.spark.fjuted.FCM_Push._
/**
  * Created by rong on 5/8/17.
  */
object fcm {
  def main(args: Array[String]): Unit ={
    val fcm = new FCM_Push

    fcm.CallFCMPush("e-XcPvmawR4:APA91bHHA8hpZcEayvMucZpnxRi5pCBv6hyyrB-wgIz3a2_I-NGaUwfTovt1CZEpWLbwW9PyqExBUxTKZLL8p83ORyHU-1HuFAUDBsx0cFAtnDwMP6BR8Qtnhnos7IMCLGorI4TufQdY")
  }
}
