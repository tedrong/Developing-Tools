package com.spark.fjuted;

import java.net.*;
import java.io.*;
import org.json.simple.JSONObject;

/**
 * Created by rong on 5/8/17.
 */
public class FCM_Push {
    public final static String AUTH_KEY_FCM = "AAAApcgqx7E:APA91bFf9bVfjzc-Bv16DZurH6Z1t7fAYfNAjySYihCM2UD02E5ibbEFkW6kLU1Hblk8rO0odxFZoQCus2X0QCR5o8bHGDCsQyzzNmNEAgNCC8iy-rFFGQbHQaqCLbtoe9lIyevxuIoH";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

    public void CallFCMPush(String DeviceToken){
        try {
            pushFCMNotification(DeviceToken);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    // userDeviceIdKey is the device id you will query from your database

    public void pushFCMNotification(String userDeviceIdKey) throws Exception{

        String authKey = AUTH_KEY_FCM; // You FCM AUTH key
        String FMCurl = API_URL_FCM;

        URL url = new URL(FMCurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization","key="+authKey);
        conn.setRequestProperty("Content-Type","application/json");

        JSONObject json = new JSONObject();
        json.put("to",userDeviceIdKey.trim());
        JSONObject info = new JSONObject();
        info.put("title", "Notificatoin Title"); // Notification title
        info.put("body", "Hello Test notification"); // Notification body
        json.put("notification", info);

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(json.toString());
        wr.flush();
        conn.getInputStream();
    }
}
