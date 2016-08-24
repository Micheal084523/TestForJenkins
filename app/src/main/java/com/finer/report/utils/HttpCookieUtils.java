package com.finer.report.utils;

import android.util.Log;
import com.finer.com.finer.report.data.HomeData;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by finereport07 on 2016/8/22.
 */
public class HttpCookieUtils {
    public static String sendGetMessage(String purl,Map<String,String> pparam,List<String> cookies) {
        String response = "";
        try {
            URL url = new URL(purl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
            httpURLConnection.setRequestProperty("contentType", "utf-8");

            if (cookies != null) {
                Iterator<String> it = cookies.iterator();
                while (it.hasNext()) {
                    httpURLConnection.addRequestProperty("Cookie", it.next());
                }
            }

            Log.d(TAGUtils.TAG_LOGD,"--->End with Cookies");

            StringBuffer params = new StringBuffer();
            int count = 0;
            for (Map.Entry<String, String> entry : pparam.entrySet()) {
                if (count != 0) {
                    params.append("&").append(entry.getKey()).append("=").append(entry.getValue());
                } else {
                    params.append(entry.getKey()).append("=").append(entry.getValue());
                    count++;
                }
            }



            byte[] bypes = params.toString().getBytes();
            httpURLConnection.getOutputStream().write(bypes);// 输入参数

            Log.d(TAGUtils.TAG_LOGD,"--->End with params");

            DataOutputStream outputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            outputStream.flush();
            outputStream.close();

            // get response
            InputStream responseStream = new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream,"GBK"));
            String line = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = responseStreamReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            responseStreamReader.close();
            response = stringBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}
