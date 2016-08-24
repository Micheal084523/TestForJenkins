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
public class HttpUtils {

    public static String sendGetMessage(String purl,Map<String,String> pparam)
    {
        String response = "";
        try {
            URL url = new URL(purl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            String cookieVal = null;
            String key=null;

            if(HomeData.getInstance().getCookies() != null)
            {
                Iterator<String> it = HomeData.getInstance().getCookies().iterator();

                while (it.hasNext())
                {
                    httpURLConnection.addRequestProperty("Cookie", it.next());
                }
            }
            /*
            for(Map.Entry<String, String> entry:pparam.entrySet()){
                httpURLConnection.setRequestProperty(entry.getKey(),entry.getValue());
            }
            */
            StringBuffer params = new StringBuffer();
            // 表单参数与get形式一样
            int count = 0;
            for(Map.Entry<String, String> entry:pparam.entrySet()){
                if(count != 0)
                {
                    params.append("&").append(entry.getKey()).append("=").append(entry.getValue());
                }
                else
                {
                    params.append(entry.getKey()).append("=").append(entry.getValue());
                    count++;
                }
            }
            byte[] bypes = params.toString().getBytes();
            httpURLConnection.getOutputStream().write(bypes);// 输入参数

            DataOutputStream outputStream = new DataOutputStream(httpURLConnection.getOutputStream());


// what should I write here to output stream to post params to server ?

            outputStream.flush();
            outputStream.close();



            // get response
            InputStream responseStream = new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));
            String line = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = responseStreamReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            responseStreamReader.close();

            List<String> lsString = new ArrayList<String>();

            for (int i = 1; (key = httpURLConnection.getHeaderFieldKey(i)) != null; i++ ) {
                if (key.equalsIgnoreCase("set-cookie")) {
                    cookieVal = httpURLConnection.getHeaderField(i);
                    cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));
                    lsString.add(cookieVal);
                    // sessionId=sessionId+cookieVal+";";
                    Log.d(TAGUtils.TAG_LOGD,"--->cookie:"+ cookieVal);
                }
            }
            HomeData.getInstance().setCookies(lsString);
            response = stringBuilder.toString();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return response;
    }
    public static String sendPostMessage(String purl,Map<String,String> pparam)
    {
        String response = "";
        try {
            URL url = new URL(purl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
            httpURLConnection.setRequestProperty("contentType", "utf-8");

            for(Map.Entry<String, String> entry:pparam.entrySet()){
                httpURLConnection.setRequestProperty(entry.getKey(),entry.getValue());
            }



            DataOutputStream outputStream = new DataOutputStream(httpURLConnection.getOutputStream());

// what should I write here to output stream to post params to server ?

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

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    return response;

    }
}
