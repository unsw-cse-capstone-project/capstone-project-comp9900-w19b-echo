package com.echo.backend.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class GoogleMapUtil {

    private static String apiKey = "AIzaSyACpbsx-Xm4re7zghBNqaZ1zoTfLCeD1i8";

    public static String getLocation(String addr) {
        // get address
        String queryUrl = "https://maps.googleapis.com/maps/api/geocode/json?address="+addr+"&key=" + apiKey + "&language=en";

        System.out.println(queryUrl);
        String queryResult = getResponse(queryUrl);

        JSONObject object = JSONObject.parseObject(queryResult);
        JSONArray array = JSONObject.parseArray(object.get("results").toString());
        JSONObject address = array.getJSONObject(0);
//        System.out.println(address);
        String formattedAddress = address.getString("formatted_address");
//        System.out.println(formattedAddress);
        JSONObject location = address.getJSONObject("geometry").getJSONObject("location");
        String lng = location.getString("lng");
        String lat = location.getString("lat");
//        System.out.println(lng);
//        System.out.println(lat);

        return formattedAddress+":"+lng+","+lat;
    }

    private static String getResponse(String serverUrl) {

        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(serverUrl);
            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
