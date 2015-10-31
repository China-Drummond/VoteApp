package com.example.drummond.voteapp;

/**
 * Created by Drummond on 2015/10/27.
 */


import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpRequest;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class HttpUtil {

    public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);

                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    Message message = new Message();
                    message.what = 0;
                    message.obj = response.toString();


                    if (listener != null) {
                        listener.onFinish(response.toString());
                    }

                }catch (Exception e){
                    if (listener != null){
                        listener.onError(e);
                    }

                }finally {
                    if (connection != null){
                        //connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static void sendHttpRequest(final String address,final String data,final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);

                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    out.writeBytes(data);

                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    Message message = new Message();
                    message.what = 0;
                    message.obj = response.toString();


                    if (listener != null) {
                        listener.onFinish(response.toString());
                    }

                }catch (Exception e){
                    if (listener != null){
                        listener.onError(e);
                    }

                }finally {
                    if (connection != null){
                        //connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public interface HttpCallbackListener{

        void onFinish(String response);
        void onError(Exception e);
    }
}
